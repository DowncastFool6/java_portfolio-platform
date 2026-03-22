<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Novo Conteúdo - CTRL+VAULT</title>
    <link rel="stylesheet" href="<%= request.getContextPath()%>/css/styles.css">
</head>
<body>

<div class="page-shell app-layout">
    <%@ include file="/WEB-INF/fragments/side-panel.jspf" %>

    <main class="app-main">
        <div class="app-container form-container">
        <section class="section-heading">
            <div>
                <h2>Novo conteúdo</h2>
                <p>Projeto: <strong><c:out value="${projeto.descricao}"/></strong></p>
            </div>
        </section>

        <c:if test="${not empty erro}">
            <p class="erro"><c:out value="${erro}"/></p>
        </c:if>

        <form action="<%= request.getContextPath() %>/projeto/conteudos/novo" method="post" enctype="multipart/form-data" class="stack-form content-editor-form">
            <input type="hidden" name="idProjeto" value="${projeto.id}">

            <section class="content-editor-card">
                <div class="content-editor-header">
                    <span class="meta-chip">Base</span>
                    <h3>Identificação do conteúdo</h3>
                    <p>Defina o título e a estrutura principal antes de preencher o material.</p>
                </div>

                <label for="titulo">Título</label>
                <input id="titulo" name="titulo" type="text" value="<c:out value="${tituloConteudo}"/>" class="input-field">

                <label for="tipoConteudo">Tipo de conteúdo</label>
                <select id="tipoConteudo" name="tipoConteudo" class="input-field" required>
                    <option value="">Selecione</option>
                    <option value="TEXTO" <c:if test="${tipoConteudoSelecionado == 'TEXTO'}">selected</c:if>>Texto</option>
                    <option value="IMAGEM" <c:if test="${tipoConteudoSelecionado == 'IMAGEM'}">selected</c:if>>Imagem</option>
                </select>
            </section>

            <section class="content-editor-card">
                <div class="content-editor-header">
                    <span class="meta-chip">Conteúdo</span>
                    <h3>Material do projeto</h3>
                    <p>Preencha o texto ou carregue o ficheiro que será apresentado no projeto.</p>
                </div>

                <div id="texto-wrapper" class="content-editor-body">
                    <label for="texto">Texto</label>
                    <textarea id="texto" name="texto" rows="8" class="textarea-field"><c:out value="${textoConteudo}"/></textarea>
                </div>

                <div id="arquivo-wrapper" class="content-editor-body">
                    <label for="arquivo">Ficheiro</label>
                    <input id="arquivo" name="arquivo" type="file">
                </div>
            </section>

            <div class="action-row">
                <button type="submit" class="btn-primary">Criar conteúdo</button>
            </div>
        </form>
        </div>
</main>
</div>
<%@ include file="/WEB-INF/fragments/app-footer.jspf" %>

<script>
(() => {
    const tipoSelect = document.getElementById('tipoConteudo');
    const textoWrapper = document.getElementById('texto-wrapper');
    const arquivoWrapper = document.getElementById('arquivo-wrapper');
    const arquivoInput = document.getElementById('arquivo');

    const sync = () => {
        const value = tipoSelect.value;
        const isText = value === 'TEXTO';
        textoWrapper.style.display = isText ? 'block' : 'none';
        arquivoWrapper.style.display = isText ? 'none' : 'block';
        arquivoInput.accept = value === 'IMAGEM' ? 'image/*' : '';
    };

    tipoSelect.addEventListener('change', sync);
    sync();
})();
</script>

</body>
</html>
