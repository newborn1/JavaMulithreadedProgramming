package adduserclass;

import dataprocessing.DataProcessing;
import dataprocessing.Doc;
import filesystem.FileSystem;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.InputMismatchException;

import static filesystem.FileSystem.in;

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

		while(true) {
			System.out.print("请输入数字进行选择:");
			try {
				selector = in.nextInt();
				break;
			} catch (InputMismatchException inputMatchE) {
				System.out.println("输入错误，请输入正确的数字！");
				in.nextLine();
			}
		}
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
				this.downloadFile();break;
			case 3:
				this.uploadFile();break;
			case 4:
				this.setPassword(super.getPassword());break;
			case 5:
				this.exitSystem();break;
			default:
				System.out.println("输入的值无效，请重新输入！");break;
		}

	}

	/**
	 * 档案上传:输入新的档案文件属性信息，保存至Hashtable中，并将档案文件拷贝至指定目录中
	 * TODO 将文档保存方式改变一下，并且进行上传有效性验证
	 *
	 * @return boolean
	 */
	public boolean uploadFile() {
		String url = null;
		String[] filenames = null;
		Doc doc = null;
		File file = null;
		FileInputStream filestream = null;

		System.out.print("请输入文件地址:");
		url = FileSystem.in.next();
		file = new File(url);

		System.out.println("uploading...");
		try{
			filestream = new FileInputStream(file);
		}catch (FileNotFoundException fileE){
			System.out.println("找不到该文件，上传失败！");
			return false;
		}

		/**
		 * 获得文件名称
		 */
		filenames = url.split("\\\\");

		try{
			Files.createFile(Paths.get(FileSystem.REMOTE_PATH));
		}catch (IOException ioe){

		}
		File files = new File(FileSystem.REMOTE_PATH+"\\"+filenames[filenames.length-1]);
		try{
			if(!files.createNewFile()){
				System.out.println("该文件已上传，请勿重复上传!");
				return false;
			}
		}catch (IOException ioE){
			System.out.println("上传失败!");
			ioE.printStackTrace();
			return false;
		}
		try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(files))) {
			try (ObjectInputStream oin = new ObjectInputStream(filestream)) {
				//将文档保存到HashTable和指定目录中
				doc = (Doc) oin.readObject();
				out.writeObject(doc);
			}
		}catch (IOException | ClassNotFoundException e) {
			System.out.println("文件上传失败，请检查文件是否损坏！");
			return false;
		}

		String id= null;
		String description = null;


		System.out.println("请根据以下输入上传的文档的信息：");
		System.out.print("Id号：");
		id = in.next();
		System.out.print("文档描述：");
		description = in.next();

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());


		while(true) {
			try {
				DataProcessing.insertDoc(id, this.getName(), timestamp, description, doc.getFilename());
				break;
			} catch (SQLException sqlE) {
				DataProcessing.init();
			}
		}

		System.out.println("upload file is successful");
		return true;
	}

}
