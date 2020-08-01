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
	
	// Aggiorna l'istanza di generale avanzando il giorno corrente e tenendo traccia delle infezioni totali
	// Ritorna true se va a buon fine, false altrimenti
	public static boolean updateGenerale() {
		
		Generale generale = getGenerale();
		int giorno = generale.getGiorno() + 1; // passa un giorno
		long infetti = DaoInfezione.getNumInfezioni(); // conta le infezioni totali
		
		try {
			sm.open();
			sm.getSession().beginTransaction();
			
			// Esegue l'update con i nuovi valori di giorno e di infezioni totali
			sm.getSession().createSQLQuery("UPDATE Generale SET giorno = :giorno , infetti = :infetti")
							.setParameter("giorno", giorno)
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

}
