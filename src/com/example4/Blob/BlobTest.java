package com.example4.Blob;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hamcrest.core.Is;
import org.junit.Test;

import com.example.bean.Customer;
import com.example.utils.JDBCUtils;

public class BlobTest {
	//向customers表插入blob数据
	@Test
	public void insertTest() throws Exception {
		Connection conn = JDBCUtils.getConnection();
		String sql = "insert into customers(name,email,birth,photo) values(?,?,?,?)";
		PreparedStatement ps = conn.prepareStatement(sql );
		ps.setObject(1, "alita");
		ps.setObject(2, "alita@qq.com");
		ps.setObject(3, "1999-9-9");
		FileInputStream is = new FileInputStream(new File("src/alita.jpg"));
		ps.setBlob(4, is);
		
		ps.execute();
		
		JDBCUtils.closeResource(conn, ps);
		
	}
	
	//查询customer信息，将blob字段以文件形式保存在本地
	@Test
	public void selectTest()  {
		
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		InputStream is = null;
		FileOutputStream fos = null;
		try {
			conn = JDBCUtils.getConnection();
			String sql = "select id,name,email,birth,photo from customers where id = ?";
			ps = conn.prepareStatement(sql );
			ps.setInt(1, 24);
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String email = rs.getString("email");
				Date birth = rs.getDate("birth");
				
				Customer customer = new Customer(id, name, email, birth);
				System.out.println(customer);
				Blob photo = rs.getBlob("photo");
				is = photo.getBinaryStream();
				
				byte[] buffer = new byte[1024];
				int len = 0;
				fos = new FileOutputStream("alitaphoto.jpg");
				while((len = is.read(buffer)) != -1 ) {
					fos.write(buffer,0,len);
				}
				
				
			}
		}  catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(is != null)
					is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				if(fos != null)
					fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			JDBCUtils.closeResource(conn, ps, rs);
		}
		
		
	}
	

}
