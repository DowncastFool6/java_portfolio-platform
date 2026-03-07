<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Gestao de Acessos - CTRL+VAULT</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<div class="">
<div class="">
    <div class="">
        <div>
            <h2>Utilizadores sem acesso</h2>
            <p class="">Clique numa linha para associar o utilizador a um projeto e definir o seu tipo.</p>
        </div>
        <a class="btn-secondary" href="${pageContext.request.contextPath}/dashboard">Voltar</a>
    </div>

    <c:if test="${not empty mensagem}">
        <p class="sucesso"><c:out value="${mensagem}"/></p>
    </c:if>

    <c:choose>
        <c:when test="${empty usuariosPendentes}">
            <p>Nao existem utilizadores pendentes de aprovacao.</p>
        </c:when>
        <c:otherwise>
            <div class="">
                <table class="">
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
                            <tr onclick="window.location='${pageContext.request.contextPath}/admin/usuarios/acesso?id=${item.id}'">
                                <td><c:out value="${item.id}"/></td>
                                <td><c:out value="${item.nome}"/></td>
                                <td><c:out value="${item.email}"/></td>
                                <td><span class="">Pendente</span></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:otherwise>
    </c:choose>
</div>
</div>

</body>
</html>
