<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Projeto - CTRL+VAULT</title>
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/styles.css">
</head>
<body>

<div class="page-shell app-layout">
    <%@ include file="/WEB-INF/fragments/app-header.jspf" %>

    <main class="app-main">
        <div class="app-container">
        <section class="section-heading section-heading-space">
            <div>
                <h2><c:out value="${projeto.descricao}"/></h2>
                <p>Visualize os conteudos do projeto e reorganize-os quando necessario.</p>
            </div>
            <div class="action-row">
                <c:if test="${usuarioPodeGerirUsuariosProjeto}">
                    <a class="btn-secondary" href="<%= request.getContextPath() %>/projeto/usuarios?idProjeto=${projeto.id}">Gerir utilizadores</a>
                </c:if>
                <c:if test="${usuarioPodeEditarProjeto and not modoEdicao}">
                    <a class="btn-secondary" href="<%= request.getContextPath() %>/projeto/conteudos/novo?idProjeto=${projeto.id}">Novo conteudo</a>
                </c:if>
                <c:choose>
                    <c:when test="${usuarioPodeEditarProjeto and not modoEdicao}">
                        <a class="btn-secondary" href="<%= request.getContextPath() %>/projeto?id=${projeto.id}&modo=editar">Editar</a>
                    </c:when>
                </c:choose>
            </div>
        </section>

        <c:if test="${not empty mensagem}">
            <p class="sucesso"><c:out value="${mensagem}"/></p>
        </c:if>

        <c:if test="${not empty erro}">
            <p class="erro"><c:out value="${erro}"/></p>
        </c:if>

        <c:if test="${empty conteudos}">
            <p class="empty-state">Este projeto ainda nao tem conteudos.</p>
        </c:if>

        <c:choose>
            <c:when test="${modoEdicao && usuarioPodeEditarProjeto}">
                <form action="<%= request.getContextPath() %>/projeto" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="idProjeto" value="${projeto.id}">
                    <input type="hidden" name="idConteudoRemover" id="idConteudoRemover">

                    <div class="content-list content-list-edit" id="content-list">
                        <c:forEach var="conteudo" items="${conteudos}">
                            <article class="content-card editable-card" draggable="true" data-content-id="${conteudo.id}">
                                <input type="hidden" name="conteudoId" value="${conteudo.id}">
                                <input type="hidden" name="ordem_${conteudo.id}" class="ordem-input" value="${conteudo.ordemExibicao}">
                                <input type="hidden" name="conteudoOrdem" class="ordem-id-input" value="${conteudo.id}">

                                <div class="drag-handle">Arraste para reordenar</div>

                                <label>Titulo</label>
                                <input type="text" name="titulo_${conteudo.id}" value="<c:out value="${conteudo.titulo}"/>" class="input-field">

                                <p class="meta-chip"><c:out value="${conteudo.tipoConteudo}"/></p>

                                <c:choose>
                                    <c:when test="${conteudo.tipoConteudo == 'TEXTO'}">
                                        <label>Texto</label>
                                        <textarea name="texto_${conteudo.id}" class="textarea-field" rows="7"><c:out value="${conteudo.conteudo}"/></textarea>
                                    </c:when>
                                    <c:otherwise>
                                        <label>Substituir ficheiro</label>
                                        <input type="file" name="arquivo_${conteudo.id}" accept="image/*">
                                        <img class="content-media content-image content-image-preview" src="<%= request.getContextPath() %>/conteudos/arquivo?idProjeto=${projeto.id}&idConteudo=${conteudo.id}" alt="<c:out value="${conteudo.titulo}"/>">
                                        <p class="helper-text">Se nao enviar um novo ficheiro, o atual sera mantido.</p>
                                    </c:otherwise>
                                </c:choose>

                                <div class="action-row content-actions">
                                    <button type="submit" name="acao" value="remover" class="btn-secondary"
                                            onclick="document.getElementById('idConteudoRemover').value='${conteudo.id}'; return confirm('Remover este conteudo do projeto?');"
                                            formnovalidate>
                                        Remover conteudo
                                    </button>
                                </div>
                            </article>
                        </c:forEach>
                    </div>

                    <div class="action-row">
                        <button type="submit" name="acao" value="guardar" class="btn-secondary">Guardar alteracoes</button>
                        <a class="btn-secondary" href="<%= request.getContextPath() %>/projeto?id=${projeto.id}">Cancelar edicao</a>
                    </div>
                </form>
            </c:when>
            <c:otherwise>
                <div class="content-list">
                    <c:forEach var="conteudo" items="${conteudos}">
                        <article class="content-card">
                            <c:if test="${not empty conteudo.titulo}">
                                <h3><c:out value="${conteudo.titulo}"/></h3>
                            </c:if>

                            <c:choose>
                                <c:when test="${conteudo.tipoConteudo == 'TEXTO'}">
                                    <div class="content-text"><c:out value="${conteudo.conteudo}"/></div>
                                </c:when>
                                <c:when test="${conteudo.tipoConteudo == 'IMAGEM'}">
                                    <img class="content-media content-image" src="<%= request.getContextPath() %>/conteudos/arquivo?idProjeto=${projeto.id}&idConteudo=${conteudo.id}" alt="<c:out value="${conteudo.titulo}"/>">
                                </c:when>
                            </c:choose>
                        </article>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
        </div>
    </main>
</div>
<%@ include file="/WEB-INF/fragments/app-footer.jspf" %>

<script>
(() => {
    const list = document.getElementById('content-list');
    if (!list) {
        return;
    }

    let dragged = null;

    const updateOrder = () => {
        const cards = list.querySelectorAll('.editable-card');
        cards.forEach((card, index) => {
            card.querySelector('.ordem-input').value = index + 1;
            card.querySelector('.ordem-id-input').value = card.dataset.contentId;
        });
    };

    list.querySelectorAll('.editable-card').forEach((card) => {
        card.addEventListener('dragstart', () => {
            dragged = card;
            card.classList.add('dragging');
        });

        card.addEventListener('dragend', () => {
            card.classList.remove('dragging');
            dragged = null;
            updateOrder();
        });

        card.addEventListener('dragover', (event) => {
            event.preventDefault();
            if (!dragged || dragged === card) {
                return;
            }

            const rect = card.getBoundingClientRect();
            const shouldInsertAfter = event.clientY > rect.top + rect.height / 2;
            if (shouldInsertAfter) {
                card.after(dragged);
            } else {
                card.before(dragged);
            }
        });
    });

    updateOrder();
})();
</script>

</body>
</html>
