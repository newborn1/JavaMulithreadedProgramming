package filesystem;

import java.sql.SQLException;
import java.util.*;

import clientapi.Client;
import gui.MainSurfaceFrame;
import adduserclass.*;
import dataprocessing.*;

import javax.swing.*;

/**
 * feature ����ϵͳ�࣬���������Ա������Ա�������߽���
 * 
 * @author 86134
 * @data 2021/11/19
 */
public class FileSystem {
	/**in ��Ϊȫ�ֱ���ʹ��
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
	 * feature ��ʾϵͳ������
	 * 
	 * @param
	 */
	public static void showMainUserSurface(Client client) {
		/**
		 * GUI
		 */
		new MainSurfaceFrame(client);

		String lineOne = "*******************��ӭ���뵵��ϵͳ**********************\n";
		String lineTwo = "\t\t\t  1����¼\n";
		String lineThree = "\t\t\t 2���˳�\n";
		String lineFour = "********************************************************\n";
		StringBuilder surface = new StringBuilder();
		surface.append(lineOne).append(lineTwo).append(lineThree).append(lineFour);
		String mainSurface = surface.toString();

		System.out.println(mainSurface);


		System.out.print("����������ѡ��:");
	}

	public static AbstractUser verifyUser() {

		String name = null;
		String password = null;
		AbstractUser u = null;

		/**
		 * TODO ����������������⴦��
		 */
		while (true) {
			System.out.print("�������û���:");
			in.nextLine();
			name = in.next();
			try {
				while (DataProcessing.searchUser(name) == null) {
					System.out.println("�����ڸ��û���");
					System.out.print("�������û���:");
					name = in.next();
				}
				System.out.print("����������:");
				password = in.next();

				u = DataProcessing.searchUser(name, password);

				if (u == null) {
					System.out.println("�������!");
					return null;
				}
				return u;
			} catch (SQLException e) {
				if ("Not Connected to Database".equals(e.getMessage())) {
					DataProcessing.init();
				}
				System.out.println("����������!");
			}
		}

	}
}
