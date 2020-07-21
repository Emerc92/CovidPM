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
				        if(!DaoInfezione.searchInfezione(u.getId())) {
				          
				          float percUtente = (float) (percInfezioniZone.get(u.getId_zona_res() - 1) + percInfezioniZone.get(u.getId_zona_lav() - 1) / 2); 
				          if(rollInfezione(percUtente)) {
				            DaoInfezione.createInfezione(u.getId());
				          }
				        }
				      }
				      
				      response.sendRedirect("index.jsp?status=AGGIORNATO");
		
	}

	  private static List<Float> getPercInfezioniPerZona() {
		    List<Long> utentiPerZona = DaoUtente.getNumUtentiPerZona();
		    List<Long> infettiPerZona = DaoInfezione.getNumInfezioniPerZona();
		    List<Float> percInfezioni = new ArrayList<Float>();
		      
		    try {
		        
		      for(int i = 0; i < 20; i++) {
		        percInfezioni.add((float) (infettiPerZona.get(i) / utentiPerZona.get(i)));
		      }
		        
		      return percInfezioni;
		    
		      } catch(Exception e) {
		        System.out.println("Errore durante la raccolta di informazioni sulle infezioni.");
		        return null;
		      }
		    }
		    
		   private static boolean rollInfezione(float percUtente) {
		     Random r = new Random();
		      percUtente = percUtente / 5;
		      
		      if(r.nextFloat() <= percUtente) {
		        return true;
		      } else {
		         return false;
		      }
		    }
}
