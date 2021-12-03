package adduserclass;

import java.io.IOException;
import java.net.Inet4Address;
import java.sql.SQLException;
import java.util.*;

import dataprocessing.DataProcessing;
import static filesystem.FileSystem.in;
import static filesystem.FileSystem.NotConnectedToDatabase;

/**
 * feature ��������Ա��,�������˵���Ϣ����ͳһ�����̳��˳����û���
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
//		Administrator administrator = (Administrator) this;
		Integer selector = 0;

		final String[] allLine = {"**************��ӭ�������Ա�˵�**********************\n",
								  "\t\t\t1����ʾ�ļ��б�\n",
								  "\t\t\t2�������ļ�\n",
								  "\t\t\t3���޸ĸ�����Ϣ\n",
								  "\t\t\t4���޸��û���Ϣ\n",
								  "\t\t\t5��ɾ���û�\n",
								  "\t\t\t6�������û�\n",
								  "\t\t\t7����ʾ�����û�\n",
								  "\t\t\t8���˳�\n",
								  "*****************************************************\n"
		};
		StringBuilder surfaceBuilder = new StringBuilder();
		for(String s:allLine) {
			surfaceBuilder.append(s);
		}
		String surface = surfaceBuilder.toString();
		System.out.print(surface);


		System.out.print("���������ֽ���ѡ��:");
		selector = in.nextInt();
		switch(selector) {
			case 1:
				try{
					this.showFileList();break;
				}catch(SQLException e) {
					System.out.println(e.getMessage());
					System.out.println("����������!");
					break;
				}
			case 2:
				this.downloadFile();break;
			case 3:
				try {
					this.changeSelfInfo(super.getPassword());
				} catch (SQLException sqlE) {
					System.out.println(sqlE.getMessage());
					System.out.println("Please do it against.");
					if(NotConnectedToDatabase.equals(sqlE.getMessage())){
						DataProcessing.init();
					}
				}
				break;
			case 4:
				while(!this.changeUserInfo()){
					System.out.println("������������´���");
				}
				break;
			case 5:
				this.delAbstractUser();break;
			case 6:
				this.addAbstractUser();break;
			case 7:
				try {
					this.listAbstractUser();
				}catch (SQLException sqlE){
					System.out.println(sqlE.getMessage());
				}
				break;
			case 8:
				this.exitSystem();break;
			default:
				break;
		}

		return;
	}
	
	/**
	 * feature ����������Ϣ�޸��û�����Ϣ
	 * 
	 * @return ture or false�ж��Ƿ�����ɹ�
	 * @throws SQLException
	 */
	public boolean changeUserInfo() {
		String name, password, role;
		System.out.print("��������µ��û���:");
		name = in.next();
		System.out.print("��������µ�����:");
		password = in.next();
		System.out.print("��������µĽ�ɫ:");
		role = in.next();
		try {
			if (!DataProcessing.updateUser(name, password, role)) {
				return false;
			}
		} catch (SQLException sqlE) {
			System.out.println(sqlE.getMessage());
			System.out.println("Please do it against.");
			if (NotConnectedToDatabase.equals(sqlE.getMessage())) {
				DataProcessing.init();
			}
		}
		return true;
	}
	
	public boolean delAbstractUser() throws NullPointerException{
		System.out.print("�����뽫ɾ�����û�����");
		String name = null;

		name = in.next();

		try {
			DataProcessing.deleteUser(name);
		}catch (SQLException sqlE){
			System.out.println(sqlE.getMessage());
			System.out.println("Please do it against.");
			if(NotConnectedToDatabase.equals(sqlE.getMessage())){
				DataProcessing.init();
			}
		}
		return true;
	}
	
	public boolean addAbstractUser() {
		String role = null;
		String password = null;
		String name = null;

		System.out.print("���������֣�");
		name = in.next();
		System.out.print("���������룺");
		password = in.next();
		System.out.print("�������ɫ��");
		role = in.next();

		try {
			DataProcessing.insertUser(name, password, role);
		} catch (SQLException sqlE) {
			System.out.println(sqlE.getMessage());
			System.out.println("Please do it against.");
			if (NotConnectedToDatabase.equals(sqlE.getMessage())) {
				DataProcessing.init();
			}
		}

		return true;
	}

	public boolean listAbstractUser() throws SQLException {
		Enumeration<AbstractUser> e = DataProcessing.listUser();
		while(e.hasMoreElements()) {
			System.out.println(e.nextElement());
		}
		return true;
	}

}
