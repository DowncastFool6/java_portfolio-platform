package pt.com.ctrl.vault.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pt.com.ctrl.vault.exception.CampoObrigatorioException;
import pt.com.ctrl.vault.model.Projeto;
import pt.com.ctrl.vault.model.Usuario;
import pt.com.ctrl.vault.service.ContatoService;
import pt.com.ctrl.vault.service.ProjetoService;
import pt.com.ctrl.vault.util.ServletUtil;

/**
 * Controller para envio de contatos associados ao projeto do utilizador
 * @author aliceslombardi, CamilaRial, VissolelaCundi
 * @since 08/03/2026
 */
public class ContatoController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Usuario usuarioLogado = ServletUtil.obterUsuarioLogado(req, resp);
        if (usuarioLogado == null) {
            return;
        }

        if (!ServletUtil.usuarioEstaAtivo(usuarioLogado)) {
            req.getSession().setAttribute("mensagemDashboard", "O seu utilizador esta inativo e apenas pode consultar informacao.");
            resp.sendRedirect(req.getContextPath() + "/dashboard");
            return;
        }

        carregarPagina(req, usuarioLogado);

        if ("true".equals(req.getParameter("sucesso"))) {
            ServletUtil.addSucesso(req, "Contato enviado com sucesso.");
        }

        req.getRequestDispatcher("/WEB-INF/contact/contact.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	ServletUtil.configurarUtf8(req, resp);
        Usuario usuarioLogado = ServletUtil.obterUsuarioLogado(req, resp);
        if (usuarioLogado == null) {
            return;
        }

        Integer idProjeto = parseInt(req.getParameter("idProjeto"));
        String mensagem = req.getParameter("mensagem");

        try {
            ContatoService contatoService = new ContatoService();
            contatoService.enviarContato(usuarioLogado, idProjeto, mensagem);
            resp.sendRedirect(req.getContextPath() + "/contatos?sucesso=true");
        } catch (CampoObrigatorioException e) {
            ServletUtil.addErro(req, e.getMessage());
            req.setAttribute("idProjetoSelecionado", idProjeto);
            req.setAttribute("mensagemContato", mensagem);
            carregarPagina(req, usuarioLogado);
            req.getRequestDispatcher("/WEB-INF/contact/contact.jsp").forward(req, resp);
        }
    }

    private void carregarPagina(HttpServletRequest req, Usuario usuarioLogado) {
        ProjetoService projetoService = new ProjetoService();
        List<Projeto> projetos = projetoService.listarProjetos();

        req.setAttribute("usuario", usuarioLogado);
        req.setAttribute("isAdmin", ServletUtil.usuarioEhAdmin(usuarioLogado));
        req.setAttribute("isUsuarioAtivo", ServletUtil.usuarioEstaAtivo(usuarioLogado));
        req.setAttribute("projetos", projetos);
        ServletUtil.prepararHeader(req, usuarioLogado);

        if (projetos == null || projetos.isEmpty()) {
            ServletUtil.addErro(req, "Nao existem projetos disponiveis para contato.");
        }
    }

    private Integer parseInt(String valor) {
        try {
            return valor == null || valor.isBlank() ? null : Integer.valueOf(valor);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
}
