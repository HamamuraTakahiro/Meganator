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
import service.QuestionLogic;

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

		RequestDispatcher dispatcher = request.getRequestDispatcher("index.jsp");
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

		//もしnullだった場合もう一度
		if(answerList == null) {
			response.sendRedirect("Home");
		}

		//質問が何回目か数えてる。
		int count = answerList.size();

		//回答数が足りない場合
		if(count < ((ArrayList<Question>)session.getAttribute(TEN_QUESTIONS)).size()) {
			//セッションスコープを空にしてもう一度doGetから
			session.setAttribute(ANSWER_LIST, null);
			session.setAttribute(TEN_QUESTIONS, null);
			response.sendRedirect("Home");
		}

		//回答数が足りていた場合
		response.sendRedirect("Result");


	}

}

