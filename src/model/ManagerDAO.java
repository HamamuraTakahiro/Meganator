package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManagerDAO {
	static final String DATABASE_NAME = "Meganator";
	static final String PROPATIES = "?characterEncoding=UTF-8&serverTimezone=JST";
	static final String ADDRESS = "jdbc:mySQL://localhost/";
	static final String URL = ADDRESS+DATABASE_NAME+PROPATIES;
	static final String USER = "root";
	static final String PASS = "root";

	public List<Question> selectQuestions(int[] ids) {
		List<Question> list = new ArrayList<>();
		String sql ="SELECT * FROM QUESTIONS WHERE ID IN (?,?,?,?,?, ?,?,?,?,?)";

		try (Connection conn = DriverManager.getConnection(URL,USER,PASS)){
			PreparedStatement stt = conn.prepareStatement(sql);

			ResultSet rs = stt.executeQuery();

			while(rs.next()) {
				int id = rs.getInt(1);
				String question_text = rs.getString(2);
				int happy = rs.getInt(3);
				int mad = rs.getInt(4);
				int sad = rs.getInt(5);
				int joy = rs.getInt(6);
				list.add(new Question(id, question_text, happy, mad, sad, joy));
			}
			if(list.size() != ids.length) {
				return null;
			}
			return list;
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return null;
		}

	}

	public int countAllQuestions() {
		String sql ="SELECT COUNT(*) FROM QUESTIONS";

		try (Connection conn = DriverManager.getConnection(URL,USER,PASS)){
			PreparedStatement stt = conn.prepareStatement(sql);
			ResultSet rs = stt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			}
			return -1;
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return -1;
		}
	}

	public List<Result> selectAllResults(){
		List<Result> list = new ArrayList<>();
		String sql ="SELECT * FROM RESULTS WHERE ID IN (?,?,?,?,?, ?,?,?,?,?)";

		try (Connection conn = DriverManager.getConnection(URL,USER,PASS)){
			PreparedStatement stt = conn.prepareStatement(sql);

			ResultSet rs = stt.executeQuery();

			while(rs.next()) {
				int id = rs.getInt(1);
				String question_text = rs.getString(2);
				int happy = rs.getInt(3);
				int mad = rs.getInt(4);
				int sad = rs.getInt(5);
				int joy = rs.getInt(6);
				list.add(new Result(id, question_text, happy, mad, sad, joy));
			}

			return list;
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {

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
