package dataprocessing;

import java.util.*;
import java.sql.*;
import adduserclass.*;

/**
 * TODO ���ݴ�����,���ڶ��û����ݵĴ洢�Ͳ���
 *
 * @author ֣ΰ��
 * @date 2021/11/23
 */
public class DataProcessing {
	private static boolean connectToDB=false;
	static final double EXCEPTION_CONNECT_PROBABILITY=0.1;
	static final double EXCEPTION_SQL_PROBABILITY=0.9;
	static Hashtable<String, AbstractUser> users;
	
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
		users = new Hashtable<String, AbstractUser>();
		users.put("jack", new Operator("jack","123","operator"));
		users.put("rose", new Browser("rose","123","browser"));
		users.put("kate", new Administrator("kate","123","administrator"));
	}
	
	/**
	 * TODO ��ʼ�����������ݿ�
	 *   
	 * @param 
	 * @return void
	 * @throws  
	*/
	public static  void init(){		
		// update database connection status
	    double ranValue= Math.random();
		if (ranValue>EXCEPTION_CONNECT_PROBABILITY) {
			connectToDB = true;
		}else {
			connectToDB = false;
		}
	}	
	
	/**
	 * TODO ���û��������û�������nullʱ����δ�ҵ������������û�
	 * 
	 * @param name �û��� 
	 * @return AbstractUser
	 * @throws SQLException 
	*/
	public static AbstractUser searchUser(String name) throws SQLException{
		if (!connectToDB) {
			throw new SQLException("Not Connected to Database");
		}
		double ranValue=Math.random();
		if (ranValue>EXCEPTION_SQL_PROBABILITY) {
			throw new SQLException("Error in excecuting Query");
		}
		if (users.containsKey(name)) {
			return users.get(name);			
		}
		return null;
	}
	
	/**
	 * TODO ���û��������������û�������nullʱ����δ�ҵ������������û�
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
	    double ranValue=Math.random();
		if (ranValue>EXCEPTION_SQL_PROBABILITY) {
			throw new SQLException("Error in executing Query");
		}
		if (users.containsKey(name)) {
		    AbstractUser temp =users.get(name);
			if ((temp.getPassword()).equals(password)) {
				return temp;
			}
		}
		return null;
	}
	
	/**
	 * TODO ȡ�����е��û� 
	 * 
	 * @param 
	 * @return Enumeration<AbstractUser>
	 * @throws SQLException 
	*/
	public static Enumeration<AbstractUser> listUser() throws SQLException{
		if (!connectToDB) {
	        throw new SQLException("Not Connected to Database");
		}
		double ranValue=Math.random();
		if (ranValue>EXCEPTION_SQL_PROBABILITY) {
			throw new SQLException("Error in excecuting Query");
		}
		Enumeration<AbstractUser> e  = users.elements();
		return e;
	}
		
	/**
	 * TODO �޸��û���Ϣ
	 * 
	 * @param name �û���
	 * @param password ����
	 * @param role ��ɫ
	 * @return boolean
	 * @throws SQLException 
	*/
	public static boolean updateUser(String name, String password, String role) throws SQLException{
	    AbstractUser user;
		if (!connectToDB) {
	        throw new SQLException("Not Connected to Database");
		}
		double ranValue=Math.random();
		if (ranValue>EXCEPTION_SQL_PROBABILITY) {
			throw new SQLException("Error in excecuting Update");
		}
		if (users.containsKey(name)) {
		    switch(ROLE_ENUM.valueOf(role.toLowerCase())) {
		        case administrator:
		            user = new Administrator(name,password, role);
		            break;
		        case operator:
		            user = new Operator(name,password, role);
                    break;
                default:
                    user = new Browser(name,password, role);    
		    }
			users.put(name, user);
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * TODO �������û�
	 * 
	 * @param name �û���
     * @param password ����
     * @param role ��ɫ
	 * @return boolean
	 * @throws SQLException 
	*/
	public static boolean insertUser(String name, String password, String role) throws SQLException{
	    AbstractUser user;		
		if (!connectToDB) {
	        throw new SQLException("Not Connected to Database");
		}
		double ranValue=Math.random();
		if (ranValue>EXCEPTION_SQL_PROBABILITY) {
			throw new SQLException("Error in excecuting Insert");
		}
		if (users.containsKey(name)) {
			return false;
		}else{
		    switch(ROLE_ENUM.valueOf(role.toLowerCase())) {
                case administrator:
                    user = new Administrator(name,password, role);
                    break;
                case operator:
                    user = new Operator(name,password, role);
                    break;
                default:
                    user = new Browser(name,password, role);    
            }
		    users.put(name, user);
			return true;
		}
	}
	
	/**
	 * TODO ɾ��ָ���û�
	 * 
	 * @param name �û���
	 * @return boolean
	 * @throws SQLException 
	*/
	public static boolean deleteUser(String name) throws SQLException{
		if (!connectToDB) {
	        throw new SQLException("Not Connected to Database");
		}
		double ranValue=Math.random();
		if (ranValue>EXCEPTION_SQL_PROBABILITY) {
			throw new SQLException("Error in executing Delete");
		}
		if (users.containsKey(name)){
			users.remove(name);
			return true;
		}else {
			return false;
		}
	}
            
	/**
	 * TODO �ر����ݿ�����
	 *   
	 * @param 
	 * @return void
	 * @throws  
	*/
	public static void disconnectFromDataBase() {
		if (connectToDB){
			// close Statement and Connection            
			try{
			    if (Math.random()>EXCEPTION_SQL_PROBABILITY) {
					throw new SQLException( "Error in disconnecting DB" );
			    }
			}catch (SQLException sqlException){                                            
			    sqlException.printStackTrace();           
			}finally{                                            
				connectToDB = false;              
			}                             
		} 
   }
	
	/**
	 * TODO ���ر��������û����м����
	 * 
	 * @param 
	 * @return Enumeration<AbstractUser>
	 */
	public static Enumeration<AbstractUser> getAllUser(){

		Enumeration<AbstractUser> e  = users.elements();
		return e;
	}
	
	/**
	 * TODO debug this file
	 * @param args
	 */
	public static void main(String[] args) {		

	}
	
}
