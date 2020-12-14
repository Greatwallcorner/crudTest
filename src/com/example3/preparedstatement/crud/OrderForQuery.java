package com.example3.preparedstatement.crud;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import org.junit.Test;

import com.example.bean.Order;
import com.example.utils.JDBCUtils;

public class OrderForQuery {
	
	@Test
	public void testQueryForOrder() {
		String sql="select order_id orderId,order_name orderName,order_date orderDate from `order` where order_name=?";
		Order order = queryForOrder(sql, "AA");
		System.out.println(order);
	}
	/**
	 * 
	 * @Description: 针对order表的通用查询操作——注意要用反射将数据库中的数据存入对象中，就必须保证
	 * 类中的名字与数据库表的名字一致——解决这个问题可以为sql语句中的参数取别名，通过getColumnLabel取得列的别名
	 * @Title: 	queryForOrder   
	 * @return:	void
	 * @throws Exception 
	 * @Time:	2020年9月14日下午8:33:15
	 */
	public Order queryForOrder(String sql,Object ...args){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			//获取数据库连接
			conn = JDBCUtils.getConnection();
			//预编译sql语句
			ps = conn.prepareStatement(sql);
			
			//填充替位符
			for (int i = 0; i < args.length; i++) {
				ps.setObject(i + 1, args[i]);
			}
			
			//执行
			rs = ps.executeQuery();
			
			//处理结果集
			if (rs.next()) {
				Order order = new Order();
				
				//获得从结果集的元数据中获得列数
				ResultSetMetaData mData = rs.getMetaData();
				int columnCount = mData.getColumnCount();
				for (int i = 0; i < columnCount; i++) {
//					String columnName = mData.getColumnName(i+1);
					String columnLabel = mData.getColumnLabel(i + 1);
					Object columValue = rs.getObject(i + 1);
					
					//使用反射将值赋给order对象
					Field field = Order.class.getDeclaredField(columnLabel);
					field.setAccessible(true);
					field.set(order, columValue);
					
				}
				return order;
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			//关闭资源
			JDBCUtils.closeResource(conn, ps, rs);
		}
		
	
		return null;
	}

}
