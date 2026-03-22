<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Contatos Recebidos - CTRL+VAULT</title>
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/styles.css">
</head>
<body>

<div class="page-shell app-layout">
    <%@ include file="/WEB-INF/fragments/side-panel.jspf" %>

<main class="app-main">
    <div class="app-container">
        <section class="portal-section">
            <div class="section-heading">
                <div>
                    <h2>Contatos recebidos</h2>
                    <p>Veja os contatos recebidos pelos projetos sob sua gestão, marque-os como lidos ou remova vários de uma vez.</p>
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
											<c:forEach var="contato" items="${contatosRecebidos}">
												<c:set var="contatoMarcado" value="false" />
												<c:forEach var="idSelecionado"
													items="${idsContatoSelecionados}">
													<c:if test="${idSelecionado == contato.id}">
														<c:set var="contatoMarcado" value="true" />
													</c:if>
												</c:forEach>
												<tr>
													<td><input type="checkbox" name="idContato"
														value="${contato.id}"
														<c:if test="${contatoMarcado}">checked</c:if>></td>
													<td><c:out value="${contato.projeto.descricao}" /></td>
													<td><c:out value="${contato.usuario.nome}" /></td>
													<td><c:out value="${contato.usuario.email}" /></td>
													<td><c:out value="${contato.mensagem}" /></td>
													<td><c:out value="${contato.dataEnvio}" /></td>
													<td><span class="status-chip"> <c:choose>
																<c:when test="${contato.flgLida}">Lido</c:when>
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
