package it.begear.corso.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.NoResultException;


public class DaoInfezione {
	
	private static SessionManager sm = new SessionManager();
	
	public static List<Float> searchPercInfezioni() {
		List<Long> utentiPerZona = new ArrayList<Long>();
		List<Long> infettiPerZona = new ArrayList<Long>();
		List<Float> percInfezioni = new ArrayList<Float>();
		
		try {
			sm.open();
			
			for(int i = 0; i < 20; i++) {
				int numeroZona = i + 1;
				int numeroZona2 = i + 1;
				
				try {
					utentiPerZona.add((Long) sm.getSession().createQuery("SELECT COUNT(nome) FROM Utente WHERE id_zona_res = :numeroZona OR id_zona_lav = :numeroZona2")
							.setParameter("numeroZona", numeroZona)
							.setParameter("numeroZona2", numeroZona2)
							.getSingleResult());
				} catch (Exception e) {
					utentiPerZona.add(0L);
				}
				
				try {
					infettiPerZona.add((Long) sm.getSession().createQuery("SELECT COUNT(nome) FROM Utente INNER JOIN Infezione ON Utente.id = Infezione.id_utente WHERE id_zona_res = :numeroZona OR id_zona_lav = :numeroZona2")
							.setParameter("numeroZona", numeroZona)
							.setParameter("numeroZona2", numeroZona2)
							.getSingleResult());
				} catch (Exception e) {
					infettiPerZona.add(0L);
				}
				
				
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
