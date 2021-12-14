package dataprocessing;

import adduserclass.*;
import com.sun.deploy.config.Config;

import java.util.Enumeration;
import java.util.Hashtable;
import java.sql.*;
import java.util.logging.Logger;

/**
 * feature ���ݴ�����,���ڶ��û����ݵĴ洢�Ͳ���
 *
 * @author ֣ΰ��
 * @date 2021/11/26
 */
public class DataProcessing {

	private static boolean connectToDB=false;

	static Hashtable<String, AbstractUser > users;
	static Hashtable<String, Doc> docs;

	private static Connection connection;
	private static Statement statement;
	private static ResultSet resultSet;

	static enum ROLE_ENUM {
		/**
		 * administrator
		 */
		administrator("administrator"),
		/**
		 * operator
		 */
		operator("operator"),
		/**
		 * browser
		 */
		browser("browser");

		private String role;

		ROLE_ENUM(String role) {
			this.role = role;
		}

		public String getRole() {
			return role;
		}
	}
	static {
		users = new Hashtable<String, AbstractUser >();
		users.put("jack", new Operator("jack","123","operator"));
		users.put("rose", new Browser("rose","123","browser"));
		users.put("kate", new Administrator("kate","123","administrator"));
		init();

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		docs = new Hashtable<String,Doc>();
		docs.put("0001",new Doc("0001","jack",timestamp,"Doc Source Java","Doc.java"));
	}

