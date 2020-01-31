package service;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;

import dao.MeganatorDAO;
import model.Question;

public class QuestionLogic {
	private final int QUEST_AMOUNT = 10;

	public ArrayList<Question> choiceRamdomQuestions() {

	//重複なし乱数格納用のSetの作成
	LinkedHashSet<Integer> idSet = new LinkedHashSet<Integer>();
	//ランダムインスタンスの作成
	Random r = new Random();

	//while文用カウンター
	int count = 0;

	//DAO用のインスタンスの生成
	MeganatorDAO meganatorDAO = new MeganatorDAO();
	List<Integer> allIdList = meganatorDAO.idList();
	//質問数の設定
	int numberOfQuestions = allIdList.size();


	//while文でランダムに数字を選出してIdを格納する
	while(count < QUEST_AMOUNT) {

		//Idの範囲内からランダムな数字を選出
		int currentNum = r.nextInt(numberOfQuestions) + 1;

		//allIdListになかったら取り直し
		if(!allIdList.contains(currentNum)) {
			continue;
		}
		//被ったらcontinue
		if(idSet.contains(currentNum) == true) {
			continue;
		}
		//被りがなければ格納してカウンターを加算する
		if(idSet.contains(currentNum) == false) {
			idSet.add(currentNum);
			count++;
		}
	}
		//配列に変換
		Integer[] integerArray = new Integer[idSet.size()];
		idSet.toArray(integerArray);

		//int配列に変換
		int[] idList = new int[idSet.size()];
		for(int i=0;i<idSet.size();i++) {
			idList[i]=integerArray[i];
		}

		//Questionリストに格納
		ArrayList<Question> questions = new ArrayList<Question>();
		questions.addAll(meganatorDAO.selectQuestions(idList));

		return questions;

	}
}
