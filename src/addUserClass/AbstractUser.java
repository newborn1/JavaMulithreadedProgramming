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
 * feature 抽象用户类，为各用户子类提供模板
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
	 * 展示文件的菜单，需要被实现
	 * @param 
	 * @return void
	 * @throws  
	*/
	public abstract void showMenu();

	/**
	 * TODO 档案下载:根据档案号在哈希表中查找得到文件信息，在未涉及网络之前，只需实现在单机上将对应文件拷贝至指定目录中
	 *
	 * @param filename
	 * @return
	 */
	public boolean downloadFile(String filename){
		Doc doc = null;
		String id = null;
		String path = null;

		do {
			System.out.print("请输入档案所对应的档案号:");
			id = FileSystem.in.next().toString();
			try {
				doc = DataProcessing.searchDoc(id);
			} catch (SQLException sqlE) {
				System.out.println(sqlE.getMessage());
				System.out.println("Connecting to Database...");
				System.out.println("请重新输入！");
			}
			if(doc == null) {
				System.out.println("找不到对应的档案，请重新输入:");
			}
		}while(doc == null);

		/**
		 * TODO 手动输入路径
		 */
		System.out.println("下载目录为：");
		System.out.println("1、D:\\JavaExperiment\\LocalFile1");
		System.out.println("2、D:\\JavaExperiment\\LocalFile2");
		System.out.print("请选择你要下载到的目录中:");
		while(path == null) {
			switch (Integer.valueOf(FileSystem.in.next()).intValue()) {
				case 1:
					path = FileSystem.PATH1;
					break;
				case 2:
					path = FileSystem.PATH2;
					break;
				default:
					System.out.println("路径输入错误，请重新输入:");
					break;
			}
		}
		System.out.println("下载文件中......");
		try {
			Files.createDirectories(Paths.get(path));
		}catch (IOException ioE){

		}
		path += "\\" + doc.getFilename();
		File file = new File(path);
		try {
			if (!file.createNewFile()) {
				System.out.println("该文件已下载到本地，请勿重复下载!");
				return false;
			}
			else{
				try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))){
					out.writeObject(doc);
				}
			}
		}catch (IOException ioE){
			System.out.println("下载失败!");
			ioE.printStackTrace();
			return false;
		}

		System.out.println("下载完成！");
		return true;
	}
	
	
	/**
	 * TODO 档案查询:实现按条件查询相应的档案文件信息，也可简化为展示所有档案文件信息.在未涉及数据库之前，档案信息存放在Hashtable中
	 * TODO 按条件查询相应的档案文件信息
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
