package com.example5.dao;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import com.example.bean.Customer;

public interface CustomerDao {
	/**
	 * 
	 * @Description: 向数据表中插入新对象
	 * @Title: 	insert   
	 * @return:	void
	 * @Time:	2020年9月20日上午8:35:49
	 */
	void insert(Connection conn,Customer cust);
	
	/**
	 * 
	 * @Description: 根据Id删除数据
	 * @Title: 	deleteById   
	 * @return:	void
	 * @Time:	2020年9月20日上午8:36:37
	 */
	
	void deleteById(Connection conn,int id);
	/**
	 * 
	 * @Description: 
	 * @Title: 	update   
	 * @return:	void
	 * @Time:	2020年9月20日上午8:37:40
	 */
	void update(Connection conn,Customer cust);
	
	Customer getCustomerById(Connection conn,int id);
	
	List<Customer> getAll(Connection conn);
	
	Long getCount(Connection conn);
	
	Date getMaxBirth(Connection conn);
	
	

}
