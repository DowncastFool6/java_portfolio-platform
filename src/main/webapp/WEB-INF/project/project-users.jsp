<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Utilizadores do Projeto - CTRL+VAULT</title>
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/styles.css">
</head>
<body>

<div class="page-shell app-layout">
    <%@ include file="/WEB-INF/fragments/app-header.jspf" %>

    <main class="app-main">
        <div class="app-container">
        <section class="section-heading section-heading-space">
            <div>
                <h2>Utilizadores do projeto</h2>
                <p>Projeto: <strong><c:out value="${projeto.descricao}"/></strong></p>
            </div>
            <div class="action-row">
                <a class="btn-secondary" href="<%= request.getContextPath() %>/projeto?id=${projeto.id}&modo=editar">Voltar ao projeto</a>
            </div>
        </section>

        <c:if test="${not empty mensagem}">
            <p class="sucesso"><c:out value="${mensagem}"/></p>
        </c:if>

        <c:if test="${not empty erro}">
            <p class="erro"><c:out value="${erro}"/></p>
        </c:if>

        <div class="stack-form">
            <table class="data-table">
                <thead>
                    <tr>
                        <th>Nome</th>
                        <th>Email</th>
                        <th>Tipo</th>
                        <th>Status</th>
                        <th>Acao</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="usuarioProjeto" items="${usuariosProjeto}">
                        <tr>
                            <td><c:out value="${usuarioProjeto.nome}"/></td>
                            <td><c:out value="${usuarioProjeto.email}"/></td>
                            <td><c:out value="${usuarioProjeto.tipoUsuario.descricao}"/></td>
                            <td>
                                <c:choose>
                                    <c:when test="${usuarioProjeto.ativo}">Ativo</c:when>
                                    <c:otherwise>Inativo</c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <form action="<%= request.getContextPath() %>/projeto/usuarios" method="post">
                                    <input type="hidden" name="idProjeto" value="${projeto.id}">
                                    <input type="hidden" name="idUsuario" value="${usuarioProjeto.id}">
                                    <c:choose>
                                        <c:when test="${usuarioProjeto.ativo}">
                                            <button type="submit" name="acao" value="desativar" class="btn-secondary">Desativar</button>
                                        </c:when>
                                        <c:otherwise>
                                            <button type="submit" name="acao" value="ativar" class="btn-secondary">Ativar</button>
                                        </c:otherwise>
                                    </c:choose>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        </div>
</main>
</div>
<%@ include file="/WEB-INF/fragments/app-footer.jspf" %>

</body>
</html>
