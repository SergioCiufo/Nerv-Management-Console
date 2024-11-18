<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Error</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
	crossorigin="anonymous">
</head>
<style>

body {
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100vh;
	margin: 0;
	background-color: #2b2a33;
	background-repeat: no-repeat;
    background-size: cover;
    background-position: center;
    
    background-image:
		url('<%= "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/media/background.svg" %>');
	font-family: "evangelionFont", sans-serif;
}

@font-face {
	font-family: "evangelionFont";
	src:
		url('<%= "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/font/NimbusRomNo9L-Reg.otf" %>')
		format("opentype");
}


</style>
<body>

     <div class="alert alert-danger text-center w-50" role="alert">
        <h1>Something went wrong</h1>
        <a href="<%= "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() +"/jsp/private/Home.jsp" %>" class="btn btn-primary mt-3">Go back to Home</a>
    </div>

</body>
</html>