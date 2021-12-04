package adduserclass;

import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.Enumeration;
import java.io.File;
import java.io.IOException;

import dataprocessing.DataProcessing;
import dataprocessing.Doc;
import filesystem.FileSystem;


/**
 * feature �����û��࣬Ϊ���û������ṩģ��
 * 
 * @author ֣ΰ��
 * @date 2021/11/19
 */
public abstract class AbstractUser {
	private String name;
	private String password;
	private String role;
	static final double EXCEPTION_PROBABILITY=0.9;
	
	AbstractUser(String name,String password,String role){
		this.name=name;
		this.password=password;
		this.role=role;				
	}
	
	@Override
	public String toString(){
		return  "name = " + name
				+"password = " + password
				+",role = " + role;
	}
	
	/**
	 *
	 * @param password �û��������֤����
	 * @return �ж��Ƿ��޸ĳɹ���
	 * @throws SQLException
	 */
	public boolean changeSelfInfo(String password) throws SQLException {
		//��ʾGUI����

		//д�û���Ϣ���洢
		if (DataProcessing.updateUser(name, password, role)){
			this.password=password;
			System.out.println("�޸ĳɹ�");
			return true;
		}else {
			return false;
		}
	}
	
	
	/**
	 * չʾ�ļ��Ĳ˵�����Ҫ��ʵ��
	 * @param 
	 * @return void
	 * @throws  
	*/
	public abstract void showMenu();

	/**
	 * ��������:���ݵ������ڹ�ϣ���в��ҵõ��ļ���Ϣ����δ�漰����֮ǰ��ֻ��ʵ���ڵ����Ͻ���Ӧ�ļ�������ָ��Ŀ¼��
	 *
	 * @return
	 */
	public boolean downloadFile(){
		Doc doc = null;
		String id = null;
		String path = null;

		do {
			System.out.print("�����뵵������Ӧ�ĵ�����:");
			id = FileSystem.in.next().toString();
			try {
				doc = DataProcessing.searchDoc(id);
			} catch (SQLException sqlE) {
				System.out.println(sqlE.getMessage());
				System.out.println("Connecting to Database...");
				System.out.println("���������룡");
			}
			if(doc == null) {
				System.out.println("�Ҳ�����Ӧ�ĵ���������������:");
			}
		}while(doc == null);

		/**
		 * TODO �ֶ�����·��
		 */
		System.out.println("����Ŀ¼Ϊ��");
		System.out.println("1��D:\\JavaExperiment\\LocalFile1");
		System.out.println("2��D:\\JavaExperiment\\LocalFile2");
		System.out.print("��ѡ����Ҫ���ص���Ŀ¼��:");
		while(path == null) {
			switch (Integer.valueOf(FileSystem.in.next()).intValue()) {
				case 1:
					path = FileSystem.PATH1;
					break;
				case 2:
					path = FileSystem.PATH2;
					break;
				default:
					System.out.println("·�������������������:");
					break;
			}
		}
		System.out.println("�����ļ���......");
		try {
			Files.createDirectories(Paths.get(path));
		}catch (IOException ioE){

		}
		path += "\\" + doc.getFilename();
		File file = new File(path);
		try {
			if (!file.createNewFile()) {
				System.out.println("���ļ������ص����أ������ظ�����!");
				return false;
			}
			else{
				try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))){
					out.writeObject(doc);
				}
			}
		}catch (IOException ioE){
			System.out.println("����ʧ��!");
			ioE.printStackTrace();
			return false;
		}

		System.out.println("������ɣ�");
		return true;
	}
	
	
	/**
	 * ������ѯ:ʵ�ְ�������ѯ��Ӧ�ĵ����ļ���Ϣ��Ҳ�ɼ�Ϊչʾ���е����ļ���Ϣ.��δ�漰���ݿ�֮ǰ��������Ϣ�����Hashtable��
	 * TODO ��������ѯ��Ӧ�ĵ����ļ���Ϣ
	 *
	 * @param
	 * @return void
	 * @throws SQLException 
	*/
	public void showFileList() throws SQLException{
		double ranValue=Math.random();
		if (ranValue > EXCEPTION_PROBABILITY) {
			throw new SQLException("Error in accessing file DB");
		}
		System.out.println("...........Document list...........");
		Enumeration<Doc> document = DataProcessing.listDoc();
		while(document.hasMoreElements()){
			System.out.println(document.nextElement());
		}
	}
	
	
	/**
	 *
	 * @param 
	 * @return void
	 * @throws  
	*/
	public void exitSystem(){
		filesystem.FileSystem.in.close();
		System.out.println("ϵͳ�˳�, ллʹ�� ! ");
		System.exit(0);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
		System.out.println("�޸����롣");
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
