package it.begear.corso.servlet.login;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.begear.corso.database.DaoGenerale;
import it.begear.corso.database.DaoInfezione;
import it.begear.corso.database.DaoMessaggio;
import it.begear.corso.database.DaoUtente;
import it.begear.corso.database.DaoZona;
import it.begear.corso.database.insert.CreaUtenti;
import it.begear.corso.database.insert.InfettaUtenti;


@WebServlet("/ResetDB")
public class ResetDB extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ResetDB() {
    }



	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		DaoUtente.resetUtente();
		DaoGenerale.resetGenerale();
		DaoInfezione.resetInfezione();
		DaoMessaggio.resetMessaggio();
		DaoZona.resetZona();
		
		CreaUtenti.crea();
		InfettaUtenti.infetta();
		
		response.sendRedirect("Login.jsp?status=RESETTATO");
	}

}
