<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Projetos - CTRL+VAULT</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/base.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/layout.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/components.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/pages.css">
</head>
<body>

<div class="page-shell app-layout">
    <%@ include file="/WEB-INF/fragments/app-header.jspf" %>

    <main class="app-main">
        <div class="app-container">
        <section class="section-heading">
            <div>
                <h2>Os seus projetos</h2>
                <p>Selecione um projeto para ver e editar os seus conteudos.</p>
            </div>
        </section>

        <c:if test="${empty projetos}">
            <p class="erro">Nao existem projetos associados ao seu utilizador.</p>
        </c:if>

        <div class="project-grid">
            <c:forEach var="projeto" items="${projetos}">
                <a class="project-card" href="<%= request.getContextPath() %>/projeto?id=${projeto.id}">
                    <span class="project-card-label">Projeto</span>
                    <strong><c:out value="${projeto.descricao}"/></strong>
                    <span>Abrir pagina do projeto</span>
                </a>
            </c:forEach>
        </div>
        </div>
    </main>
</div>
<%@ include file="/WEB-INF/fragments/app-footer.jspf" %>

</body>
</html>
