package it.begear.corso.servlet.messaggi;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.begear.corso.database.DaoMessaggio;
import it.begear.corso.database.Messaggio;
import it.begear.corso.database.Utente;




@WebServlet("/VisualizzaMsg")
public class VisualizzaMsg extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public VisualizzaMsg() {
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Utente utente = (Utente) session.getAttribute("client");
		//ottiene messaggi del client
		List<Messaggio> messaggi = DaoMessaggio.getMessaggiUtente(utente);
		for(Messaggio txt: messaggi)
		{
			System.out.println(txt.getTxt_msg());
		}
		if(utente.getTipo().equals("Operatore")) {
			response.sendRedirect("Comune.jsp?status=visualizzati");
			}
			else if(utente.getTipo().equals("Agente")){
				response.sendRedirect("Polizia.jsp?status=visualizzati");
				}
			else if(utente.getTipo().equals("Medico")){
				response.sendRedirect("Medico.jsp?status=visualizzati");
				}
//			else if(utente.getTipo().equals("Cittadino")){
//				response.sendRedirect("Cittadino.jsp?status=visualizzati");
//				}
	}

}
