<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="pt">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>404 — Página não encontrada · CTRL+VAULT</title>
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700;800&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/error-pages.css">
</head>
<body>
  <div class="error-card">

    <div class="mascot-wrap mascot-float-sway">
      <img src="<%= request.getContextPath() %>/images/6.svg" alt="Mascote CTRL+VAULT" />
    </div>

    <div class="error-code">404</div>
    <h1 class="error-title">Página não encontrada</h1>
    <p class="error-msg">
      Parece que esta página adormeceu e não voltou mais.<br>
      O endereço pode ter mudado ou o conteúdo foi removido.
    </p>

    <div class="actions">
      <a href="<%= request.getContextPath() %>/login" class="btn btn-primary">&#128100; Iniciar sessão</a>
      <a href="<%= request.getContextPath() %>/index.jsp" class="btn btn-ghost">&#8962; Página inicial</a>
    </div>

    <p class="brand">CTRL+VAULT</p>
  </div>
</body>
</html>
