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
	/**in 作为全局变量使用
	 *
	 */
	public static Scanner in = new Scanner(System.in);
	public static String NotConnectedToDatabase = "Not Connected to Database";
	public static final int EXIT_SYSTEM = 2;
	public static final int LOAD = 1;

	/**
	 * TODO 显示系统主界面
	 * 
	 * @param
	 */
	public static void showMainUserSurface() {

		String lineOne = "*******************欢迎进入档案系统**********************\n";
		String lineTwo = "\t\t\t  1、登录\n";
		String lineThree = "\t\t\t 2、退出\n";
		String lineFour = "********************************************************\n";
		StringBuilder surface = new StringBuilder();
		surface.append(lineOne).append(lineTwo).append(lineThree).append(lineFour);
		String mainSurface = surface.toString();

		System.out.println(mainSurface);


		System.out.print("请输入数字选择:");
		Integer selection = FileSystem.EXIT_SYSTEM;
		selection = in.nextInt();
		if (selection.equals(FileSystem.EXIT_SYSTEM)) {
			filesystem.FileSystem.in.close();
			System.out.println("已退出系统");
			System.exit(0);
			return;
		}
	}

	public static AbstractUser verifyUser() {

		String name = null;
		String password = null;
		AbstractUser u = null;

//		Console cons = System.console();

		while (true) {
			System.out.print("请输入用户名:");
			in.nextLine();
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
					System.out.println("密码错误!");
					return null;
				}
				return u;
			} catch (SQLException e) {
				if ("Not Connected to Database".equals(e.getMessage())) {
					DataProcessing.init();
				}
				System.out.println("请重新输入!");
			}
		}

	}
}
