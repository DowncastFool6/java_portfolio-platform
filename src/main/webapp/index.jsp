<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="pt">

    <head>

        <meta charset="UTF-8">
        <title>CTRL+VAULT – Your Knowledge Under Control</title>

        <link rel="stylesheet" href="<%= request.getContextPath()%>/css/base.css">
        <link rel="stylesheet" href="<%= request.getContextPath()%>/css/layout.css">
        <link rel="stylesheet" href="<%= request.getContextPath()%>/css/components.css">
        <link rel="stylesheet" href="<%= request.getContextPath()%>/css/pages.css">

        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">

    </head>


    <body>

        <header>

            <div class="logo">
                CTRL<span class="plus">+</span>VAULT
            </div>

            <nav>
                <a href="#">Home</a>
                <a href="#features">Funcionalidades</a>
                <a href="#sobre">Sobre</a>
                <a href="<%= request.getContextPath()%>/login">Login</a>
            </nav>

        </header>



        <section class="hero">

            <div class="hero-text">

                <h1>Seu conhecimento sob controle</h1>

                <p>
                    Uma aplicação web segura para armazenar,
                    organizar e gerir credenciais, documentos
                    e dados confidenciais.
                </p>

                <div class="btn-group">

                    <a href="<%= request.getContextPath()%>/register" class="btn-primary">
                        Criar Conta
                    </a>

                </div>

            </div>


            <img src="<%= request.getContextPath()%>/images/logo.png"
                 class="hero-img">

        </section>



        <section id="features" class="features">

            <h2>Funcionalidades</h2>

            <div class="cards">

                <div class="card">
                    <h3>Autenticação Segura</h3>
                    <p>Registo, login e gestão de sessão protegida.</p>
                </div>

                <div class="card">
                    <h3>Armazenamento Seguro</h3>
                    <p>Credenciais protegidas com encriptação.</p>
                </div>

                <div class="card">
                    <h3>Gestão de Dados</h3>
                    <p>CRUD completo para gerir informações sensíveis.</p>
                </div>

            </div>

        </section>



        <footer>

            <p>© 2026 CTRL+VAULT – Academic Project</p>

            <p>
                Projeto desenvolvido por  
                Camila Rial • Alice Lombardi • Visselola
            </p>

        </footer>

    </body>
</html>
