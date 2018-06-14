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

import Model.Database;

/**
 * Servlet implementation class Dane1Servlet
 */
@WebServlet({ "/RegisterServlet", "/register.do" })
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterServlet() {
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
		String name = request.getParameter("name");
		String surname = request.getParameter("surname");
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		String url = "/register.jsp";
		String message = "";
		request.setAttribute("message", message);
		try {
			message = Database.register(name,  surname,  login,  password);
			request.setAttribute("message", message);
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
