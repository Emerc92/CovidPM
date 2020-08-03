package it.begear.corso.database;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;


public class DaoInfezione {
	
	private static SessionManager sm = new SessionManager();
	
	// Ritorna il numero di infezioni totali nella tabella infezione
	// In caso di errore durante la raccolta dei dati ritorna -1
	public static long getNumInfezioni() {
		try {
			sm.open();
			
			try {
				// conta il numero di record nella tabella infezione
				Long infetti = (Long) sm.getSession().createQuery("SELECT COUNT(*) FROM Infezione")
										.getSingleResult();
				
				return infetti;
				
			} catch (Exception e) {
				// in caso di nessun infetto ritorniamo 0
				return 0L;
			}
			
		} catch(Exception e) {
			System.out.println("Errore durante la raccolta di informazioni sulle infezioni.");
			return -1L;
			
		}finally {
			sm.close();
		}
	}
	
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
					infettiPerZona.add((Long) sm.getSession().createQuery("SELECT COUNT(username) FROM Utente u INNER JOIN Infezione i ON u.id = i.id_utente WHERE id_zona_res = :numeroZona OR id_zona_lav = :numeroZona2")
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
	
	// Ritorna una lista contenente il numero di infetti per zona (indici 0-19 per le zone 1-20)
	// NB: conta solo gli utenti che risiedono in tale zona
	public static List<Long> getNumInfezioniPerZonaRes() {
		List<Long> infettiPerZona = new ArrayList<Long>(); // lista da ritornare
		
		try {
			sm.open();
			
			// ciclo for che itera le 20 zone 
			for(int i = 0; i < 20; i++) {
				int numeroZona = i + 1;
				
				try {
					// aggiungiamo alla lista il risultato della funzione COUNT()
					// di utenti infetti che risiedono o lavorano in tale zona
					infettiPerZona.add((Long) sm.getSession().createQuery("SELECT COUNT(username) FROM Utente u INNER JOIN Infezione i ON u.id = i.id_utente WHERE id_zona_res = :numeroZona")
							.setParameter("numeroZona", numeroZona)
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
	// Ritorna l'infezione se c'è un match (la persona è infetta), null altrimenti
	private static Infezione getInfezione(int id_utente) {
		try {
			sm.open();
			Infezione infezione = (Infezione) sm.getSession().createQuery("FROM Infezione WHERE id_utente = :id_utente")
										.setParameter("id_utente", id_utente)
										.getSingleResult();
				
			return infezione;
						
		} catch(NoResultException e) {
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
	
	// Toglie 1 ai giorni rimasti alla guarigione, poi ritorna i nuovi giorni rimasti
	// o -1 in caso di mancato update dei giorni rimasti
	public static int updateInfezione(int id_utente) {
		
		Infezione infezione = getInfezione(id_utente);
		
		try {
			sm.open();
			sm.getSession().beginTransaction();
			
			int giorni_rimasti = infezione.getGiorni_rimasti() - 1;
			sm.getSession().createQuery("UPDATE Infezione SET giorni_rimasti = :giorni_rimasti WHERE id_utente = :id_utente")
							.setParameter("giorni_rimasti", giorni_rimasti)
							.setParameter("id_utente", id_utente)
							.executeUpdate();
			
			sm.getSession().getTransaction().commit();
			return giorni_rimasti;
			
		} catch(Exception e) {
			System.out.println("Errore nell'update infezione, eseguo un rollback.");
			sm.getSession().getTransaction().rollback();
			return -1;
			
		} finally {
			sm.close();
		}
	}
	
	// Elimina l'infezione associata all'id utente passato (l'utente è guarito)
	// Ritorna true se l'infezione viene eliminata, false altrimenti
	public static boolean deleteInfezione(int id_utente) {
		
		try {
			sm.open();
			sm.getSession().beginTransaction();
			
			sm.getSession().createQuery("DELETE FROM Infezione WHERE id_utente = :id_utente")
							.setParameter("id_utente", id_utente)
							.executeUpdate();
			
			sm.getSession().getTransaction().commit();
			return true;
			
		} catch(Exception e) {
			System.out.println("Errore nell'eliminazione, eseguo un rollback.");
			sm.getSession().getTransaction().rollback();
			return false;
			
		} finally {
			sm.close();
		}
	}
	
	public static boolean resetInfezione() {
		
		try {
			sm.open();
			sm.getSession().beginTransaction();
			
			sm.getSession().createQuery("DELETE FROM Infezione")
							.executeUpdate();
			
			sm.getSession().getTransaction().commit();
			
		} catch(Exception e) {
			System.out.println("Errore nell'eliminazione, eseguo un rollback.");
			sm.getSession().getTransaction().rollback();
			return false;
			
		} finally {
			sm.close();
		}
		
		try {
			sm.open();
			sm.getSession().beginTransaction();
			
			sm.getSession().createSQLQuery("ALTER TABLE Infezione AUTO_INCREMENT = 1")
							.executeUpdate();
			
			sm.getSession().getTransaction().commit();
			
			return true;
			
		} catch(Exception e) {
			System.out.println("Errore nell'eliminazione, eseguo un rollback.");
			sm.getSession().getTransaction().rollback();
			return false;
			
		} finally {
			sm.close();
		}
	}
	
}
