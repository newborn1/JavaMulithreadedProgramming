package adduserclass;

import java.sql.SQLException;
import java.util.*;

import gui.MainAdministratorFrame;
import dataprocessing.DataProcessing;

import javax.swing.*;

import static filesystem.FileSystem.in;
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
		JFrame mainFrame = new MainAdministratorFrame(this);
		((MainAdministratorFrame) mainFrame).addAllComponent();
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
				this.downloadFile();break;
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
				while(!this.changeUserInfo()){
					System.out.println("输入错误！请重新处理。");
				}
				break;
			case 5:
				this.delAbstractUser();break;
			case 6:
				this.addAbstractUser();break;
			case 7:
				listAbstractUser();break;
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
	 * @return ture or false判断是否操作成功
	 * @throws SQLException
	 */
	public boolean changeUserInfo() {
		String name, password, role;
		System.out.print("请输入更新的用户名:");
		name = in.next();
		System.out.print("请输入更新的密码:");
		password = in.next();
		System.out.print("请输入更新的角色:");
		role = in.next();
		try {
			if (!DataProcessing.updateUser(name, password, role)) {
				return false;
			}
		} catch (SQLException sqlE) {
			System.out.println(sqlE.getMessage());
			System.out.println("Please do it against.");
			if (NotConnectedToDatabase.equals(sqlE.getMessage())) {
				DataProcessing.init();
			}
		}
		return true;
	}
	
	public boolean delAbstractUser() throws NullPointerException{
		System.out.print("请输入将删除的用户名：");
		String name = null;

		name = in.next();

		try {
			DataProcessing.deleteUser(name);
		}catch (SQLException sqlE){
			System.out.println(sqlE.getMessage());
			System.out.println("Please do it against.");
			if(NotConnectedToDatabase.equals(sqlE.getMessage())){
				DataProcessing.init();
			}
		}
		return true;
	}
	
	public boolean addAbstractUser() {
		String role = null;
		String password = null;
		String name = null;

		System.out.print("请输入名字：");
		name = in.next();
		System.out.print("请输入密码：");
		password = in.next();
		System.out.print("请输入角色：");
		role = in.next();

		try {
			DataProcessing.insertUser(name, password, role);
		} catch (SQLException sqlE) {
			System.out.println(sqlE.getMessage());
			System.out.println("Please do it against.");
			if (NotConnectedToDatabase.equals(sqlE.getMessage())) {
				DataProcessing.init();
			}
		}

		return true;
	}

	public boolean listAbstractUser() {
		Enumeration<AbstractUser> e = null;
		try{
			e = DataProcessing.listUser();
		}catch (SQLException sqlE){
			System.out.println(sqlE.getMessage());
			return false;
		}
		while(e.hasMoreElements()) {
			System.out.println(e.nextElement());
		}
		return true;
	}

	/**
	 * 有异常的就需要重载（为了实现GUI）
	 */
	@Override
	public void showFileList() {
		try {
			super.showFileList();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("请重新输入!");
		}
	}

	@Override
	public boolean changeSelfInfo(String password){
		try {
			super.changeSelfInfo(super.getPassword());
		} catch (SQLException sqlE) {
			System.out.println(sqlE.getMessage());
			System.out.println("Please do it against.");
			if(NotConnectedToDatabase.equals(sqlE.getMessage())){
				DataProcessing.init();
			}
			return false;
		}
		return true;
	}
}
