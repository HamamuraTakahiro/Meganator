package servlet;

import java.io.IOException;
import java.util.List;

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

/**
 * Servlet implementation class Home
 */
@WebServlet("/Home")
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		QuestionLogic questionLogic = new QuestionLogic();
		List<Question> tenQuestions = questionLogic.choiceRamdomQuestions();

		//10個の質問をsession scopeに格納
		HttpSession session = request.getSession();
		session.setAttribute("tenQuestions", tenQuestions);


		RequestDispatcher dispatcher = request.getRequestDispatcher("home.jsp");
		dispatcher.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
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
		List<Integer> answerList = (List<Integer>)session.getAttribute("answerList");
		answerList.add(Integer.parseInt(request.getParameter("answer")));
		session.setAttribute("answerList", answerList);


		//質問が何回目か数えてる。
		int count = answerList.size();


		if(count >= 10) {
			//10個の答えをもらったらResultServlet.javaに処理をリダイレクト
			ResultLogic resultLogic = new ResultLogic();
			List<Result> results = resultLogic.choiceResults((List<Question>)session.getAttribute("tenQuestions"), answerList);
			session.setAttribute("results", results);
			response.sendRedirect("/Meganator/ResultServlet");
		}

		//答えが9個以下の場合はまた尋ねる
		List<Question> tenQuestions = (List<Question>)session.getAttribute("tenQuestions");

		request.setAttribute("questionText", tenQuestions.get(count));

		RequestDispatcher dispatcher = request.getRequestDispatcher("home.jsp");
		dispatcher.forward(request, response);


	}

}
