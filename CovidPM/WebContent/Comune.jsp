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
<title>Insert title here</title>
<!-- prende i parametri username e password dall'URL-->
</head>
<body>
<!-- Servlet RegistraUtente -->	
	<form action="RegistraUtente" method="post">
		<label for="name">Nome:</label> <input type="text"
			id="name" name="nome"><br>
		<br> <label for="surname">Cognome:</label> <input type="text"
			id="surname" name="cognome"><br>
		<br> <label for="genre">Genere:</label> <input type="text"
			id="genre" name="genere"><br>
		<br> <label for="homezone">Regione di residenza:</label> <input type="text"
			id="homezone" name="zonaResidenza"><br>
		<br> <label for="jobzone">Regione di lavoro:</label> <input type="text"
			id="jobzone" name="zonaLavoro"><br>
		<br> <label for="type">Tipo di Utente:</label> <input type="text"
			id="type" name="tipoUtente"><br>
		
		<p id="frase">Registra Utente</p>
		<input type="submit" value="Submit">
	</form>
	<% String accesso = request.getParameter("status"); 
           if (accesso != null && accesso.equals("creato")) {%>
		<font color="green"> L'utente è stato creato con successo!</font>
		<% } %>
<!-- Servlet MsgMedico -->	
	<form action="MsgMedico" method="post">
		<br> <label for="nTamp">numero tamponi:</label> <input type="text"
			id="nTamp" name="nTamponi"><br>
		<p id="frase">Richiedi tampone ai medici</p>
		<input type="submit" value="Submit">
	</form>
          <%  if (accesso != null && accesso.equals("inviato")) {%>
		<font color="green"> Il messaggio è stato inviato!</font>
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
 <%  if (accesso != null && accesso.equals("visualizzati")) {
	 List<Messaggio> messaggi = (List) session.getAttribute("messages");
	 for(Messaggio msg : messaggi){
		 Utente x = DaoUtente.getUtenteId(msg.getId_mittente());
		 String name = x.getNome();
		 String surname = x.getCognome();
		 String type = x.getTipo();
		 String txt = msg.getTxt_msg();
		 out.println(name + " " + surname + " " + type);
		 out.println(txt);
	 }
 } %>
</body>
</html>