package com.example.exer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

import org.junit.Test;

import com.example.utils.JDBCUtils;

public class insertTest1 {
	
	@Test
	public void insetExamStudent() {
		Scanner scan = new Scanner(System.in);
		
		System.out.println("请输入学生的详细信息：");
		/*
		 * 	Type: 
			IDCard:
			ExamCard:
			StudentName:
			Location:
			Grade:
		 */
		System.out.println("四/六级：");
		int Type = scan.nextInt();
		System.out.println("身份证号：");
		String IDCard = scan.next();
		System.out.println("准考证号：");
		String  ExamCard = scan.next();
		System.out.println("学生姓名：");
		String StudentName = scan.next();
		System.out.println("区域：");
		String Location = scan.next();
		System.out.println("成绩：");
		String Grade = scan.next();
		String sql = "insert into examstudent(Type,IDCard,ExamCard,StudentName,Location,Grade)values(?,?,?,?,?,?)";
		int update = update(sql, Type,IDCard,ExamCard,StudentName,Location,Grade);
	
		if(update > 0) {
			System.out.println("信息录入成功");
		}else {
			System.out.println("信息录入成功");
		}
	}
	
	
	//插入一个customer信息
	@Test
	public void insertOne() {
		Scanner scan = new Scanner(System.in);
		System.out.println("请输入姓名：");
		String name = scan.next();
		System.out.println("请输入邮箱：");
		String email = scan.next();
		System.out.println("请输入生日：");
		String birth = scan.next();
		
		String sql = "insert into customers(name,email,birth) values(?,?,?)";
		int update = update(sql, name,email,birth);
		
		if(update > 0) {
			System.out.println("注册成功");
		}else {
			System.out.println("注册失败");
		}
		
		
		
		scan.close();
		
	}
	
	
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
