package com.example7.dbutils;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import com.example.bean.Customer;
import com.example6.util.JDBCUtils;

public class DBUtilsTest {
	@Test
	public void getConnection1() throws Exception {
		String url = "jdbc:mysql://localhost:3306/test";
		String user= "root";
		String password = "root";
		Class<?> name = Class.forName("com.mysql.jdbc.Driver");
		Object driver = name.newInstance();
		DriverManager.registerDriver((Driver) driver);
		Connection conn = DriverManager.getConnection(url, user, password);
		System.out.println(conn);
	}
	
	@Test
	public void BlobTest() throws Exception {
		Connection conn = JDBCUtils.getConnection3();
		String sql = "insert into customers(name,email,birth,photo) values(?,?,?,?)";
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setObject(1, "风景画");
		ps.setObject(2, "painting@qq.com");
		ps.setObject(3, "1897-9-7");
		FileInputStream is = new FileInputStream("src/photo.jpg");
		ps.setBlob(4, is);
		
		ps.execute();
		
		JDBCUtils.closeResource(conn, ps);
	}
	@Test
	public void testInsert(){
		Connection conn = null;
		try {
			QueryRunner runner = new QueryRunner();
			
			conn = JDBCUtils.getConnection3();
			String sql = "insert into customers(name,email,birth)values(?,?,?)";
			int count = runner.update(conn, sql, "潘唐颖","pantangying@qq.com","1998-08-09");
			System.out.println("影响了"+count+"行");
		}  catch (Exception e) {
			e.printStackTrace();
		}finally {
			
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		
		
	}
	//看文档很关键
	
	@Test
	public void testQuery() throws Exception {
			QueryRunner runner = new QueryRunner();
			
			Connection conn = JDBCUtils.getConnection3();
			String sql = "select name,email,birth from customers where id < ? ";
			BeanListHandler<Customer> rsh = new BeanListHandler<>(Customer.class);
			List<Customer> list = runner.query(conn, sql, rsh, 7);
			list.forEach(System.out::println);
			
			
		}
	
	//ScalarHandler查询特殊值 count（*）
	@Test
	public void testQuery1() throws Exception {
			QueryRunner runner = new QueryRunner();
			
			Connection conn = JDBCUtils.getConnection3();
			String sql = "select COUNT(*) from customers";
			ScalarHandler handler= new ScalarHandler();
			Object query = runner.query(conn, sql, handler);
			System.out.println(query);
			
			
		}

}
