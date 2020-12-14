package com.example5.dao;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import com.example.bean.Customer;
/**
 * @Class_Name CustomerDaoImpl
 * @author heatdesert
 * @Description 本来以为接口里的方法里的增删改查操作都是要自己做的，没想到直接用BaseDao就可以了
 * 2020年9月20日 上午8:49:57
 */
public class CustomerDaoImpl extends BaseDao<Customer> implements CustomerDao{

	
	@Override
	public void insert(Connection conn, Customer cust) {
		String sql = "insert into customers(name,email,birth)values(?,?,?)";
		update(conn, sql, cust.getName(),cust.getEmail(),cust.getBirth());
	}

	@Override
	public void deleteById(Connection conn, int id) {

		String sql = "delete from customers where id = ?";
		update(conn, sql, id);
	}

	@Override
	public void update(Connection conn, Customer cust) {
		String sql = "update customers set name=?,email=?,birth=? where id = ?";
		update(conn, sql, cust.getName(),cust.getEmail(),cust.getBirth(),cust.getId());
	}

	@Override
	public Customer getCustomerById(Connection conn, int id) {
		String sql = "select id,name,email,birth from customers where id = ?";
		Customer customer = getInstance(conn, sql,id);
		return customer;
	}

	@Override
	public List<Customer> getAll(Connection conn) {
		String sql = "select id,name,email,birth from customers";
		List<Customer> list = getForList(conn, sql);
		return list;
	}

	@Override
	public Long getCount(Connection conn) {
		String sql = "select count(*) from customers";
		Object value = getValue(conn, sql);
		return (Long) value;
	}

	@Override
	public Date getMaxBirth(Connection conn) {
		String sql = "select MAX(birth) from customers";
		  Object value = getValue(conn, sql);
		return (Date) value;
	}
	

}
