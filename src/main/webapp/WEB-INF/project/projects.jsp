<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Projetos - CTRL+VAULT</title>
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/styles.css">
</head>
<body>

<div class="page-shell app-layout">
    <%@ include file="/WEB-INF/fragments/app-header.jspf" %>

    <main class="app-main">
        <div class="app-container">
        <section class="section-heading">
            <div>
                <h2>Os seus projetos</h2>
                <p>Selecione um projeto para ver e editar os seus conteúdos.</p>
            </div>
        </section>

        <c:if test="${empty projetos}">
            <p class="erro">Não existem projetos associados ao seu utilizador.</p>
        </c:if>

        <div class="project-grid">
            <c:forEach var="projeto" items="${projetos}">
                <a class="project-card" href="<%= request.getContextPath() %>/projeto?id=${projeto.id}">
                    <span class="project-card-label">Projeto</span>
                    <strong><c:out value="${projeto.descricao}"/></strong>
                    <span>Abrir página do projeto</span>
                </a>
            </c:forEach>
        </div>
        </div>
    </main>
</div>
<%@ include file="/WEB-INF/fragments/app-footer.jspf" %>

</body>
</html>
