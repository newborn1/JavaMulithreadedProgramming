package addUserClass;

import java.util.*;

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
		return true;
	}
	
	public boolean delUser() {
		return true;
	}
	
	public boolean addUser() {
		return true;
	}
	public boolean listUser() {
		return true;
	}

}
