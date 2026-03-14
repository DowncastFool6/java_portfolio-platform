<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Contato do Projeto - CTRL+VAULT</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>

<div class="">
<div class="">
    <%@ include file="/WEB-INF/fragments/app-header.jspf" %>

    <div class="">
        <div>
            <h2>Contato do projeto</h2>
            <p>Selecione o projeto e envie a mensagem de contato.</p>
        </div>
    </div>

    <p><strong>Utilizador:</strong> <c:out value="${usuario.nome}"/></p>

    <c:if test="${not empty mensagem}">
        <p class="sucesso"><c:out value="${mensagem}"/></p>
    </c:if>

    <c:if test="${not empty erro}">
        <p class="erro"><c:out value="${erro}"/></p>
    </c:if>
    <c:if test="${not empty projetos}">
        <form action="<%= request.getContextPath() %>/contatos" method="post">
            <label for="idProjeto">Projeto</label><br>
            <select id="idProjeto" name="idProjeto" required>
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
            <br><br>

            <label for="mensagem">Mensagem</label><br>
            <textarea id="mensagem" name="mensagem" rows="6" required><c:out value="${mensagemContato}"/></textarea>
            <br><br>

            <div class="">
                <button type="submit" class="btn-primary">Enviar contato</button>
            </div>
        </form>
    </c:if>
</div>
</div>

</body>
</html>
