package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Question;
import model.Result;
import service.QuestionLogic;

public class MeganatorDAO {
	//DB接続用定数
	static final String DATABASE_NAME = "meganator_proto";
	static final String PROPATIES = "?characterEncoding=UTF-8&serverTimezone=JST";
	static final String ADDRESS = "jdbc:mySQL://localhost/";
	static final String URL = ADDRESS+DATABASE_NAME+PROPATIES;
	//DB接続用・ユーザ定数
	static final String USER = "test";
	static final String PASS = "";

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
	 * QUESTIONSテーブルにあるデータのIDの一覧を返します。
	 * 要素番号をIDで直接指定するのに利用します。
	 * @return
	 * QUESTIONSテーブルにあるIDをIndexの小さい順で並べたIDを値に持つArrayList
	 */
	public List<Integer> questionsIdList(){
		//返り値用変数
		List<Integer> list = new ArrayList<>();
		String sql = "SELECT id FROM questions";
		try {
			//接続
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(URL,USER,PASS);			PreparedStatement stt = conn.prepareStatement(sql);

			//結果の取得
			ResultSet rs = stt.executeQuery();
			while(rs.next()) {
				list.add( rs.getInt(1));
			}

			//上手くいったばあいのみ値を返す
			return list;
		}catch(Exception e) {
			//例外処理
			e.printStackTrace();
			return null;
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

		QuestionLogic ql = new QuestionLogic();
		List<Question> questionList = ql.choiceRamdomQuestions();
		for(Question quest : questionList) {
			if(quest !=null) {
				System.out.println("["+quest.getId()+" , "+quest.getQuestion_text()+"]");
			}
		}
	}
}
