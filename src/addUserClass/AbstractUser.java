package adduserclass;

import java.awt.*;
import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.File;
import java.io.IOException;

import clientapi.Client;
import dataprocessing.DataProcessing;
import dataprocessing.Doc;

import static filesystem.FileSystem.NotConnectedToDatabase;


/**
 * feature 抽象用户类，为各用户子类提供模板
 * 
 * @author 郑伟鑫
 * @date 2021/11/19
 */
public abstract class AbstractUser {
	private String name;
	private String password;
	private String role;
	private Client client;

	private JButton buttonYes = new JButton("确定");
	private JButton buttonNo = new JButton("取消");

	AbstractUser(String name, String password, String role){
		this.name=name;
		this.password=password;
		this.role=role;
	}
	public void setClient(Client client){
		this.client = client;
	}
	
	@Override
	public String toString(){
		return  "name = " + name
				+"password = " + password
				+",role = " + role
				+",client = " + client;
	}

	/**
	 * 提拱函数让子类重载
	 *
	 * @param panel
	 * @return
	 */
	public boolean changeSelfInfo(JPanel panel){
		/**
		 * 添加用户名，密码，角色，按钮组件并绑定确定按钮
		 */
		panel.setLayout(new GridLayout(0,1));
		JPanel panel1 = new JPanel();
		Label nameLabel = new Label("用户名");
		JTextField nameField = new JTextField(this.getName());
		nameField.setEditable(false);
		panel1.add(nameLabel);
		panel1.add(nameField);
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		Label passwordOldLabel = new Label("旧密码");
		Label passwordNewLabel = new Label("新密码");
		JPasswordField passwordOldField = new JPasswordField(20);
		JPasswordField passwordNewField = new JPasswordField(20);
		panel2.add(passwordOldLabel);
		panel2.add(passwordOldField);
		panel3.add(passwordNewLabel);
		panel3.add(passwordNewField);
		JPanel panel4 = new JPanel();
		Label roleLabel = new Label("身份");
		JTextField roleField = new JTextField(this.getRole());
		roleField.setEditable(false);
		panel4.add(roleLabel);
		panel4.add(roleField);
		/**
		 * 每次都要new一个，不然只能显示最后一个
		 */
		JPanel panel5 = new JPanel();
		JButton buttonYes  = new JButton("确定");
		JButton buttonNo = new JButton("取消");
		panel5.add(buttonYes);
		panel5.add(buttonNo);

		panel.add(new JPanel());
		panel.add(panel1);
		panel.add(panel2);
		panel.add(panel3);
		panel.add(panel4);
		panel.add(panel5);
		panel.add(new JPanel());

		buttonNo.addActionListener(e -> {
			panel.getParent().getParent().getParent().getParent().getParent().setVisible(false);
		});

		final boolean[] flag = {true};
		/**
		 * TODO 不能完美调用
		 */
		buttonYes.addActionListener( e->{
				flag[0] = false;
				try {
				String oldPassword ="", newPassword="";

				System.out.print("请输入更新的密码:");
				oldPassword = new String(passwordOldField.getPassword());
				if(!this.getPassword().equals(oldPassword)){
					JOptionPane.showConfirmDialog(buttonYes,"密码错误!","警告",JOptionPane.OK_CANCEL_OPTION);
					System.out.println("输入错误！请重新处理。");
					System.out.println("密码错误");
					return;
				}
				newPassword = new String(passwordNewField.getPassword());
				if (!DataProcessing.updateUser(this.getName(), newPassword, this.getRole())) {
					return;
				}
				JOptionPane.showConfirmDialog(buttonYes,"修改成功!","提示",JOptionPane.OK_CANCEL_OPTION);
			} catch (SQLException sqlE) {
				System.out.println(sqlE.getMessage());
				System.out.println("Please do it against.");
				if (NotConnectedToDatabase.equals(sqlE.getMessage())) {
					DataProcessing.init();
				}
			}
		});
		return true;
	}
	/**
	 *
	 * @param password 用户输入的验证密码
	 * @return 判断是否修改成功过
	 * @throws SQLException
	 */
	public boolean changeSelfInfo(String password) throws SQLException {
		//写用户信息到存储
		if (DataProcessing.updateUser(name, password, role)){
			this.password=password;
			JOptionPane.showConfirmDialog(buttonYes,"修改成功。","提示",JOptionPane.OK_CANCEL_OPTION);
			System.out.println("修改成功");
			return true;
		}else {
			return false;
		}
	}
	
	
	/**
	 * 展示文件的菜单，需要被实现
	 * @param 
	 * @return void
	 * @throws  
	*/
	public abstract void showMenu();

