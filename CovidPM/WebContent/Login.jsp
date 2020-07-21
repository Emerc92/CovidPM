<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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

<!-- Servlet Acchiappa -->	
	<form action="Acchiappa" method="post">
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
		<p id="frase">01/03/2020</p>
		<input type="submit" value="Aggiorna">

<!-- nella servlet Acchiappa controlliamo l'username e la password inseriti e,
in caso negativo, ricarica la pagina cambiando la var "status" con valore stringa "denied",TRAMITE URL -->	

<!-- otteniamo il valore della variabile "status"  -->	
		<% String accesso = request.getParameter("status"); 
           if (accesso != null && accesso.equals("DENIED")) {%>
		<font color="red"> Username o Password non valide!</font>
		<% } %>
	</form>
</body>
</html>