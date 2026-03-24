<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt">
<head>
    <title>Projetos - CTRL+VAULT</title>
    <%@ include file="/WEB-INF/fragments/app-head.jspf" %>
</head>
<body>

<div class="page-shell app-layout">
    <%@ include file="/WEB-INF/fragments/side-panel.jspf" %>

    <main class="page-shell">
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
                    <strong><c:out value="${projeto.titulo}"/></strong>
                    <c:out value="${projeto.descricao}"/>
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
