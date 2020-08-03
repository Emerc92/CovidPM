<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ page import="it.begear.corso.database.Generale"%>
 <%@ page import="it.begear.corso.database.DaoGenerale"%>
 <%@ page import="java.util.List"%>
 <%@ page import="it.begear.corso.database.DaoUtente"%>
 <%@ page import="it.begear.corso.database.DaoInfezione"%>
<!-- RICORDARSI DI QUESTIIIIIIIIIIIIIIIIIIIIII!!!!!!!!!!!!! -->
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<!-- setta la variabile status con il valore stringa "ok"
(nb.la sessione dura 30 min se non specificato diversamente nel web.xml) -->
<%session.setAttribute("status", "ok"); %> 

<!-- Servlet Login -->	
	<form action="Login" method="post">
		<label for="username">Username:</label> <input type="text"
			id="username" name="username"><br>
		<br> <label for="password">Password:</label> <input type="text"
			id="password" name="password"><br>
		<br>
		<p id="frase">Premi submit per connetterti</p>
		<input type="submit" value="Submit">
	</form>
<!-- Servlet AggiornaDB -->	
	<form action="AggiornaDB" method="post">
		<p id="frase"><% 
		int giorno = DaoGenerale.getGenerale().getGiorno();
		out.println("giorno " + giorno);
		%></p>
		<input type="submit" value="Avanza giorno">
    </form>
   <%  
    List<Long> numUtentiPerZonaRes = DaoUtente.getNumUtentiPerZonaRes();
	List<Long> numInfezioniPerZonaRes = DaoInfezione.getNumInfezioniPerZonaRes();
	%>
<!-- nella servlet Login controlliamo l'username e la password inseriti e,
in caso negativo, ricarica la pagina cambiando la var "status" con valore stringa "denied",TRAMITE URL -->	

<!-- otteniamo il valore della variabile "status"  -->	
		<% String accesso = request.getParameter("status"); 
           if (accesso != null && accesso.equals("DENIED")) {%>
		<font color="red"> Username o Password non valide!</font>
		<% } %>

<!-- Servlet ResetDB -->	
	<form action="ResetDB" method="post">
		<input type="submit" value="Reset Database">
    </form>	
</body>
</html>