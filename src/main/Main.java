package main;

import adduserclass.*;
import filesystem.*;
import java.util.Scanner;

/**
 * feature ��Ϊ��������������
 * 
 * @author ֣ΰ��
 * @data 2021/11/19
 */
public class Main {
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		FileSystem.showMainUserSurface();

		AbstractUser user = null;
		do{
			user = FileSystem.verifyUser();
		}while(user == null);

		do {//ѡ��˵�
			user.showMenu();
		} while (true);
	}

}
