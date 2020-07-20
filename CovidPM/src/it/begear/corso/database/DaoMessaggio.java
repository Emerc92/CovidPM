package it.begear.corso.database;

import java.util.List;

public class DaoMessaggio {
	
	private static SessionManager sm = new SessionManager();
	
	// Ricerca dei propri messaggi da parte di un utente, ritorna l'intera lista di messaggi
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
	public static boolean createMessaggio(int id_utente, String txt_msg) {
		
		try {
			sm.open();
			sm.getSession().beginTransaction();
			
			Messaggio messaggio = new Messaggio(id_utente, txt_msg);
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
	
	// Eliminazione dei propri messaggi da parte di un utente
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
