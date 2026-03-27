<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="pt">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>403 — Acesso negado · ctrl-vault</title>
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

    body::before {
      content: "";
      position: fixed;
      inset: 0;
      background:
        radial-gradient(ellipse 55% 50% at 15% 25%, rgba(153,27,27,0.07) 0%, transparent 70%),
        radial-gradient(ellipse 50% 55% at 85% 75%, rgba(111,63,181,0.08) 0%, transparent 70%);
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

    /* Lock shake animation */
    @keyframes lockShake {
      0%, 100% { transform: translateX(0) rotate(0deg); }
      10%       { transform: translateX(-4px) rotate(-3deg); }
      20%       { transform: translateX(4px) rotate(3deg); }
      30%       { transform: translateX(-4px) rotate(-2deg); }
      40%       { transform: translateX(4px) rotate(2deg); }
      50%       { transform: translateX(-2px) rotate(-1deg); }
      60%       { transform: translateX(2px) rotate(1deg); }
      70%       { transform: translateX(0); }
    }

    /* Red pulse ring */
    @keyframes pulseRing {
      0%   { r: 36; opacity: 0.5; }
      70%  { r: 52; opacity: 0; }
      100% { r: 52; opacity: 0; }
    }

    /* Exclamation blink */
    @keyframes excBlink {
      0%, 100% { opacity: 1; }
      50%       { opacity: 0.3; }
    }

    .lock-group {
      transform-origin: 90px 80px;
      animation: lockShake 2.8s ease-in-out infinite;
      animation-delay: 0.5s;
    }

    .pulse-ring {
      transform-origin: 90px 82px;
      animation: pulseRing 2s ease-out infinite;
    }

    .exclaim { animation: excBlink 1.6s ease-in-out infinite; }

    .error-code {
      font-size: 5rem;
      font-weight: 800;
      line-height: 1;
      background: linear-gradient(135deg, #991b1b 0%, var(--color-grape-soda) 100%);
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

        <!-- Pulse ring -->
        <circle class="pulse-ring" cx="90" cy="82" r="36" fill="none"
                stroke="#991b1b" stroke-width="2" opacity="0"/>

        <!-- Lock group -->
        <g class="lock-group">
          <!-- Shackle (arc) -->
          <path d="M68 82 L68 65 Q68 48 90 48 Q112 48 112 65 L112 82"
                fill="none" stroke="#6f3fb5" stroke-width="5"
                stroke-linecap="round" stroke-linejoin="round"/>
          <!-- Lock body -->
          <rect x="58" y="80" width="64" height="50" rx="8" ry="8"
                fill="#6f3fb5"/>
          <!-- Keyhole outer -->
          <circle cx="90" cy="100" r="8" fill="#eef1ef"/>
          <!-- Keyhole slot -->
          <rect x="87" y="100" width="6" height="12" rx="2" fill="#eef1ef"/>
        </g>

        <!-- Exclamation badge -->
        <circle cx="130" cy="48" r="16" fill="#991b1b" class="exclaim"/>
        <text x="130" y="54" font-family="Poppins, sans-serif" font-size="18"
              font-weight="800" text-anchor="middle" fill="#fff" class="exclaim">!</text>
      </svg>
    </div>

    <div class="error-code">403</div>
    <h1 class="error-title">Acesso negado</h1>
    <p class="error-msg">
      Não tem permissão para aceder a este recurso.<br>
      Contacte o administrador se acreditar que isto é um engano.
    </p>

    <div class="actions">
      <a href="<%= request.getContextPath() %>/dashboard" class="btn btn-primary">
        &#8962; Dashboard
      </a>
      <a href="<%= request.getContextPath() %>/login" class="btn btn-ghost">
        &#128100; Entrar com outra conta
      </a>
    </div>

  </div>

</body>
</html>
