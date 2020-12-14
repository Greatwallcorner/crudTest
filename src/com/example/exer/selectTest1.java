package com.example.exer;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Scanner;

import org.junit.Test;

import com.example.utils.ExamStudent;
import com.example.utils.JDBCUtils;
import com.example.utils.UserSelectUtils;

public class selectTest1 {
	
	@Test
	public void delete() {
		Scanner scan = new Scanner(System.in);
		System.out.println("请输入学生的考号：");
		String nextInt = scan.next();
		deleteStudentInfo(nextInt);
	}
	
	public void deleteStudentInfo(String number) {
		String sql = "delete from ExamStudent where ExamCard = ?";
		int state = update(sql, number);
		if(state > 0) {
			System.out.println("删除成功");
		}else {
			System.out.println("查无此人，请重新进入程序");
		}
	}
	
	
	@Test
	public void query() {
		Scanner scan = new Scanner(System.in);
		//输入身份证号或准考证号可以查询到学生的基本信息
		
			String info = null;
			String type = UserSelectUtils.getCertificateType();
			switch (type) {
			case "a":
				System.out.print("请输入准考证号：");
				info = scan.next();
				selectByIDCard(info);
				break;
			case "b":
				System.out.println("请输入身份证号：");
				info = scan.next();
				selectByExamCard(info);
				break;
			default:
				break;
			}
			
		
		
	}

	
	public void selectByIDCard(String info) {
		String sql = "select FlowID FlowId,Type,IDCard,ExamCard,StudentName,Location,Grade from examstudent where ExamCard=?";
		ExamStudent query1 = query1(ExamStudent.class,sql,info);
		showStudentInfo(query1);
//		return query1;
//		if(query1 != null) {
//			showStudentInfo(query1);
//		}else {
//			System.out.println("查无此人，请重新进入程序");
//		}
	}
	
	
	public void selectByExamCard(String info) {
		String sql = "select FlowID FlowId,Type,IDCard,ExamCard,StudentName,Location,Grade from examstudent where IDCard=?";
		ExamStudent query1 = query1(ExamStudent.class,sql,info);
		showStudentInfo(query1);
//		return query1;
//		if(query1 != null) {
//			showStudentInfo(query1);
//		}else {
//			System.out.println("查无此人，请重新进入程序");
//		}
	}
	
	private void showStudentInfo(ExamStudent stu) {
		/*
		 * 
		 * 
		 */
		if(stu != null) {
			
			System.out.println("===============查询结果===============");
			System.out.println("流水号："+stu.getFlowId());
			System.out.println("四级/六级："+ stu.getType());
			System.out.println("身份证号："+ stu.getIDCard());
			System.out.println("准考证号："+ stu.getExamCard());
			System.out.println("学生姓名："+stu.getStudentName());
			System.out.println("区域："+stu.getLocation());
			System.out.println("成绩："+stu.getGrade());
		}else {
			System.out.println("查无此人，请重新进入程序");
		}
	}
	
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
					
					Field field = clazz.getDeclaredField(columnLabel);
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
	
	/**
	 * 
	 * @Description: 使用时只提供sql语句和填充占位符的参数
	 * @Title: update   
	 * @return:   void
	 *
	 */
	public int update(String sql , Object ...args) {
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
			return ps.executeUpdate();
			//关闭资源
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			
			JDBCUtils.closeResource(conn, ps);
		}
		return -1;
	}

}
