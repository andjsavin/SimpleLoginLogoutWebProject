package Servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Model.Database;

/**
 * Servlet implementation class Dane1Servlet
 */
@WebServlet({ "/LoginServlet", "/login.do" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String login = request.getParameter("login");
		String pw = request.getParameter("pw");
		String url = "/index.jsp";
		boolean b = false;
		try {
			b = Database.login(login, pw);
			if (b == true) {
				HttpSession session = request.getSession();
				session.setAttribute("curlog",login); 
				String userid = (String) session.getAttribute("curlog");
				url = "/loggedin.jsp";
				request.setAttribute("message", Database.loggedin(userid));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// if (user == "") {
		// url = "/signin.jsp";
		// } else {
		// url = "/loggedin.jsp";
		// request.setAttribute("user", user);
		// }
		
		ServletContext context = getServletContext();
		RequestDispatcher dispatcher = context.getRequestDispatcher(url);
		dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
