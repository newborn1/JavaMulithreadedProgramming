package main;

import java.sql.SQLException;
import java.util.*;

import adduserclass.*;
import dataprocessing.*;
import filesystem.*;

/**
 * TODO 作为主程序驱动程序
 * 
 * @author 郑伟鑫
 * @data 2021/11/19
 */
public class Main {
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		
		Administrator administrator = null;
		Operator operator = null;
		Browser browser = null;
		
		FileSystem.showMainUserface();
		
//		Scanner in = new Scanner(System.in);
		
		System.out.print("请输入数字选择:");
		Integer selection = FileSystem.EXIT_SYSTEM;
		try (Scanner in = new Scanner(System.in)) {
				selection = in.nextInt();
				if(selection.equals(FileSystem.EXIT_SYSTEM)) {
					System.out.println("已退出系统");
					System.exit(0);
					return;
				}
			AbstractUser user = FileSystem.verifyUser();
			int selector = 0;
		
			String password = user.getPassword();
			String name = user.getName();
		
			do{//选择菜单
				user.showMenu();
				System.out.print("请输入数字进行选择:");
				selector = in.nextInt();
				if(user instanceof Browser) {
					browser = (Browser) user;
					switch(selector) {
					case 1:
						browser.downloadFile("***");break;
					case 2:
						browser.showFileList();break;
					case 3:
						browser.setPassword(password);break;
					case 4:
						browser.exitSystem();break;
					default:
						break;
					}
				}else if(user instanceof Administrator){
					administrator = (Administrator) user; 
					switch(selector) {
					case 1:
						try{
							administrator.showFileList();break;
						}catch(SQLException e) {
							System.out.println(e.getMessage());
							System.out.println("请重新输入!");
							break;
						}
					case 2:
						administrator.downloadFile(name);break;
					case 3:
						administrator.changeSelfInfo(password);break;
					case 4:
						administrator.changeUserInfo();break;
					case 5:
						administrator.delAbstractUser();break;
					case 6:
						administrator.addAbstractUser();break;
					case 7:
						administrator.listAbstractUser();break;
					case 8:
						administrator.exitSystem();break;
					default:
						break;
					}
				}else if(user instanceof Operator) {
					operator =(Operator) user;
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
						operator.exitSystem();break;
					default:
						break;
					}
				}
			}while(true);
		}
	}

}
