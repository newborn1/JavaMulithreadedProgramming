package sqltest;

import java.sql.*;

/**
 * @author ��
 */
public class SQLtest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet;
		// �������ݿ�������
		String driverName="com.mysql.cj.jdbc.Driver";
		// �������ݿ��URL
		String url="jdbc:mysql://localhost:3306/new_schema";
		// ���ݿ��û�
	    String user="root";
	    String password="1234321wx";
	    try{
			//ע��JDBC����
	    	Class.forName(driverName);
			// �������ݿ�����
			connection=DriverManager.getConnection(url, user, password);
			//ʵ����Statement����
			statement = connection.createStatement( 
			         ResultSet.TYPE_SCROLL_INSENSITIVE,
			         ResultSet.CONCUR_READ_ONLY );
			//ִ�в�ѯ
			String sql="select * from user_info";
			resultSet = statement.executeQuery(sql);
			// չ����������ݿ�
			while (resultSet.next()){
				//ͨ���ֶμ���
				String username=resultSet.getString("username");
				String pwd=resultSet.getString("password");
				String role=resultSet.getString("role");
				//�������
				System.out.println(username+";"+pwd+";"+role);
			}
			String name="wang";
			String pwd="123";
			String role="browser";
			String sql2 = "INSERT INTO user_info(username,password,role) VALUES ('" + name +"','" +pwd+"','"+role+"')";
			String sql1 = "INSERT INTO user_info(username,password,role) VALUES ('wan','123','browser')";
			System.out.println(sql1.equals(sql2));
			statement.executeUpdate(sql2);
			//��ɺ�ر�
			resultSet.close();                        
            statement.close();                        
            connection.close();  
	    }catch(ClassNotFoundException e ){
	    	System.out.println("������������");
	    }catch(SQLException e){
	    	System.out.println("���ݿ����");
	    	e.printStackTrace();
	    }finally{
			//�ر���Դ
			try{
				if(statement!=null)
					statement.close();
			}catch(SQLException se2){
			}// ʲô������
			try{
				if(connection!=null)
					connection.close();
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
	}

}
