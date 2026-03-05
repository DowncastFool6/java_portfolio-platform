- ⚠️ PROJETO PRIVADO – NÃO UTILIZAR

> **Este projeto não é destinado a uso público ou distribuição. A utilização não autorizada é estritamente proibida.**

---

# 📌 Visão Geral do Projeto

**Ctrl Vault** 🔐 é um projeto **fullstack baseado em Java**, desenvolvido para gerir dados sensíveis de forma segura, fornecendo uma interface robusta, escalável e fácil de utilizar para armazenamento e recuperação de informação. O projeto enfatiza **segurança**, **acessibilidade** e **manutenibilidade**.

---

# 🗂️ Âmbito do Projeto

**Included:**
- Armazenamento e recuperação segura de dados
- Autenticação de utilizadores e gestão de papéis (roles)
- Implementação fullstack em Java (Frontend + Backend)
- Interface web com design responsivo 

**Excluído:**
- Integrações com APIs públicas de terceiros 
- Implementação de aplicação móvel
- Acesso de utilizadores externos além dos colaboradores autorizados 

---

# 💻 Tecnologias Utilizadas

- **Backend:** Java, Spring Boot ☕  
- **Frontend:** JSP, HTML, CSS, JavaScript 🌐  
- **Base de Dados:** SQL Server 🗄️  
- **Controlo de Versão:** Git + GitHub 🐙  
- **Ferramenta de Build:** Maven ⚙️  
- **Metodologias:** Scrum + Kanban 📋  

---

# 🏗️ Arquitetura do Sistema

- **Frontend:** Interface de utilizador para operações CRUD e visualização segura de dados  
- **Backend:** Lógica de negócio, autenticação e endpoints de API  
- **Base de Dados:** Armazenamento relacional de dados com controlos de acesso seguros 

---

# 🛠️ Metodologia

- **Híbrido Scrum + Kanban:**  
  - Desenvolvimento iterativo baseado em sprints ⏱️  
  - Priorização de tarefas e acompanhamento do fluxo de trabalho ✅  
- **Tipo de programação:** Desenvolvimento Fullstack em Java ☕🌐 
- **Alocação de recursos:** Cada colaborador é responsável por módulos específicos de acordo com a sua especialização 🧑‍💻  

---

# 🎯 Funcionalidades Mínimas

1. 🔑 Login e logout seguro de utilizadores
2. ➕ Adicionar, ✏️ Editar, 🗑️ Eliminar e 👁️ Visualizar registos
3. 🛡️ Controlo de acesso baseado em papéis (roles)
4. 🗄️ Integração com base de dados SQL Server
5. 📱 Interface responsiva e intuitiva
6. 📜 Registo básico (logging) e auditoria das ações dos utilizadores 

---

# 👥 Colaboradores

- ALICE SANTOS LOMBARDI              https://github.com/aliceslombardi
- CAMILA PEREIRA RIAL                https://github.com/rialcamila
- VISSOLELA EMANUELA MARTINS CUNDI   https://github.com/DowncastFool6

---

# 📜 Licença

Este projeto é proprietário. Consulte o ficheiro `LICENSE` para os direitos completos e termos de utilização.

© 2026 Ctrl Vault – Todos os Direitos Reservados

---

# 📁 Estrutura do Projeto

```text
Crtl-Vault/
│
├─ src/
│  └─ main/
│     ├─ java/
│     │  └─ pt/com/ctrl/vault/
│     │     ├─ controller/
│     │     │  ├─ DashboardController.java
│     │     │  ├─ LoginController.java
│     │     │  ├─ LogoutController.java
│     │     │  └─ RegisterController.java
│     │     │
│     │     ├─ exception/
│     │     │  ├─ CampoObrigatorioException.java
│     │     │  ├─ EmailJaRegistadoException.java
│     │     │  ├─ SenhaInvalidaException.java
│     │     │  └─ UsuarioNaoEncontradoException.java
│     │     │
│     │     ├─ model/
│     │     │  ├─ Contato.java
│     │     │  ├─ Conteudo.java
│     │     │  ├─ Projeto.java
│     │     │  ├─ TipoUsuario.java
│     │     │  ├─ Usuario.java
│     │     │  └─ UsuarioProjeto.java
│     │     │
│     │     ├─ repository/
│     │     │  └─ UsuarioRepository.java
│     │     │
│     │     ├─ service/
│     │     │  ├─ BCrypt.java
│     │     │  └─ UsuarioService.java
│     │     │
│     │     └─ util/
│     │        ├─ ConnectionFactory.java
│     │        └─ ServletUtil.java
│     │
│     └─ webapp/
│        ├─ css/
│        │  └─ style.css
│        ├─ images/
│        │  └─ logo.png
│        ├─ META-INF/
│        │  └─ context.xml
│        └─ WEB-INF/
│           ├─ applicationContext.xml
│           ├─ dispatcher-servlet.xml
│           ├─ web.xml
│           │
│           ├─ dashboard/
│           │  └─ dashboard.jsp
│           │
│           ├─ login/
│           │  └─ login.jsp
│           │
│           └─ register/
│              └─ register.jsp
│
├─ target/
├─ .gitignore
├─ LICENSE.txt
├─ nb-configuration.xml
├─ pom.xml
└─ README.md