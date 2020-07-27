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
	
	// Ricerca di utente tramite username (unico) e password. Se entrambi corrispondono,
	// ritorna l'istanza di utente del database, altrimenti ritorna null
	public static Utente login(String username, String password) {
		try {
			sm.open(); //apre una session creata dalla "sessionFactory"
			Utente utente = (Utente) sm.getSession().createQuery("FROM Utente WHERE username = :username AND password = :password")
							.setParameter("username", username)
							.setParameter("password", password)
							.getSingleResult(); //ritorna un risultato singolo o lancia un eccezione!
			
			return utente;
					
		} catch(NoResultException e) {
			System.out.println("Nessun utente con tali dati nel database.");
			return null;
			
		} finally {
			sm.close(); //chiude la session 
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
	
	// Ritorna la lista di tutti gli utenti con nome e cognome cercati
	// Se non vi è alcun utente che corrisponde ritorna null
	public static List<Utente> getUtentiNomeCognome(String nome, String cognome) {
		try {
			sm.open();
			
			@SuppressWarnings("unchecked")
			List<Utente> utenti =  (List<Utente>) sm.getSession().createQuery("FROM Utente WHERE nome = :nome AND cognome = :cognome")
									.setParameter("nome", nome)
									.setParameter("cognome", cognome)
									.getResultList();									
			
			return utenti;
					
		} catch(Exception e) {
			System.out.println("Nessun utente con tali dati nel database.");
			return null;
			
		} finally {
			sm.close();
		}
	}
	
	// Ritorna la lista di tutti gli utenti positivi in quella zona
	// Se non vi è alcun utente positivo corrisponde ritorna null
	public static List<Utente> getPositiviZona(int id_zona_res) {
		try {
			sm.open();
			
			@SuppressWarnings("unchecked")
			List<Utente> utenti =  (List<Utente>) sm.getSession().createQuery("FROM Utente WHERE id_zona_res = :id_zona_res AND status = :status")
									.setParameter("id_zona_res", id_zona_res)
									.setParameter("status", "Positivo")
									.getResultList();								
			
			return utenti;
					
		} catch(Exception e) {
			System.out.println("Nessun utente con tali dati nel database.");
			return null;
			
		} finally {
			sm.close();
		}
	}
	
	// Ritorna la lista di tutti gli utenti negativi in quella zona
	// Se non vi è alcun utente negativo corrisponde ritorna null
	public static List<Utente> getNegativiZona(int id_zona_res) {
		try {
			sm.open();
			
			@SuppressWarnings("unchecked")
			List<Utente> utenti =  (List<Utente>) sm.getSession().createQuery("FROM Utente WHERE id_zona_res = :id_zona_res AND status = :status")
										.setParameter("id_zona_res", id_zona_res)
										.setParameter("status", "Negativo")
										.getResultList();								
				
			return utenti;
						
		} catch(Exception e) {
			System.out.println("Nessun utente con tali dati nel database.");
			return null;
			
		} finally {
			sm.close();
		}
	}
	
	// Ritorna la lista di tutti gli utenti non positivi (negativi o non testati) in quella zona
	// Se tutti gli utenti son positivi ritorna null
	public static List<Utente> getNonPositiviZona(int id_zona_res) {
		try {
			sm.open();
			
			@SuppressWarnings("unchecked")
			List<Utente> utenti =  (List<Utente>) sm.getSession().createQuery("FROM Utente WHERE id_zona_res = :id_zona_res AND status <> :status")
										.setParameter("id_zona_res", id_zona_res)
										.setParameter("status", "Positivo")
										.getResultList();								
				
			return utenti;
						
		} catch(Exception e) {
			System.out.println("Nessun utente con tali dati nel database.");
			return null;
				
		} finally {
			sm.close();
		}
	}
	
	
	// Ritorna una lista contenente il numero di utenti per zona (indici 0-19 per le zone 1-20)
	// NB: conta sia utenti che risiedono o che lavorano in tale zona
	public static List<Long> getNumUtentiPerZona() {
		List<Long> utentiPerZona = new ArrayList<Long>(); // lista da ritornare
		
		try {
			sm.open();
			
			// ciclo for che itera le 20 zone 
			for(int i = 0; i < 20; i++) {
				int numeroZona = i + 1;
				int numeroZona2 = i + 1;
				
				try {
					// aggiungiamo alla lista il risultato della funzione COUNT()
					// di utenti che risiedono o lavorano in tale zona
					utentiPerZona.add((Long) sm.getSession().createQuery("SELECT COUNT(nome) FROM Utente WHERE id_zona_res = :numeroZona OR id_zona_lav = :numeroZona2")
							.setParameter("numeroZona", numeroZona)
							.setParameter("numeroZona2", numeroZona2)
							.getSingleResult());
				} catch (Exception e) {
					// in caso di nessun utente aggiungiamo 0
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
	
	
	// Ritorna la lista degli id dei medici che lavora in tale zona
	// Se non vi sono medici ritorna null
	@SuppressWarnings("unchecked")
	public static List<Integer> getIdMediciZona(int id_zona_lav) {
		List<Integer> idMediciZona;
		
		try {
			sm.open();
			
			idMediciZona = sm.getSession().createQuery("SELECT id FROM Utente WHERE tipo = :tipo AND id_zona_lav = :id_zona_lav")
							.setParameter("tipo", "Medico")
							.setParameter("id_zona_lav", id_zona_lav)
							.getResultList();
			
			return idMediciZona;
					
		} catch(Exception e) {
			System.out.println("Errore durante la raccolta di informazioni sugli utenti.");
			return null;
			
		} finally {
			sm.close();
		}
	}
	
	// Ritorna l'id dell'operatore comunale che lavora in tale zona
	// Se non vi è operatore in tale zona ritorna -1
	public static int getIdOperatoreZona(int id_zona_lav) {
		int idOperatoreZona;
			
		try {
			sm.open();
				
			idOperatoreZona = (int) sm.getSession().createQuery("SELECT id FROM Utente WHERE tipo = :tipo AND id_zona_lav = :id_zona_lav")
											.setParameter("tipo", "Operatore")
											.setParameter("id_zona_lav", id_zona_lav)
											.getSingleResult();
				
			return idOperatoreZona;
						
		} catch(Exception e) {
			System.out.println("Errore durante la raccolta di informazioni sugli utenti.");
			return -1;
			
		} finally {
			sm.close();
		}
	}
	
	
	// Creazione utente da parte dell'operatore
	// Ritorna true se l'utente viene creato, altrimenti false
	public static boolean createUtente(String nome, String cognome, String genere, int id_zona_res, int id_zona_lav, String tipo) {
		// Username e password vengono generati randomicamente
		Random random = new Random();
		String username = nome.trim() + cognome.trim() + (1000 + random.nextInt(9000)); 
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
	
	// Modifica lo status di un utente in base alla presenza o meno di un infezione a suo nome
	// Ritorna true se lo status viene modificato, false altrimenti
	public static boolean updateStatus(int id, String status) {
		
		try {
			sm.open();
			sm.getSession().beginTransaction();
			
			sm.getSession().createQuery("UPDATE Utente SET status = :status WHERE id = :id")
							.setParameter("status", status)
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
	
}
