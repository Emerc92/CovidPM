package it.begear.corso.database.insert;

import java.util.Random;

import it.begear.corso.database.DaoUtente;


public class CreaUtenti {

	// array dei possibili nomi maschili
	private static String[] nomiMaschili = {
			"Francesco", "Alessandro", "Leonardo", "Lorenzo", "Mattia", "Andrea", "Gabriele", "Matteo", "Tommaso", "Riccardo", "Edoardo", "Giuseppe", "Davide", "Antonio",
			"Federico", "Diego", "Giovanni", "Christian", "Nicolò", "Samuele", "Pietro", "Marco", "Luca", "Filippo", "Simone", "Alessio", "Gabriel", "Michele", "Emanuele",
			"Jacopo", "Salvatore", "Giulio", "Cristian", "Daniele", "Vincenzo", "Giacomo", "Gioele", "Manuel", "Elia", "Thomas", "Samuel", "Giorgio", "Daniel", "Enea",
			"Stefano", "Luigi", "Nicola", "Domenico", "Angelo", "Kevin", "Luca", "Abramo", "Adam", "Brando", "Benedetto", "Carlo", "Ciro", "Corrado", "Cesare", "Denis",
			"Donato", "Enrico", "Eros", "Enzo", "Flavio", "Ferdinando", "Fabio", "Gaetano", "Guido", "Gianpiero", "Gaspare", "Ivan", "Iacopo", "Italo", "Jacopo", "Jason",
			"Livio", "Mirco", "Mauro", "Milo", "Nicola", "Nathan", "Nelson", "Omar", "Orlando", "Otto", "Paolo", "Pablo", "Pasquale", "Raffaele", "Romeo", "Raimondo",
			"Ruggero", "Remo", "Sebastiano", "Saverio", "Santo", "Thomas", "Tiago", "Teo", "Tiberio", "Umberto", "Ugo", "Valerio", "William", "Yassin", "Zeno", "Bruno",
			"Claudio", "Lucio", "Roberto", "Tiziano", "Vittorio", "Emiliano"
	};
	
	// array dei possibili nomi femminili
	private static String[] nomiFemminili = {
			"Sofia", "Aurora", "Giulia", "Emma", "Giorgia", "Martina", "Alice", "Greta", "Ginevra", "Chiara", "Anna", "Sara", "Beatrice", "Nicole", "Gaia", "Matilde", "Vittoria",
			"Noemi", "Francesca", "Alessia", "Ludovica", "Arianna", "Viola", "Camilla", "Elisa", "Bianca", "Giada", "Rebecca", "Elena", "Mia", "Adele", "Marta", "Gioia", "Maria",
			"Asia", "Eleonora", "Carlotta", "Miriam", "Irene", "Melissa", "Margherita", "Emily", "Caterina", "Anita", "Serena", "Benedetta", "Rachele", "Angelica", "Cecilia", "Isabel",
			"Ambra", "Angela", "Agnese", "Anastasia", "Antonella", "Barbara", "Brenda", "Bruna", "Claudia", "Carmen", "Cinzia", "Diletta", "Diana", "Desiree", "Denise", "Daniela",
			"Dalila", "Domenica", "Eva", "Ester", "Erica", "Elettra", "Federica", "Flavia", "Gloria", "Hillary", "Ilaria", "Ilenia", "Iolanda", "Jessica", "Jennifer", "Laura",
			"Lucia", "Lisa", "Letizia", "Luisa", "Michela", "Maddalena", "Morena", "Monica", "Nadia", "Natalia", "Olivia", "Olga", "Paola", "Petra", "Patrizia", "Rosa", "Roberta",
			"Rossella", "Regina", "Simona", "Stella", "Sabrina", "Teresa", "Tiziana", "Veronica", "Vanessa", "Viviana"
	};
	
