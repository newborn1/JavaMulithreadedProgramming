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
	public static final int EXIT_SYSTEM = 2;
	public static final int LOAD = 1;

	/**
	 * TODO ��ʾϵͳ������
	 * 
	 * @param
	 * @return
	 */
	public static void showMainUserface() {

		String lineone = "*******************��ӭ���뵵��ϵͳ**********************\n";
		String linetwo = "\t\t\t  1����¼\n";
		String linethree = "\t\t\t 2���˳�\n";
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
			System.out.print("�������û���:");
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
					System.out.println("��������˳�ϵͳ");
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
				System.out.println("����������!");
			}
		}
	}
}
