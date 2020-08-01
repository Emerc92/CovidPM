package it.begear.corso.database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "generale")
public class Generale {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "giorno")
	private int giorno;
	
	@Column(name = "infetti")
	private long infetti;
	
	public Generale() {}

	public int getId() {
		return id;
	}

	public int getGiorno() {
		return giorno;
	}

	public long getInfetti() {
		return infetti;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setGiorno(int giorno) {
		this.giorno = giorno;
	}

	public void setInfetti(long infetti) {
		this.infetti = infetti;
	}
	
}
