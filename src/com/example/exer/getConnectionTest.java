package com.example.exer;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Properties;

import org.junit.Test;

public class getConnectionTest {
	@Test
	public void getInstance() throws Exception {
		InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("info.properties");
		Properties pros = new Properties();
		pros.load(is);
		
		String driver = pros.getProperty("driver");
		String url = pros.getProperty("url");
		String user = pros.getProperty("user");
		String password = pros.getProperty("password");
		
		Class<?> clazz = Class.forName(driver);
		Driver newInstance = (Driver)clazz.newInstance();
		DriverManager.registerDriver(newInstance);
		
		Connection conn = DriverManager.getConnection(url, user, password);
		System.out.println(conn);
		
				
		
	}

}
