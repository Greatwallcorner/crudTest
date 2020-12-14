package com.example6.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JDBCUtils {
	//c3p0获取连接
	public static Connection getConnection1() throws Exception {
		ComboPooledDataSource source = new ComboPooledDataSource("helloc3p0");
		Connection conn = source.getConnection();
		
		return conn;
	}
	
	//德鲁伊数据库连接池
	private static DataSource source1 = null;
	static {
		try {
			Properties pros = new Properties();
			InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("Druid.properties");
			pros.load(is);
			source1 = DruidDataSourceFactory.createDataSource(pros );
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static Connection getConnection3() throws Exception {
		Connection conn = source1.getConnection();
		return conn;
	}
	
	
	//创建数据库连接池——保证只有一个数据库连接池——静态代码块只执行一次
	private static DataSource source = null;
	static {
		try {
			Properties pros = new Properties();
			InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("dbcp.properties");
			
			pros.load(is);
			source = BasicDataSourceFactory.createDataSource(pros);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection2() throws Exception {
		Connection conn = source.getConnection();
		return conn;
	}
	
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
