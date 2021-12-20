package filesystem;

import java.sql.SQLException;
import java.util.*;

import clientapi.Client;
import gui.MainSurfaceFrame;
import adduserclass.*;
import dataprocessing.*;

import javax.swing.*;

/**
 * feature 管理系统类，用于与浏览员、管理员、操作者交互
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
	public static final String PATH1 = "D:\\JavaExperiment\\LocalFile1";
	public static final String PATH2 = "D:\\JavaExperiment\\LocalFile2";
	public static final String REMOTE_PATH = "D:\\JavaExperiment\\RemoteFile";

	/**
	 * feature 显示系统主界面
	 * 
	 * @param
	 */
	public static void showMainUserSurface(Client client) {
		/**
		 * GUI
		 */
		new MainSurfaceFrame(client);

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
	}

	public static AbstractUser verifyUser() {

		String name = null;
		String password = null;
		AbstractUser u = null;

		/**
		 * TODO 对输入密码进行特殊处理
		 */
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
