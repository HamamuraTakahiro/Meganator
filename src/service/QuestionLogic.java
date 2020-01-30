package service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Random;

import dao.MeganatorDAO;
import model.Question;

public class QuestionLogic {
	private final int QUEST_AMOUNT = 10;

	public ArrayList<Question> choiceRamdomQuestions() {

	//重複なし乱数格納用のSetの作成
	LinkedHashSet<Integer> idListSet = new LinkedHashSet<Integer>();
	//ランダムインスタンスの作成
	Random r = new Random();

	//while文用カウンター
	int count = 0;

	//DAO用のインスタンスの生成
	MeganatorDAO meganatorDAO = new MeganatorDAO();
	//質問数の設定
	int numberOfQuestions = meganatorDAO.countAllQuestions();


	//while文でランダムに数字を選出してIdを格納する
	while(count < QUEST_AMOUNT) {

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
		Integer[] integerArray = new Integer[idListSet.size()];
		idListSet.toArray(integerArray);

		//int配列に変換
		int[] idList = new int[idListSet.size()];
		for(int i=0;i<idListSet.size();i++) {
			idList[i]=integerArray[i];
		}

		//Questionリストに格納
		ArrayList<Question> questions = new ArrayList<Question>();
		questions.addAll(meganatorDAO.selectQuestions(idList));

		return questions;

	}
}
