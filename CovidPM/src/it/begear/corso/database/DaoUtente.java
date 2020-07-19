package it.begear.corso.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.NoResultException;


public class DaoUtente {

	private static SessionManager sm = new SessionManager();
	
	private static final char[] caratteri = {
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
			'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '!', '_', '-', '?'
	};
	
	// Ricerca di utente tramite username(unico) e password. Se entrambi corrispondono,
	// ritorna l'istanza di utente del database, altrimenti ritorna null
	public static Utente login(String username, String password) {
		try {
			sm.open();
			Utente utente = (Utente) sm.getSession().createQuery("FROM Utente WHERE username = :username AND password = :password")
							.setParameter("username", username)
							.setParameter("password", password)
							.getSingleResult();
			
			return utente;
					
		} catch(NoResultException e) {
			System.out.println("Nessun utente con tali dati nel database.");
			return null;
		} finally {
			sm.close();
		}
	}
	
	// Ritorna la lista di tutti gli utenti sul database
	public static List<Utente> getListaUtenti() {
		try {
			sm.open();
			
			@SuppressWarnings("unchecked")
			List<Utente> utenti = sm.getSession().createQuery("FROM Utente")
									.getResultList();
			return utenti;
					
		} catch(Exception e) {
			System.out.println("Errore durante la lettura di utenti dal database.");
			return null;
		} finally {
			sm.close();
		}
	}
	
	// Ritorna una lista di numero di utenti per zona (residenza o lavoro)
	public static List<Long> getNumUtentiPerZona() {
		List<Long> utentiPerZona = new ArrayList<Long>();
		
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
				
			}
			
			return utentiPerZona;
					
		} catch(Exception e) {
			System.out.println("Errore durante la raccolta di informazioni sugli utenti.");
			return null;
		} finally {
			sm.close();
		}
	}
	
	
	// Creazione utente da parte dell'operatore, ritorna boolean in base all'esito
	public static boolean createUtente(String nome, String cognome, String genere, int id_zona_res, int id_zona_lav, String tipo) {
		Random random = new Random();
		String username = nome + cognome + (1000 + random.nextInt(9000));
		String password = createPassword();
		
		try {
			sm.open();
			sm.getSession().beginTransaction();
			
			Utente utente = new Utente(nome, cognome, genere, id_zona_res, id_zona_lav, username, password, tipo);
			sm.getSession().save(utente);
			
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
	
	// Crea una password randomica di 8 caratteri
	private static String createPassword() {
		Random r = new Random();
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < 8; i++) {
			sb.append(caratteri[r.nextInt(caratteri.length)]);
		}
		
		return sb.toString();
	}
	
}
