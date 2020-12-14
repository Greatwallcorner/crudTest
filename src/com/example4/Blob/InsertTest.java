package com.example4.Blob;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.Test;

import com.example.utils.JDBCUtils;

public class InsertTest {
	/*
	 * 
	 * 在上一个方法的基础上  设置自动提交为false 在所有执行完毕后提交
	 */
	@Test
	public void insertBatched2() throws Exception {
		Connection conn = JDBCUtils.getConnection();
		conn.setAutoCommit(false);
		String sql = "insert into goods(name) values(?) ";
		PreparedStatement ps = conn.prepareStatement(sql );
		long start = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			ps.setString(1, "name——"+ i);
			ps.addBatch();
			
			if(i % 500 == 0) {
				ps.executeBatch();
				ps.clearBatch();
			}
			
		}
		conn.commit();
		long end = System.currentTimeMillis();
		System.out.println("花费的时间为："+(end - start));//批处理20000：485
														//批处理1000000：23995
														//关闭自动提交：12217
		//5.1.7：73794  5.1.37：85449
		
		JDBCUtils.closeResource(conn, ps);
		
	}
	
	//批量插入   mysql 默认不支持批处理 需要在url后面加上 ?rewriteBatchedStatements=true 参数
	@Test
	public void insertBatched() throws Exception {
		Connection conn = JDBCUtils.getConnection();
		String sql = "insert into goods(name) values(?) ";
		PreparedStatement ps = conn.prepareStatement(sql );
		long start = System.currentTimeMillis();
		for (int i = 0; i <= 1000000; i++) {
			ps.setString(1, "name——"+ i);
			ps.addBatch();
			
			if(i % 500 == 0) {
				ps.executeBatch();
				ps.clearBatch();
			}
			
		}
		
		long end = System.currentTimeMillis();
		System.out.println("花费的时间为："+(end - start));//批处理20000：485
														//批处理1000000：23995
		//5.1.7：73794  5.1.37：85449
		
		JDBCUtils.closeResource(conn, ps);
		
	}
	/*
	 * 如果通过statement，则每次插入都要生成一个String型sql语句
	 * preparedStatement：
	 */
	
	@Test
	public void insertByPs() throws Exception {
		Connection conn = JDBCUtils.getConnection();
		String sql = "insert into goods(name) values(?) ";
		PreparedStatement ps = conn.prepareStatement(sql );
		long start = System.currentTimeMillis();
		for (int i = 0; i < 20000; i++) {
			ps.setString(1, "name——"+ i);
			ps.execute();
		}
		
		long end = System.currentTimeMillis();
		System.out.println("花费的时间为："+(end - start));//61216 mills
		
		JDBCUtils.closeResource(conn, ps);
	}

}
