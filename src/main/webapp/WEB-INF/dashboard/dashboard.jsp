<%-- 
    Document   : dashboard
    Created on : 01/03/2026, 13:50:24
    Author     : santo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
    <title>Dashboard - CTRL+VAULT</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<h2>Dashboard</h2>


<c:if test="${not empty erro}">
    <p class="erro"><c:out value="${erro}"/></p>
</c:if>

<c:if test="${empty erro}">
    <p>Bem-vindo, <strong>${usuario.nome}</strong>!</p>
</c:if>    

<a href="${pageContext.request.contextPath}/logout">
    Logout
</a>

</body>
</html>
