package it.begear.corso.database.insert;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import it.begear.corso.database.DaoInfezione;
import it.begear.corso.database.DaoUtente;
import it.begear.corso.database.Utente;

public class InfettaUtenti {

	public static ArrayList<Integer> zone = new ArrayList<Integer>();
	
	static { // inizializzazione dell'id delle 20 zone (1-20)
		for(int i = 1; i <= 20; i++) {
			zone.add(i);
		}
	}
	
	// Crea 100 infezioni (20 infezioni per zona per 5 zone)
	public static void infetta() {
		
		ArrayList<Integer> zoneInfette = selezionaZoneInfette();
		
		for(Integer idZona : zoneInfette) { // per ognuna delle 5 zone random...
			
			// lista degli utenti che risiedono nella zona
			List<Utente> utenti = DaoUtente.getNonPositiviZona(idZona);
			
			for(int j = 0; j < 20; j++) {
				Random random = new Random();
			    int randomIndex = random.nextInt(utenti.size()); 
			    
			    Utente utente = utenti.get(randomIndex); // prende un utente random dalla lista della zona
			    int idUtente = utente.getId();
			    
			    DaoInfezione.createInfezione(idUtente); // crea infezione con l'id dell'utente preso
			    
			    utenti.remove(randomIndex); // rimuovi utente dalla lista della zona per evitare doppioni
			}
		}
	}
	
	// Sceglie 5 id random tra i 20 delle zone e li ritorna come lista
	private static ArrayList<Integer> selezionaZoneInfette() {
		
		ArrayList<Integer> zoneInfette = new ArrayList<Integer>();
		
		for(int i = 0; i < 5; i++) {
			Random r = new Random();
			int randomIndex = r.nextInt(zone.size());
			
			zoneInfette.add(zone.get(randomIndex));
			zone.remove(randomIndex);
		}
		
		return zoneInfette;
	}
}
