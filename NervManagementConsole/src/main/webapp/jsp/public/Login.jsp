<!DOCTYPE html>
<%@page import="com.company.nervManagementConsole.utils.Costants"%>
<%@page import="com.company.nervManagementConsole.model.User"%>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
        integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
        crossorigin="anonymous">
    <link rel="stylesheet"
        href="<%="http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/css/Login.css"%>" />
</head>
<body>
<% 
    User user=(User)request.getSession().getAttribute(Costants.KEY_SESSION_USER);
    String errorLogin=(String)request.getAttribute(Costants.ERROR_LOGIN);
%>

<style>
    @font-face {
        font-family: "evangelionFont";
        src: url('<%="http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/font/NimbusRomNo9L-Reg.otf"%>') format("opentype");
    }

    body {
        background-image: url('<%="http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/media/background.svg"%>');
        font-family: "evangelionFont", sans-serif;
    }
</style>

    <div class="login-container">
    <div class="text-center">
    <img id="logo" alt="nerv logo" src="<%="http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/media/nervLogo.png"%>">
    </div>
    	
        <div class="row justify-content-center text-center">
            <form action="<%=request.getContextPath() + "/login"%>" method="post" class="col-12 mt-5">
                <div class="row justify-content-center mb-3">
                    <div class="col-6">                   
                        <h3>Username</h3>
                        <input type="text" name="loginUsername" class="form-control" placeholder="Enter username" required>
                    </div>
                </div>

                <div class="row justify-content-center mb-3">
                    <div class="col-6">
                        <h3>Password</h3>
                        <input type="password" name="loginPassword" class="form-control" placeholder="Enter password" required>
                    </div>
                </div>

                <div class="row justify-content-center mb-3">
                    <% if (errorLogin!=null) { %>
                    <p class="pError"><b><%=errorLogin%></b></p>
                    <% } %>
                    <div class="col-6 mt-2">                
                        <button type="submit" class="btn btn-danger">ACCESS</button>
                    </div>
                </div>
            </form>
        </div>
        <div style="text-align: center;">
		    <a href="<%="http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/jsp/public/Register.jsp"%>">
		        REGISTER
    		</a>
</div>
    </div>
</body>
</html>