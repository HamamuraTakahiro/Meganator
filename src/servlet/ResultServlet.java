package servlet;

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
import model.Result;
import model.ResultLogic;

/**
 * Servlet implementation class Result
 */
@WebServlet("/Result")
public class ResultServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResultServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Object sessionQuests = request.getSession().getAttribute("tenQuestions");
		Object sessionAnswer = request.getSession().getAttribute("answerList");
		ArrayList<Question> quests =(ArrayList<Question>)sessionQuests;
		ArrayList<Integer> answers =(ArrayList<Integer> )sessionAnswer;
		/**
		 * 差し戻し条件
		 * ①いずれかがnull
		 * ②サイズが異なる
		 * ③どこかにnullの値が入っている
		 */
		if(quests == null || answers == null ||quests.size() != answers.size()  || quests.contains(null) || answers.contains(null)) {
			/*セッションスコープを破棄*/
			HttpSession session1 = (HttpSession)sessionQuests;
			session1.invalidate();
			HttpSession session2 = (HttpSession)sessionAnswer;
			session2.invalidate();
			response.sendRedirect("Home");
		}else {
			ArrayList<Result> advice = new ResultLogic().choiceResults(quests, answers);
			request.getSession().setAttribute("advices", advice);
		}
		/*セッションスコープを破棄*/
		HttpSession session1 = (HttpSession)sessionQuests;
		session1.invalidate();
		HttpSession session2 = (HttpSession)sessionAnswer;
		session2.invalidate();
		/*Result.jspにフォワード*/
		RequestDispatcher dispatcher = request.getRequestDispatcher("/META-INF/result.jsp");
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);

	}

}
