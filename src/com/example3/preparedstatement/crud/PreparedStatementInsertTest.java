package com.example3.preparedstatement.crud;

import java.io.InputStream;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.junit.Test;

import com.example.utils.JDBCUtils;
/**
 * @Class_Name PreparedStatementInsertTest
 * @author heatdesert
 * @Description 
 * 2020年9月13日 下午5:21:22
 */
public class PreparedStatementInsertTest {
	
	@Test
	public void testCommonUpdate() {
//		String sql = "update customers set name=? where id=?";
//		update(sql, "风采不在的纳豆奶奶",19);
		
		String sql = "delete from customers where id=?";
		update(sql, 12);
	}
	/**
	 * 
	 * @Description: 使用时只提供sql语句和填充占位符的参数
	 * @Title: update   
	 * @return:   void
	 *
	 */
	public void update(String sql , Object ...args) {
		//1.获取连接
		Connection conn = null;
		//预编译sql语句
		PreparedStatement ps = null;
		try {
			conn = JDBCUtils.getConnection();
			ps = conn.prepareStatement(sql);
			//填充占位符
			for (int i = 0; i < args.length; i++) {
				ps.setObject(i + 1, args[i]);
			}
			//执行
			ps.execute();
			//关闭资源
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
			JDBCUtils.closeResource(conn, ps);
		}
	}
	
	@Test
	public void updateTest() throws Exception {
		PreparedStatement ps = null;
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			String sql = "update customers set name=? where name= ?";
			ps = conn.prepareStatement(sql );
			ps.setString(1, "凉风");
			ps.setString(2, "??");
			ps.execute();
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {		
				JDBCUtils.closeResource(conn, ps);
		}
	}
	@Test
	public void inserttest() throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("info.properties");
			Properties pros = new Properties();
			pros.load(is);
			
			String driver = pros.getProperty("driver");
			String user = pros.getProperty("user");
			String password = pros.getProperty("password");
			String url = pros.getProperty("url");
			
			Class clazz = Class.forName(driver);
			
			conn = DriverManager.getConnection(url, user, password);
			
//		System.out.println(conn);
			//预编译sql语句
			String sql = "insert into customers(name,email,birth)values(?,?,?)";//占位符
			//填充占位符
			ps = conn.prepareStatement(sql);
			ps.setString(1,"凉风有名");
			ps.setString(2, "tutou@qq.com");
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date parse = sdf.parse("1987-1-23");
			
			ps.setDate(3, new java.sql.Date(parse.getTime()));
			
			ps.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if (ps!= null) {
				ps.close();				
			}
			
			if (conn != null) {
				conn.close();				
			}
			
		}
		
		
		
	}

}
