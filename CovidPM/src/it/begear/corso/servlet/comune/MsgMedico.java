package it.begear.corso.servlet.comune;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.begear.corso.database.DaoMessaggio;
import it.begear.corso.database.DaoUtente;
import it.begear.corso.database.Utente;


@WebServlet("/MsgMedico")
public class MsgMedico extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public MsgMedico() {}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String nTamp = request.getParameter("nTamp");
		HttpSession session = request.getSession(); // richiama la sessione se esiste già o ne crea una!!!
		Utente client = (Utente) session.getAttribute("client"); // prende l'oggetto "client" presente nella sessione
		
		String msg = "Attenzione!! Il signor " + client.getCognome() + " " + client.getNome()
				+ " ha richiesto "+ nTamp + " tamponi per la regione " + client.getId_zona_lav();
		//prendi gli id dei medici nella zona di lavoro del client(che è un operatore regionale)
		List <Integer> IdMedici = DaoUtente.getIdMediciZona(client.getId_zona_lav());
		for(Integer x : IdMedici)
		{
		DaoMessaggio.createMessaggio(x, client.getId(), msg); //manda un messaggio a tutti gli id dei medici della zona 		
		}
	response.sendRedirect("Comune.jsp?status=inviato");
		
	}

}
