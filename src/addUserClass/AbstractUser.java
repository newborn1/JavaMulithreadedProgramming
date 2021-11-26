package adduserclass;

import java.sql.SQLException;
import java.io.IOException;

import dataprocessing.DataProcessing;

/**
 * TODO 抽象用户类，为各用户子类提供模板
 * 
 * @author 郑伟鑫
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
	 * TODO 通过密码验证是否正确来修改用户信息
	 * 
	 * @param password 用户输入的验证密码
	 * @return 判断是否修改成功过
	 * @throws SQLException
	 */
	public boolean changeSelfInfo(String password) throws SQLException {
		//写用户信息到存储
		if (DataProcessing.updateUser(name, password, role)){
			this.password=password;
			System.out.println("修改成功");
			return true;
		}else {
			return false;
		}
	}
	
	
	/**
	 * TODO 展示菜单，需子类加以覆盖
	 *   
	 * @param 
	 * @return void
	 * @throws  
	*/
	public abstract void showMenu();
	
	public boolean downloadFile(String filename){
		System.out.println("下载文件... ...");
		return true;
	}
	
	
	/**
	 * TODO 展示档案文件列表
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
		System.out.println("列表... ...");
	}
	
	
	/**
	 * TODO 退出系统
	 *   
	 * @param 
	 * @return void
	 * @throws  
	*/
	public void exitSystem(){
		filesystem.FileSystem.in.close();
		System.out.println("系统退出, 谢谢使用 ! ");
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
		System.out.println("修改密码。");
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
