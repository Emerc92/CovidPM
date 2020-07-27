package it.begear.corso.database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "messaggio")
public class Messaggio {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "id_utente")
	private int id_utente;
	
	@Column(name = "id_mittente")
	private int id_mittente;
	
	@Column(name = "txt_msg")
	private String txt_msg;

	public Messaggio() {}

	public Messaggio(int id_utente, int id_mittente, String txt_msg) {
		this.id_utente = id_utente;
		this.id_mittente = id_mittente;
		this.txt_msg = txt_msg;
	}

	public int getId() {
		return id;
	}

	public int getId_utente() {
		return id_utente;
	}
	
	public int getId_mittente() {
		return id_mittente;
	}

	public String getTxt_msg() {
		return txt_msg;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setId_utente(int id_utente) {
		this.id_utente = id_utente;
	}
	
	public void setId_mittente(int id_mittente) {
		this.id_mittente = id_mittente;
	}

	public void setTxt_msg(String txt_msg) {
		this.txt_msg = txt_msg;
	}
	
}
