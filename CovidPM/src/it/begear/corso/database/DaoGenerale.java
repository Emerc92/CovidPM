package it.begear.corso.database;

import javax.persistence.NoResultException;

public class DaoGenerale {
	
	private static SessionManager sm = new SessionManager();
	
	// Ritorna l'unica istanza di generale. In caso di più istanze (errore) ritorna null
	public static Generale getGenerale() {
		try {
			sm.open();
			Generale generale = (Generale) sm.getSession().createQuery("FROM Generale")
										.getSingleResult();
				
			return generale;
						
		} catch(NoResultException e) {
			return null;
				
		} finally {
			sm.close();
		}
	}
	
	// Aggiorna l'istanza di generale avanzando il giorno corrente
	// Ritorna true se va a buon fine, false altrimenti
	public static boolean updateGiorno() {
		
		Generale generale = getGenerale();
		int giorno = generale.getGiorno() + 1; // passa un giorno
		
		try {
			sm.open();
			sm.getSession().beginTransaction();
			
			// Esegue l'update con i nuovi valori di giorno e di infezioni totali
			sm.getSession().createQuery("UPDATE Generale SET giorno = :giorno")
							.setParameter("giorno", giorno)
							.executeUpdate();
			
			sm.getSession().getTransaction().commit();
			return true;
			
		} catch(Exception e) {
			System.out.println("Errore nell'update generale, eseguo un rollback.");
			sm.getSession().getTransaction().rollback();
			return false;
			
		} finally {
			sm.close();
		}
	}
	
	// Aggiorna l'istanza di generale con le nuove infezioni totali
	// Ritorna true se va a buon fine, false altrimenti
	public static boolean updateInfetti() {
		
		Generale generale = getGenerale();
		long infetti = DaoInfezione.getNumInfezioni(); // conta le infezioni totali
		
		try {
			sm.open();
			sm.getSession().beginTransaction();
			
			// Esegue l'update con i nuovi valori di giorno e di infezioni totali
			sm.getSession().createQuery("UPDATE Generale SET infetti = :infetti")
							.setParameter("infetti", infetti)
							.executeUpdate();
			
			sm.getSession().getTransaction().commit();
			return true;
			
		} catch(Exception e) {
			System.out.println("Errore nell'update generale, eseguo un rollback.");
			sm.getSession().getTransaction().rollback();
			return false;
			
		} finally {
			sm.close();
		}
	}
	
	public static boolean resetGenerale() {
		
		try {
			sm.open();
			sm.getSession().beginTransaction();
			
			sm.getSession().createQuery("UPDATE Generale SET giorno = 0 , infetti = 0")
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
