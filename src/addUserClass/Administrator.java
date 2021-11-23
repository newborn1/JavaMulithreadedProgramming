package adduserclass;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

import dataprocessing.DataProcessing;

/**
 * TODO ��������Ա��,�������˵���Ϣ����ͳһ�����̳��˳����û���
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
		
		return;
	}
	
	/**
	 * TODO ����������Ϣ�޸��û�����Ϣ
	 * 
	 * @return �ж��Ƿ�����ɹ�
	 * @throws SQLException
	 */
	public boolean changeUserInfo() throws SQLException {
		String name,password,role;
		try(Scanner sc = new Scanner(System.in))
		{
		System.out.print("��������µ��û���");
		name = sc.next();
		System.out.print("��������µ�����");
		password = sc.next();
		System.out.print("��������µĽ�ɫ");
		role = sc.next();
		}
		if(!DataProcessing.updateUser(name, password, role)) {
//			throw new SQLException("Not Connected to Database");
			return false;
		}
		return true;
	}
	
	public boolean delAbstractUser() throws NullPointerException, SQLException,IOException {
		System.out.print("�����뽫ɾ�����û�����");
		String name = null;
		try(Scanner sc = new Scanner(System.in))
		{
			name = sc.next();
		}
		if(name == null) {
			throw new NullPointerException("this is a null pointer");
		}
		DataProcessing.deleteUser(name);
		return true;
	}
	
	public boolean addAbstractUser() throws SQLException {
		String role = null;
		String password = null;
		String name = null;
		try(Scanner sc = new Scanner(System.in))
		{
		System.out.print("���������֣�");
		name = sc.next();
		System.out.print("���������룺");
		password = sc.next();
		System.out.print("�������ɫ��");
		role = sc.next();
		}
		try {
		DataProcessing.insertUser(name,password,role);
		}
		catch(SQLException e) {
			throw e;
		}
		return true;
	}
	public boolean listAbstractUser() {
		Enumeration<AbstractUser> e = DataProcessing.getAllUser();
		while(e.hasMoreElements()) {
			System.out.println(e.nextElement());
		}
		return true;
	}

}
