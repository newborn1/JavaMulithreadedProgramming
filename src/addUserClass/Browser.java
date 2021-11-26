package adduserclass;

import java.sql.SQLException;
import java.util.Scanner;

import static filesystem.FileSystem.in;

/**
 * TODO 浏览者，对某些个人信息进行修改，浏览文件，继承了抽象用户类
 * 
 * @author 86134
 * @data 2021/11/19
 */
public class Browser extends AbstractUser {
	public Browser(String name,String password,String role) {
		//调用的构造器只能是本类的构造器。如何初始化父类的：用super
		super(name, password, role);
	}
	
	@Override
	public void showMenu() {
		//browser不需要强制类型转换
//		Browser browser = (Browser) this;
		Integer selector = 0;

		final String[] allLine = {"************欢迎进入档案浏览员菜单******************\n",
								  "\t\t\t1、下载文件\n",
								  "\t\t\t2、文件列表\n",
								  "\t\t\t3、修改密码\n",
								  "\t\t\t4、退出\n",
								  "***************************************************\n"
		};
		
		StringBuilder surfaceBuilder = new StringBuilder();
		for(String s:allLine) {
			surfaceBuilder.append(s);
		}
		String surface = surfaceBuilder.toString();
		System.out.print(surface);


		System.out.print("请输入数字进行选择:");
		selector = in.nextInt();
		switch(selector) {
			case 1:
				this.downloadFile("***");
				break;
			case 2:
				try{
					this.showFileList();
				}catch (SQLException sqe){
					System.out.println(sqe.getMessage());
					System.out.println("The problem has been solved.Please input the selector against.");
				}
				break;
			case 3:
				this.setPassword(super.getPassword());
				break;
			case 4:
				this.exitSystem();
				break;
			default:
				break;
		}

		return;
	}

}
