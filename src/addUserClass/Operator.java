package adduserclass;

import java.sql.SQLException;
import java.util.Scanner;

import filesystem.FileSystem;
import static filesystem.FileSystem.in;//should use static

/**
 * TODO 档案操作员，对后台的文件进行统一管理，继承了抽象用户类 
 *
 * @author 郑伟鑫
 * @data 2021/11/19
 */
public class Operator extends AbstractUser{
	public Operator(String name,String password,String role) {
		super(name,password,role);
	}
	
	@Override
	public void showMenu() {
		Integer selector = 0;
//		Operator operator = (Operator) this;

		final String[] allLine = {"*****************欢迎来到档案操作员菜单****************\n",
						  		  "\t\t\t1、显示文件列表\n",
						  		  "\t\t\t2、下载文件\n",
						  		  "\t\t\t3、上传文件\n",
						  		  "\t\t\t4、修改密码\n",
						  		  "\t\t\t5、退出\n",
						  		  "*******************************************************\n"
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
				try{
					this.showFileList();
				}catch (SQLException sqe){
					System.out.println(sqe.getMessage());
					System.out.println("The problem has been solved.Please input the selector against.");
				}
				break;
			case 2:
				this.downloadFile(super.getName());break;
			case 3:
				this.uploadFile();break;
			case 4:
				this.setPassword(super.getPassword());break;
			case 5:
				this.exitSystem();break;
			default:
				break;
		}

		return;
	}

	public boolean uploadFile() {
		System.out.print("uploading...");
		System.out.printf("upload file is sucessful");
		return true;
	}
}
