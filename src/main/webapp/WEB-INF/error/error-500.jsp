<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="pt">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>500 — Erro interno · CTRL+VAULT</title>
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700;800&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/error-pages.css">
</head>
<body>
  <div class="error-card">

    <div class="mascot-wrap mascot-float-wobble">
      <img src="<%= request.getContextPath() %>/images/8.svg" alt="Mascote CTRL+VAULT" />
    </div>

    <div class="error-code">500</div>
    <h1 class="error-title">Algo correu mal</h1>
    <p class="error-msg">
      O servidor encontrou um problema inesperado e não conseguiu processar o pedido.<br>
      Tenta novamente dentro de momentos.
    </p>

    <p class="brand">CTRL+VAULT</p>
  </div>
</body>
</html>
