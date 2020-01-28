package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ManagerDAO {
	public static void main(String[] args) {
		final String DATABASE_NAME = "Meganator";
		final String PROPATIES = "?characterEncoding=UTF-8&serverTimezone=JST";
		final String URL = "jdbc:mySQL://localhost/"+DATABASE_NAME+PROPATIES;
		final String USER = "root";
		final String PASS = "root";

		Connection conn = null;
		try {
			conn = DriverManager.getConnection(URL,USER,PASS);
			String sql ="SELECT * FROM TEST";
			PreparedStatement stt = conn.prepareStatement(sql);

			ResultSet rs = stt.executeQuery();
			while(rs.next()) {
				System.out.print(rs.getObject(1));
				System.out.print(",");
				System.out.print(rs.getObject(2));
				System.out.println();
			}

		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
