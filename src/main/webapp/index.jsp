<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>CTRL+VAULT – Your Knowledge Under Control</title>

    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>

<body>

<div class="container">
    
    <img src="<%= request.getContextPath() %>/images/logo.png"
         alt="CTRL+VAULT Logo"
         class="logo">

    <h1>CTRL<span><span class="plus">+</span>VAULT</span></h1>

    <div class="tagline">
        Uma aplicação web segura e estruturada, concebida para gerir informação 
        modular com autenticação, controlo de acesso e armazenamento de dados 
        encriptados.
    </div>

    <div class="btn-group">
        <a href="<%= request.getContextPath() %>/register" class="btn-primary">Register</a>
        <a href="<%= request.getContextPath() %>/login" class="btn-secondary">Login</a>
    </div>

</div>

<footer>
    © 2026 CTRL+VAULT – Academic Project
</footer>

</body>
</html>