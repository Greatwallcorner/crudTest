package com.example4.transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.Test;

import com.example.utils.JDBCUtils;

public class tansactionTest {
	
	
	/*
	 * 
	 * 【案例：用户AA向用户BB转账100】
	 * 数据一旦提交就无法回滚
	 * 数据在连接关闭后
	 * 
	 * String sql = "update user_table set balance = balance + 10 where user = ?";
	 * 
	 * String sql = "update user_table set balance = balance - 10 where user = ?";
	 */
	
	
	/**
	 * 
	 * @Description:加入事务处理，数据库连接不关闭，如果出现意外数据可回滚 ,但是要把autocommit设置为flase
	 * @Title: 	updateTest   
	 * @return:	void
	 * @Time:	2020年9月19日上午10:36:18
	 */
	@Test
	public void updateTest1(){
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			conn.setAutoCommit(false);
			String sql = "update user_table set balance = balance + 10 where user = ?";
			update1(conn,sql, "AA");
			
			System.out.println(10/0);
			String sql2 = "update user_table set balance = balance - 10 where user = ?";
			update1(conn,sql2, "BB");
		} catch (Exception e) {
			try {
				conn.rollback();//回滚
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			e.printStackTrace();
		}finally {
			
			JDBCUtils.closeResource(conn, null);
		}
		
		
		
	}
	
	/**
	 * 
	 * @Description: 无事务处理，两个操作分开，如果意外中断，对于数据的改变不一致
	 * @Title: 	updateTest   
	 * @return:	void
	 * @Time:	2020年9月19日上午10:33:59
	 */
@Test
public void updateTest() throws Exception {
	Connection conn = JDBCUtils.getConnection();
	
	String sql = "update user_table set balance = balance + 10 where user = ?";
	update(sql, "AA");
	
	System.out.println(10/0);
	String sql2 = "update user_table set balance = balance - 10 where user = ?";
	update(sql2, "BB");
	
	
}

/**
 * 
 * @Description: 连接依靠外部传入,在外部关闭，保证事务完成
 * @Title: 	update   
 * @return:	void
 * @Time:	2020年9月19日上午10:38:31
 */
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
}
