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
 * feature ��������Ա���Ժ�̨���ļ�����ͳһ�����̳��˳����û���
 *
 * @author ֣ΰ��
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

		final String[] allLine = {"*****************��ӭ������������Ա�˵�****************\n",
						  		  "\t\t\t1����ʾ�ļ��б�\n",
						  		  "\t\t\t2�������ļ�\n",
						  		  "\t\t\t3���ϴ��ļ�\n",
						  		  "\t\t\t4���޸�����\n",
						  		  "\t\t\t5���˳�\n",
						  		  "*******************************************************\n"
		};
		StringBuilder surfaceBuilder = new StringBuilder();
		for(String s:allLine) {
			surfaceBuilder.append(s);
		}
		String surface = surfaceBuilder.toString();
		System.out.print(surface);

		while(true) {
			System.out.print("���������ֽ���ѡ��:");
			try {
				selector = in.nextInt();
				break;
			} catch (InputMismatchException inputMatchE) {
				System.out.println("���������������ȷ�����֣�");
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
				System.out.println("�����ֵ��Ч�����������룡");break;
		}

	}

	/**
	 * �����ϴ�:�����µĵ����ļ�������Ϣ��������Hashtable�У����������ļ�������ָ��Ŀ¼��
	 * TODO ���ĵ����淽ʽ�ı�һ�£����ҽ����ϴ���Ч����֤
	 *
	 * @return boolean
	 */
	public boolean uploadFile() {
		String url = null;
		String[] filenames = null;
		Doc doc = null;
		File file = null;
		FileInputStream filestream = null;

		System.out.print("�������ļ���ַ:");
		url = FileSystem.in.next();
		file = new File(url);

		System.out.println("uploading...");
		try{
			filestream = new FileInputStream(file);
		}catch (FileNotFoundException fileE){
			System.out.println("�Ҳ������ļ����ϴ�ʧ�ܣ�");
			return false;
		}

		/**
		 * ����ļ�����
		 */
		filenames = url.split("\\\\");

		try{
			Files.createFile(Paths.get(FileSystem.REMOTE_PATH));
		}catch (IOException ioe){

		}
		File files = new File(FileSystem.REMOTE_PATH+"\\"+filenames[filenames.length-1]);
		try{
			if(!files.createNewFile()){
				System.out.println("���ļ����ϴ��������ظ��ϴ�!");
				return false;
			}
		}catch (IOException ioE){
			System.out.println("�ϴ�ʧ��!");
			ioE.printStackTrace();
			return false;
		}
		try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(files))) {
			try (ObjectInputStream oin = new ObjectInputStream(filestream)) {
				//���ĵ����浽HashTable��ָ��Ŀ¼��
				doc = (Doc) oin.readObject();
				out.writeObject(doc);
			}
		}catch (IOException | ClassNotFoundException e) {
			System.out.println("�ļ��ϴ�ʧ�ܣ������ļ��Ƿ��𻵣�");
			return false;
		}

		String id= null;
		String description = null;


		System.out.println("��������������ϴ����ĵ�����Ϣ��");
		System.out.print("Id�ţ�");
		id = in.next();
		System.out.print("�ĵ�������");
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
