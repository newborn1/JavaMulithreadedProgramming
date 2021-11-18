package addUserClass;

import java.util.*;
import dataProcessing.*;

public class Administrator extends User {
	public Administrator(String name,String password,String role) {
		super(name,password,role);
	}
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
	
	public boolean changeUserInfo() {
		System.out.println("�޸ĳɹ�!");
		return true;
	}
	
	public boolean delUser() {
		System.out.print("�����뽫ɾ�����û�����");
		String name = null;
		Scanner sc = new Scanner(System.in);
		name = sc.next();
		DataProcessing.delete(name);
		return true;
	}
	
	public boolean addUser() {
		Scanner sc = new Scanner(System.in);
		System.out.print("���������֣�");
		String name = null;
		name = sc.next();
		System.out.print("���������룺");
		String password = null;
		password = sc.next();
		System.out.print("�������ɫ��");
		String role = null;
		role = sc.next();
		DataProcessing.insert(name,password,role);
		return true;
	}
	public boolean listUser() {
		Enumeration<User> e = DataProcessing.getAllUser();
		while(e.hasMoreElements()) {
			System.out.println(e.nextElement());
		}
		return true;
	}

}
