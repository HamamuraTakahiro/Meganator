package model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

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

		Result you = new Result(0, "you", happy, mad, sad, joy);

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
		//全データを代入しインスタンス内で扱えるようにする
		allResultData = new MeganatorDAO().selectAllResults();
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
	 * calcAllResultClosenessメソッドを使用したResultLogicインスタンスから呼んでいない場合nullが出る
	 * @param closeness
	 * index順に入った近さの値
	 * @param number
	 * 取得件数
	 * @return
	 * 最低[取得件数]個の要素が入ったオススメ内容を示すResultデータ。
	 * 同率[取得件数]位の要素全てが入る。
	 */
	public ArrayList<Result> pickUpResult(ArrayList<Integer> closeness,int number){
		//返り値用変数
		ArrayList<Result> list = new ArrayList<>();

		//nullチェック
		if(closeness == null || allResultData == null) return null;

		//何番目のResultがどれくらいの近さを持っているかをMapに配分する
		Map<Integer, Integer> distanceMap = new LinkedHashMap<>();
		for(int i=0;i<closeness.size();i++) {
			distanceMap.put(i,closeness.get(i));
		}

		//近さ:0から順に、「その値を持つキーの持つindexをもつResult」を格納、一致した場合削除
		//その近さを値にもつ要素がないこと確認し、近さを増やす。
		int currentDistance=0;
		while(list.size() < number) {
			if(distanceMap.containsValue(currentDistance)) {
				for(int index = 0;index < distanceMap.size();index++) {
					if(Integer.valueOf(currentDistance).equals(distanceMap.get(index))) {
						list.add(allResultData.get(index));
						distanceMap.remove(index);
					}
				}
			}
			if(!distanceMap.containsValue(currentDistance)) currentDistance++;
		}

		//場合によってはnumber個より多い場合もある。
		return list;
	}
}
