<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - CTRL+VAULT</title>
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/styles.css">
</head>
<body>

<div class="page-shell-login">
<main class="app-container portal-narrow">
    <section class="portal-panel">
        <div class="section-heading">
            <div>
                <h2>Login</h2>
                <p>Aceda ao portal para consultar projetos e gerir conteudos.</p>
            </div>
        </div>

        <form class="login-form" action="<%= request.getContextPath() %>/login" method="post">
            <label>Email</label>
            <input type="email" name="email" value="${param.email}" required>

            <label>Senha</label>
            <input type="password" name="senha" required>

            <button type="submit" class="btn-secondary login-submit">Entrar</button>
        </form>

        <c:if test="${not empty mensagem}">
            <p class="sucesso"><c:out value="${mensagem}"/></p>
        </c:if>
        <c:if test="${not empty erro}">
            <p class="erro"><c:out value="${erro}"/></p>
        </c:if>
    </section>
</main>
</div>
<%@ include file="/WEB-INF/fragments/app-footer.jspf" %>

</body>
</html>
