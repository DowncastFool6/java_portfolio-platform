<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt">
<head>
    <title>Dashboard - CTRL+VAULT</title>
    <%@ include file="/WEB-INF/fragments/app-head.jspf" %>
</head>
<body>

<div class="page-shell app-layout">
    <%@ include file="/WEB-INF/fragments/side-panel.jspf" %>

<main class="app-main">
    <div class="app-container">
    <section class="portal-section">
        <div>
            <h2>Dashboard</h2>
            <p>Consulte os seus atalhos e acompanhe o estado da sua área.</p>
        </div>
    </section>

    <c:if test="${not empty mensagem}">
        <p class="sucesso"><c:out value="${mensagem}"/></p>
    </c:if>

    <c:if test="${not empty erro}">
        <p class="erro"><c:out value="${erro}"/></p>
    </c:if>

    <c:if test="${not isUsuarioAtivo}">
        <p class="empty-state">O seu utilizador está inativo. Pode consultar informação, mas não pode enviar contatos nem editar conteúdos.</p>
    </c:if>

    <div class="dashboard-grid">
        <c:if test="${isAdmin and isUsuarioAtivo}">
            <a class="dashboard-card" href="<%= request.getContextPath() %>/admin/usuarios">
                <span class="status-chip">Administração</span>
                <strong>Gerir acessos</strong>
                <span>Administrar utilizadores sem acesso, projeto associado e tipo de utilizador.</span>
            </a>
        </c:if>
        <c:if test="${not empty projetosUsuario}">
            <a class="dashboard-card" href="<%= request.getContextPath() %>/projetos">
                <span class="status-chip">Projetos</span>
                <strong>Meus projetos</strong>
                <span>Ver todos os projetos em que participa e abrir o conteúdo de cada um.</span>
            </a>
        </c:if>
    </div>
    </div>
</main>
</div>

<div class="lottie-corner">
  <iframe
    src="https://lottie.host/embed/1a969a32-fa37-4a03-a77f-c83eb822f7df/9ZzMgLVqMY.lottie"
    allowfullscreen
  ></iframe>
</div>

<%@ include file="/WEB-INF/fragments/app-footer.jspf" %>

</body>
</html>
