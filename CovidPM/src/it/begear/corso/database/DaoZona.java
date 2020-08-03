package it.begear.corso.database;

public class DaoZona {
	
	private static SessionManager sm = new SessionManager();

	// Modifica l'allerta di una zona assegnandole come nuovo valore la stringa immessa come parametro
	// Ritorna true se l'allerta viene modificata, altrimenti false
	public static boolean updateAllerta(int id, String allerta) {
			
		try {
			sm.open();
			sm.getSession().beginTransaction();
				
			sm.getSession().createQuery("UPDATE Zona SET allerta = :allerta WHERE id = :id")
								.setParameter("allerta", allerta)
								.setParameter("id", id)
								.executeUpdate();
				
			sm.getSession().getTransaction().commit();
			return true;
				
		} catch(Exception e) {
			System.out.println("Errore nella modifica, eseguo un rollback.");
			sm.getSession().getTransaction().rollback();
			return false;
				
		} finally {
			sm.close();
		}
	}
	
	public static boolean resetZona() {
		
		try {
			sm.open();
			sm.getSession().beginTransaction();
			
			String allerta = "Verde";
			
			sm.getSession().createQuery("UPDATE Zona SET allerta = :allerta")
							.setParameter("allerta", allerta)
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
