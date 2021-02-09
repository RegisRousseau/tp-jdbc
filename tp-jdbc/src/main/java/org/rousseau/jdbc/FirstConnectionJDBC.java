package org.rousseau.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FirstConnectionJDBC {

	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/tp_jdbc";
		String user = "jdbc_user";
		String pwd = "jdbc";
		
		try(Connection conn = DriverManager.getConnection(url, user,pwd);){
			
			PreparedStatement ps = conn.prepareStatement("select * from User");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("ID");
				String name = rs.getString("name");
				int age = rs.getInt("age");
				System.out.printf("User : %d, %s, %d\n",id, name,age);
				
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Connexion failed");
		}

	}

}
