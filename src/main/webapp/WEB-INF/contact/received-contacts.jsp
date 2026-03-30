<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt">
<head>
	<title>Contatos Recebidos - CTRL+VAULT</title>
    <%@ include file="/WEB-INF/fragments/app-head.jspf" %>
</head>
<body>

<div class="page-shell app-layout">
    <%@ include file="/WEB-INF/fragments/side-panel.jspf" %>

<main class="app-main">
    <div class="app-container">
        <div class="section-heading">
            <div>
                <h2>Contatos recebidos</h2>
                <p>Veja os contatos recebidos pelos projetos sob sua gestão, marque-os como lidos ou remova vários de uma vez.</p>
            </div>
        </div>    
        <section class="portal-section">


            <c:if test="${not empty mensagem}">
                <p class="sucesso"><c:out value="${mensagem}"/></p>
            </c:if>

            <c:if test="${not empty erro}">
                <p class="erro"><c:out value="${erro}"/></p>
            </c:if>

            <c:choose>
                <c:when test="${empty contatosRecebidos}">
                    <p class="empty-state">Não existem contatos recebidos para os seus projetos.</p>
                </c:when>
                <c:otherwise>
                    <form action="<%= request.getContextPath() %>/contatos/recebidos" method="post" class="stack-form">
                        <div class="action-row">
                            <button type="submit" name="acao" value="ler" class="btn-primary">
                                Marcar como lidos
                            </button>
                            <button type="submit" name="acao" value="remover" class="btn-primary" id="remove-contacts-button">
                                Remover
                            </button>
                        </div>

								<div class="table-wrap">
									<table class="data-table">
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
											<c:forEach var="contatoItem" items="${contatosRecebidos}">
												<c:set var="contatoSelecionado" value="false" />
												<c:forEach var="contatoSelecionadoId"
													items="${idsContatoSelecionados}">
													<c:if test="${contatoSelecionadoId == contatoItem.id}">
														<c:set var="contatoSelecionado" value="true" />
													</c:if>
												</c:forEach>
												<tr>
													<td><input type="checkbox" name="idContato"
														value="${contatoItem.id}"
														<c:if test="${contatoSelecionado}">checked</c:if>></td>
													<td><c:out value="${contatoItem.projeto.titulo}" /></td>
													<td><c:out value="${contatoItem.usuario.nome}" /></td>
													<td><c:out value="${contatoItem.usuario.email}" /></td>
													<td><c:out value="${contatoItem.mensagem}" /></td>
													<td><c:out value="${contatoItem.dataEnvio}" /></td>
													<td><span class="status-chip"> <c:choose>
																<c:when test="${contatoItem.flgLida}">Lido</c:when>
																<c:otherwise>Pendente</c:otherwise>
															</c:choose>
													</span></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</div>
							</form>
                </c:otherwise>
            </c:choose>
        </section>
    </div>
</main>
</div>
<%@ include file="/WEB-INF/fragments/app-footer.jspf" %>

<script>
(() => {
    const button = document.getElementById('remove-contacts-button');
    if (!button) {
        return;
    }

    button.addEventListener('click', (event) => {
        const confirmed = window.confirm('Remover os contatos selecionados?');
        if (!confirmed) {
            event.preventDefault();
        }
    });
})();
</script>

</body>
</html>
