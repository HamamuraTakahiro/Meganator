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

/**
 * Servlet implementation class Home
 */
@WebServlet("/Home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Home() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/* 10個の質問をsession scopeにList<Question>型として格納。
		 * QuestionLogic.choiceRandomQuestions()List<Question>を取得。
		 * 変数 questions に格納。2. 1つ目の質問を出す。
		 *  QuestioLogic.choiceRandomQuestions()を呼ぶ
		 */
		QuestionLogic qLogic = new QuestionLogic();
		List<Question> questions = qLogic.choiceRamdomQuestions();

		HttpSession session = request.getSession();
		session.setAttribute("questions", questions/* 10個の質問 */);

		request.setAttribute("questionText", questions.get(0));
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/home.jsp");
		dispatcher.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {




	}

}
