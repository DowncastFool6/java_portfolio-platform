<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="pt">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>401 — Não autenticado · CTRL+VAULT</title>
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700;800&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="<%= request.getContextPath() %>/css/error-pages.css">
</head>
<body>
  <div class="error-card">

    <div class="mascot-wrap mascot-float-bob">
      <img src="<%= request.getContextPath() %>/images/9.svg" alt="Mascote CTRL+VAULT" />
    </div>

    <div class="error-code">401</div>
    <h1 class="error-title">Sessão não iniciada</h1>
    <p class="error-msg">
      Precisas de iniciar sessão para aceder a esta página.<br>
      As tuas credenciais podem ter expirado — entra novamente para continuar.
    </p>

    <div class="actions">
      <a href="<%= request.getContextPath() %>/login" class="btn btn-primary">&#128100; Iniciar sessão</a>
      <a href="<%= request.getContextPath() %>/index.jsp" class="btn btn-ghost">&#8962; Página inicial</a>
    </div>

    <p class="brand">CTRL+VAULT</p>
  </div>
</body>
</html>
