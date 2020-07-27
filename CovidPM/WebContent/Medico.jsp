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
<title>Medico</title>
</head>
<body>
<!-- Servlet EseguiTest -->
	<form action="EseguiTest" method="post">
		<label for="nTamp">N°Tamponi:</label> <input type="text" id="nTamp"
			name="nTamp"><br> <br>
		<p id="frase">Esegui test</p>
		<input type="submit" value="Submit">
	</form>
	<% String accesso = request.getParameter("status"); 
           if (accesso != null && accesso.equals("test")) {%>
	<font color="green"> Test eseguito!</font>
	<% } 
	//aggiungere risultati test
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