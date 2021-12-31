package adduserclass;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import clientapi.Client;
import gui.AdministratorFrame;
import dataprocessing.DataProcessing;
import sun.rmi.server.Activation$ActivationSystemImpl_Stub;

import javax.swing.*;

import static filesystem.FileSystem.NotConnectedToDatabase;

/**
 * feature 档案管理员类,对所有人的信息进行统一管理，继承了抽象用户类
 * 
 * @author 86134
 * @data 2021/11/19
 */
public class Administrator extends AbstractUser {
	public Administrator(String name, String password, String role) {
		super(name, password, role);
	}

	@Override
	public void showMenu() {
		new AdministratorFrame(this);

		System.out.print("进入管理管界面");

		return;
	}

	/**
	 * feature 根据输入信息修改用户的信息
	 *
	 * @throws SQLException
	 */
	public void changeUserInfo(JPanel panel) {
		panel.setLayout(new GridLayout(0, 1));
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
		while (true) {
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
		if (nameBox.getSelectedIndex() != 0) {
			roleBox.setSelectedIndex(0);
		}
		panel3.add(roleLabel);
		panel3.add(roleBox);
		panel.add(panel3);
		/**
		 * 每次都要new一个，不然只能显示最后一个
		 */
		JPanel panel4 = new JPanel();
		JButton buttonYes = new JButton("确定");
		JButton buttonNo = new JButton("取消");
		panel4.add(buttonYes);
		panel4.add(buttonNo);
		panel.add(panel4);
		panel.add(new JPanel());
		buttonNo.addActionListener(e -> {
			panel.getParent().getParent().getParent().getParent().getParent().setVisible(false);
		});
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
					JOptionPane.showConfirmDialog(buttonYes, "密码错误!", "警告", JOptionPane.OK_CANCEL_OPTION);
					System.out.println("输入错误！请重新处理。");
					return;
				}
				//更新本机上的数据，这样如果修改的是自己的信息才可以在修改个人信息模块上进行及时显示。也可每次通过从数据库上获得用户信息来进行，但是开销大。
				if(name.equals(this.getName())){
					this.setName(name);
					this.setPassword(password);
					this.setRole(role);
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

			JOptionPane.showConfirmDialog(buttonYes, "修改成功!", "提示", JOptionPane.OK_CANCEL_OPTION);
		});

	}

	/**
	 * 这里的delAbstractUser显示未被使用是因为没有直接调用，但是通过了仿射来调用
	 *
	 * @param panel
	 * @return
	 * @throws NullPointerException
	 */
	public boolean delAbstractUser(JPanel panel) throws NullPointerException {
		panel.setLayout(new BorderLayout());
		JPanel panel1 = new JPanel();
		listAbstractUser(panel1);
		panel.add(panel1, BorderLayout.CENTER);
		JPanel panel2 = new JPanel();
		JButton buttonYes = new JButton("确定");
		JButton buttonNo = new JButton("取消");
		panel2.add(buttonYes);
		panel2.add(buttonNo);
		panel.add(panel2, BorderLayout.SOUTH);

		buttonNo.addActionListener(e -> {
			panel.getParent().getParent().getParent().getParent().getParent().setVisible(false);
		});
		buttonYes.addActionListener(actionListener -> {
			/**
			 * 获得JTable组件
			 */
			JPanel getPanel = (JPanel) panel.getComponent(0);
			int count = getPanel.getComponentCount();
			JTable tableData = null;
			for (int i = 0; i < count; i++) {
				Object component = getPanel.getComponent(i);
				if (component instanceof JScrollPane) {
					JScrollPane scrollPane = ((JScrollPane) component);
					tableData = (JTable) scrollPane.getViewport().getComponent(0);
				}
			}
			int selectedRow = tableData.getSelectedRow();

			String name = tableData.getValueAt(selectedRow, 0).toString();
			if(name.equals(this.getName())){
				System.out.println("不能删除自己！");
				JOptionPane.showConfirmDialog(null, "不能删除自己!", "警告", JOptionPane.OK_CANCEL_OPTION);
				return;
			}
			try {
				DataProcessing.deleteUser(name);
			} catch (SQLException sqlE) {
				//有bug
				System.out.println(sqlE.getMessage());
				System.out.println("Please do it against.");
				JOptionPane.showConfirmDialog(buttonYes, "Please do it against.", "警告", JOptionPane.OK_CANCEL_OPTION);
				if (NotConnectedToDatabase.equals(sqlE.getMessage())) {
					DataProcessing.init();
				}
				return;
			}
			System.out.print("删除的用户成功");
			JOptionPane.showConfirmDialog(buttonYes, "删除用户成功", "提示", JOptionPane.OK_CANCEL_OPTION);		panel.removeAll();
			delAbstractUser(panel);
		});


		return true;
	}

	/**
	 * 这里通过仿射来调用函数
	 * @param panel
	 * @return
	 */
	public boolean addAbstractUser(JPanel panel) {
		panel.setLayout(new GridLayout(0, 1));
		panel.add(new JPanel());
		JPanel panel1 = new JPanel();
		Label nameLabel = new Label("用户名");
		JTextField nameField = new JTextField("", 20);
		panel1.add(nameLabel);
		panel1.add(nameField);
		panel.add(panel1);
		Label passwordLabel = new Label("密码");
		JPasswordField passwordField = new JPasswordField("", 20);
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

		buttonNo.addActionListener(e -> {
			panel.getParent().getParent().getParent().getParent().getParent().setVisible(false);
		});
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
				JOptionPane.showConfirmDialog(buttonYes, "增加用户成功", "提醒", JOptionPane.OK_CANCEL_OPTION);
			} catch (SQLException sqlE) {
				/**
				 * TODO 有bug,解决:有再抛出异常给父框架处理
				 */
				System.out.println(sqlE.getMessage());
				System.out.println("Please do it against.");
				JOptionPane.showConfirmDialog(buttonYes, "增加失败,请重新输入!", "警告", JOptionPane.OK_CANCEL_OPTION);
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

	public boolean listAbstractUser(JPanel panel) {
		Object[][] tableData = new Object[100][5];
		try {
			ResultSet users = DataProcessing.listUser();
			AbstractUser user = null;
			int index = 0;
			while (users.next()) {
				user = DataProcessing.newUser(users);

				tableData[index][0] = user.getName();
				tableData[index][1] = user.getPassword();
				tableData[index][2] = user.getRole();

				index++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String[] tableName = {"用户名", "密码", "身份"};
		JTable fileTable = new JTable(tableData, tableName);
		panel.add(new JScrollPane(fileTable));
		try {
			showFileList();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("The problem has been solved.Please input the selector against.");
			//这里的buttonYes能不能换成panel,为什么。这里也不能把注释的语句加进去。
			JOptionPane.showConfirmDialog(null,"The problem has been solved.Please input the selector against.","警告",JOptionPane.OK_CANCEL_OPTION);
			System.out.println("请重新输入!");
			JOptionPane.showConfirmDialog(null,"请重新输入!","警告",JOptionPane.OK_CANCEL_OPTION);
		}
		return true;
	}
}
