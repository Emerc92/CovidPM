package it.begear.corso.servlet.medico;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.begear.corso.database.DaoInfezione;
import it.begear.corso.database.DaoMessaggio;
import it.begear.corso.database.DaoUtente;
import it.begear.corso.database.DaoZona;
import it.begear.corso.database.Utente;


/**
 * Servlet implementation class EseguiTest
 */
@WebServlet("/EseguiTest")
public class EseguiTest extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public EseguiTest() {}
   
    private void msgOperatore(Utente client, int tamponiEseguiti,int nPositivi,int nNegativi) {
    	 int idOperatoreZona = DaoUtente.getIdOperatoreZona(client.getId_zona_lav());
    	    String messaggioOperatore = "Abbiamo eseguito " + tamponiEseguiti +" tamponi e abbiamo trovato " + nPositivi + " utenti positivi e " + nNegativi + " utenti negativi";
    	    DaoMessaggio.createMessaggio(idOperatoreZona, client.getId(), messaggioOperatore);
    }
    
    private void updateZona(Utente client) {
        float positiviZona = (float)DaoUtente.getPositiviZona(client.getId_zona_lav()).size();
        float negativiZona = (float)DaoUtente.getNegativiZona(client.getId_zona_lav()).size();
        float testatiZona = positiviZona + negativiZona;
        float percPositiviZona = positiviZona / testatiZona;
        if(percPositiviZona < 0.10) {
          DaoZona.updateAllerta(client.getId_zona_lav(), "Verde");
        } else if(percPositiviZona < 0.40) {
          DaoZona.updateAllerta(client.getId_zona_lav(), "Gialla");
        } else {
          DaoZona.updateAllerta(client.getId_zona_lav(), "Rossa");
        }
        
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		    String nTamp = request.getParameter("nTamp"); //prende il numero di tamponi
		    int nT = Integer.parseInt(nTamp);
		    HttpSession session = request.getSession();
		    Utente client = (Utente) session.getAttribute("client"); //prende l'oggetto utente "client"
		    //ritorna la lista di utenti non positivi
		    List<Utente> utentiNonPositivi = DaoUtente.getNonPositiviZona(client.getId_zona_lav());
		    
		    int tamponiEseguiti = 0;
		    int nPositivi = 0;
		    int nNegativi = 0;
		    
		    // esecuzione tamponi su utenti random finché non si raggiunge la quota o si finiscono i non positivi
		    while(!utentiNonPositivi.isEmpty() && tamponiEseguiti < nT) { //finche la lista non è vuota e i tamponi eseguiti sono meno dei tamponi richiesti
		      Random random = new Random();
		      int randomIndex = random.nextInt(utentiNonPositivi.size()); 
		      
		      Utente paziente = utentiNonPositivi.get(randomIndex);//prendi un paziente a caso dalla lista
		      boolean infetto = DaoInfezione.searchInfezione(paziente.getId()); //cerca paziente nella tabella infezioni
		      String status;
		      
		      if(infetto) { //se è presente cambia lo status a positivo
		        status = "Positivo";
		        nPositivi++;
		      } else { //altrimenti cambia lo status a negativo
		        status = "Negativo";
		        nNegativi++;
		      }
		      
		      DaoUtente.updateStatus(paziente.getId(), status);
		      String messaggioPaziente = "Le comunichiamo il risultato del tampone: " + status.toLowerCase();
		      DaoMessaggio.createMessaggio(paziente.getId(), client.getId(), messaggioPaziente); //manda un messaggio al paziente
		      
		      utentiNonPositivi.remove(paziente); //rimuovi paziente dalla lista di controllo del while
		      
		      tamponiEseguiti++;
		    }
		    
		msgOperatore(client,tamponiEseguiti,nPositivi,nNegativi);// manda messaggio all'operatore con i risultati del test
		updateZona(client); //aggiorna lo status della zona di lavoro del client: verde,giallo e rosso
		response.sendRedirect("Medico.jsp?status=testato");
	}

}
