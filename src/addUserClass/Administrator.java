package adduserclass;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import com.sun.org.apache.xpath.internal.XPathAPI;
import dataprocessing.Doc;
import gui.AdministratorFrame;
import dataprocessing.DataProcessing;

import javax.swing.*;

import static filesystem.FileSystem.NotConnectedToDatabase;

/**
 * feature 档案管理员类,对所有人的信息进行统一管理，继承了抽象用户类
 * 
 * @author 86134
 * @data 2021/11/19
 */
public class Administrator extends AbstractUser {
	public Administrator(String name,String password,String role) {
		super(name,password,role);
	}
	@Override
	public void showMenu() {
		JFrame mainFrame = new AdministratorFrame(this);
		mainFrame.setVisible(true);


		Integer selector = 0;

		final String[] allLine = {"**************欢迎进入管理员菜单**********************\n",
								  "\t\t\t1、显示文件列表\n",
								  "\t\t\t2、下载文件\n",
								  "\t\t\t3、修改个人信息\n",
								  "\t\t\t4、修改用户信息\n",
								  "\t\t\t5、删除用户\n",
								  "\t\t\t6、增加用户\n",
								  "\t\t\t7、显示所有用户\n",
								  "\t\t\t8、退出\n",
								  "*****************************************************\n"
		};
		StringBuilder surfaceBuilder = new StringBuilder();
		for(String s:allLine) {
			surfaceBuilder.append(s);
		}
		String surface = surfaceBuilder.toString();
		System.out.print(surface);


		System.out.print("请输入数字进行选择:");
		switch(selector) {
			case 1:
				try{
					super.showFileList();break;
				}catch(SQLException e) {
					System.out.println(e.getMessage());
					System.out.println("请重新输入!");
					break;
				}
			case 2:
				this.downloadFile(null);break;
			case 3:
				try {
					super.changeSelfInfo(super.getPassword());
				} catch (SQLException sqlE) {
					System.out.println(sqlE.getMessage());
					System.out.println("Please do it against.");
					if(NotConnectedToDatabase.equals(sqlE.getMessage())){
						DataProcessing.init();
					}
				}
				break;
			case 4:
				/**
				 * 这个可以另外判断
				 */
				this.changeUserInfo(null);
//				while(!this.changeUserInfo(null)){
//					System.out.println("输入错误！请重新处理。");
//				}
				break;
			case 5:
				this.delAbstractUser(null);break;
			case 6:
				this.addAbstractUser(null);break;
			case 7:
				listAbstractUser(null);break;
			case 8:
				this.exitSystem();break;
			default:
				break;
		}

		return;
	}
	
