<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="pt">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>404 — Página não encontrada · ctrl-vault</title>
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
      --color-danger:     #991b1b;
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

    /* ── Soft radial gradient backdrop ── */
    body::before {
      content: "";
      position: fixed;
      inset: 0;
      background:
        radial-gradient(ellipse 60% 50% at 20% 30%, rgba(111,63,181,0.10) 0%, transparent 70%),
        radial-gradient(ellipse 50% 60% at 80% 70%, rgba(136,73,143,0.08) 0%, transparent 70%);
      pointer-events: none;
    }

    /* ── Card ── */
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

    /* ── SVG illustration ── */
    .svg-wrap {
      margin: 0 auto 1.75rem;
      width: 180px;
      height: 160px;
    }

    /* Vault door spin */
    @keyframes vaultSpin {
      0%   { transform: rotate(0deg); }
      15%  { transform: rotate(-15deg); }
      30%  { transform: rotate(8deg); }
      50%  { transform: rotate(-8deg); }
      65%  { transform: rotate(5deg); }
      80%  { transform: rotate(-3deg); }
      100% { transform: rotate(0deg); }
    }

    /* Floating key */
    @keyframes floatKey {
      0%, 100% { transform: translateY(0) rotate(-10deg); }
      50%       { transform: translateY(-8px) rotate(10deg); }
    }

    /* Question marks pulse */
    @keyframes qPop {
      0%, 100% { opacity: 0.3; transform: scale(0.85); }
      50%       { opacity: 0.9; transform: scale(1.1); }
    }

    .vault-wheel {
      transform-origin: 90px 75px;
      animation: vaultSpin 3.5s ease-in-out infinite;
    }

    .key-group {
      transform-origin: 50px 120px;
      animation: floatKey 2.4s ease-in-out infinite;
    }

    .q1 { animation: qPop 2s ease-in-out infinite; }
    .q2 { animation: qPop 2s ease-in-out 0.4s infinite; }
    .q3 { animation: qPop 2s ease-in-out 0.8s infinite; }

    /* ── Code number ── */
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

    /* ── Actions ── */
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

    <!-- Animated SVG -->
    <div class="svg-wrap">
      <svg viewBox="0 0 180 160" xmlns="http://www.w3.org/2000/svg" aria-hidden="true">
        <!-- Vault body -->
        <rect x="30" y="30" width="120" height="100" rx="10" ry="10"
              fill="#ffffff" stroke="#e4e4e7" stroke-width="2.5"/>
        <!-- Vault door shading -->
        <rect x="40" y="40" width="100" height="80" rx="8" ry="8"
              fill="#eef1ef" stroke="#d0d4d1" stroke-width="1.5"/>

        <!-- Spinning wheel group -->
        <g class="vault-wheel">
          <!-- Outer ring -->
          <circle cx="90" cy="75" r="22" fill="none" stroke="#6f3fb5" stroke-width="3"/>
          <!-- Inner dot -->
          <circle cx="90" cy="75" r="5" fill="#6f3fb5"/>
          <!-- Spokes -->
          <line x1="90" y1="53" x2="90" y2="59" stroke="#6f3fb5" stroke-width="3" stroke-linecap="round"/>
          <line x1="90" y1="91" x2="90" y2="97" stroke="#6f3fb5" stroke-width="3" stroke-linecap="round"/>
          <line x1="68" y1="75" x2="74" y2="75" stroke="#6f3fb5" stroke-width="3" stroke-linecap="round"/>
          <line x1="106" y1="75" x2="112" y2="75" stroke="#6f3fb5" stroke-width="3" stroke-linecap="round"/>
          <line x1="74" y1="59" x2="78" y2="63" stroke="#6f3fb5" stroke-width="3" stroke-linecap="round"/>
          <line x1="102" y1="87" x2="106" y2="91" stroke="#6f3fb5" stroke-width="3" stroke-linecap="round"/>
          <line x1="106" y1="59" x2="102" y2="63" stroke="#6f3fb5" stroke-width="3" stroke-linecap="round"/>
          <line x1="78" y1="87" x2="74" y2="91" stroke="#6f3fb5" stroke-width="3" stroke-linecap="round"/>
        </g>

        <!-- Handle bar -->
        <rect x="115" y="72" width="18" height="6" rx="3" fill="#88498f"/>

        <!-- Floating key -->
        <g class="key-group">
          <!-- Key head -->
          <circle cx="50" cy="118" r="8" fill="none" stroke="#88498f" stroke-width="2.5"/>
          <circle cx="50" cy="118" r="3.5" fill="#88498f"/>
          <!-- Key shaft -->
          <line x1="56" y1="118" x2="75" y2="118" stroke="#88498f" stroke-width="2.5" stroke-linecap="round"/>
          <!-- Key teeth -->
          <line x1="65" y1="118" x2="65" y2="124" stroke="#88498f" stroke-width="2.5" stroke-linecap="round"/>
          <line x1="71" y1="118" x2="71" y2="122" stroke="#88498f" stroke-width="2.5" stroke-linecap="round"/>
        </g>

        <!-- Question marks -->
        <text class="q1" x="22" y="50" font-family="Poppins, sans-serif" font-size="18"
              font-weight="700" fill="#6f3fb5" opacity="0.4">?</text>
        <text class="q2" x="148" y="45" font-family="Poppins, sans-serif" font-size="14"
              font-weight="700" fill="#88498f" opacity="0.4">?</text>
        <text class="q3" x="155" y="110" font-family="Poppins, sans-serif" font-size="20"
              font-weight="700" fill="#6f3fb5" opacity="0.35">?</text>
      </svg>
    </div>

    <div class="error-code">404</div>
    <h1 class="error-title">Página não encontrada</h1>
    <p class="error-msg">
      O recurso que procura não existe ou foi removido.<br>
      Verifique o endereço ou regresse ao início.
    </p>

    <div class="actions">
      <a href="<%= request.getContextPath() %>/dashboard" class="btn btn-primary">
        &#8962; Dashboard
      </a>
      <a href="javascript:history.back()" class="btn btn-ghost">
        &#8592; Voltar
      </a>
    </div>

  </div>

</body>
</html>
