package it.begear.corso.servlet.mappa;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.begear.corso.database.DaoInfezione;
import it.begear.corso.database.DaoUtente;


@WebServlet("/VisualizzaInfezioni")
public class VisualizzaInfezioni extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public VisualizzaInfezioni() {

    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<Long> numUtentiPerZonaRes = DaoUtente.getNumUtentiPerZonaRes();
		List<Long> numInfezioniPerZonaRes = DaoInfezione.getNumInfezioniPerZonaRes();
		
	}

}
