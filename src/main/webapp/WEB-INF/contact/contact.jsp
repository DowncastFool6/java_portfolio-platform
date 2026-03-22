<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Contato do Projeto - CTRL+VAULT</title>
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/styles.css">
</head>
<body>

<div class="page-shell app-layout">
    <%@ include file="/WEB-INF/fragments/side-panel.jspf" %>

<main class="app-main">
    <div class="app-container">
    <section class="portal-panel">
        <div class="section-heading">
            <div>
                <h2>Contato do projeto</h2>
                <p>Selecione o projeto e envie a mensagem de contato.</p>
            </div>
        </div>

        <c:if test="${not empty mensagem}">
            <p class="sucesso"><c:out value="${mensagem}"/></p>
        </c:if>

        <c:if test="${not empty erro}">
            <p class="erro"><c:out value="${erro}"/></p>
        </c:if>
        <c:if test="${not empty projetos}">
            <form action="<%= request.getContextPath() %>/contatos" method="post">
                <label for="idProjeto">Projeto</label>
                <select id="idProjeto" name="idProjeto" class="input-field" required>
                    <option value="">Selecione um projeto</option>
                    <c:forEach var="projeto" items="${projetos}">
                        <c:choose>
                            <c:when test="${projeto.id == idProjetoSelecionado}">
                                <option value="${projeto.id}" selected>
                                    <c:out value="${projeto.descricao}"/>
                                </option>
                            </c:when>
                            <c:otherwise>
                                <option value="${projeto.id}">
                                    <c:out value="${projeto.descricao}"/>
                                </option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select>

                <label for="mensagem">Mensagem</label>
                <textarea id="mensagem" name="mensagem" rows="6" class="textarea-field" required><c:out value="${mensagemContato}"/></textarea>

                <div class="action-row">
                    <button type="submit" class="btn-primary">Enviar contato</button>
                </div>
            </form>
        </c:if>
    </section>
    </div>
</main>
</div>
<%@ include file="/WEB-INF/fragments/app-footer.jspf" %>

</body>
</html>
