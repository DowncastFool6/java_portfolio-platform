<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="pt">

    <head>

        <meta charset="UTF-8">
        <title>CTRL+VAULT – Your Knowledge Under Control</title>
        
        <link rel="stylesheet" href="<%= request.getContextPath()%>/css/styles.css">

        <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">

    </head>


    <body class="landing-page">

        <header>

            <div class="logo">
                <span class="ctrl">CTRL</span>
                <span class="plus">+</span>
                <span class="vault">VAULT</span>
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

                    <a href="<%= request.getContextPath()%>/register" class="btn btn-create">Criar Conta</a>

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

        <section id="sobre" class="features">

            <h2>Quem Somos</h2>

            <div class="cards about-cards">

                <div class="card">
                    <p>
                        Somos uma equipa de estudantes apaixonados por tecnologia e desenvolvimento
                        de software. Este projeto foi desenvolvido no ambito da formacao EFA NS PRO - Programacao
                        Informatica, com o objetivo de aplicar na pratica os conhecimentos adquiridos durante o curso.
                    </p>
                </div>

                <div class="card">
                    <h3>A nossa equipa</h3>
                    <p>Alice Lombardi - Desenvolvedora Full Stack</p>
                    <p>Camila Rial - Product Owner</p>
                    <p>Vissolela Cundi - Scrum Master</p>
                </div>

                <div class="card">
                    <h3>Contribuicao</h3>
                    <p>
                        Cada membro contribuiu para diferentes areas do projeto, incluindo desenvolvimento backend,
                        interface do utilizador, planeamento do produto e gestao do processo de desenvolvimento.
                    </p>
                </div>

            </div>

            <h2>Sobre o Projeto</h2>

            <div class="cards about-cards">

                <div class="card">
                    <p>
                        O CTRL+VAULT e uma aplicacao web desenvolvida em Java, criada para armazenar
                        e organizar informacoes sensiveis de forma segura.
                    </p>
                </div>

                <div class="card">
                    <h3>O que a aplicacao permite</h3>
                    <p>Criem uma conta e facam login de forma segura.</p>
                    <p>Organizem projetos pessoais.</p>
                    <p>Guardem conteudos e informacoes importantes.</p>
                    <p>Gerenciem os seus dados de forma simples e estruturada.</p>
                </div>

                <div class="card">
                    <h3>Arquitetura</h3>
                    <p>
                        O sistema foi desenvolvido seguindo a arquitetura MVC (Model-View-Controller),
                        separando claramente a logica da aplicacao, a interface e o acesso aos dados.
                    </p>
                </div>

            </div>

            <h2>Tecnologias Utilizadas</h2>

            <div class="cards about-cards">

                <div class="card">
                    <p>
                        Durante o desenvolvimento do projeto utilizamos diversas tecnologias comuns
                        no desenvolvimento de aplicacoes web:
                    </p>
                </div>

                <div class="card">
                    <h3>Tecnologias</h3>
                    <p>Java</p>
                    <p>JSP / Servlets</p>
                    <p>MySQL</p>
                    <p>Apache Tomcat</p>
                    <p>Maven</p>
                    <p>BCrypt para seguranca das passwords</p>
                </div>

                <div class="card">
                    <h3>Resultado</h3>
                    <p>
                        Estas tecnologias permitiram criar uma aplicacao funcional,
                        segura e organizada.
                    </p>
                </div>

            </div>

            <h2>Nosso Objetivo</h2>

            <div class="cards about-cards">

                <div class="card">
                    <p>
                        O principal objetivo do CTRL+VAULT e demonstrar na pratica a construcao
                        de uma aplicacao web completa, aplicando conceitos importantes como:
                    </p>
                </div>

                <div class="card">
                    <h3>Conceitos aplicados</h3>
                    <p>Arquitetura MVC</p>
                    <p>Programacao orientada a objetos</p>
                    <p>Seguranca de dados</p>
                    <p>Organizacao em camadas (Controller, Service, Repository)</p>
                    <p>Integracao com base de dados</p>
                </div>

                <div class="card">
                    <h3>Aprendizagem</h3>
                    <p>
                        Este projeto representa nao apenas um exercicio tecnico, mas tambem uma
                        oportunidade de desenvolver competencias de trabalho em equipa, planeamento
                        de projeto e boas praticas de programacao.
                    </p>
                </div>

            </div>

            <h2>Contato</h2>

            <div class="cards about-cards">

                <div class="card-contato">
                    <h3>Perfis do GitHub</h3>
                    <p>
                        <a href="https://github.com/rialcamila" target="_blank" rel="noopener noreferrer">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 16 16" fill="currentColor" style="vertical-align: text-bottom;">
                                <path d="M8 0C3.58 0 0 3.58 0 8a8 8 0 0 0 5.47 7.59c.4.07.55-.17.55-.38 0-.19-.01-.82-.01-1.49-2.01.37-2.53-.49-2.69-.94-.09-.23-.48-.94-.82-1.13-.28-.15-.68-.52-.01-.53.63-.01 1.08.58 1.23.82.72 1.21 1.87.87 2.33.66.07-.52.28-.87.5-1.07-1.78-.2-3.64-.89-3.64-3.95 0-.87.31-1.59.82-2.15-.08-.2-.36-1.02.08-2.12 0 0 .67-.21 2.2.82a7.5 7.5 0 0 1 4 0c1.53-1.04 2.2-.82 2.2-.82.44 1.1.16 1.92.08 2.12.51.56.82 1.27.82 2.15 0 3.07-1.87 3.75-3.65 3.95.29.25.54.73.54 1.48 0 1.07-.01 1.93-.01 2.2 0 .21.15.46.55.38A8 8 0 0 0 16 8c0-4.42-3.58-8-8-8Z"/>
                            </svg>
                            rialcamila
                        </a>
                    </p>
                    <p>
                        <a href="https://github.com/DowncastFool6" target="_blank" rel="noopener noreferrer">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 16 16" fill="currentColor" style="vertical-align: text-bottom;">
                                <path d="M8 0C3.58 0 0 3.58 0 8a8 8 0 0 0 5.47 7.59c.4.07.55-.17.55-.38 0-.19-.01-.82-.01-1.49-2.01.37-2.53-.49-2.69-.94-.09-.23-.48-.94-.82-1.13-.28-.15-.68-.52-.01-.53.63-.01 1.08.58 1.23.82.72 1.21 1.87.87 2.33.66.07-.52.28-.87.5-1.07-1.78-.2-3.64-.89-3.64-3.95 0-.87.31-1.59.82-2.15-.08-.2-.36-1.02.08-2.12 0 0 .67-.21 2.2.82a7.5 7.5 0 0 1 4 0c1.53-1.04 2.2-.82 2.2-.82.44 1.1.16 1.92.08 2.12.51.56.82 1.27.82 2.15 0 3.07-1.87 3.75-3.65 3.95.29.25.54.73.54 1.48 0 1.07-.01 1.93-.01 2.2 0 .21.15.46.55.38A8 8 0 0 0 16 8c0-4.42-3.58-8-8-8Z"/>
                            </svg>
                            DowncastFool6
                        </a>
                    </p>
                    <p>
                        <a href="https://github.com/aliceslombardi" target="_blank" rel="noopener noreferrer">
                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 16 16" fill="currentColor" style="vertical-align: text-bottom;">
                                <path d="M8 0C3.58 0 0 3.58 0 8a8 8 0 0 0 5.47 7.59c.4.07.55-.17.55-.38 0-.19-.01-.82-.01-1.49-2.01.37-2.53-.49-2.69-.94-.09-.23-.48-.94-.82-1.13-.28-.15-.68-.52-.01-.53.63-.01 1.08.58 1.23.82.72 1.21 1.87.87 2.33.66.07-.52.28-.87.5-1.07-1.78-.2-3.64-.89-3.64-3.95 0-.87.31-1.59.82-2.15-.08-.2-.36-1.02.08-2.12 0 0 .67-.21 2.2.82a7.5 7.5 0 0 1 4 0c1.53-1.04 2.2-.82 2.2-.82.44 1.1.16 1.92.08 2.12.51.56.82 1.27.82 2.15 0 3.07-1.87 3.75-3.65 3.95.29.25.54.73.54 1.48 0 1.07-.01 1.93-.01 2.2 0 .21.15.46.55.38A8 8 0 0 0 16 8c0-4.42-3.58-8-8-8Z"/>
                            </svg>
                            aliceslombardi
                        </a>
                    </p>
                    <p>
                        Acompanhe o nosso trabalho e saiba mais sobre o desenvolvimento do projeto.
                    </p>
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
