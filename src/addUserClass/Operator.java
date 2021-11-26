package adduserclass;

import java.io.*;
import java.sql.SQLException;

import dataprocessing.DataProcessing;
import dataprocessing.Doc;
import filesystem.FileSystem;
import static filesystem.FileSystem.in;//should use static

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

		System.out.print("���������ֽ���ѡ��:");
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
	 * TODO �����ϴ�:�����µĵ����ļ�������Ϣ��������Hashtable�У����������ļ�������ָ��Ŀ¼��
	 *
	 * @return boolean
	 */
	public boolean uploadFile() {
		String url = null;
		Doc doc = null;
		File file = null;
		FileInputStream filestream = null;

		System.out.println("�������ļ���ַ:");
		url = FileSystem.in.next();
		file = new File(url);

		System.out.print("uploading...");
		try{
			filestream = new FileInputStream(file);
		}catch (FileNotFoundException fileE){
			System.out.println("�Ҳ������ļ����ϴ�ʧ�ܣ�");
			return false;
		}

		try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FileSystem.REMOTE_PATH))) {
			try (ObjectInputStream oin = new ObjectInputStream(filestream)) {
				//���ĵ����浽HashTable��ָ��Ŀ¼��
				doc = (Doc) oin.readObject();
				out.writeObject(doc);
			}
		}catch (IOException | ClassNotFoundException e) {
			System.out.println("�ļ��ϴ�ʧ�ܣ������ļ��Ƿ��𻵣�");
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
