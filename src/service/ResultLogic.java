package service;

import java.util.ArrayList;

import dao.MeganatorDAO;
import model.Question;
import model.Result;

public class ResultLogic {
	/**
	 * 全Resultデータ保存用。
	 * calcAllResultClosenessメソッド中で初期化する。
	 */
	private ArrayList<Result> allResultData;

	/**
	 * 回答した質問と回答内容からオススメを出す
	 * @param questions
	 * 回答した質問。
	 * @param selects
	 * 回答内容のリスト。選択肢０～４の中から選ばせる。
	 * @return
	 * オススメのリスト。最小４（ハードコーディング）個で、同率4位だった全ての項目を含む。
	 */
	public ArrayList<Result> choiceResults(ArrayList<Question> questions,ArrayList<Integer> selects){
		//回答による配点バランス
		final int[] balance= {150,120,100,70,60};

		//合計用４値
		int happy=0;
		int joy=0;
		int mad=0;
		int sad = 0;

		//順に足す
		for(int i = 0;i<questions.size();i++) {
			happy+=questions.get(i).getHappy() * balance[selects.get(i)];
			joy+=questions.get(i).getJoy() * balance[selects.get(i)];
			sad+=questions.get(i).getSad() * balance[selects.get(i)];
			mad+=questions.get(i).getMad() * balance[selects.get(i)];
		}

		//割合で圧縮
		happy = happy * 10 / questions.size() / 100;
		joy= joy * 10 / questions.size() /  100;
		mad= mad * 10 / questions.size() / 100;
		sad= sad * 10 / questions.size() / 100;

		Result you = new Result(0, "you", happy, mad, sad, joy, null);

		return pickUpResult(calcAllResultCloseness(you), 4);
	}

	/**
	 * ユーザーのリザルトデータとDB内データを比較して各項目の差の絶対値を取る。
	 * @param user
	 * ユーザーの入れた結果で出せるユーザーの感情値
	 * @return
	 * index:0から順に近さの値を入れたInteger配列
	 */
	private ArrayList<Integer> calcAllResultCloseness(Result user){
		fillAllResultData();
		//nullチェック
		if(allResultData == null) return null;

		//返り値用変数
		ArrayList<Integer> result = new ArrayList<>();

		//順に入れていく
		for(int i=0;i<allResultData.size();i++) {
			int happy = allResultData.get(i).getHappy() - user.getHappy();
			int sad = allResultData.get(i).getSad() - user.getSad();
			int mad = allResultData.get(i).getMad() - user.getMad();
			int joy = allResultData.get(i).getJoy() - user.getJoy();
			Integer closeness = Math.max(joy, -joy) + Math.max(happy, -happy) +Math.max(mad, -mad) + Math.max(sad, -sad);
			result.add(closeness);
		}

		//件数の一致をみる
		if(allResultData.size() != result.size()) return null;

		//値を返す
		return result;

	}

	/**
	 * 計算した近さの値から近いものnumber個のResultを入れたListを返す
	 * fillAllResultDataメソッドを使用したResultLogicインスタンスから呼んでいない場合nullが出る
	 * @param closeness
	 * index順に入った近さの値
	 * @param number
	 * 取得件数
	 * @return
	 * 最低[取得件数]個の要素が入ったオススメ内容を示すResultデータ。
	 * 同率[取得件数]位の要素全てが入る。
	 */
	private ArrayList<Result> pickUpResult(ArrayList<Integer> closeness,int number){
		//返り値用変数
		ArrayList<Result> list = new ArrayList<>();

		//nullチェック
		if(closeness == null || allResultData == null) return null;

		//近さ:0から順に、「その値を持つキーの持つindexをもつResult」を格納、一致した場合削除
		//その近さを値にもつ要素がないこと確認し、近さを増やす。
		int currentDistance = 0;
		//4つを超えるまでやる
		while(list.size() < number) {
			//「近さリスト」の中にその近さがあるなら（計算時間短縮用）
			if(closeness.contains(currentDistance)) {

				//「リザルト全体の何番目にあるデータが近い」ことを利用して返り値リストに加える
				for(int index = 0;index < closeness.size();index++) {

					if(closeness.get(index) == currentDistance) {
						list.add(allResultData.get(index));
					}

				}
			}
			//「その近さ」の処理が終わったら遠くしていく
			currentDistance++;
		}

		//場合によってはnumber個より多い場合もある。
		return list;
	}

	private void  fillAllResultData() {
		//全データを代入しインスタンス内で扱えるようにする
		allResultData = new MeganatorDAO().selectAllResults();
	}
}
