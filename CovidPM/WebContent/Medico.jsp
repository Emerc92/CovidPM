<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="it.begear.corso.database.Utente"%>
<!-- RICORDARSI DI QUESTIIIIIIIIIIIIIIIIIIIIII!!!!!!!!!!!!! -->
<%@ page import="it.begear.corso.database.DaoUtente"%>
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
	<%  if (accesso != null && accesso.equals("visualizzati")) {%>
	<font color="red"> I messaggi sono stati visualizzati!</font>
	<% } %>
</body>
</html>