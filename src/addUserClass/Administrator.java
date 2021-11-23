package adduserclass;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import dataprocessing.DataProcessing;

/**
 * TODO 档案管理员类,对所有人的信息进行统一管理，继承了抽象用户类
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
		
		return;
	}
	
	/**
	 * TODO 根据输入信息修改用户的信息
	 * 
	 * @return 判断是否操作成功
	 * @throws SQLException
	 */
	public boolean changeUserInfo() throws SQLException {
		String name,password,role;
		try(Scanner sc = new Scanner(System.in))
		{
		System.out.print("请输入更新的用户名");
		name = sc.next();
		System.out.print("请输入更新的密码");
		password = sc.next();
		System.out.print("请输入更新的角色");
		role = sc.next();
		}
		if(!DataProcessing.updateUser(name, password, role)) {
//			throw new SQLException("Not Connected to Database");
			return false;
		}
		return true;
	}
	
	public boolean delAbstractUser() throws NullPointerException, SQLException,IOException {
		System.out.print("请输入将删除的用户名：");
		String name = null;
		try(Scanner sc = new Scanner(System.in))
		{
			name = sc.next();
		}
		if(name == null) {
			throw new NullPointerException("this is a null pointer");
		}
		DataProcessing.deleteUser(name);
		return true;
	}
	
	public boolean addAbstractUser() throws SQLException {
		String role = null;
		String password = null;
		String name = null;
		try(Scanner sc = new Scanner(System.in))
		{
		System.out.print("请输入名字：");
		name = sc.next();
		System.out.print("请输入密码：");
		password = sc.next();
		System.out.print("请输入角色：");
		role = sc.next();
		}
		try {
		DataProcessing.insertUser(name,password,role);
		}
		catch(SQLException e) {
			throw e;
		}
		return true;
	}
	public boolean listAbstractUser() {
		Enumeration<AbstractUser> e = DataProcessing.getAllUser();
		while(e.hasMoreElements()) {
			System.out.println(e.nextElement());
		}
		return true;
	}

}
