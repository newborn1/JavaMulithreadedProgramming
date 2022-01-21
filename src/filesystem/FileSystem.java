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

		System.out.println("��¼����");

	}

	/**
	 * �������ֺ�����������֤�����û������ע��name��password�����Ƿ�null
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
		 * TODO ����������������⴦��
		 */
		while (true) {
			try {
				while (DataProcessing.searchUser(name) == null) {
					JOptionPane.showConfirmDialog(null,"�����ڸ��û������������롣","����",JOptionPane.OK_CANCEL_OPTION);
					System.out.println("�����ڸ��û���");
					return null;
				}

				u = DataProcessing.searchUser(name, password);

				if (u == null) {
					JOptionPane.showConfirmDialog(null,"�������!","����",JOptionPane.OK_CANCEL_OPTION);
					System.out.println("�������!");
					return null;
				}else{
					return u;
				}
			} catch (SQLException e) {
				if ("Not Connected to Database".equals(e.getMessage())) {
					DataProcessing.init();
					JOptionPane.showConfirmDialog(null,"���ݿ����ӳ��������µ�¼!","����",JOptionPane.OK_CANCEL_OPTION);
				}
				JOptionPane.showConfirmDialog(null,"����������!","����",JOptionPane.OK_CANCEL_OPTION);
				System.out.println("����������!");
				return null;
			}
		}

	}
}
