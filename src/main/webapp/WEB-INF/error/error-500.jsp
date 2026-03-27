<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="pt">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>500 — Erro interno · ctrl-vault</title>
  <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700;800&display=swap" rel="stylesheet">
  <style>
    :root {
      --color-background: #eef1ef;
      --color-surface:    #ffffff;
      --color-primary:    #6f3fb5;
      --color-primary-hover: #5d33a1;
      --color-grape-soda: #88498f;
      --color-text:       #101010;
      --color-muted:      #6b6b6b;
      --color-border:     #e4e4e7;
      --shadow-md:        0 10px 30px rgba(0,0,0,0.08);
      --radius-lg:        14px;
      --transition:       0.25s ease;
    }

    *, *::before, *::after { margin: 0; padding: 0; box-sizing: border-box; }

    body {
      min-height: 100vh;
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      font-family: "Poppins", sans-serif;
      background: var(--color-background);
      color: var(--color-text);
      overflow: hidden;
      position: relative;
    }

    body::before {
      content: "";
      position: fixed;
      inset: 0;
      background:
        radial-gradient(ellipse 60% 50% at 25% 20%, rgba(111,63,181,0.09) 0%, transparent 70%),
        radial-gradient(ellipse 55% 60% at 75% 80%, rgba(136,73,143,0.09) 0%, transparent 70%);
      pointer-events: none;
    }

    .error-card {
      position: relative;
      background: var(--color-surface);
      border-radius: var(--radius-lg);
      box-shadow: var(--shadow-md);
      padding: 3rem 3.5rem;
      max-width: 540px;
      width: 90%;
      text-align: center;
      animation: cardIn 0.6s cubic-bezier(0.22,1,0.36,1) both;
    }

    @keyframes cardIn {
      from { opacity: 0; transform: translateY(28px) scale(0.97); }
      to   { opacity: 1; transform: translateY(0)   scale(1); }
    }

    .svg-wrap {
      margin: 0 auto 1.75rem;
      width: 180px;
      height: 160px;
    }

    /* Gear rotation */
    @keyframes gearSpin {
      from { transform: rotate(0deg); }
      to   { transform: rotate(360deg); }
    }

    @keyframes gearSpinRev {
      from { transform: rotate(0deg); }
      to   { transform: rotate(-360deg); }
    }

    /* Warning flash */
    @keyframes warnFlash {
      0%, 90%, 100% { opacity: 1; }
      95%            { opacity: 0.2; }
    }

    /* Bolt jitter */
    @keyframes boltJitter {
      0%, 100% { transform: translate(0, 0); }
      25%       { transform: translate(-1px, 1px); }
      50%       { transform: translate(1px, -1px); }
      75%       { transform: translate(-1px, -1px); }
    }

    .gear-big {
      transform-origin: 72px 72px;
      animation: gearSpin 5s linear infinite;
    }

    .gear-small {
      transform-origin: 112px 88px;
      animation: gearSpinRev 3.3s linear infinite;
    }

    .warn-triangle { animation: warnFlash 2s ease-in-out infinite; }

    .bolt { animation: boltJitter 0.35s ease-in-out infinite; }

    .error-code {
      font-size: 5rem;
      font-weight: 800;
      line-height: 1;
      background: linear-gradient(135deg, var(--color-primary) 0%, var(--color-grape-soda) 100%);
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
      background-clip: text;
      letter-spacing: -2px;
      margin-bottom: 0.5rem;
    }

    .error-title {
      font-size: 1.25rem;
      font-weight: 600;
      color: var(--color-text);
      margin-bottom: 0.75rem;
    }

    .error-msg {
      font-size: 0.9rem;
      color: var(--color-muted);
      line-height: 1.7;
      margin-bottom: 2rem;
    }

    /* Collapsible detail block (dev-friendly) -->
    .detail-toggle {
      background: none;
      border: none;
      font-family: "Poppins", sans-serif;
      font-size: 0.8rem;
      color: var(--color-grape-soda);
      cursor: pointer;
      margin-bottom: 1.5rem;
      padding: 0;
      text-decoration: underline;
      text-underline-offset: 2px;
    }

    .detail-block {
      display: none;
      background: #f8f5ff;
      border: 1px solid #d9cff0;
      border-radius: 8px;
      padding: 1rem;
      margin-bottom: 1.5rem;
      text-align: left;
      font-size: 0.78rem;
      color: var(--color-muted);
      word-break: break-word;
      line-height: 1.6;
    }

    .detail-block.open { display: block; }

    .actions {
      display: flex;
      gap: 0.75rem;
      justify-content: center;
      flex-wrap: wrap;
    }

    .btn {
      display: inline-flex;
      align-items: center;
      gap: 0.4rem;
      padding: 0.65rem 1.4rem;
      border-radius: 8px;
      font-family: "Poppins", sans-serif;
      font-size: 0.875rem;
      font-weight: 500;
      cursor: pointer;
      text-decoration: none;
      border: none;
      transition: var(--transition);
    }

    .btn-primary {
      background: var(--color-primary);
      color: #fff;
    }
    .btn-primary:hover { background: var(--color-primary-hover); transform: translateY(-1px); }

    .btn-ghost {
      background: transparent;
      color: var(--color-primary);
      border: 1.5px solid var(--color-border);
    }
    .btn-ghost:hover { border-color: var(--color-primary); background: rgba(111,63,181,0.05); }
  </style>
</head>
<body>

  <div class="error-card">

    <div class="svg-wrap">
      <svg viewBox="0 0 180 160" xmlns="http://www.w3.org/2000/svg" aria-hidden="true">

        <!-- Big gear -->
        <g class="gear-big">
          <circle cx="72" cy="72" r="22" fill="#eef1ef" stroke="#6f3fb5" stroke-width="3"/>
          <circle cx="72" cy="72" r="8"  fill="#ffffff" stroke="#6f3fb5" stroke-width="2"/>
          <!-- Teeth -->
          <rect x="69" y="44" width="6" height="10" rx="2" fill="#6f3fb5"/>
          <rect x="69" y="90" width="6" height="10" rx="2" fill="#6f3fb5"/>
          <rect x="44" y="69" width="10" height="6" rx="2" fill="#6f3fb5"/>
          <rect x="90" y="69" width="10" height="6" rx="2" fill="#6f3fb5"/>
          <rect x="52" y="51" width="6" height="10" rx="2" fill="#6f3fb5" transform="rotate(45 55 56)"/>
          <rect x="88" y="51" width="6" height="10" rx="2" fill="#6f3fb5" transform="rotate(-45 91 56)"/>
          <rect x="52" y="82" width="6" height="10" rx="2" fill="#6f3fb5" transform="rotate(-45 55 87)"/>
          <rect x="88" y="82" width="6" height="10" rx="2" fill="#6f3fb5" transform="rotate(45 91 87)"/>
        </g>

        <!-- Small gear -->
        <g class="gear-small">
          <circle cx="112" cy="88" r="14" fill="#eef1ef" stroke="#88498f" stroke-width="2.5"/>
          <circle cx="112" cy="88" r="5"  fill="#ffffff" stroke="#88498f" stroke-width="2"/>
          <!-- Teeth small -->
          <rect x="109.5" y="68" width="5" height="8" rx="1.5" fill="#88498f"/>
          <rect x="109.5" y="102" width="5" height="8" rx="1.5" fill="#88498f"/>
          <rect x="92" y="85.5" width="8" height="5" rx="1.5" fill="#88498f"/>
          <rect x="126" y="85.5" width="8" height="5" rx="1.5" fill="#88498f"/>
          <rect x="97" y="73" width="5" height="8" rx="1.5" fill="#88498f" transform="rotate(45 99.5 77)"/>
          <rect x="123" y="73" width="5" height="8" rx="1.5" fill="#88498f" transform="rotate(-45 125.5 77)"/>
          <rect x="97" y="96" width="5" height="8" rx="1.5" fill="#88498f" transform="rotate(-45 99.5 100)"/>
          <rect x="123" y="96" width="5" height="8" rx="1.5" fill="#88498f" transform="rotate(45 125.5 100)"/>
        </g>

        <!-- Warning triangle -->
        <g class="warn-triangle">
          <polygon points="34,138 18,155 50,155" fill="#991b1b" opacity="0.85"/>
          <text x="34" y="153" font-family="Poppins, sans-serif" font-size="12"
                font-weight="800" text-anchor="middle" fill="#fff">!</text>
        </g>

        <!-- Lightning bolt -->
        <g class="bolt">
          <polygon points="148,30 140,48 147,48 139,66 155,44 147,44 156,30"
                   fill="#6f3fb5" opacity="0.85"/>
        </g>

      </svg>
    </div>

    <div class="error-code">500</div>
    <h1 class="error-title">Erro interno do servidor</h1>
    <p class="error-msg">
      Algo correu mal da nossa parte.<br>
      A equipa foi notificada. Tente novamente em instantes.
    </p>

    <%-- Show exception details in development (remove/guard in production) --%>
    <% if (exception != null) { %>
      <button class="detail-toggle" onclick="this.nextElementSibling.classList.toggle('open')">
        &#128269; Ver detalhe do erro
      </button>
      <div class="detail-block">
        <strong><%= exception.getClass().getName() %></strong><br>
        <%= exception.getMessage() != null ? exception.getMessage() : "Sem mensagem disponível" %>
      </div>
    <% } %>

    <div class="actions">
      <a href="<%= request.getContextPath() %>/dashboard" class="btn btn-primary">
        &#8962; Dashboard
      </a>
      <a href="javascript:location.reload()" class="btn btn-ghost">
        &#8635; Tentar novamente
      </a>
    </div>

  </div>

</body>
</html>
