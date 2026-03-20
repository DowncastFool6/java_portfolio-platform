<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Registar Utilizador</title>
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/styles.css">
</head>
<body>

<div class="page-shell-register">
<main class="app-container portal-narrow">
    <section class="portal-panel">
        <div class="section-heading">
            <div>
                <h2>Registar Novo Utilizador</h2>
                <p>Crie a sua conta para aceder ao portal.</p>
            </div>
        </div>

        <c:if test="${not empty erro}">
            <p class="erro"><c:out value="${erro}"/></p>
        </c:if>

        <form action="<%= request.getContextPath() %>/register" method="post">
            <label>Nome</label>
            <input type="text" name="nome" value="${param.nome}" required>

            <label>Email</label>
            <input type="email" name="email" value="${param.email}" required>

            <label>Senha</label>
            <input type="password" name="senha" required>

            <div class="rgpd-box">
                <strong>Informação RGPD</strong>
                <p>
                    Nos termos do Regulamento Geral sobre a Protecao de Dados (RGPD) aplicável em Portugal,
                    os dados fornecidos neste formulário serão tratados apenas para criação e gestão da sua conta no sistema.
                </p>
                <label class="checkbox-row">
                    <input type="checkbox" name="aceitouRgpd" required>
                    Declaro que li e compreendi a informação relativa ao tratamento dos meus dados pessoais.
                </label>
            </div>

            <div class="action-row">
                <button type="submit" class="btn-secondary">Registar</button>
            </div>
        </form>
    </section>
</main>
</div>
<%@ include file="/WEB-INF/fragments/app-footer.jspf" %>

</body>
</html>
