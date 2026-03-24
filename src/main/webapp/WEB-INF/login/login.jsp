<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt">
<head>
    <title>Login - CTRL+VAULT</title>
    <%@ include file="/WEB-INF/fragments/app-head.jspf" %>
</head>
<body class="landing-page login-page">

<c:set var="ocultarLoginHeader" value="true" scope="request"/>
<%@ include file="/WEB-INF/fragments/app-header.jspf" %>

<div class="register-split">

    <div class="register-visual">
        <img src="<%= request.getContextPath()%>/images/logo.svg" alt="Visual">
    </div>

    <div class="register-form-wrapper">
        <main class="portal-narrow">

        <div class="form-header">
            <h2>Entrar</h2>
            <p>Introduza as suas credenciais para aceder ao portal.</p>
        </div>

        <c:if test="${not empty mensagem}">
            <p class="sucesso"><c:out value="${mensagem}"/></p>
        </c:if>

        <c:if test="${not empty erro}">
            <p class="erro"><c:out value="${erro}"/></p>
        </c:if>

        <form action="<%= request.getContextPath() %>/login" method="post" class="stack-form">
            <label>Email</label>
            <input type="email" name="email" value="${param.email}" required>

            <label>Senha</label>
            <input type="password" name="senha" required>

            <button type="submit" class="btn-primary">Entrar</button>
        </form>

        <p class="helper-text">
            Não tem uma conta? <a href="<%= request.getContextPath() %>/register">Registe-se aqui</a>.
        </p>

    </main>
    </div>
</div>

<%@ include file="/WEB-INF/fragments/app-footer.jspf" %>

</body>
</html>
