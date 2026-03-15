<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Novo Conteudo - CTRL+VAULT</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/base.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/layout.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/components.css">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/pages.css">
</head>
<body>

<div class="page-shell">
    <%@ include file="/WEB-INF/fragments/app-header.jspf" %>

    <main class="app-container form-container">
        <section class="section-heading">
            <div>
                <h2>Novo conteudo</h2>
                <p>Projeto: <strong><c:out value="${projeto.descricao}"/></strong></p>
            </div>
        </section>

        <c:if test="${not empty erro}">
            <p class="erro"><c:out value="${erro}"/></p>
        </c:if>

        <form action="<%= request.getContextPath() %>/projeto/conteudos/novo" method="post" enctype="multipart/form-data" class="stack-form">
            <input type="hidden" name="idProjeto" value="${projeto.id}">

            <label for="titulo">Titulo</label>
            <input id="titulo" name="titulo" type="text" value="<c:out value="${tituloConteudo}"/>" class="input-field">

            <label for="tipoConteudo">Tipo de conteudo</label>
            <select id="tipoConteudo" name="tipoConteudo" class="input-field" required>
                <option value="">Selecione</option>
                <option value="TEXTO" <c:if test="${tipoConteudoSelecionado == 'TEXTO'}">selected</c:if>>Texto</option>
                <option value="IMAGEM" <c:if test="${tipoConteudoSelecionado == 'IMAGEM'}">selected</c:if>>Imagem</option>
            </select>

            <div id="texto-wrapper">
                <label for="texto">Texto</label>
                <textarea id="texto" name="texto" rows="8" class="textarea-field"><c:out value="${textoConteudo}"/></textarea>
            </div>

            <div id="arquivo-wrapper">
                <label for="arquivo">Ficheiro</label>
                <input id="arquivo" name="arquivo" type="file">
            </div>

            <div class="action-row">
                <button type="submit" class="btn-secondary">Criar conteudo</button>
            </div>
        </form>
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
