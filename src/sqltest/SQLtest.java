package sqltest;

import java.sql.*;

/**
 * @author 鑫
 */
public class SQLtest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet;
		// 加载数据库驱动类
		String driverName="com.mysql.cj.jdbc.Driver";
		// 声明数据库的URL
		String url="jdbc:mysql://localhost:3306/new_schema";
		// 数据库用户
	    String user="root";
	    String password="1234321wx";
	    try{
			//注册JDBC驱动
	    	Class.forName(driverName);
			// 建立数据库连接
			connection=DriverManager.getConnection(url, user, password);
			//实例化Statement对象
			statement = connection.createStatement( 
			         ResultSet.TYPE_SCROLL_INSENSITIVE,
			         ResultSet.CONCUR_READ_ONLY );
			//执行查询
			String sql="select * from user_info";
			resultSet = statement.executeQuery(sql);
			// 展开结果集数据库
			while (resultSet.next()){
				//通过字段检索
				String username=resultSet.getString("username");
				String pwd=resultSet.getString("password");
				String role=resultSet.getString("role");
				//输出数据
				System.out.println(username+";"+pwd+";"+role);
			}
			String name="wang";
			String pwd="123";
			String role="browser";
			String sql2 = "INSERT INTO user_info(username,password,role) VALUES ('" + name +"','" +pwd+"','"+role+"')";
			String sql1 = "INSERT INTO user_info(username,password,role) VALUES ('wan','123','browser')";
			System.out.println(sql1.equals(sql2));
			statement.executeUpdate(sql2);
			//完成后关闭
			resultSet.close();                        
            statement.close();                        
            connection.close();  
	    }catch(ClassNotFoundException e ){
	    	System.out.println("数据驱动错误");
	    }catch(SQLException e){
	    	System.out.println("数据库错误");
	    	e.printStackTrace();
	    }finally{
			//关闭资源
			try{
				if(statement!=null)
					statement.close();
			}catch(SQLException se2){
			}// 什么都不做
			try{
				if(connection!=null)
					connection.close();
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
	}

}
