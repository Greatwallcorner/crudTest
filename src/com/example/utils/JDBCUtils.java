package com.example.utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.mysql.jdbc.Statement;

public class JDBCUtils {
	/**
	 * 
	 * @Description: 使用从properties文件中读取到的参数使用preparedStatement获取Connection对象
	 * url,user,password,driver四大参数
	 * @Title: 	getConnection   
	 * @return:	Connection
	 * @Time:	下午4:54:08
	 */
	public static Connection getConnection() throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		
		InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("info.properties");
		Properties pros = new Properties();
		pros.load(is);
		
		String driver = pros.getProperty("driver");
		String user = pros.getProperty("user");
		String password = pros.getProperty("password");
		String url = pros.getProperty("url");
		
		Class clazz = Class.forName(driver);//获得注册驱动的对象
//		Object driver1 = clazz.newInstance();
//		DriverManager.registerDriver((Driver) driver1);
		//可省略以上两步
		
		return conn = DriverManager.getConnection(url, user, password);
	}
	 /**
	  * 
	  * @Description: 关闭Connection和preparedStatement连接
	  * @Title: 	closeResource   
	  * @return:	void
	  * @Time:	2020年9月14日下午5:00:14
	  */
	public static void closeResource(Connection conn , java.sql.Statement state) {
		if (state!= null) {
			try {
				state.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}				
		}
		
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}				
		}
		
	}
	
	
	public static void closeResource(Connection conn , java.sql.Statement state , ResultSet rs) {
		if (state!= null) {
			try {
				state.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}				
		}
		
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}				
		}
		
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}				
		}
		
	}

}
