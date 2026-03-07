<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard - CTRL+VAULT</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<div class="">
<div class="">
    <div class="">
        <div>
            <h2>Dashboard</h2>
            <p class="">Bem-vindo, <strong><c:out value="${usuario.nome}"/></strong>.</p>
        </div>
        <a class="btn-secondary" href="${pageContext.request.contextPath}/logout">Logout</a>
    </div>

    <c:if test="${not empty erro}">
        <p class="erro"><c:out value="${erro}"/></p>
    </c:if>

    <c:if test="${isAdmin}">
        <div class="">
            <a class="" href="${pageContext.request.contextPath}/admin/usuarios">
                <strong>Gerir acessos</strong>
                <span>Administrar utilizadores sem acesso, projeto associado e tipo de utilizador.</span>
            </a>
        </div>
    </c:if>
</div>
</div>

</body>
</html>
