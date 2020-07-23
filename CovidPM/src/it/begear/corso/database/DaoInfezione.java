package it.begear.corso.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.NoResultException;


public class DaoInfezione {
	
	private static SessionManager sm = new SessionManager();
	
	// Ritorna una lista contenente il numero di infetti per zona (indici 0-19 per le zone 1-20)
	// NB: conta sia utenti che risiedono o che lavorano in tale zona
	public static List<Long> getNumInfezioniPerZona() {
		List<Long> infettiPerZona = new ArrayList<Long>(); // lista da ritornare
		
		try {
			sm.open();
			
			// ciclo for che itera le 20 zone 
			for(int i = 0; i < 20; i++) {
				int numeroZona = i + 1;
				int numeroZona2 = i + 1;
				
				try {
					// aggiungiamo alla lista il risultato della funzione COUNT()
					// di utenti infetti che risiedono o lavorano in tale zona
					infettiPerZona.add((Long) sm.getSession().createQuery("SELECT COUNT(nome) FROM Utente u INNER JOIN Infezione i ON u.id = i.id_utente WHERE id_zona_res = :numeroZona OR id_zona_lav = :numeroZona2")
							.setParameter("numeroZona", numeroZona)
							.setParameter("numeroZona2", numeroZona2)
							.getSingleResult());
				} catch (Exception e) {
					// in caso di nessun infetto aggiungiamo 0
					infettiPerZona.add(0L);
				}
				
			}
			
			return infettiPerZona;
	
		} catch(Exception e) {
			System.out.println("Errore durante la raccolta di informazioni sulle infezioni.");
			return null;
			
		} finally {
			sm.close();
		}
	}
	
	// Cerca un'infezione con l'id_utente corrispondente
	// Ritorna true se c'è un match (la persona è infetta), false altrimenti
	public static boolean searchInfezione(int id_utente) {
		try {
			sm.open();
			Infezione infezione = (Infezione) sm.getSession().createQuery("FROM Infezione WHERE id_utente = :id_utente")
										.setParameter("id_utente", id_utente)
										.getSingleResult();
			
			return true;
					
		} catch(NoResultException e) {
			return false;
			
		} finally {
			sm.close();
		}
	}
	
	// Creazione di una nuova infezione con l'id_utente specificato
	// Ritorna true se l'infezione viene creata, altrimenti false
	public static boolean createInfezione(int id_utente) {
		try {
			sm.open();
			sm.getSession().beginTransaction();
			
			Infezione infezione = new Infezione(id_utente);
			sm.getSession().save(infezione);
			
			sm.getSession().getTransaction().commit();
			return true;
			
		} catch(Exception e) {
			System.out.println("Errore nell'inserimento, eseguo un rollback.");
			sm.getSession().getTransaction().rollback();
			return false;
			
		} finally {
			sm.close();
		}
	}
	

}
