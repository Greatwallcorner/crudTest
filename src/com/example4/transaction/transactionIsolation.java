package com.example4.transaction;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import org.junit.Test;

import com.example.utils.JDBCUtils;

public class transactionIsolation {
	/*
	 * 
	 * 
	 */
	@Test
	public void testTrasactionIsolation() throws Exception {
		Connection conn = JDBCUtils.getConnection();
		conn.setAutoCommit(false);
		conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
		String sql = "select user,password,balance from user_table where user = ?";
		for (int i = 0; i < 50; i++) {
			User user = query1(conn, User.class, sql, "CC");
			System.out.println(user);
			Thread.sleep(200);
		}
		
	}
	/*
	 * 不可重复读：当一个连接未提交时，其他链接改变了数据，但是在这个链接里，多次查询的数据是一致的
	 * 事务得ACID：
	 * 原子性：
	 * 一致性：
	 * 隔离性：
	 * 持久性：一旦commit，数据就不可在改变
	 *
	 */
	
	@Test
	public void testUpdate() throws Exception {
		Connection conn = JDBCUtils.getConnection();
		conn.setAutoCommit(false);
		conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
		String sql = "update user_table set balance = 6704 where user = ?";
		update1(conn, sql, "CC");
		conn.commit();
		Thread.sleep(15000);
		JDBCUtils.closeResource(conn, null);
		
	}
	
	public void update1(Connection conn,String sql , Object ...args) {
		//预编译sql语句
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			//填充占位符
			for (int i = 0; i < args.length; i++) {
				ps.setObject(i + 1, args[i]);
			}
			//执行
			ps.execute();
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			
			//关闭资源
			JDBCUtils.closeResource(null, ps);
		}
	}
	

	//查询操作
	public static <T> T query1(Connection conn,Class<T> clazz,String sql , Object ...args) {//泛型方法
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			//预编译sql语句
			ps = conn.prepareStatement(sql);
			
			//填充sql语句中的占位符
			for (int i = 0; i < args.length; i++) {
				ps.setObject(i + 1, args[i]);
			}
			//执行preparedStatement,并得到ResultSet对象
			rs = ps.executeQuery();
			
			//处理resultset返回数据
			ResultSetMetaData mData = rs.getMetaData();
			int columnCount = mData.getColumnCount();
			if (rs.next()) {
				T instance = clazz.newInstance();
				for(int i = 0; i < columnCount; i++) {
					Object columnValue = rs.getObject(i + 1);
					String columnLabel = mData.getColumnLabel(i + 1);
//					System.out.println(columnLabel);
					
					Field field = instance.getClass().getDeclaredField(columnLabel);
					field.setAccessible(true);
					field.set(instance, columnValue);
				}
				return instance;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//关闭资源
			JDBCUtils.closeResource(null, ps, rs);
		}
		return null;
	}
}
