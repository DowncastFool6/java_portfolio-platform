<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Gestao de Acessos - CTRL+VAULT</title>
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/styles.css">
</head>
<body>

<div class="page-shell app-layout">
    <%@ include file="/WEB-INF/fragments/app-header.jspf" %>
<main class="app-main">
    <div class="app-container">
    <section class="section-heading">
        <div>
            <h2>Utilizadores sem acesso</h2>
            <p>Clique numa linha para associar o utilizador a um projeto e definir o seu tipo.</p>
        </div>
    </section>

    <c:if test="${not empty mensagem}">
        <p class="sucesso"><c:out value="${mensagem}"/></p>
    </c:if>

    <c:choose>
        <c:when test="${empty usuariosPendentes}">
            <p class="empty-state">Não existem utilizadores pendentes de aprovação.</p>
        </c:when>
        <c:otherwise>
            <div class="table-wrap">
                <table class="data-table">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nome</th>
                            <th>Email</th>
                            <th>Estado</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="item" items="${usuariosPendentes}">
                            <tr class="clickable-row" onclick="window.location='<%= request.getContextPath() %>/admin/usuarios/acesso?id=${item.id}'">
                                <td><c:out value="${item.id}"/></td>
                                <td><c:out value="${item.nome}"/></td>
                                <td><c:out value="${item.email}"/></td>
                                <td><span class="status-chip">Pendente</span></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:otherwise>
    </c:choose>
    </div>
</main>
</div>
<%@ include file="/WEB-INF/fragments/app-footer.jspf" %>

</body>
</html>
