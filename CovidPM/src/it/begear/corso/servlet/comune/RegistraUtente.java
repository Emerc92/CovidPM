package it.begear.corso.servlet.comune;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.begear.corso.database.DaoMessaggio;
import it.begear.corso.database.DaoUtente;
import it.begear.corso.database.Utente;


@WebServlet("/RegistraUtente")
public class RegistraUtente extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public RegistraUtente() {}
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String nome = request.getParameter("nome");
		String cognome = request.getParameter("cognome");
		String genere = request.getParameter("genere");
		String zonaRes = request.getParameter("zonaResidenza");
		String zonaLav = request.getParameter("zonaLavoro");
		String tipo = request.getParameter("tipoUtente");
		
		
		int id_zona_res = Integer.parseInt(zonaRes);
		int id_zona_lav = Integer.parseInt(zonaLav);
		
		Utente nuovoUtente = DaoUtente.createUtente(nome, cognome, genere, id_zona_res, id_zona_lav, tipo);
		
		if(nuovoUtente != null) //se l'utente è creato
		{
			// creazione messaggio con dati di accesso per il nuovo utente
			HttpSession session = request.getSession(); // richiama la sessione se esiste già o ne crea una!!!
			Utente client = (Utente) session.getAttribute("client"); // prende l'oggetto "client" presente nella sessione
			String txt_msg = "Benvenuto/a! I suoi dati di accesso sono: Username - " + nuovoUtente.getUsername() 
							+ ", Password - " + nuovoUtente.getPassword();
			
			DaoMessaggio.createMessaggio(nuovoUtente.getId(), client.getId(), txt_msg); // invia un messaggio al nuovo utente
			response.sendRedirect("Comune.jsp?status=creato");
		}	
	
	}

}
