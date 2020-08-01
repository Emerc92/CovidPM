package it.begear.corso.database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "infezione")
public class Infezione {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "id_utente")
	private int id_utente;
	
	@Column(name = "giorni_rimasti")
	private int giorni_rimasti;

	public Infezione() {}
	
	public Infezione(int id_utente) {
		this.id_utente = id_utente;
		this.giorni_rimasti = 10;
	}

	public int getId() {
		return id;
	}

	public int getId_utente() {
		return id_utente;
	}
	
	public int getGiorni_rimasti() {
		return giorni_rimasti;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setId_utente(int id_utente) {
		this.id_utente = id_utente;
	}
	
	public void setGiorni_rimasti(int giorni_rimasti) {
		this.giorni_rimasti = giorni_rimasti;
	}
	
}
