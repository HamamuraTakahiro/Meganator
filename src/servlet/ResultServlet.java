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
		ArrayList<Question> quests =(ArrayList<Question>)request.getSession().getAttribute(TEN_QUESTIONS);
		ArrayList<Integer> answers =(ArrayList<Integer> )request.getSession().getAttribute(ANSWER_LIST);
		/**
		 * 差し戻し条件
		 * ①いずれかがnull
		 * ②サイズが異なる
		 * ③どこかにnullの値が入っている
		 */
		if(quests == null || answers == null ||quests.size() != answers.size()  || quests.contains(null) || answers.contains(null)) {
			/*セッションスコープを破棄*/
			request.getSession().removeAttribute(TEN_QUESTIONS);
			request.getSession().removeAttribute(ANSWER_LIST);
			response.sendRedirect("Home");
		}else {
			ArrayList<Result> advice = new ResultLogic().choiceResults(quests, answers);
			request.getSession().setAttribute(ADVICES, advice);
		}
		/*セッションスコープを破棄*/
		request.getSession().removeAttribute(TEN_QUESTIONS);
		request.getSession().removeAttribute(ANSWER_LIST);
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
