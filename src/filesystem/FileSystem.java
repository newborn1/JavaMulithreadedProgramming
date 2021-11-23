package filesystem;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import adduserclass.*;
import dataprocessing.*;

/**
 * TODO 管理系统类，用于与浏览员、管理员、操作者交互
 * 
 * @author 86134
 * @data 2021/11/19
 */
public class FileSystem {
	public static final int EXIT_SYSTEM = 2;
	public static final int LOAD = 1;

	/**
	 * TODO 显示系统主界面
	 * 
	 * @param
	 * @return
	 */
	public static void showMainUserface() {

		String lineone = "*******************欢迎进入档案系统**********************\n";
		String linetwo = "\t\t\t  1、登录\n";
		String linethree = "\t\t\t 2、退出\n";
		String linefour = "********************************************************\n";
		StringBuilder surface = new StringBuilder();
		surface.append(lineone).append(linetwo).append(linethree).append(linefour);
		String mainSurface = surface.toString();

		System.out.println(mainSurface);
	}

	public static AbstractUser verifyUser() throws IOException, SQLException {

		String name = null;
		String password = null;
		AbstractUser u = null;

		Scanner in = new Scanner(System.in);
//		Console cons = System.console();
		
		while (true) {
			System.out.print("请输入用户名:");
			name = in.next();
			try {
				while (DataProcessing.searchUser(name) == null) {
					System.out.println("不存在该用户。");
					System.out.print("请输入用户名:");
					name = in.next();
				}
				System.out.print("请输入密码:");
//				password = new String(cons.readPassword("请输入密码:"));//读取密码
				password = in.next();

				u = DataProcessing.searchUser(name, password);

				if (u == null) {
					System.out.println("密码错误！退出系统");
					return null;
				}
				return u;
			} catch (SQLException e) {
/*
						e.printStackTrace();
						System.out.println();
*/
				if ("Not Connected to Database".equals(e.getMessage())) {
					DataProcessing.init();
				}
				System.out.println("请重新输入!");
			}
		}
	}
}
