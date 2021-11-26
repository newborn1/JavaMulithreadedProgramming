package adduserclass;

import java.sql.SQLException;
import java.io.IOException;

import dataprocessing.DataProcessing;

/**
 * TODO �����û��࣬Ϊ���û������ṩģ��
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
	 * TODO ͨ��������֤�Ƿ���ȷ���޸��û���Ϣ
	 * 
	 * @param password �û��������֤����
	 * @return �ж��Ƿ��޸ĳɹ���
	 * @throws SQLException
	 */
	public boolean changeSelfInfo(String password) throws SQLException {
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
	 * TODO չʾ�˵�����������Ը���
	 *   
	 * @param 
	 * @return void
	 * @throws  
	*/
	public abstract void showMenu();
	
	public boolean downloadFile(String filename){
		System.out.println("�����ļ�... ...");
		return true;
	}
	
	
	/**
	 * TODO չʾ�����ļ��б�
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
		System.out.println("�б�... ...");
	}
	
	
	/**
	 * TODO �˳�ϵͳ
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
