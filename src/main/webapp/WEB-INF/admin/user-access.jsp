<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Atualizar Acesso - CTRL+VAULT</title>
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/styles.css">
</head>
<body>

<div class="page-shell app-layout">
    <%@ include file="/WEB-INF/fragments/app-header.jspf" %>
<main class="app-main">
    <div class="app-container">
    <section class="portal-panel">
        <div class="section-heading">
            <div>
                <h2>Gerir acesso do utilizador</h2>
                <p>Atualize projeto e tipo de utilizador para liberar o acesso ao sistema.</p>
            </div>
        </div>

        <p><strong>Nome:</strong> <c:out value="${usuarioSelecionado.nome}"/></p>
        <p><strong>Email:</strong> <c:out value="${usuarioSelecionado.email}"/></p>

        <c:if test="${not empty erro}">
            <p class="erro"><c:out value="${erro}"/></p>
        </c:if>

        <form action="<%= request.getContextPath() %>/admin/usuarios/acesso" method="post">
            <input type="hidden" name="idUsuario" value="${usuarioSelecionado.id}">

            <label>Projetos</label>
            <c:forEach var="projeto" items="${projetos}">
                <c:set var="projetoMarcado" value="false"/>
                <c:forEach var="idSelecionado" items="${projetoSelecionadoIds}">
                    <c:if test="${idSelecionado == projeto.id}">
                        <c:set var="projetoMarcado" value="true"/>
                    </c:if>
                </c:forEach>
                <label>
                    <input type="checkbox" name="idProjeto" value="${projeto.id}"
                        <c:if test="${projetoMarcado}">checked</c:if>>
                    <c:out value="${projeto.descricao}"/>
                </label>
                <br>
            </c:forEach>
            <br>

            <label for="idTipoUsuario">Tipo de utilizador</label>
            <select id="idTipoUsuario" name="idTipoUsuario" class="input-field" required>
                <option value="">Selecione um tipo</option>
                <c:forEach var="tipo" items="${tiposUsuario}">
                    <c:choose>
                        <c:when test="${tipo.id == tipoSelecionadoId}">
                            <option value="${tipo.id}" selected>
                                <c:out value="${tipo.descricao}"/>
                            </option>
                        </c:when>
                        <c:otherwise>
                            <option value="${tipo.id}">
                                <c:out value="${tipo.descricao}"/>
                            </option>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </select>

            <div class="action-row">
                <button type="submit" class="btn-secondary">Guardar acesso</button>
            </div>
        </form>
    </section>
    </div>
</main>
</div>
<%@ include file="/WEB-INF/fragments/app-footer.jspf" %>

</body>
</html>
