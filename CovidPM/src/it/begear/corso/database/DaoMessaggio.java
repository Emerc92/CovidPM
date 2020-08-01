package it.begear.corso.database;

import java.util.List;

public class DaoMessaggio {
	
	private static SessionManager sm = new SessionManager();
	
	// Ritorna l'intera lista di messaggi appartenente ad un determinato utente
	public static List<Messaggio> getMessaggiUtente(Utente utente) {
		int id_utente = utente.getId();
		
		try {
			sm.open();
			
			@SuppressWarnings("unchecked")
			List<Messaggio> messaggi = sm.getSession().createQuery("FROM Messaggio WHERE id_utente = :id_utente")
										.setParameter("id_utente", id_utente)
										.getResultList();
			
			return messaggi;
					
		} catch(Exception e) {
			System.out.println("Errore nella ricerca di messaggi.");
			return null;
			
		} finally {
			sm.close();
		}
	}
	
	// Creazione messaggio associato ad un determinato utente
	// Ritorna true se il messaggio viene creato, altrimenti false
	public static boolean createMessaggio(int id_utente, int id_mittente, String txt_msg) {
		
		// estrae il giorno corrente dalla tabella generale
		// per salvarlo come giorno di creazione del messaggio
		Generale generale = DaoGenerale.getGenerale();
		int giorno = generale.getGiorno();
		
		try {
			sm.open();
			sm.getSession().beginTransaction();
			
			Messaggio messaggio = new Messaggio(id_utente, id_mittente, giorno, txt_msg);
			sm.getSession().save(messaggio);
			
			sm.getSession().getTransaction().commit();
			return true;
			
		} catch(Exception e) {
			System.out.println("Errore nella creazione del messaggio, eseguo un rollback.");
			sm.getSession().getTransaction().rollback();
			return false;
			
		} finally {
			sm.close();
		}
	}
	
	// Eliminazione di tutti i messaggi appartenenti ad un determinato utente
	public static void deleteMessaggiUtente(Utente utente) {
		int id_utente = utente.getId();
		
		try {
			sm.open();
			sm.getSession().beginTransaction();
			
			sm.getSession().createSQLQuery("DELETE FROM Messaggio WHERE id_utente = :id_utente")
			.setParameter("id_utente", id_utente)
			.executeUpdate();

			sm.getSession().getTransaction().commit();
					
		} catch(Exception e) {
			sm.getSession().getTransaction().rollback();
			System.out.println("Errore nell'eliminazione di messaggi, eseguo un rollback.");
		} finally {
			sm.close();
		}
	}
		

}
