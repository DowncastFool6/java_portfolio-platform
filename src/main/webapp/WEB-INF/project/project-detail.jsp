<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt">
<head>
    <title>Projeto - CTRL+VAULT</title>
    <%@ include file="/WEB-INF/fragments/app-head.jspf" %>
</head>
<body>

<div class="page-shell app-layout">
    <%@ include file="/WEB-INF/fragments/side-panel.jspf" %>

    <main class="page-shell">
        <div class="app-container">
        <section class="section-heading section-heading-space">
            <div>
                <h2>
                    <c:choose>
                        <c:when test="${not empty projeto.titulo}">
                            <c:out value="${projeto.titulo}"/>
                        </c:when>
                        <c:otherwise>
                            <c:out value="${projeto.descricao}"/>
                        </c:otherwise>
                    </c:choose>
                </h2>
                <c:if test="${not empty projeto.descricao}">
                    <p><c:out value="${projeto.descricao}"/></p>
                </c:if>
                <div class="project-header-meta">
                    <span class="status-chip">${projeto.statusDescricao}</span>
                    <span class="status-chip">Criado em ${projeto.dataCriacaoFormatada}</span>
                    <c:if test="${not empty projeto.dataFim}">
                        <span class="status-chip">Finalizado em ${projeto.dataFimFormatada}</span>
                    </c:if>
                </div>
                <div class="action-row">
                    <c:if test="${usuarioPodeGerirUsuariosProjeto}">
                        <a class="btn-primary" href="<%= request.getContextPath() %>/projeto/usuarios?idProjeto=${projeto.id}">Gerir utilizadores</a>
                    </c:if>
                    <c:if test="${usuarioPodeAlterarStatusProjeto and not modoEdicao}">
                        <form action="<%= request.getContextPath() %>/projeto" method="post">
                            <input type="hidden" name="idProjeto" value="${projeto.id}">
                            <c:choose>
                                <c:when test="${projetoFechado}">
                                    <button type="submit" name="acao" value="reabrir-projeto" class="btn-primary">Re-abrir projeto</button>
                                </c:when>
                                <c:otherwise>
                                    <button type="submit" name="acao" value="fechar-projeto" class="btn-primary">Fechar projeto</button>
                                </c:otherwise>
                            </c:choose>
                        </form>
                    </c:if>
                    <c:if test="${usuarioPodeEditarProjeto and not modoEdicao}">
                        <a class="btn-primary" href="<%= request.getContextPath() %>/projeto/conteudos/novo?idProjeto=${projeto.id}">Novo conteudo</a>
                    </c:if>
                    <c:choose>
                        <c:when test="${usuarioPodeEditarProjeto and not modoEdicao}">
                            <a class="btn-primary" href="<%= request.getContextPath() %>/projeto?id=${projeto.id}&modo=editar">Editar</a>
                        </c:when>
                    </c:choose>
                </div>
            </div>
        </section>

        <c:if test="${not empty mensagem}">
            <p class="sucesso"><c:out value="${mensagem}"/></p>
        </c:if>

        <c:if test="${not empty erro}">
            <p class="erro"><c:out value="${erro}"/></p>
        </c:if>

        <c:if test="${projetoFechado}">
            <p class="empty-state">Projeto fechado. O conteudo pode ser visualizado, mas nao pode ser alterado ate ser reaberto por um gestor.</p>
        </c:if>

        <c:if test="${empty conteudos}">
            <p class="empty-state">Este projeto ainda nao tem conteudos.</p>
        </c:if>

        <c:choose>
            <c:when test="${modoEdicao && usuarioPodeEditarProjeto}">
                <form action="<%= request.getContextPath() %>/projeto" method="post" enctype="multipart/form-data" class="content-edit-form">
                    <input type="hidden" name="idProjeto" value="${projeto.id}">
                    <input type="hidden" name="idConteudoRemover" id="idConteudoRemover">

                    <div class="content-list content-list-edit" id="content-list">
                        <c:forEach var="conteudo" items="${conteudos}">
                            <article class="content-card editable-card" draggable="true" data-content-id="${conteudo.id}">
                                <input type="hidden" name="conteudoId" value="${conteudo.id}">
                                <input type="hidden" name="ordem_${conteudo.id}" class="ordem-input" value="${conteudo.ordemExibicao}">
                                <input type="hidden" name="conteudoOrdem" class="ordem-id-input" value="${conteudo.id}">

                                <div class="content-editor-card">
                                    <label>Titulo</label>
                                    <input type="text" name="titulo_${conteudo.id}" value="<c:out value="${conteudo.titulo}"/>">

                                    <c:choose>
                                        <c:when test="${conteudo.tipoConteudo == 'TEXTO'}">
                                            <label>Texto</label>
                                            <textarea name="texto_${conteudo.id}" rows="7"><c:out value="${conteudo.conteudo}"/></textarea>
                                        </c:when>
                                        <c:otherwise>
                                            <label>Substituir ficheiro</label>
                                            <input type="file" name="arquivo_${conteudo.id}" accept="image/*">
                                            <img class="content-media content-image content-image-preview" src="<%= request.getContextPath() %>/conteudos/arquivo?idProjeto=${projeto.id}&idConteudo=${conteudo.id}" alt="<c:out value="${conteudo.titulo}"/>">
                                        </c:otherwise>
                                    </c:choose>

                                    <div class="action-row content-actions">
                                        <button type="submit" name="acao" value="remover" class="btn-primary remove-content-button"
                                                data-remover-conteudo-id="${conteudo.id}"
                                                formnovalidate>
                                            Remover conteudo
                                        </button>
                                    </div>

                                    <div class="content-card-footer">
                                        <p>
                                            Criado por
                                            <strong><c:out value="${empty conteudo.usuarioCriacao ? 'Utilizador desconhecido' : conteudo.usuarioCriacao.nome}"/></strong>
                                            em
                                            <strong><c:out value="${conteudo.dataCriacaoFormatada}"/></strong>
                                        </p>
                                        <c:if test="${conteudo.editado}">
                                            <p>
                                                Editado por
                                                <strong><c:out value="${empty conteudo.usuarioEdicao ? 'Utilizador desconhecido' : conteudo.usuarioEdicao.nome}"/></strong>
                                                em
                                                <strong><c:out value="${conteudo.dataEdicaoFormatada}"/></strong>
                                            </p>
                                        </c:if>
                                    </div>
                                </div>
                            </article>
                        </c:forEach>
                    </div>

                    <div class="action-row">
                        <button type="submit" name="acao" value="guardar" class="btn-primary">Guardar alteracoes</button>
                        <a class="btn-primary" href="<%= request.getContextPath() %>/projeto?id=${projeto.id}">Cancelar edicao</a>
                    </div>
                </form>
            </c:when>
            <c:otherwise>
                <div class="content-list">
                    <c:forEach var="conteudo" items="${conteudos}">
                        <article class="content-card content-card-view">
                            <c:if test="${not empty conteudo.titulo}">
                                <div class="content-card-header">
                                    <h3 class="content-card-title"><c:out value="${conteudo.titulo}"/></h3>
                                </div>
                            </c:if>

                            <div class="content-card-body">
                                <c:choose>
                                    <c:when test="${conteudo.tipoConteudo == 'TEXTO'}">
                                        <div class="content-text"><c:out value="${conteudo.conteudo}"/></div>
                                    </c:when>
                                    <c:when test="${conteudo.tipoConteudo == 'IMAGEM'}">
                                        <img class="content-media content-image" src="<%= request.getContextPath() %>/conteudos/arquivo?idProjeto=${projeto.id}&idConteudo=${conteudo.id}" alt="<c:out value="${conteudo.titulo}"/>" loading="lazy">
                                    </c:when>
                                </c:choose>
                            </div>

                            <div class="content-card-footer">
                                <p>
                                    Criado por
                                    <strong><c:out value="${empty conteudo.usuarioCriacao ? 'Utilizador desconhecido' : conteudo.usuarioCriacao.nome}"/></strong>
                                    em
                                    <strong><c:out value="${conteudo.dataCriacaoFormatada}"/></strong>
                                </p>
                                <c:if test="${conteudo.editado}">
                                    <p>
                                        Editado por
                                        <strong><c:out value="${empty conteudo.usuarioEdicao ? 'Utilizador desconhecido' : conteudo.usuarioEdicao.nome}"/></strong>
                                        em
                                        <strong><c:out value="${conteudo.dataEdicaoFormatada}"/></strong>
                                    </p>
                                </c:if>
                            </div>
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

    list.querySelectorAll('.remove-content-button').forEach((button) => {
        button.addEventListener('click', (event) => {
            const confirmed = window.confirm('Remover este conteudo do projeto?');
            if (!confirmed) {
                event.preventDefault();
                return;
            }

            const removerConteudoId = button.getAttribute('data-remover-conteudo-id');
            const input = document.getElementById('idConteudoRemover');
            if (input && removerConteudoId) {
                input.value = removerConteudoId;
            }
        });
    });

    updateOrder();
})();
</script>

</body>
</html>
