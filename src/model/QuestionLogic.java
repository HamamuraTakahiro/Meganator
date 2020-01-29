package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class QuestionLogic {

	public ArrayList<Question> choiceRamdomQuestions() {

	//重複なし乱数格納用のSetの作成
	HashSet<Integer> idListSet = new HashSet<Integer>();
	//ランダムインスタンスの作成
	Random r = new Random();

	//while文用カウンター
	int count = 0;

	//DAO用のインスタンスの生成
	ManagerDAO managerDAO = new ManagerDAO();
	//質問数の設定
	int numberOfQuestions = managerDAO.countAllQuestions();


	//while文でランダムに数字を選出してIdを格納する
	while(count < numberOfQuestions) {

			//Idの範囲内からランダムな数字を選出
			int currentNum = r.nextInt(numberOfQuestions) + 1;

			//被ったらcontinue
				if(idListSet.contains(currentNum) == true) {
					continue;
			}
			//被りがなければ格納してカウンターを加算する
				if(idListSet.contains(currentNum) == false) {
					idListSet.add(currentNum);
					count++;
				}
			}
		//配列に変換
		Integer[] idList = new Integer[idListSet.size()];
		idListSet.toArray(idList);

		//Questionリストに格納
		ArrayList<Question> questions = new ArrayList<Question>();
		questions.addAll(managerDAO.selectQuestions(idList));

		return questions;

	}
}
