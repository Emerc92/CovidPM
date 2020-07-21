package it.begear.corso.servlet.messaggi;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.begear.corso.database.DaoMessaggio;
import it.begear.corso.database.Utente;


/**
 * Servlet implementation class EliminaMsg
 */
@WebServlet("/EliminaMsg")
public class EliminaMsg extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public EliminaMsg() {}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		HttpSession session = request.getSession();
		Utente utente = (Utente) session.getAttribute("client");
		DaoMessaggio.deleteMessaggiUtente(utente);
		response.sendRedirect("Comune.jsp?status=eliminati");
	}

}
