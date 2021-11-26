package adduserclass;

import java.io.*;
import java.sql.SQLException;

import dataprocessing.DataProcessing;
import dataprocessing.Doc;
import filesystem.FileSystem;
import static filesystem.FileSystem.in;//should use static

/**
 * feature 档案操作员，对后台的文件进行统一管理，继承了抽象用户类
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

	}

	/**
	 * TODO 档案上传:输入新的档案文件属性信息，保存至Hashtable中，并将档案文件拷贝至指定目录中
	 *
	 * @return boolean
	 */
	public boolean uploadFile() {
		String url = null;
		Doc doc = null;
		File file = null;
		FileInputStream filestream = null;

		System.out.println("请输入文件地址:");
		url = FileSystem.in.next();
		file = new File(url);

		System.out.print("uploading...");
		try{
			filestream = new FileInputStream(file);
		}catch (FileNotFoundException fileE){
			System.out.println("找不到该文件，上传失败！");
			return false;
		}

		try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FileSystem.REMOTE_PATH))) {
			try (ObjectInputStream oin = new ObjectInputStream(filestream)) {
				//将文档保存到HashTable和指定目录中
				doc = (Doc) oin.readObject();
				out.writeObject(doc);
			}
		}catch (IOException | ClassNotFoundException e) {
			System.out.println("文件上传失败，请检查文件是否损坏！");
			return false;
		}
		while(true) {
			try {
				DataProcessing.insertDoc(doc.getId(), doc.getCreator(), doc.getTimestamp(), doc.getDescription(), doc.getFilename());
				break;
			} catch (SQLException sqlE) {
				DataProcessing.init();
			}
		}

		System.out.println("upload file is successful");
		return true;
	}

}
