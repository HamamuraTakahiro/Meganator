package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Question;
import model.Result;

public class MeganatorDAO {
	//DB接続用定数
	static final String DATABASE_NAME = "meganater_protor";
	static final String PROPATIES = "?characterEncoding=UTF-8&serverTimezone=JST";
	static final String ADDRESS = "jdbc:mySQL://localhost/";
	static final String URL = ADDRESS+DATABASE_NAME+PROPATIES;
	//DB接続用・ユーザ定数
	static final String USER = "root";
	static final String PASS = "2020mgt";

	/**
	 * int配列の数値に対応したIDを持つデータをQUESTIONSテーブルから呼び、
	 * Qusestionクラスに格納してリストで返す
	 * @param ids
	 * QUESTIONSテーブルのIDに対応した値をもつint配列
	 * @return
	 * idsの要素に対応する質問データを持ったQuestionのリスト
	 */
	public ArrayList<Question> selectQuestions(int[] ids) {
		//返り値用変数
		ArrayList<Question> list = new ArrayList<>();

		//SQL文
		String sql ="SELECT * FROM QUESTIONS WHERE ID IN (?,?,?,?,?, ?,?,?,?,?)";

		//DB接続～returnまで
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(URL,USER,PASS);			PreparedStatement stt = conn.prepareStatement(sql);

			for(int i=0;i<ids.length;i++) {
				stt.setInt(i+1, ids[i]);
			}
			ResultSet rs = stt.executeQuery();

			while(rs.next()) {
				//ヒットした数だけリストに格納
				int id = rs.getInt(1);
				String question_text = rs.getString(2);
				int happy = rs.getInt(3);
				int mad = rs.getInt(4);
				int sad = rs.getInt(5);
				int joy = rs.getInt(6);
				list.add(new Question(id, question_text, happy, mad, sad, joy));
			}
			//引数と返り値のサイズが合わない場合
			if(list.size() != ids.length) {
				return null;
			}
			//成功時
			return list;
		} catch (SQLException | ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * 質問の総数を返すメソッド
	 * @return
	 */
	public int countAllQuestions() {
		//SQL文
		String sql ="SELECT COUNT(*) FROM QUESTIONS";

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(URL,USER,PASS);
			PreparedStatement stt = conn.prepareStatement(sql);
			ResultSet rs = stt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			}
			//数なので不正の場合負の値を返すように
			return -1;
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return -1;
		} catch (ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return -1;
		}
	}

	/**
	 * 結果DBの全体をリスト形式で返すメソッド
	 * @return
	 */
	public ArrayList<Result> selectAllResults(){
		//返り値用変数
		ArrayList<Result> list = new ArrayList<>();

		//SQL文
		String sql ="SELECT * FROM RESULTS";

		//DB接続～結果の取得～returnまで
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(URL,USER,PASS);			PreparedStatement stt = conn.prepareStatement(sql);

			ResultSet rs = stt.executeQuery();

			while(rs.next()) {
				int id = rs.getInt(1);
				String question_text = rs.getString(2);
				int happy = rs.getInt(3);
				int mad = rs.getInt(4);
				int sad = rs.getInt(5);
				int joy = rs.getInt(6);
				String imageName = rs.getString(7);
				list.add(new Result(id, question_text, happy, mad, sad, joy, imageName));
			}

			return list;
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * テスト用メインメソッド
	 * @param args
	 */
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
