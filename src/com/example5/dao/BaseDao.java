package com.example5.dao;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.utils.JDBCUtils;
/*
 * 
 * 针对数据表的增删改查操作
 */

public abstract class BaseDao<T> {
	Class<T> clazz = null;
	
	{
		Type superclass = this.getClass().getGenericSuperclass();
		ParameterizedType type = (ParameterizedType) superclass;
		
		Type[] typeArguments = type.getActualTypeArguments();
		clazz = (Class<T>) typeArguments[0];
		
	}
	
	public void update(Connection conn,String sql , Object ...args) {
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
		public  T getInstance(Connection conn,String sql , Object ...args) {//泛型方法
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
//						System.out.println(columnLabel);
						
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
		
		
		public  List<T> getForList(Connection conn,String sql , Object ...args){
			
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
				ArrayList<T> list = new ArrayList<T>();
				while(rs.next()) {
					T instance = clazz.newInstance();
					for(int i = 0; i < columnCount; i++) {
						Object columnValue = rs.getObject(i + 1);
						String columnLabel = mData.getColumnLabel(i + 1);
//						System.out.println(columnLabel);
						
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
				JDBCUtils.closeResource(null, ps, rs);
			}
			return null;
			
		}
		//用于返回特殊值
		public <E> E getValue(Connection conn,String sql , Object...args){
			PreparedStatement ps = null;
			ResultSet rs = null;
			try {
				ps = conn.prepareStatement(sql);
				for(int i = 0; i < args.length ;i++) {
					ps.setObject(i + 1, args[i]);
				}
				
				rs = ps.executeQuery();
				
				if(rs.next()) {
					return (E) rs.getObject(1);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				JDBCUtils.closeResource(null, ps, rs);
			}
			return null;
			
			
		}

}
