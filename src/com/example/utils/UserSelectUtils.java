package com.example.utils;

import java.util.Scanner;

public class UserSelectUtils {
	
	public static String  getCertificateType() {
		Scanner scan = new Scanner(System.in);
		boolean isFlag = false;
		System.out.println("请输入你要查询的类型："
				+ "\na:准考证号"
				+ "\nb:身份证号");
		while(true) {
			
			String next = scan.next();
			String lowerCase = next.toLowerCase();
			switch (lowerCase) {
			case "a":
			case "b":
				return lowerCase;
				
			default:
				System.out.println("输入错误请重新输入");
//				isFlag = true;
				break;
			}
		}
	}

}
