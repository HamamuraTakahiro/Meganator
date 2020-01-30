package servlet;

import static config.Constant_text.*;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Question;
import model.QuestionLogic;
import model.Result;
import model.ResultLogic;

@WebServlet("/Home")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public HomeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		QuestionLogic questionLogic = new QuestionLogic();
		ArrayList<Question> tenQuestions = questionLogic.choiceRamdomQuestions();

		//10個の質問をsession scopeに格納
		HttpSession session = request.getSession();
		session.setAttribute(TEN_QUESTIONS, tenQuestions);

		RequestDispatcher dispatcher = request.getRequestDispatcher("home.jsp");
		dispatcher.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		/*　変更前
		 * 答えのリストを１つずつsession scopeから取得
		 * リクエストスコープから取得した答えをリストに追加
		 * １０個できたら次の処理に移る
		 *
		 * 変更後
		 * 答えをリストで１０個まとめて取得。そのまま次の処理へ
		*/
		HttpSession session = request.getSession();
		ArrayList<Integer> answerList = (ArrayList<Integer>)session.getAttribute(ANSWER_LIST);
		answerList.add(Integer.parseInt(request.getParameter(ANSWER)));
		session.setAttribute(ANSWER_LIST, answerList);

		//質問が何回目か数えてる。
		int count = answerList.size();
		if(count >= 10) {
			//10個の答えをもらったらResultServlet.javaに処理をリダイレクト
			ResultLogic resultLogic = new ResultLogic();
			ArrayList<Result> results = resultLogic.choiceResults((ArrayList<Question>)session.getAttribute(TEN_QUESTIONS), answerList);
			session.setAttribute(RESULTS, results);
			response.sendRedirect("/Meganator/ResultServlet");
		}

		//答えが9個以下の場合はまた尋ねる
		ArrayList<Question> tenQuestions = (ArrayList<Question>)session.getAttribute(TEN_QUESTIONS);

		request.setAttribute(QUESTION_TEXT, tenQuestions.get(count));

		RequestDispatcher dispatcher = request.getRequestDispatcher("home.jsp");
		dispatcher.forward(request, response);

	}

}

