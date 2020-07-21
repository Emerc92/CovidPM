package it.begear.corso.servlet.polizia;

import java.io.IOException;
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
 * Servlet implementation class ControllaUtente
 */
@WebServlet("/ControllaUtente")
public class ControllaUtente extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public ControllaUtente() {}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(); // richiama la sessione se esiste già o ne crea una!!!
		Utente client = (Utente) session.getAttribute("client"); 
		String nome = request.getParameter("nome");
		String cognome = request.getParameter("cognome");
		List<Utente> listUtenti = DaoUtente.getUtentiNomeCognome(nome, cognome);
		session.setAttribute("listaUT", listUtenti);

		for(Utente u: listUtenti) {
			System.out.println(u.getNome() + " " + u.getCognome());
		}
		
		response.sendRedirect("Polizia.jsp?status=controllo");
	}

}
