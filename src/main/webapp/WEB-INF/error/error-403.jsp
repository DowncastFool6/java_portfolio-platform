<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="pt">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>403 — Acesso negado · CTRL+VAULT</title>
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700;800&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/error-pages.css">
</head>
<body class="error-403">
  <div class="error-card">

    <div class="mascot-wrap mascot-float-run">
      <img src="<%= request.getContextPath() %>/images/7.svg" alt="Mascote CTRL+VAULT" />
    </div>

    <div class="error-code error-code--danger">403</div>
    <h1 class="error-title">Acesso negado</h1>
    <p class="error-msg">
      Não tens permissão para aceder a este recurso.<br>
      Se achares que isto é um engano, contacta o administrador do sistema.
    </p>

    <p class="brand">CTRL+VAULT</p>
  </div>
</body>
</html>
