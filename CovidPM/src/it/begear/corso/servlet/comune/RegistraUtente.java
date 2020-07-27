package it.begear.corso.servlet.comune;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.begear.corso.database.DaoUtente;


@WebServlet("/RegistraUtente")
public class RegistraUtente extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public RegistraUtente() {}
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String nome = request.getParameter("nome");
		String cognome = request.getParameter("cognome");
		String genere = request.getParameter("genere");
		String zonaRes = request.getParameter("zonaResidenza");
		String zonaLav = request.getParameter("zonaLavoro");
		String tipo = request.getParameter("tipoUtente");
		
		
		int id_zona_res = Integer.parseInt(zonaRes);
		int id_zona_lav = Integer.parseInt(zonaLav);
		
		//anche se infilato nell'if, crea ugualmente l'utente e poi restituisce il booleano TRUE se è andato tutto bene
		if(DaoUtente.createUtente(nome, cognome, genere, id_zona_res, id_zona_lav, tipo)) //se l'utente è creato
		{
			response.sendRedirect("Comune.jsp?status=creato");
		}	
	
	}

}
