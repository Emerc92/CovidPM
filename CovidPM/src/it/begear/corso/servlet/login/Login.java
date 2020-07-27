package it.begear.corso.servlet.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.begear.corso.database.DaoUtente;
import it.begear.corso.database.Utente;


@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Login() {
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//prendiamo i parametri immessi dal client
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		//NB. se il DAO .login non trova il pojo ritorna un null
		Utente client = DaoUtente.login(username,password); //instanziamo l'utente "client"
		HttpSession session = request.getSession();         //instanzia la session web(NB.di default ha una durata di 30 min se non specificato diversamente nel file web.xml)
		session.setAttribute("client", client);             //carica l'oggetto "client" in sessione per poterlo riusare successivamente
		
		// se username e password sono sbagliati, reindirizza al login (modificando la variabile status)
		if(client == null)response.sendRedirect("Login.jsp?status=DENIED");		
		else if( client.getTipo().equals("Operatore")){
			response.sendRedirect("Comune.jsp");	
		}
		else if( client.getTipo().equals("Agente")){
			response.sendRedirect("Polizia.jsp");	
		}
		else if( client.getTipo().equals("Medico")){
			response.sendRedirect("Medico.jsp");	
		}
		}
		
		
	}


