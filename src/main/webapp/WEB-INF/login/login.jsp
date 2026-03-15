<%-- 
    Document   : login
    Created on : 001/03/2026, 12:50:07
    Author     : aliceslombardi
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Login - CTRL+VAULT</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<h2>Login</h2>

<form action="login" method="post">
    
    <label>Email:</label><br>
    <input type="email" name="email" required /><br><br>
    
    <label>Senha:</label><br>
    <input type="password" name="senha" required /><br><br>

    <button type="submit">Entrar</button>
</form>

<c:if test="${not empty mensagem}">
    <p class="sucesso">${mensagem}</p>
</c:if>
<c:if test="${not empty erro}">
    <p class="erro">${erro}</p>
</c:if>

</body>
</html>
