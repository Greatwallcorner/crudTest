package com.example1.connections;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.junit.Test;

public class ConnectionTest {
	public static void main(String[] args) {
		try {
			Driver dri = new com.mysql.jdbc.Driver();

			
			String url = "jdbc:mysql://localhost:3306/test";
			Properties info = new Properties();
			info.setProperty("user", "root");
			info.setProperty("password", "root");
			
			Connection con = dri.connect(url, info);
			
			System.out.println(con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private String user;

	
@Test
public void test2() {
	//利用反射获得实例
	try {
		Class clazz = Class.forName("com.mysql.jdbc.Driver");
		Driver dri = (Driver)clazz.newInstance();
		
		//准备要传入的参数
		String url = "jdbc:mysql://localhost:3306/test";
		Properties info = new Properties();
		info.setProperty("user", "root");
		info.setProperty("password","root");
		
		Connection connection = dri.connect(url, info);
		System.out.println(connection);
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (InstantiationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IllegalAccessException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	
}


//使用DriverManager替代Driver
@Test
public void test3() {
	//得到Driver
	try {
		Class clazz = Class.forName("com.mysql.jdbc.Driver");
		Driver instance = (Driver)clazz.newInstance();
		DriverManager.registerDriver(instance);
		
		String password = "root";
		String user = "root";
		String url = "jdbc:mysql://localhost:3306/test";
		Connection connection = DriverManager.getConnection(url, user, password);
		
		System.out.println(connection);
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (InstantiationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IllegalAccessException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}

//最终版 从properties文件中读取 所需要的信息
//使得代码与数据分离
@Test
public void test4() {
	//得到Driver
	try {
		String password = "root";
		String user = "root";
		String url = "jdbc:mysql://localhost:3306/test";
		
		
		
		Class clazz = Class.forName("com.mysql.jdbc.Driver");
		//省略注册步骤 mysql会帮我们做
		
		Connection connection = DriverManager.getConnection(url, user, password);
		
		System.out.println(connection);
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}


@Test
public void test5() {

	try {
		Properties pros = new Properties();
		pros.load(ConnectionTest.class.getClassLoader().getResourceAsStream("info.properties"));
		String user =pros.getProperty("user");
		String password =  pros.getProperty("password");
		String url = pros.getProperty("url");
		String driver = pros.getProperty("driver");
		Class clazz = Class.forName(driver);
		
		
	
		Connection connection = DriverManager.getConnection(url, user, password);
		System.out.println(connection);
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	
}
@Test
public void test6() {
	try {
		InputStream is= ConnectionTest.class.getClassLoader().getResourceAsStream("info.properties");
		
		Properties pros = new Properties();
		pros.load(is);
		
		String user = pros.getProperty("user");
		String password = pros.getProperty("password");
		String url = pros.getProperty("url");
		String driver = pros.getProperty("driver");
		
		Class clazz = Class.forName(driver);
		
		Connection conn = DriverManager.getConnection(url,user,password);
		System.out.println(conn);
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}
/*
 * 使用DriverManager链接数据库，需要四个参数url，user，password，Driver（利用反射（可以只写froName，剩下的自动
 * 会做））
 * 使用Properties可以实现代码与数据的分离
 * 
 */
@Test
public void test7() {
	try {
		InputStream is = ConnectionTest.class.getClassLoader().getResourceAsStream("info.properties");
		Properties pros = new Properties();
		pros.load(is);
		String password = pros.getProperty("password");
		String url = pros.getProperty("url");
		String user = pros.getProperty("user");
		String driver = pros.getProperty("driver");
		Class clazz = Class.forName(driver);
		Driver instance = (Driver)clazz.newInstance();
		DriverManager.registerDriver(instance);
		
		Connection connection = DriverManager.getConnection(url, user, password);
		
		System.out.println(connection);
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (InstantiationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IllegalAccessException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}


}





