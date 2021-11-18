package addUserClass;

import java.util.*;
import dataProcessing.*;

public class Administrator extends User {
	public Administrator(String name,String password,String role) {
		super(name,password,role);
	}
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
	
	public boolean changeUserInfo() {
		System.out.println("修改成功!");
		return true;
	}
	
	public boolean delUser() {
		System.out.print("请输入将删除的用户名：");
		String name = null;
		Scanner sc = new Scanner(System.in);
		name = sc.next();
		DataProcessing.delete(name);
		return true;
	}
	
	public boolean addUser() {
		Scanner sc = new Scanner(System.in);
		System.out.print("请输入名字：");
		String name = null;
		name = sc.next();
		System.out.print("请输入密码：");
		String password = null;
		password = sc.next();
		System.out.print("请输入角色：");
		String role = null;
		role = sc.next();
		DataProcessing.insert(name,password,role);
		return true;
	}
	public boolean listUser() {
		Enumeration<User> e = DataProcessing.getAllUser();
		while(e.hasMoreElements()) {
			System.out.println(e.nextElement());
		}
		return true;
	}

}
