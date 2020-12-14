package com.example5.dao.junit;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import org.junit.Test;

import com.example.bean.Customer;
import com.example.utils.JDBCUtils;
import com.example5.dao.CustomerDaoImpl;

public class CustomerDAOImplTest {
	CustomerDaoImpl dao = new CustomerDaoImpl();
	@Test
	public void testInsert() {
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			Customer cust = new Customer(1, "新希望", "hope@qq.com", new Date(235436234345753L));
			dao.insert(conn, cust);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			JDBCUtils.closeResource(conn, null);
		}
	}

	@Test
	public void testDeleteById() {
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			dao.deleteById(conn, 21);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			
			JDBCUtils.closeResource(conn, null);
		}
	}

	@Test
	public void testUpdateConnectionCustomer() {
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			Customer cust = new Customer(23, "与神同行", "withGodwalk@QQ.com", new Date(543542353252L));
			dao.update(conn, cust);
		
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			
			JDBCUtils.closeResource(conn, null);
		}
	}

	@Test
	public void testGetCustomerById() {
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			Customer cust = dao.getCustomerById(conn, 19);
			System.out.println(cust);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			
			JDBCUtils.closeResource(conn, null);
		}
	}

	@Test
	public void testGetAll() {
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
		List<Customer> all = dao.getAll(conn);
		all.forEach(System.out::println);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			
			JDBCUtils.closeResource(conn, null);
		}
	}

	@Test
	public void testGetCount() {
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			Long count = dao.getCount(conn);
			System.out.println(count);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			
			JDBCUtils.closeResource(conn, null);
		}
	}

	@Test
	public void testGetMaxBirth() {
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			Date maxBirth = dao.getMaxBirth(conn);
			System.out.println(maxBirth);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			
			JDBCUtils.closeResource(conn, null);
		}
	}

}
