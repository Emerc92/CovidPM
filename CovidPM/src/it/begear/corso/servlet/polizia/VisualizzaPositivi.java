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
 * Servlet implementation class VisualizzaPositivi
 */
@WebServlet("/VisualizzaPositivi")
public class VisualizzaPositivi extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public VisualizzaPositivi() {
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(); // richiama la sessione se esiste già o ne crea una!!!
		Utente client = (Utente) session.getAttribute("client"); 
		List <Utente> listaPositivi = DaoUtente.getPositiviZona(client.getId_zona_lav());
		session.setAttribute("listaPositivi", listaPositivi);
		
		if(listaPositivi.isEmpty() || listaPositivi== null) response.sendRedirect("Polizia.jsp?status=vuotaPositivi");
		else response.sendRedirect("Polizia.jsp?status=visualizza");
		
	}

}
