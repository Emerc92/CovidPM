<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="it.begear.corso.database.Utente"%>
<!-- RICORDARSI DI QUESTIIIIIIIIIIIIIIIIIIIIII!!!!!!!!!!!!! -->
<%@ page import="it.begear.corso.database.DaoUtente"%>
<!-- RICORDARSI DI QUESTIIIIIIIIIIIIIIIIIIIIII!!!!!!!!!!!!! -->
<%@ page import="it.begear.corso.database.Messaggio"%>
<!-- RICORDARSI DI QUESTIIIIIIIIIIIIIIIIIIIIII!!!!!!!!!!!!! -->
<%@ page import="java.util.List"%>
<!-- RICORDARSI DI QUESTIIIIIIIIIIIIIIIIIIIIII!!!!!!!!!!!!! -->
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Polizia</title>
</head>
<body>
	<!-- Servlet ControllaUtente -->
	<form action="ControllaUtente" method="post">
		<label for="name">Nome:</label> <input type="text" id="name"
			name="nome"><br> <br> <label for="surname">Cognome:</label>
		<input type="text" id="surname" name="cognome"><br>
		<p id="frase">Controlla Utente</p>
		<input type="submit" value="Submit">
	</form>
	<% String accesso = request.getParameter("status"); 
           if (accesso != null && accesso.equals("controllo")) {
        	   List<Utente> lista = (List<Utente>)session.getAttribute("listaUT");
        	   for(Utente u : lista){
        		   out.println(u.getNome() + " " + u.getCognome() + " status: " + u.getStatus());    		   
        	   }
        	 
           }
           else if (accesso != null && accesso.equals("vuota")){
        	   out.println("La persona non è stata trovata nel database");
           }
           
           %>
	<!-- Servlet VisualizzaPositivi -->
	<form action="VisualizzaPositivi" method="post">
		<p id="frase">Visualizza positivi Regione</p>
		<input type="submit" value="Submit">
	</form>
	<%   if (accesso != null && accesso.equals("visualizza")) {
      	   List<Utente> listaPos = (List<Utente>)session.getAttribute("listaPositivi");
      	   for(Utente u : listaPos){
      		   out.println(u.getNome() + " " + u.getCognome() + " status: " + u.getStatus());    		   
      	   }
      	 
         }
         else if (accesso != null && accesso.equals("vuotaPositivi")){
      	   out.println("Non esistono positivi nella zona");
         }
	  %>

	<!-- Servlet EliminaMsg -->
	<form action="EliminaMsg" method="post">
		<p id="frase">Elimina messaggi</p>
		<input type="submit" value="Submit">
	</form>
	<%  if (accesso != null && accesso.equals("eliminati")) {%>
	<font color="red"> I messaggi sono stati eliminati!</font>
	<% } %>

	<!-- Servlet VisualizzaMsg -->
	<form action="VisualizzaMsg" method="post">
		<p id="fraseVisualilizzaMsg">Visualizza messaggi</p>
		<input type="submit" value="Submit">
	</form>
	 <%  if (accesso != null && accesso.equals("visualizzati")) {
	 List<Messaggio> messaggi = (List) session.getAttribute("messages");
	 for(Messaggio msg : messaggi){
		 Utente x = DaoUtente.getUtenteId(msg.getId_mittente());
		 //ecco la schiera di elementi da utilizzare per visualizzare i messaggi
		 int giorno = msg.getGiorno();
		 String name = x.getNome();
		 String surname = x.getCognome();
		 String type = x.getTipo();
		 String txt = msg.getTxt_msg();
		 out.println(name + " " + surname + " " + type);
		 out.println("messaggio: '" + txt + "'");
	 }
 } %>
</body>
</html>