package it.begear.corso.servlet.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.begear.corso.database.DaoUtente;
import it.begear.corso.database.Utente;



/**
 * Servlet implementation class Acchiappa
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Login() {
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = request.getParameter("username");//request è quando viene immesso dall'user
		String password = request.getParameter("password");
		Utente client = DaoUtente.login(username,password);
		HttpSession session = request.getSession();
		session.setAttribute("username", client.getUsername());
		session.setAttribute("password", client.getPassword());
		
		if(client == null)response.sendRedirect("Login.jsp?status=DENIED");
		else if( client.getTipo().equals("Operatore")){
			response.sendRedirect("Comune.jsp");	
		}
		else if( client.getTipo().equals("Agente")){
			response.sendRedirect("Polizia.jsp");	
		}
		}
		
//		  response.setContentType("text/html");
//        PrintWriter out = response.getWriter();
//        out.println("<html>");
//        out.println("<head><title>HTML interpretato da Java</title></head>");
//        out.println("<body>"); 
//		
//        out.println("<a href='http://www.google.com'>Vai al motore di ricerca</a>");
//        out.println("<p>L'username è '" + username + "'<p>");
//		
//        out.println("</body>");
//        out.println("</html>");
//        out.close();
		
	}


