package com.example3.preparedstatement.crud;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.junit.Test;

import com.example.bean.Customer;
import com.example.bean.Order;
import com.example.utils.JDBCUtils;

public class CommonForQuery {
	@Test
	public void testQueryForList() {
		String sql="select id,name,email,birth from customers where id<?";
		List<Customer> list = queryForList(Customer.class, sql, 12);
		list.forEach(System.out::println);//Lambda表达式
	}
//	Consumer<Customer> con = new Consumer<Customer>() {
//
//		@Override
//		public void accept(Customer t) {
//			// TODO Auto-generated method stub
//			
//		}
//		
//	};
	/**
	 * 
	 * @Description: 使查询可以返回list型得集合
	 * @Title: 	queryForList   
	 * @return:	List<T>
	 * @Time:	2020年9月15日下午5:25:08
	 */
	
	public <T> List<T> queryForList(Class<T> clazz,String sql , Object ...args){
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			//获取数据库连接
			conn = JDBCUtils.getConnection();
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
			ArrayList<T> list = new ArrayList<T>();
			while(rs.next()) {
				T instance = clazz.newInstance();
				for(int i = 0; i < columnCount; i++) {
					Object columnValue = rs.getObject(i + 1);
					String columnLabel = mData.getColumnLabel(i + 1);
//					System.out.println(columnLabel);
					
					Field field = instance.getClass().getDeclaredField(columnLabel);
					field.setAccessible(true);
					field.set(instance, columnValue);
				}
				list.add(instance);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//关闭资源
			JDBCUtils.closeResource(conn, ps, rs);
		}
		return null;
		
	}
	@Test
	public void testQuery() {
//		String sql="select order_id orderId,order_name orderName,order_date orderDate from `order` where order_id=?";
		String sql1="select id,name,email,birth from customers where id=?";
//		Object query = query(sql, Order.class, 1);
//		System.out.println(query);
		
		Object query = query( Customer.class,sql1, 1);
		System.out.println(query);
		
		Object query2 = query(Customer.class,sql1,13);
		System.out.println(query2);
	}
	/**
	 * 
	 * @Description: 使用泛型作为返回值 ，使用Class对象创建对象
	 * @Title: 	query1   
	 * @return:	T
	 * @Time:	2020年9月15日下午5:10:05
	 */
	
	
	public static <T> T query1(Class<T> clazz,String sql , Object ...args) {//泛型方法
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			//获取数据库连接
			conn = JDBCUtils.getConnection();
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
			JDBCUtils.closeResource(conn, ps, rs);
		}
		return null;
	}
	
	public static Object query( Class<?> clazz,String sql ,Object ...args) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			//获取数据库连接
			conn = JDBCUtils.getConnection();
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
				Object instance = clazz.newInstance();
				for(int i = 0; i < columnCount; i++) {
					Object columnValue = rs.getObject(i + 1);
					String columnLabel = mData.getColumnLabel(i + 1);
//					System.out.println(columnLabel);
					
					Field field = clazz.getDeclaredField(columnLabel);
//					Field field = instance.getClass().getDeclaredField(columnLabel);
					field.setAccessible(true);
					field.set(instance, columnValue);
				}
				return instance;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//关闭资源
			JDBCUtils.closeResource(conn, ps, rs);
		}
		return null;
	}

}