	/**
	 * ��ʼ�����������ݿ�
	 *
	 * @param
	 * @return void
	 * @throws
	 */
	public static void init(){
		// �������ݿ�������
		String driverName="com.mysql.cj.jdbc.Driver";
		// �������ݿ��UR
		String url="jdbc:mysql://localhost:3306/new_schema";
		// ���ݿ��û�
		String user="root";
		String password="1234321wx";
		try{
			Class.forName(driverName);
			// �������ݿ�����
			connection=DriverManager.getConnection(url, user, password);
			statement = connection.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY );
			String sql="select * from user_info";
			resultSet = statement.executeQuery(sql);
			connectToDB = true;

		}catch(ClassNotFoundException e ){
			System.out.println("������������");
			//�ر���Դ
			try{
				if(statement!=null) {
					statement.close();
				}
			}catch(SQLException se2){
			}// ʲô������
			try{
				if(connection!=null) {
					connection.close();
				}
			}catch(SQLException se){
				se.printStackTrace();
			}
		}catch(SQLException e){
			System.out.println("���ݿ����");
			e.printStackTrace();
			//�ر���Դ
			try{
				if(statement!=null) {
					statement.close();
				}
			}catch(SQLException se2){
			}// ʲô������
			try{
				if(connection!=null) {
					connection.close();
				}
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
	}

	/**
	 * feature �������������������Ϣ������nullʱ����δ�ҵ�
	 *
	 * @param id
	 * @return Doc
	 * @throws SQLException
	 */
	public static Doc searchDoc(String id) throws SQLException {
		if (!connectToDB) {
			throw new SQLException("Not Connected to Database");
		}
		String sql = "SELECT * FROM doc_info WHERE Id='"+id+"'";
		resultSet = statement.executeQuery(sql);
		if(resultSet.next()){
			return newDoc(resultSet);
		}
//		if (docs.containsKey(id)) {
//			Doc temp =docs.get(id);
//			return temp;
//		}
		return null;
	}

	/**
	 * feature �г����е�����Ϣ
	 *
	 * @param
	 * @return Enumeration<Doc>
	 * @throws SQLException
	 */
	public static ResultSet listDoc() throws SQLException{
		if (!connectToDB) {
			throw new SQLException("Not Connected to Database");
		}

		String sql = "SELECT * FROM doc_info";
		resultSet = statement.executeQuery(sql);

		/**
		 * TODO �������͵Ĵ���
		 */
		return resultSet;
	}

	/**
	 * feature �����µĵ���
	 *
	 * @param id
	 * @param creator
	 * @param timestamp
	 * @param description
	 * @param filename
	 * @return boolean
	 * @throws SQLException
	 */
	public static boolean insertDoc(String id, String creator, Timestamp timestamp, String description, String filename) throws SQLException{
//		Doc doc;

		if (!connectToDB) {
			throw new SQLException("Not Connected to Database");
		}

		if (docs.containsKey(id)) {
			return false;
		}
		else{
//			doc = new Doc(id,creator,timestamp,description,filename);
			String sql = null;
			if(description!=null) {
				sql = "INSERT INTO doc_info(Id,creator,timestamp,description,filename) VALUES (" + id + ",'" + creator + "','" + timestamp + "',��" + description + "��,'" + filename + "')";
			}else{
				sql = "INSERT INTO doc_info(Id,creator,timestamp,filename) VALUES ("+id+",'"+creator+"','"+timestamp+"','"+filename+"')";
			}
			statement.executeUpdate(sql);
//			docs.put(id, doc)
			return true;
		}
	}

	/**
	 * ���û��������û�������nullʱ����δ�ҵ������������û�
	 *
	 * @param name �û���
	 * @return AbstractUser
	 * @throws SQLException
	 */
	public static AbstractUser searchUser(String name) throws SQLException{
		if (!connectToDB) {
			throw new SQLException("Not Connected to Database");
		}

		String sql = "SELECT * FROM user_info WHERE username='"+name+"'";
		resultSet =  statement.executeQuery(sql);
		if(resultSet.next()){
			return newUser(resultSet);
		}

//		if (users.containsKey(name)) {
//			return users.get(name);
//		}
		return null;
	}

	/**
	 * ���û��������������û�������nullʱ����δ�ҵ������������û�
	 *
	 * @param name �û���
	 * @param password  ����
	 * @return AbstractUser
	 * @throws SQLException
	 */
	public static AbstractUser searchUser(String name, String password) throws SQLException {
		if (!connectToDB) {
			throw new SQLException("Not Connected to Database");
		}

		String sql = "SELECT * FROM user_info WHERE username='"+name+"'AND password='"+password+"'";
		resultSet = statement.executeQuery(sql);
		if(resultSet.next()){
			return newUser(resultSet);
		}
//		if (users.containsKey(name)) {
//			AbstractUser temp =users.get(name);
//			if ((temp.getPassword()).equals(password)) {
//				return temp;
//			}
//		}
		return null;
	}

	/**
	 * feature ȡ�����е��û�
	 *
	 * @param
	 * @return Enumeration<AbstractUser>
	 * @throws SQLException
	 */
	public static ResultSet listUser() throws SQLException{
		if (!connectToDB) {
			throw new SQLException("Not Connected to Database");
		}

		String sql = "SELECT * FROM user_info";
		resultSet = statement.executeQuery(sql);
		return resultSet;
	}

	/**
	 * feature �޸��û���Ϣ
	 *
	 * @param name �û���
	 * @param password ����
	 * @param role ��ɫ
	 * @return boolean
	 * @throws SQLException
	 */
	public static boolean updateUser(String name, String password, String role) throws SQLException{
		AbstractUser user;
		String sql = "UPDATE user_info SET username='"+name+"', password='"+password+"',role='"+role+"' WHERE username='"+name+"'";
		if(statement.executeUpdate(sql)!=0){
			return true;
		}else {
			return false;
		}
	}

	/**
	 * ���ɶ�Ӧ�Ķ���
	 * @param resultSet
	 * @return
	 */
	public static Doc newDoc(ResultSet resultSet){
		Doc doc = null;
		String id = "",description="",creator="",filename="";
		Timestamp timestamp = null;
		try{
			id = resultSet.getString("Id");
			creator = resultSet.getString("creator");
			timestamp = resultSet.getTimestamp("timestamp");
			description = resultSet.getString("description");
			filename = resultSet.getString("filename");
		}catch (SQLException e){
			Logger.getGlobal().info("DataBase->�Ҳ�����Ӧ����");
		}

		doc = new Doc(id,creator,timestamp,description,filename);

		return doc;
	}

	/**
	 * ���ɶ�Ӧ�Ķ���
	 */
	public static AbstractUser newUser(ResultSet resultSet){
		AbstractUser user = null;
		String name = "",password="",role="";
		try {
			name = resultSet.getString("username");
			password = resultSet.getString("password");
			role = resultSet.getString("role");
		}catch (SQLException e){
			Logger.getGlobal().info("DataBase->�����ڶ�Ӧ����");
		}
		switch(ROLE_ENUM.valueOf(role.toLowerCase())) {
			case administrator:
				user = new Administrator(name, password, role);
				break;
			case operator:
				user = new Operator(name, password, role);
				break;
			default:
				user = new Browser(name, password, role);
		}

		return user;
	}

	/**
	 * feature: �������û�
	 *
	 * @param name �û���
	 * @param password ����
	 * @param role ��ɫ
	 * @return boolean
	 * @throws SQLException
	 */
	public static boolean insertUser(String name, String password, String role) throws SQLException{
		String sql = "INSERT INTO user_info(username,password,role) VALUES ('" + name +"','" +password+"','"+role+"')";
		int status = statement.executeUpdate(sql);
		if(status != 1){
			return false;
		}
		return true;
	}

	/**
	 *
	 * @param name �û���
	 * @return boolean
	 * @throws SQLException
	 */
	public static boolean deleteUser(String name) throws SQLException{
		String sql = "DELETE FROM user_info WHERE username='"+name+"'";
		int status =statement.executeUpdate(sql);
		if(status != -1){
			return true;
		}
		else {
			return false;
		}
//		if (users.containsKey(name)){
//			users.remove(name);
//			return true;
//		}else {
//			return false;
//		}
	}

	/**
	 *
	 * @param
	 * @return void
	 * @throws
	 */
	public static void disconnectFromDataBase() {
		if (connectToDB){
			// close Statement and Connection
			try{
				//����Ҫ�ر�
				resultSet.close();
				statement.close();
				connection.close();
			}catch (SQLException sqlE){
				sqlE.printStackTrace();
			}finally {
				connectToDB = false;
			}

		}
	}


	public static void main(String[] args) {
		init();
		try {
			Doc doc = searchDoc("1");
			System.out.println(doc);
//			doc.setId("2");
//			insertDoc(doc.getId(),doc.getCreator(),doc.getTimestamp(),doc.getDescription(),doc.getFilename());
			AbstractUser user = searchUser("jack");
			user = searchUser("jack","111");
			System.out.println(user);
			DataProcessing.updateUser("jack","111","administrator");
		} catch (SQLException e) {
			disconnectFromDataBase();
			e.printStackTrace();
		}
		disconnectFromDataBase();
	}

}