	// array dei cognomi possibili
	private static String[] cognomi = {
			"Rossi", "Ferrari", "Russo", "Bianchi", "Romano", "Gallo", "Costa", "Fontana", "Conti", "Esposito", "Ricci", "Bruno", "Rizzo", "Moretti", "De Luca", "Marino", "Greco",
			"Barbieri", "Lombardi", "Giordano", "Rinaldi", "Colombo", "Mancini", "Longo", "Leone", "Martinelli", "Marchetti", "Martini", "Galli", "Gatti", "Mariani", "Ferrara",
			"Santoro", "Marini", "Bianco", "Conte", "Serra", "Farina", "Gentile", "Caruso", "Morelli", "Ferri", "Testa", "Ferraro", "Pellegrini", "Grassi", "Rossetti",
			"D'Angelo", "Bernardi", "Mazza", "Rizzi", "Silvestri", "Vitale", "Franco", "Parisi", "Martino", "Valentini", "Mogavino", "Castelli", "Bellini", "Monti", "Lombardo",
			"Fiore", "Grasso", "Ferro", "Carbone", "Orlando", "Guerra", "Palmieri", "Milani", "Villa", "Viola", "Ruggeri", "De Santis", "D'Amico", "Battaglia", "Negri", "Sala",
			"Palumbo", "Benedetti", "Olivieri", "Giuliani", "Rosa", "Amato", "Molinari", "Alberti", "Barone", "Pellegrino", "Piazza", "Moro", "Caputo", "Poli", "Vitali", "De Angelis",
			"D'Agostino", "Cattaneo", "Bassi", "Valente", "Coppola", "Spinelli", "Sartori", "Barzani", "Belzani", "Mercado", "Di Puccio", "Di Vincenzo", "Marchina", "Consolati", 
			"Martinazzoli", "Gazzoli", "Orizio", "Gorla", "Zanzari", "Franzoni", "Bertocchi", "Zanoni", "Zorzi", "Morelli"
	};
	
	// crea tutti gli utenti (1000 cittadini, 40 agenti, 40 medici, 20 operatori)
	public static void crea() {
		creaOperatori();
		creaMedici();
		creaAgenti();
		creaCittadini();
	}
	
	// crea 1000 cittadini
	public static void creaCittadini() {
		
		for(int i = 0; i < 1000; i++) { 
			creaCittadino();
		}
	}
	
	// crea un cittadino con nome, cognome, genere, zona residenza e zona lavorativa random
	private static void creaCittadino() {

		String genere = rollGenere();
		String nome = rollNome(genere);
		String cognome = rollCognome();
		int id_zona_res = rollZona();
		int id_zona_lav = rollZona();
		
		DaoUtente.createUtente(nome, cognome, genere, id_zona_res, id_zona_lav, "Cittadino");
	}
	
	// crea 40 agenti, 2 per zona
	public static void creaAgenti() {
		
		for(int i = 0; i < 2; i++) {
			for(int j = 1; j <= 20; j++) {
				creaAgente(j);
			}
		}
	}
	
	// crea un agente con nome, cognome, genere random che risiede e lavora in una determinata zona
	private static void creaAgente(int zona) {

		String genere = rollGenere();
		String nome = rollNome(genere);
		String cognome = rollCognome();
		
		DaoUtente.createUtente(nome, cognome, genere, zona, zona, "Agente");
	}
	
	// crea 40 medici, 2 per zona
	public static void creaMedici() {
		
		for(int i = 0; i < 2; i++) {
			for(int j = 1; j <= 20; j++) {
				creaMedico(j);
			}
		}
	}
	
	// crea un medico con nome, cognome, genere random che risiede e lavora in una determinata zona
	private static void creaMedico(int zona) {

		String genere = rollGenere();
		String nome = rollNome(genere);
		String cognome = rollCognome();
		
		DaoUtente.createUtente(nome, cognome, genere, zona, zona, "Medico");
	}
	
	// crea 20 operatori, 1 per zona
	public static void creaOperatori() {
		
		for(int j = 1; j <= 20; j++) {
			creaOperatore(j);
		}
	}
	
	// crea un operatore con nome, cognome, genere random che risiede e lavora in una determinata zona
	private static void creaOperatore(int zona) {

		String genere = rollGenere();
		String nome = rollNome(genere);
		String cognome = rollCognome();
		
		DaoUtente.createUtente(nome, cognome, genere, zona, zona, "Operatore");
	}
	
	// sceglie randomicamente il genere ritornando o M o F
	private static String rollGenere() {
		Random r = new Random();
		return (r.nextInt(2) == 0)? "M" : "F";
		
	}
	
	// sceglie un nome random tra una delle due liste di nomi (scelta in base al genere)
	private static String rollNome(String genere) {
		Random r = new Random();
		String[] nomi;
		switch(genere) {
		case "M":
			nomi = nomiMaschili;
			break;
		case "F":
			nomi = nomiFemminili;
			break;
			default:
			nomi = null;
		}
		return nomi[r.nextInt(nomi.length)];
	}
	
	// sceglie un cognome random tra la lista dei cognomi
	private static String rollCognome() {
		Random r = new Random();
		return cognomi[r.nextInt(cognomi.length)];
	}
	
	// sceglie una zona random tra 1 e 20
	private static int rollZona() {
		Random r = new Random();
		return (r.nextInt(20) + 1);
	}
	
}
