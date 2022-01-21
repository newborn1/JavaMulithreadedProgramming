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

		System.out.println("登录界面");

	}

	/**
	 * 输入名字和密码用于验证返回用户情况，注意name和password必须是非null
	 *
	 * @param name
	 * @param password
	 * @return AbstractUser
	 */
	public static AbstractUser verifyUser(String name,String password) {
		assert name != null;
		assert password != null;

		AbstractUser u = null;

		/**
		 * TODO 对输入密码进行特殊处理
		 */
		while (true) {
			try {
				while (DataProcessing.searchUser(name) == null) {
					JOptionPane.showConfirmDialog(null,"不存在该用户！请重新输入。","警告",JOptionPane.OK_CANCEL_OPTION);
					System.out.println("不存在该用户。");
					return null;
				}

				u = DataProcessing.searchUser(name, password);

				if (u == null) {
					JOptionPane.showConfirmDialog(null,"密码错误!","警告",JOptionPane.OK_CANCEL_OPTION);
					System.out.println("密码错误!");
					return null;
				}else{
					return u;
				}
			} catch (SQLException e) {
				if ("Not Connected to Database".equals(e.getMessage())) {
					DataProcessing.init();
					JOptionPane.showConfirmDialog(null,"数据库连接出错，请重新登录!","警告",JOptionPane.OK_CANCEL_OPTION);
				}
				JOptionPane.showConfirmDialog(null,"请重新输入!","警告",JOptionPane.OK_CANCEL_OPTION);
				System.out.println("请重新输入!");
				return null;
			}
		}

	}
}
