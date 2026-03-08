<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Contatos Recebidos - CTRL+VAULT</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
</head>
<body>

<div class="">
<div class="">
    <%@ include file="/WEB-INF/fragments/app-header.jspf" %>

    <div class="">
        <div>
            <h2>Contatos recebidos</h2>
            <p>Veja os contatos recebidos pelos projetos sob sua gestao e marque-os como lidos.</p>
        </div>
    </div>

    <c:if test="${not empty mensagem}">
        <p class="sucesso"><c:out value="${mensagem}"/></p>
    </c:if>

    <c:if test="${not empty erro}">
        <p class="erro"><c:out value="${erro}"/></p>
    </c:if>

    <c:choose>
        <c:when test="${empty contatosRecebidos}">
            <p>Nao existem contatos recebidos para os seus projetos.</p>
        </c:when>
        <c:otherwise>
            <form action="<%= request.getContextPath() %>/contatos/recebidos" method="post">
                <div class="">
                    <button type="submit" class="btn-primary">
                        Marcar selecionados como lidos
                    </button>
                </div>
                <br>

                <table class="">
                    <thead>
                        <tr>
                            <th></th>
                            <th>Projeto</th>
                            <th>Remetente</th>
                            <th>Email</th>
                            <th>Mensagem</th>
                            <th>Data</th>
                            <th>Estado</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="contato" items="${contatosRecebidos}">
                            <c:set var="contatoMarcado" value="false"/>
                            <c:forEach var="idSelecionado" items="${idsContatoSelecionados}">
                                <c:if test="${idSelecionado == contato.id}">
                                    <c:set var="contatoMarcado" value="true"/>
                                </c:if>
                            </c:forEach>
                            <tr>
                                <td>
                                    <c:if test="${not contato.flgLida}">
                                        <input type="checkbox" name="idContato" value="${contato.id}"
                                            <c:if test="${contatoMarcado}">checked</c:if>>
                                    </c:if>
                                </td>
                                <td><c:out value="${contato.projeto.descricao}"/></td>
                                <td><c:out value="${contato.usuario.nome}"/></td>
                                <td><c:out value="${contato.usuario.email}"/></td>
                                <td><c:out value="${contato.mensagem}"/></td>
                                <td><c:out value="${contato.dataEnvio}"/></td>
                                <td>
                                    <c:choose>
                                        <c:when test="${contato.flgLida}">Lido</c:when>
                                        <c:otherwise>Pendente</c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </form>
        </c:otherwise>
    </c:choose>
</div>
</div>

</body>
</html>
