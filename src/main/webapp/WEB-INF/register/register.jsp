<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt">
<head>
    <title>Registar Utilizador</title>
    <%@ include file="/WEB-INF/fragments/app-head.jspf" %>
</head>
<body class="landing-page">

<%@ include file="/WEB-INF/fragments/app-header.jspf" %>

<div class="register-split">

    <!-- LEFT SIDE -->
    <div class="register-visual">
        <img src="<%= request.getContextPath()%>/images/logo1.svg" alt="Visual">
    </div>

    <!-- RIGHT SIDE -->
    <div class="register-form-wrapper">

        <main class="portal-narrow">

            <h2>Registar Novo Utilizador</h2>
            <p>Crie a sua conta para aceder ao portal.</p>

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

                <div class="form-info-block">
                    <strong>Informações RGPD</strong>
                    <p>
                        Nos termos do RGPD, os dados serão usados apenas para gestão da conta.
                    </p>

                    <label class="checkbox-row">
                        <input type="checkbox" name="aceitouRgpd" required>
                        <span>Li e aceito o tratamento dos dados.</span>
                    </label>
                </div>

                <button type="submit" class="btn-primary">Registar</button>

            </form>

        </main>

    </div>

</div>

<%@ include file="/WEB-INF/fragments/app-footer.jspf" %>

<script
  src="https://unpkg.com/@lottiefiles/dotlottie-wc@0.9.3/dist/dotlottie-wc.js"
  type="module"
></script>
</body>
</html>
