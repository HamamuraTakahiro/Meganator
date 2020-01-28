package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.org.apache.bcel.internal.generic.NEW;

import model.Question;
import model.QuestionLogic;
import model.Result;
import model.ResultLogic;

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

		QuestionLogic questionLogic = new QuestionLogic();
		List<Question> tenQuestions = questionLogic.choiceRamdomQuestions();

		HttpSession session = request.getSession();
		session.setAttribute("tenQuestions", tenQuestions);

		session.setAttribute("answers", new ArrayList<Integer>());

		request.setAttribute("questionText", tenQuestions.get(0));
		RequestDispatcher dispatcher = request.getRequestDispatcher("home.jsp");
		dispatcher.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//64行目getParameterの引数を未設定です。あとで修正してください。

		HttpSession session = request.getSession();
		List<Integer> answerlist = (List<Integer>)session.getAttribute("answers");
		answerlist.add(Integer.parseInt(request.getParameter("JSPのコードに対応させる")));
		session.setAttribute("answers", answerlist);

		int count = answerlist.size();
		if(count >= 10) {
			ResultLogic resultLogic = new ResultLogic();
			List<Result> results = ResultLogic.choiceResults(session.getAttribute("tenQuestions"), answerlist):
			session.setAttribute("results", results);
			response.sendRedirect("/Meganator/Result");
		}

		List<Question> tenQuestions = (List<Question>)session.getAttribute("tenQuestions");

		request.setAttribute("questionText", tenQuestions.get(count));

		RequestDispatcher dispatcher = request.getRequestDispatcher("home.jsp");
		dispatcher.forward(request, response);


	}

}
