<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.util.GregorianCalendar"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="it.begear.corso.database.Generale"%>
<%@page import="it.begear.corso.database.DaoGenerale"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css">
<!-- fogli di style css -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
<link href="css/myStyle.css" rel="stylesheet" type="text/css" />

<!-- Custom fonts for this template-->
<link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet"
	type="text/css">
<link
	href="https:/ /fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
	rel="stylesheet">
<!-- particole js -->
<link rel="stylesheet" media="screen" href="css/style.css" />

<!-- Custom styles for this template-->
<link href="css/sb-admin-2.min.css" rel="stylesheet">
<link href="css/sweetalert.css" rel="stylesheet" type="text/css">
<!-- Bootstrap core JavaScript-->
<script src="vendor/jquery/jquery.min.js"></script>
<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="js/sweetalert.min.js" type="text/javascript"></script>
<!-- Core plugin JavaScript-->
<script src="vendor/jquery-easing/jquery.easing.min.js"></script>

<!-- Custom scripts for all pages-->
<script src="js/sb-admin-2.min.js"></script>

<title>Login</title>
</head>

<body id="body">
	<%
		session.setAttribute("status", "ok");
	%>
	<!-- <div id="particles-js"></div>-->

		<!-- Nested Row within Card Body    10806 -->
	<div class="container-fluid" style="margin-top: 90px; z-index: 99;">

		<div class="row justify-content-center">

			<div class="filter" id="container" style="margin-top: 0px;">

				<div class="row">
					<div class=" col-sm-6 col-md-6  col-xl-6 content"
						style="margin: auto;">
						<div class="text-center">
							<h1 class="h4 text-white mb-4"
								style="margin-top: 20px; opacity: 0.8;">
								<img id="gif" src="img/tenor.gif"
									class="d-inline-block align-top"
									style="width: 50px; heigth: 50px;">Benvenuto!
							</h1>
						</div>
						<form action="Login" method="post">
							<div class="form-group">
								<input type="text" class="form-control form-control-user"
									name="username" aria-describedby="username"
									placeholder="Utente" required>
							</div>
							<div class="form-group">
								<input type="password" class="form-control form-control-user"
									name="password" id="InputPassword" placeholder="Password"
									required>

							</div>
							<br> <input name="submit" type="submit"
								class="btn btn-primary btn-user btn-block" value="Login" />

							<!-- nella servlet Acchiappa controlliamo l'username e la password inseriti e,
												in caso negativo, ricarica la pagina cambiando la var "status" con valore stringa "denied",TRAMITE URL -->
							<br> <br>
							<!-- otteniamo il valore della variabile "status"  -->

							<%
								String accesso = request.getParameter("status");
							if (accesso != null && accesso.equals("DENIED")) {
							%>
							<script>
								swal("Password o Utente non valido!", "riprova")
							</script>
							<%
								}
							%>
						</form>
						<!-- Servlet AggiornaDB -->

					</div>
					<div class="container-fluid">
						<div class="col-sm-6 col-md-6  col-xl-5 content mt-5"
							id="database">
			
							<form action="AggiornaDB" method="post">
								<input name="submit" type="submit" id="aggiornadb"
									class="btn btn-primary btn-user btn-block"
									value="Aggiorna Data Base" />
								</form>	
							<form action="ResetDB" method="post">
								<input name="submit" type="submit" id="resetdb"
									class="btn btn-primary btn-user btn-block"
									value="Resetta Data Base" />
							</form>
								<%
										String accessodb = request.getParameter("status");
									if (accessodb != null && accessodb.equals("AGGIORNATO")) {
									%>
								<h6
									style="color: white; font-size: 12px; margin-bottom: 5px; margin-top: 5px;">
									DataBase aggiornato: <br>
									<%
										out.print("Giorno: " + DaoGenerale.getGenerale().getGiorno());
									%>

									<br>
									<%	out.print("Infetti: " + DaoGenerale.getGenerale().getInfetti());
									}
									%>
								</h6>
							
								<%
										accessodb = request.getParameter("status");
									if (accessodb != null && accessodb.equals("RESETTATO")) {
									%>
								<h6
									style="color: white; font-size: 12px; margin-bottom: 5px; margin-top: 5px;">
									DataBase resettato: <br>
									<%
										out.print("Giorno: " + DaoGenerale.getGenerale().getGiorno());
									%>

									<br>
									<%	out.print("Infetti: " + DaoGenerale.getGenerale().getInfetti());
									}
									%>
								</h6>
							
						
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- particole js -->
	
</body>
</html>