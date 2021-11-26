package main;

import adduserclass.*;
import filesystem.*;
import java.util.Scanner;

/**
 * feature 作为主程序驱动程序
 * 
 * @author 郑伟鑫
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

		do {//选择菜单
			user.showMenu();
		} while (true);
	}

}
