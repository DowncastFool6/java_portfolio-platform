<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard - CTRL+VAULT</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>

<div class="">
<div class="">
    <%@ include file="/WEB-INF/fragments/app-header.jspf" %>

    <div class="">
        <div>
            <h2>Dashboard</h2>
            <p class="">Bem-vindo, <strong><c:out value="${usuario.nome}"/></strong>.</p>
            <c:if test="${not empty projetoUsuario}">
                <p>Projeto associado: <strong><c:out value="${projetoUsuario.descricao}"/></strong>.</p>
            </c:if>
        </div>
    </div>

    <c:if test="${not empty mensagem}">
        <p class="sucesso"><c:out value="${mensagem}"/></p>
    </c:if>

    <c:if test="${not empty erro}">
        <p class="erro"><c:out value="${erro}"/></p>
    </c:if>

    <c:if test="${isAdmin}">
        <div class="">
            <a class="" href="<%= request.getContextPath() %>/admin/usuarios">
                <strong>Gerir acessos</strong>
                <span>Administrar utilizadores sem acesso, projeto associado e tipo de utilizador.</span>
            </a>
        </div>
    </c:if>

    <c:if test="${not empty projetoUsuario}">
        <div class="">
            <a class="" href="<%= request.getContextPath() %>/contatos">
                <strong>Contato do projeto</strong>
                <span>Enviar uma mensagem associada ao seu projeto atual.</span>
            </a>
        </div>
    </c:if>
</div>
</div>

</body>
</html>
