<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Atualizar Acesso - CTRL+VAULT</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<div class="">
<div class="">
    <div class="">
        <div>
            <h2>Gerir acesso do utilizador</h2>
            <p class="">Atualize projeto e tipo de utilizador para liberar o acesso ao sistema.</p>
        </div>
        <a class="btn-secondary" href="${pageContext.request.contextPath}/admin/usuarios">Voltar</a>
    </div>

    <p><strong>Nome:</strong> <c:out value="${usuarioSelecionado.nome}"/></p>
    <p><strong>Email:</strong> <c:out value="${usuarioSelecionado.email}"/></p>

    <c:if test="${not empty erro}">
        <p class="erro"><c:out value="${erro}"/></p>
    </c:if>

    <form action="${pageContext.request.contextPath}/admin/usuarios/acesso" method="post">
        <input type="hidden" name="idUsuario" value="${usuarioSelecionado.id}">

        <label for="idProjeto">Projeto</label><br>
        <select id="idProjeto" name="idProjeto" required>
            <option value="">Selecione um projeto</option>
            <c:forEach var="projeto" items="${projetos}">
                <c:choose>
                    <c:when test="${projeto.id == projetoSelecionadoId}">
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

        <label for="idTipoUsuario">Tipo de utilizador</label><br>
        <select id="idTipoUsuario" name="idTipoUsuario" required>
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
        <br><br>

        <div class="">
            <button type="submit" class="btn-primary">Guardar acesso</button>
            <a class="btn-secondary" href="${pageContext.request.contextPath}/admin/usuarios">Cancelar</a>
        </div>
    </form>
</div>
</div>

</body>
</html>
