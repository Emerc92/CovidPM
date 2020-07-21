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
<title>Polizia</title>
<%
//String user = request.getParameter("username"); //possiamo fare la richiesta perch� il parametro � stato passato dall URL
//String psw = request.getParameter("password");
String user = (String)session.getAttribute("username");
String psw = (String)session.getAttribute("password");
//qui settiamo l'oggetto utente dell'USER
session.setAttribute("client", DaoUtente.login(user,psw)); // setta nella sessione l'oggetto dato da login(l'USER)
                                                     // cosi possiamo prenderlo pi� avanti
%>
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
           if (accesso != null && accesso.equals("controllo")) {%>
	<font color="red"> Controllo Utente!</font>
	<% } %>
	<!-- Servlet VisualizzaPositivi -->
	<form action="VisualizzaPositivi" method="post">
		<p id="frase">Visualizza positivi Regione</p>
		<input type="submit" value="Submit">
	</form>
	<%   if (accesso != null && accesso.equals("visualizza")) {%>
	<font color="red"> Lista dei positivi!</font>
	<% } %>

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
</body>
</html>