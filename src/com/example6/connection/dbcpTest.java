package com.example6.connection;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.junit.Test;

public class dbcpTest {
	//方式一
	@Test
	public void testGetConnection() throws SQLException {
		//创建了dbcp的数据库连接池
		BasicDataSource data =  new BasicDataSource();
		
		//设置基本信息
		data.setUsername("root");
		data.setPassword("root");
		data.setDriverClassName("com.mysql.jdbc.Driver");
		data.setUrl("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8");
		
		Connection conn = data.getConnection();
		System.out.println(conn);
	}
	
	@Test
	public void testGetConnection1() throws Exception {
		Properties pros = new Properties();
		InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("dbcp.properties");
		
		pros.load(is);
		
		DataSource source = BasicDataSourceFactory.createDataSource(pros);
		Connection conn = source.getConnection();                                                                                                        
		System.out.println(conn);
		
	}

}
