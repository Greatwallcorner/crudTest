package com.example4.transaction;

public class User {
	private String user;
	private String password;
	private int balance;
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	@Override
	public String toString() {
		return "User [user=" + user + ", password=" + password + ", balance=" + balance + "]";
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public User(String user, String password, int balance) {
		super();
		this.user = user;
		this.password = password;
		this.balance = balance;
	}
	public User() {
		super();
	}
	
	

}
