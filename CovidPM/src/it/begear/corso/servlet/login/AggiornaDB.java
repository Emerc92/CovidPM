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
import javax.servlet.http.HttpSession;

import it.begear.corso.database.DaoGenerale;
import it.begear.corso.database.DaoInfezione;
import it.begear.corso.database.DaoMessaggio;
import it.begear.corso.database.DaoUtente;
import it.begear.corso.database.Utente;


@WebServlet("/AggiornaDB")
public class AggiornaDB extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AggiornaDB() { }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		List<Float> percInfezioniZone = getPercInfezioniPerZona(); //per ognuna delle 20 regioni ottiene la percentuale di infettivit�
		List<Utente> utenti = DaoUtente.getListaUtenti();          //lista utenti presenti nel DB

		
		DaoGenerale.updateGiorno(); // facciamo avanzare di un giorno la tabella generale
		
		for(Utente u : utenti) {
			if(!DaoInfezione.searchInfezione(u.getId())) { //se l'utente non � infetto...
                //calcolo percentuale infettivit� come media tra (probabilit� di infezione ZonaRes + probabilit� di infezione ZonaLav)/2
				float percUtente = (float) (percInfezioniZone.get(u.getId_zona_res() - 1) + percInfezioniZone.get(u.getId_zona_lav() - 1) / 2); 
				
				if(rollInfezione(percUtente)) {              //se si infetta
					DaoInfezione.createInfezione(u.getId()); //aggiungilo alla tabella degli infetti (tempo di guarigione 10 giorni)
				}
			} else { // se l'utente � infetto...
				// togliamo uno ai giorni rimasti alla guarigione
				int giorni_rimasti = DaoInfezione.updateInfezione(u.getId());
				
				if(giorni_rimasti == 0) { // se l'utente � guarito...
					
					DaoInfezione.deleteInfezione(u.getId()); // elimina l'infezione
					
					if(u.getStatus().equals("Positivo")) { // se l'utente era risultato positivo viene monitorato giorno per giorno
						DaoUtente.updateStatus(u.getId(), "Negativo"); // cambia status in negativo
						
						// creazione e invio del messaggio di guarigione dall'operatore che lavora nella zona
						// in cui l'utente guarito (destinatario) risiede
						int idOperatore = DaoUtente.getIdOperatoreZona(u.getId_zona_res());
						String txt_msg = "Congratulazioni! Nell'ultimo controllo giornaliero sugli utenti positivi lei � risultato/a negativo! "
								+ "Da oggi (giorno " + DaoGenerale.getGenerale().getGiorno() + ") si pu� considerare guarito!";
						DaoMessaggio.createMessaggio(u.getId(), idOperatore, txt_msg);
					}
					
				}
			}
		}
		
		// aggiorniamo il numero di infetti totali nella tabella generale tramite conta
		// di infezioni nella tabella infezione
		DaoGenerale.updateInfetti(); 
		response.sendRedirect("Login.jsp?status=AGGIORNATO");

	}
//ritorna una lista di 20 numeri float, con le "percentuali di infettivit�" per ogni regione
	private static List<Float> getPercInfezioniPerZona() {
		List<Long> utentiPerZona = DaoUtente.getNumUtentiPerZona(); // ritorna una lista di 20 numeri(popolazione per regione)
		List<Long> infettiPerZona = DaoInfezione.getNumInfezioniPerZona(); // ritorna una lista di 20 numeri(infetti per regione)
		List<Float> percInfezioni = new ArrayList<Float>();

		try {

			for(int i = 0; i < 20; i++) {
				float infxZona = (float)infettiPerZona.get(i); //castati come float per fare la divisione(sarebbero int)
				float utentixZona = (float)utentiPerZona.get(i);
				percInfezioni.add((infxZona / utentixZona));
			}

			return percInfezioni;

		} catch(Exception e) {
			System.out.println("Errore durante la raccolta di informazioni sulle infezioni.");
			return null;
		}
	}
// se il numero random tra 0 e 1 � minore della percentuale dell'infettivit� allora si ha INFEZIONE
	private static boolean rollInfezione(float percUtente) { // ritorna TRUE se la condizione per l'infezione si � attivata
		Random r = new Random();
		float delta = 1; //modificando questo valore possiamo moltiplicare la probabilit� di infettivit� dell'utente
		percUtente = percUtente * delta ;
		
        //condizione per l'infezione
		if(r.nextFloat() <= percUtente) { // se il numero compreso tra 0 e 1 e minore dell' infettivit�(percentuale nella forma float compresa tra 0 e 1)
			return true;
		} else {
			return false;
		}
	}
}
