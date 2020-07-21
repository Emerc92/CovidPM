package it.begear.corso.servlet.login;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.begear.corso.database.DaoInfezione;
import it.begear.corso.database.DaoUtente;
import it.begear.corso.database.Utente;



/**
 * Servlet implementation class AggiornaDB
 */
@WebServlet("/AggiornaDB")
public class AggiornaDB extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AggiornaDB() { }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		List<Float> percInfezioniZone = getPercInfezioniPerZona();
		List<Utente> utenti = DaoUtente.getListaUtenti();

		for(Utente u : utenti) {
			if(!DaoInfezione.searchInfezione(u.getId())) { //se l'utente non è infetto...
                //calcolo percentuale infettività come media tra:
				//(probabilità di infezione ZonaRes + probabilità di infezione ZonaLav)/2
				float percUtente = (float) (percInfezioniZone.get(u.getId_zona_res() - 1) + percInfezioniZone.get(u.getId_zona_lav() - 1) / 2); 
				//se si infetta
				if(rollInfezione(percUtente)) { 
					DaoInfezione.createInfezione(u.getId()); //aggiungilo alla tabella degli infetti
				}
			}
		}

		response.sendRedirect("Login.jsp?status=AGGIORNATO");

	}
//ritorna una lista di 20 numeri float, con le percentuali di infettività per ogni regione
	private static List<Float> getPercInfezioniPerZona() {
		List<Long> utentiPerZona = DaoUtente.getNumUtentiPerZona(); // ritorna una lista di 20 numeri(popolazione per regione)
		List<Long> infettiPerZona = DaoInfezione.getNumInfezioniPerZona(); // ritorna una lista di 20 numeri(infetti per regione)
		List<Float> percInfezioni = new ArrayList<Float>();

		try {

			for(int i = 0; i < 20; i++) {
				float infxZona = (float)infettiPerZona.get(i);
				float utentixZona = (float)utentiPerZona.get(i);
				percInfezioni.add((infxZona / utentixZona));
			}

			return percInfezioni;

		} catch(Exception e) {
			System.out.println("Errore durante la raccolta di informazioni sulle infezioni.");
			return null;
		}
	}
// se il numero random tra 0 e 1 è minore della percentuale dell'infettività / 5 allora si ha INFEZIONE
	private static boolean rollInfezione(float percUtente) {
		Random r = new Random();
		percUtente = percUtente ;

		if(r.nextFloat() <= percUtente) {
			return true;
		} else {
			return false;
		}
	}
}
