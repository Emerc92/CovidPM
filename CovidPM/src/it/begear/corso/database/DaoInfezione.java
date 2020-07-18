package it.begear.corso.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.NoResultException;


public class DaoInfezione {
	
	private static SessionManager sm = new SessionManager();
	
	public static List<Float> searchPercInfezioni() {
		List<Integer> utentiPerZona = new ArrayList<Integer>();
		List<Integer> infettiPerZona = new ArrayList<Integer>();
		List<Float> percInfezioni = new ArrayList<Float>();
		
		try {
			sm.open();
			
			for(int i = 0; i < 20; i++) {
				int numeroZona = i + 1;
				
				utentiPerZona.add((Integer) sm.getSession().createQuery("SELECT SUM(id_zona_res = :numeroZona OR id_zona_lav = :numeroZona) FROM Utente")
						.setParameter("numeroZona", numeroZona)
						.getSingleResult());
				
				Integer infettiZonaAttuale = (Integer) sm.getSession().createQuery("SELECT SUM(id_zona_res = :numeroZona OR id_zona_lav = :numeroZona) FROM Utente INNER JOIN Infezione ON Utente.id = Infezione.id_utente")
						.setParameter("numeroZona", numeroZona)
						.getSingleResult();
				if(infettiZonaAttuale == null) {
					infettiZonaAttuale = 0;
				}
				infettiPerZona.add(infettiZonaAttuale);
				
				percInfezioni.add((float) (infettiPerZona.get(i) / utentiPerZona.get(i)));
				
			}
			
			return percInfezioni;

					
		} catch(Exception e) {
			System.out.println("Errore durante la raccolta di informazioni sulle infezioni.");
			return null;
		} finally {
			sm.close();
		}
	}
	
	public static boolean searchInfezione(int id_utente) {
		try {
			sm.open();
			Infezione infezione = (Infezione) sm.getSession().createQuery("FROM Infezione WHERE id_utente = :id_utente")
										.setParameter("id_utente", id_utente)
										.getSingleResult();
			
			return true;
					
		} catch(NoResultException e) {
			System.out.println("Nessuna infezione con tali dati nel database.");
			return false;
		} finally {
			sm.close();
		}
	}
	
	public static void createInfezione(int id_utente) {
		try {
			sm.open();
			sm.getSession().beginTransaction();
			
			Infezione infezione = new Infezione(id_utente);
			sm.getSession().save(infezione);
			
			sm.getSession().getTransaction().commit();
			
		} catch(Exception e) {
			System.out.println("Errore nell'inserimento, eseguo un rollback.");
			sm.getSession().getTransaction().rollback();
		} finally {
			sm.close();
		}
	}
	

}
