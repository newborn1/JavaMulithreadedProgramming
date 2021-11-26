package filesystem;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import adduserclass.*;
import dataprocessing.*;

/**
 * TODO ����ϵͳ�࣬���������Ա������Ա�������߽���
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

	/**
	 * TODO ��ʾϵͳ������
	 * 
	 * @param
	 */
	public static void showMainUserSurface() {

		String lineOne = "*******************��ӭ���뵵��ϵͳ**********************\n";
		String lineTwo = "\t\t\t  1����¼\n";
		String lineThree = "\t\t\t 2���˳�\n";
		String lineFour = "********************************************************\n";
		StringBuilder surface = new StringBuilder();
		surface.append(lineOne).append(lineTwo).append(lineThree).append(lineFour);
		String mainSurface = surface.toString();

		System.out.println(mainSurface);


		System.out.print("����������ѡ��:");
		Integer selection = FileSystem.EXIT_SYSTEM;
		selection = in.nextInt();
		if (selection.equals(FileSystem.EXIT_SYSTEM)) {
			filesystem.FileSystem.in.close();
			System.out.println("���˳�ϵͳ");
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
//				password = new String(cons.readPassword("����������:"));//��ȡ����
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
