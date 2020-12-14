package com.example6.connection;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;

import org.junit.Test;

import com.example.bean.Customer;
import com.example6.util.JDBCUtils;
import com.example5.dao.CustomerDaoImpl;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3P0Test {
	@Test
	public void testGetConnenction() throws Exception {
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		cpds.setDriverClass( "com.mysql.jdbc.Driver" ); //loads the jdbc driver            
		cpds.setJdbcUrl( "jdbc:mysql://localhost:3306/test" );
		cpds.setUser("root");                                  
		cpds.setPassword("root");   
		
		cpds.setInitialPoolSize(12);
		
		Connection connection = cpds.getConnection();
		System.out.println(connection);
	}
	//通过xml文件配置基本信息 --- c3p0-config.xml
	@Test
	public void testGetConnection1() throws Exception {
		ComboPooledDataSource source = new ComboPooledDataSource("helloc3p0");
		Connection conn = source.getConnection();
		System.out.println(conn);
		
	}
	
	@Test
	public void getC3P0Connection() throws Exception {
		Connection connection1 = com.example6.util.JDBCUtils.getConnection1();
		
	}
	
	
	CustomerDaoImpl dao = new CustomerDaoImpl();
	@Test
	public void testInsert() {
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection2();
			Customer cust = new Customer(1, "意外之喜", "hopewalk@qq.com", new Date(235436234345753L));
			dao.insert(conn, cust);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtils.closeResource(conn, null);
		}
	}

	@Test
	public void testDeleteById() {
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection1();
			dao.deleteById(conn, 27);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			
			JDBCUtils.closeResource(conn, null);
		}
	}

	@Test
	public void testUpdateConnectionCustomer() {
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection3();
			Customer cust = new Customer(23, "与神同行", "withGodWalk@126.com", new Date(543542353252L));
			dao.update(conn, cust);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			
			JDBCUtils.closeResource(conn, null);
		}
	}

	
	
}
