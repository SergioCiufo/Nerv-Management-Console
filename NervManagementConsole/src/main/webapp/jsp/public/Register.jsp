<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Register</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
	crossorigin="anonymous">
<link rel="stylesheet"
	href="<%="http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/css/Login.css"%>" />
</head>
<body>
	<% 
	//errori
%>

	<style>
body {
	background-image:
		url('<%="http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/media/background.svg"%>')
}
</style>

	<div class="login-container">
		<div class="text-center">
			<img id="logo" alt="nerv logo"
				src="<%="http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/media/nervLogo.png"%>">
		</div>

		<div class="row justify-content-center text-center">
			<form action="<%=request.getContextPath() + "/register"%>"
				method="post" class="col-12 mt-5">
				<div class="row justify-content-center mb-3">
					<div class="col-6">
						<h3>Name</h3>
						<input type="text" name="registerName" class="form-control"
							placeholder="Enter name" required>
					</div>
				</div>

				<div class="row justify-content-center mb-3">
					<div class="col-6">
						<h3>Surname</h3>
						<input type="text" name="registerSurname" class="form-control"
							placeholder="Enter surname" required>
					</div>
				</div>

				<div class="row justify-content-center mb-3">
					<div class="col-6">
						<h3>Username</h3>
						<input type="text" name="registerUsername" class="form-control"
							placeholder="Enter username" required>
					</div>
				</div>

				<div class="row justify-content-center mb-3">
					<div class="col-6">
						<h3>Password</h3>
						<input type="password" name="registerPassword" class="form-control"
							placeholder="Enter password" required>
					</div>
				</div>

				<div class="row justify-content-center mb-3">
					
					<div class="col-6 mt-2">
						<button type="submit" class="btn btn-danger">Register</button>
					</div>
				</div>
			</form>
		</div>
	</div>
</body>
</html>