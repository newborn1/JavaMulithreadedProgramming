package main;

import addUserClass.*;
import java.util.*;
import dataProcessing.*;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Administrator administrator = null;
		Operator operator = null;
		Browser browser = null;
		
		String lineone = "*******************欢迎进入档案系统**********************\n";
		String linetwo = "\t\t\t  1、登录\n";
		String linethree = "\t\t\t 2、退出\n";
		String linefour = "********************************************************\n";
		StringBuilder surface = new StringBuilder();
		surface.append(lineone)
		       .append(linetwo)
		       .append(linethree)
		       .append(linefour);
		String mainSurface = surface.toString();
		
		System.out.println(mainSurface);
		
		System.out.print("请输入数字选择:");
		Integer selection = 2;
		Scanner in = new Scanner(System.in);
		
		selection = in.nextInt();
		if(selection.equals(2)) {
			System.out.println("已退出系统");
			System.exit(0);
			return;
		}
		
		String name = null;
		String password = null;
		
		System.out.print("请输入用户名:");
		name = in.next();
		while(DataProcessing.searchUser(name) == null) {
			System.out.println("不存在该用户。");
			System.out.print("请输入用户名:");
			name = in.next();
		}
		System.out.print("请输入密码:");
		password = in.next();
		
		User u = DataProcessing.search(name,password);
		if(u == null) {
			System.out.println("密码错误！退出系统");
			return;
		}
		
		int selector = 0;

			do{//选择菜单
				u.showMenu();
				System.out.print("请输入数字进行选择:");
				selector = in.nextInt();
				if(u instanceof Browser) {
					browser = (Browser) u;
					switch(selector) {
					case 1:
						browser.downloadFile("***");break;
					case 2:
						browser.showFileList();break;
					case 3:
						browser.setPassword(password);break;
					case 4:
						browser.exitSystem();
					}
				}else if(u instanceof Administrator){
					administrator = (Administrator) u; 
					switch(selector) {
					case 1:
						administrator.showFileList();break;
					case 2:
						administrator.downloadFile(name);break;
					case 3:
						administrator.changeSelfInfo(password);break;
					case 4:
						administrator.changeUserInfo();break;
					case 5:
						administrator.delUser();break;
					case 6:
						administrator.addUser();break;
					case 7:
						administrator.listUser();break;
					case 8:
						administrator.exitSystem();
					}
				}else if(u instanceof Operator) {
					operator =(Operator) u;
					switch(selector) {
					case 1:
						operator.showFileList();break;
					case 2:
						operator.downloadFile(name);break;
					case 3:
						operator.uploadFile();break;
					case 4:
						operator.setPassword(password);break;
					case 5:
						operator.exitSystem();
					}
				}
			}while(true);
		}
		
}
