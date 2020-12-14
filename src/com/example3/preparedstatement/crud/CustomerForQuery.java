package com.example3.preparedstatement.crud;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.junit.Test;

import com.example.bean.Customer;
import com.example.utils.JDBCUtils;

public class CustomerForQuery {
	@Test
	public void testQueryForCustomer() {
		String sql = "select id,name,email,birth from customers where id=? ";
		Customer customer = queryForCustomer(sql, 1);
		System.out.println(customer);
	}
	
	/**
	 * 
	 * @Description: 针对customers表的通用查询操作
	 * @Title: 	queryForCustomer   
	 * @return:	void
	 * @throws Exception 
	 * @Time:	2020年9月14日下午7:22:06
	 */
	public Customer queryForCustomer(String sql,Object ...args){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			//连接数据库
			conn = JDBCUtils.getConnection();
			//预编译数据库
			ps = conn.prepareStatement(sql);
			
			//填充替位符
			for(int i = 0; i < args.length ; i++) {
				ps.setObject(i + 1, args[i]);
			}
			
			//执行
			rs = ps.executeQuery();
			
			//操作结果集
			if (rs.next()) {
				Customer customer = new Customer();
				//得到总列数
				ResultSetMetaData mData = rs.getMetaData();
				for (int i = 0; i < mData.getColumnCount(); i++) {
					Object columnvalue = rs.getObject(i + 1);
					//得到列名称
					String columnName = mData.getColumnName(i + 1);
					//利用反射得到类的同名属性，将从数据库中得到的数据赋给类的属性
					Field field = Customer.class.getDeclaredField(columnName);
					field.setAccessible(true);
					field.set(customer, columnvalue);
					
				}
				return customer;
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
	
	@Test
	public void testQuery(){
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			//获取连接
			conn = JDBCUtils.getConnection();
			//预编译sql语句
			String sql = "select * from customers where id=?";
			ps = conn.prepareStatement(sql);
			//填充占位符
			ps.setObject(1, 2);
			
			//执行
			rs = ps.executeQuery();
			
			
			//处理resultset
			if(rs.next()) {//resultset的next方法，返回一个布尔值判断下一个位置是否有值，如果有返回true
							//，否则反之
				int id = rs.getInt(1);
				String name=rs.getString(2);
				String email = rs.getString(3);
				Date birth = rs.getDate(4);
				Customer customer = new Customer(id, name, email, birth);
				System.out.println(customer);
			}
			
		}  catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			//关闭资源
			JDBCUtils.closeResource(conn, ps, rs);
			
		}
		
		
	}

}