	/**
	 * feature 根据输入信息修改用户的信息
	 * 
	 * @throws SQLException
	 */
	public void changeUserInfo(JPanel panel) {
		panel.setLayout(new GridLayout(0,1));
		panel.add(new JPanel());
		Label nameLabel = new Label("用户名");
		JComboBox nameBox = new JComboBox();
		nameBox.addItem(null);
		ResultSet allUsers = null;
		try {
			allUsers = DataProcessing.listUser();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//遍历获得所有的用户
		while(true){
			try {
				if (!allUsers.next()) {
					break;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			AbstractUser user = DataProcessing.newUser(allUsers);
			nameBox.addItem(user.getName());
		}


		JPanel panel1 = new JPanel();
		panel1.add(nameLabel);
		panel1.add(nameBox);
		panel.add(panel1);
		JPanel panel2 = new JPanel();
		Label passwordNewLabel = new Label("密码");
		JPasswordField passwordNewField = new JPasswordField(20);
		panel2.add(passwordNewLabel);
		panel2.add(passwordNewField);
		panel.add(panel2);
		JPanel panel3 = new JPanel();
		Label roleLabel = new Label("身份");
		JComboBox roleBox = new JComboBox();
		roleBox.addItem("administrator");
		roleBox.addItem("browser");
		roleBox.addItem("operator");

		/**
		 * TODO 优化显示
		 */
		if(nameBox.getSelectedIndex()!=0){
			roleBox.setSelectedIndex(0);
		}
		panel3.add(roleLabel);
		panel3.add(roleBox);
		panel.add(panel3);
		/**
		 * 每次都要new一个，不然只能显示最后一个
		 */
		JPanel panel4 = new JPanel();
		JButton buttonYes  = new JButton("确定");
		JButton buttonNo = new JButton("取消");
		panel4.add(buttonYes);
		panel4.add(buttonNo);
		panel.add(panel4);
		panel.add(new JPanel());
		buttonYes.addActionListener(actionEvent -> {
				try {
					String name, password, role;

					System.out.print("请输入更新的用户名:");
					name = nameBox.getSelectedItem().toString();
					System.out.print("请输入更新的密码:");
					password = new String(passwordNewField.getPassword());
					System.out.print("请输入更新的角色:");
					role = roleBox.getSelectedItem().toString();

					if (!DataProcessing.updateUser(name, password, role)) {
						JOptionPane.showConfirmDialog(buttonYes,"密码错误!","警告",JOptionPane.OK_CANCEL_OPTION);
						System.out.println("输入错误！请重新处理。");
						return;
					}
				} catch (SQLException sqlE) {
					System.out.println(sqlE.getMessage());
					System.out.println("Please do it against.");
					if (NotConnectedToDatabase.equals(sqlE.getMessage())) {
						DataProcessing.init();
					}
				}
				nameBox.setSelectedIndex(0);
				passwordNewField.setText("");
				roleBox.setSelectedIndex(0);
				JOptionPane.showConfirmDialog(buttonYes,"修改!","提示",JOptionPane.OK_CANCEL_OPTION);
		});

	}
	
	public boolean delAbstractUser(JPanel panel) throws NullPointerException{
		panel.setLayout(new BorderLayout());
		JPanel panel1 = new JPanel();
		listAbstractUser(panel1);
		panel.add(panel1,BorderLayout.CENTER);
		JPanel panel2 = new JPanel();
		JButton buttonYes = new JButton("确定");
		JButton buttonNo = new JButton("取消");
		panel2.add(buttonYes);
		panel2.add(buttonNo);
		panel.add(panel2,BorderLayout.SOUTH);

		buttonYes.addActionListener(actionListener -> {
			/**
			 * 获得JTable组件
			 */
			JPanel getPanel = (JPanel) panel.getComponent(0);
			int count = getPanel.getComponentCount();
			JTable tableData = null;
			for(int i = 0;i < count;i++) {
				Object component = getPanel.getComponent(i);
				if(component instanceof JScrollPane) {
					JScrollPane scrollPane = ((JScrollPane) component);
					tableData = (JTable) scrollPane.getViewport().getComponent(0);
				}
			}
			int selectedRow = tableData.getSelectedRow();

			System.out.print("请输入将删除的用户名：");
			JOptionPane.showConfirmDialog(buttonYes,"删除用户成功","警告",JOptionPane.OK_CANCEL_OPTION);
			String name = tableData.getValueAt(selectedRow, 0).toString();
			try {
				DataProcessing.deleteUser(name);
			}catch (SQLException sqlE){
				/**
				 * TODO 有bug
				 */
				System.out.println(sqlE.getMessage());
				System.out.println("Please do it against.");
				JOptionPane.showConfirmDialog(buttonYes,"Please do it against.","警告",JOptionPane.OK_CANCEL_OPTION);
				if(NotConnectedToDatabase.equals(sqlE.getMessage())){
					DataProcessing.init();
				}
				return;
			}
			panel.removeAll();
			delAbstractUser(panel);
		});



		return true;
	}
	
	public boolean addAbstractUser(JPanel panel) {
		panel.setLayout(new GridLayout(0,1));
		panel.add(new JPanel());
		JPanel panel1 = new JPanel();
		Label nameLabel = new Label("用户名");
		JTextField nameField = new JTextField("",20);
		panel1.add(nameLabel);
		panel1.add(nameField);
		panel.add(panel1);
		Label passwordLabel = new Label("密码");
		JPasswordField passwordField = new JPasswordField("",20);
		JPanel panel2 = new JPanel();
		panel2.add(passwordLabel);
		panel2.add(passwordField);
		panel.add(panel2);
		Label roleLabel = new Label("身份");
		JComboBox roleBox = new JComboBox();
		roleBox.addItem("browser");
		roleBox.addItem("operator");
		roleBox.addItem("administrator");
		JPanel panel3 = new JPanel();
		panel3.add(roleLabel);
		panel3.add(roleBox);
		panel.add(panel3);
		JButton buttonYes = new JButton("确定");
		JButton buttonNo = new JButton("取消");
		JPanel panel4 = new JPanel();
		panel4.add(buttonYes);
		panel4.add(buttonNo);
		panel.add(panel4);
		panel.add(new JPanel());

		buttonYes.addActionListener(actionListener -> {
			String role = "";
			String password = "";
			String name = "";

			System.out.print("请输入名字：");
			name = nameField.getText();
			System.out.print("请输入密码：");
			password = new String(passwordField.getPassword());
			System.out.print("请输入角色：");
			role = roleBox.getSelectedItem().toString();

			try {
				DataProcessing.insertUser(name, password, role);
				JOptionPane.showConfirmDialog(buttonYes,"增加用户成功","提醒",JOptionPane.OK_CANCEL_OPTION);
			} catch (SQLException sqlE) {
				/**
				 * TODO 有bug,解决:有再抛出异常给父框架处理
				 */
				System.out.println(sqlE.getMessage());
				System.out.println("Please do it against.");
				JOptionPane.showConfirmDialog(buttonYes,"增加失败,请重新输入!","警告",JOptionPane.OK_CANCEL_OPTION);
				if (NotConnectedToDatabase.equals(sqlE.getMessage())) {
					DataProcessing.init();
				}
			}
			//将内容恢复,增加安全
			nameField.setText("");
			passwordField.setText("");
			roleBox.setSelectedIndex(0);
		});

		return true;
	}

	public boolean listAbstractUser(JPanel panel){
		Object[][] tableData = new Object[100][5];
		try {
			ResultSet users = DataProcessing.listUser();
			AbstractUser user = null;
			int index = 0;
			while(users.next()){
				user = DataProcessing.newUser(users);

				tableData[index][0] = user.getName();
				tableData[index][1] = user.getPassword();
				tableData[index][2] = user.getRole();

				index++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String[] tableName = {"用户名","密码","身份"};
		JTable fileTable = new JTable(tableData,tableName);
		panel.add(new JScrollPane(fileTable));
		try {
			showFileList();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("The problem has been solved.Please input the selector against.");
			/**
			 * TODO 这里的buttonYes能不能换成panel,为什么。这里也不能把注释的语句加进去。
			 */
//			JOptionPane.showConfirmDialog(new Button(),"The problem has been solved.Please input the selector against.","警告",JOptionPane.OK_CANCEL_OPTION);
			System.out.println("请重新输入!");
//			JOptionPane.showConfirmDialog(new Button(),"请重新输入!","警告",JOptionPane.OK_CANCEL_OPTION);
		}
		return true;
	}

	public boolean listAbstractUser() {
		ResultSet e = null;
		try{
			e = DataProcessing.listUser();
		}catch (SQLException sqlE){
			System.out.println(sqlE.getMessage());
			return false;
		}
		while(true) {
			try {
				if (!e.next()) {
					break;
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			System.out.println(DataProcessing.newUser(e));
		}
		return true;
	}

	/**
	 * 有异常的就需要重载（为了实现GUI）
	 */
}