	/**
	 * 档案下载:根据档案号在数据库中查找，将文件从网络上下载，而不是从本地下载：即下载来源为服务端
	 *
	 * @return
	 */
	public boolean downloadFile(JPanel panel){
		panel.setLayout(new BorderLayout());
		//显示文件夹
		JPanel panel1 = new JPanel();
		showFileList(panel1);
		JButton buttonYes = new JButton("确定");
		JPanel panel2 = new JPanel();
		panel2.add(buttonYes);
		panel.add(panel1,BorderLayout.CENTER);
		panel.add(panel2,BorderLayout.SOUTH);
		buttonYes.addActionListener(e -> {
			JFileChooser fileChooser = new JFileChooser();
			//设置当前的所在目录
			fileChooser.setCurrentDirectory(new File("."));
			//设置可选文件和文件夹
			fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			//显示文件对话框为保存,result来保存status
			int result = fileChooser.showSaveDialog(panel);
			switch (result) {
				case JFileChooser.ERROR_OPTION:
					System.out.println("打开失败");
					break;
				case JFileChooser.CANCEL_OPTION:
					System.out.println("下载失败");
					break;
				case JFileChooser.APPROVE_OPTION:
					/**
					 * 获得JTable组件
					 */
					JPanel tempPanel = (JPanel) panel.getComponent(0);
					int count = tempPanel.getComponentCount();
					JTable tableData = null;
					for(int i = 0;i < count;i++) {
						Object component = tempPanel.getComponent(i);
						if(component instanceof JScrollPane) {
							JScrollPane scrollPane = ((JScrollPane) component);
							tableData = (JTable) scrollPane.getViewport().getComponent(0);
						}
					}
					int selectedRow = tableData.getSelectedRow();

					Doc doc = null;
					String id = tableData.getValueAt(selectedRow, 0).toString();
					try {
						doc = DataProcessing.searchDoc(id);
					} catch (SQLException sqlE) {
						System.out.println(sqlE.getMessage());
						System.out.println("Connecting to Database...");
						JOptionPane.showConfirmDialog(buttonYes, "连接数据库失败，请重新输入。", "警告", JOptionPane.OK_CANCEL_OPTION);
						System.out.println("请重新输入！");
					}
					if (doc == null) {
						JOptionPane.showConfirmDialog(buttonYes, "找不到对应的档案。", "警告", JOptionPane.OK_CANCEL_OPTION);
						System.out.println("找不到对应的档案，请重新输入:");
					}
					System.out.println("下载文件中......");
					String path = fileChooser.getSelectedFile().getPath();

					try {
						client.getFile(doc.getFilename(),path+"\\"+doc.getFilename());
					} catch (IOException ex) {
						ex.printStackTrace();
						JOptionPane.showConfirmDialog(buttonYes, "下载失败。", "警告", JOptionPane.OK_CANCEL_OPTION);
					}
					break;
				default:
					break;
			}

		});

		return true;
	}

	public void showFileList(JPanel panel){
		Object[][] tableData = new Object[100][5];
		try {
			ResultSet document = DataProcessing.listDoc();
			Doc doc = null;
			int index = 0;
			while(document.next()){
				doc = DataProcessing.newDoc(document);

				tableData[index][0] = doc.getId();
				tableData[index][1] = doc.getCreator();
				tableData[index][2] = doc.getTimestamp();
				tableData[index][3] = doc.getFilename();
				tableData[index][4] = doc.getDescription();

				index++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String[] tableName = {"档案号","创建者","时间","文件名","描述"};
		JTable fileTable = new JTable(tableData,tableName);
		panel.add(new JScrollPane(fileTable));
		try {
			showFileList();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			JOptionPane.showConfirmDialog(null,"操作失败，请重新输入!","警告",JOptionPane.OK_CANCEL_OPTION);
		}
	}
	/**
	 * 档案查询:实现按条件查询相应的档案文件信息，也可简化为展示所有档案文件信息.在未涉及数据库之前，档案信息存放在Hashtable中
	 *
	 * @param
	 * @return void
	 * @throws SQLException 
	*/
	public void showFileList() throws SQLException{
		System.out.println("...........Document list...........");
		ResultSet document = DataProcessing.listDoc();
		while(document.next()){
			System.out.println(DataProcessing.newDoc(document));
		}
	}
	
	
	/**
	 *
	 * @param 
	 * @return void
	 * @throws  
	*/
	public void exitSystem(){
		filesystem.FileSystem.in.close();
		JOptionPane.showConfirmDialog(buttonYes,"系统推出，谢谢使用!","警告",JOptionPane.OK_CANCEL_OPTION);
		System.out.println("系统退出, 谢谢使用 ! ");
		System.exit(0);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
		System.out.println("修改密码。");
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Client getClient(){
		return client;
	}
}
