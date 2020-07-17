package it.begear.corso.database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "utente")
public class Utente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "cognome")
	private String cognome;
	
	@Column(name = "genere")
	private String genere;
	
	@Column(name = "cod_fis")
	private String cod_fis;
	
	@Column(name = "id_zona_res")
	private int id_zona_res;
	
	@Column(name = "id_zona_lav")
	private int id_zona_lav;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "tipo")
	private String tipo;
	
	public Utente() {}

	public int getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public String getCognome() {
		return cognome;
	}

	public String getGenere() {
		return genere;
	}

	public String getCod_fis() {
		return cod_fis;
	}

	public int getId_zona_res() {
		return id_zona_res;
	}

	public int getId_zona_lav() {
		return id_zona_lav;
	}

	public String getStatus() {
		return status;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getTipo() {
		return tipo;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public void setGenere(String genere) {
		this.genere = genere;
	}

	public void setCod_fis(String cod_fis) {
		this.cod_fis = cod_fis;
	}

	public void setId_zona_res(int id_zona_res) {
		this.id_zona_res = id_zona_res;
	}

	public void setId_zona_lav(int id_zona_lav) {
		this.id_zona_lav = id_zona_lav;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
}
