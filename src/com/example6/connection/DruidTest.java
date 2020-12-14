package com.example6.connection;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

import javax.sql.DataSource;

import org.junit.Test;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

public class DruidTest {
//	private static DataSource source = null;
//	static {
//		try {
//			Properties pros = new Properties();
//			InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("Druid.properties");
//			pros.load(is);
//			source = DruidDataSourceFactory.createDataSource(pros );
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//	}
	@Test
	public  void testGetConnection() throws Exception {
		
		Properties pros = new Properties();
		InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("Druid.properties");
		pros.load(is);
		DataSource source = DruidDataSourceFactory.createDataSource(pros );
		Connection conn = source.getConnection();
		System.out.println(conn);
	}

}
