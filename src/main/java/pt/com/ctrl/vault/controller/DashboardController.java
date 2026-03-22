package pt.com.ctrl.vault.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pt.com.ctrl.vault.model.Projeto;
import pt.com.ctrl.vault.model.Usuario;
import pt.com.ctrl.vault.service.ProjetoService;
import pt.com.ctrl.vault.util.ServletUtil;

/**
 * Classe com logica apresentacao do dashboard do usuario
 * @author aliceslombardi, CamilaRial, VissolelaCundi
 * @since 01/03/2026
 */
public class DashboardController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Usuario usuarioLogado = ServletUtil.obterUsuarioLogado(req, resp);
        if (usuarioLogado == null) {
            return;
        }

        ProjetoService projetoService = new ProjetoService();
        Projeto projetoUsuario = projetoService.buscarProjetoDoUsuario(usuarioLogado.getId());
        List<Projeto> projetosUsuario = projetoService.listarProjetosDoUsuario(usuarioLogado.getId());

        req.setAttribute("usuario", usuarioLogado);
        req.setAttribute("isAdmin", ServletUtil.usuarioEhAdmin(usuarioLogado));
        req.setAttribute("isGestor", ServletUtil.usuarioEhGestor(usuarioLogado));
        req.setAttribute("isUsuarioAtivo", ServletUtil.usuarioEstaAtivo(usuarioLogado));
        req.setAttribute("projetoUsuario", projetoUsuario);
        req.setAttribute("projetosUsuario", projetosUsuario);
        req.setAttribute("mostrarBotaoHome", false);
        req.setAttribute("mostrarBotaoVoltar", false);
        ServletUtil.prepararHeader(req, usuarioLogado);

        Object mensagemDashboard = req.getSession().getAttribute("mensagemDashboard");
        if (mensagemDashboard != null) {
            ServletUtil.addSucesso(req, String.valueOf(mensagemDashboard));
            req.getSession().removeAttribute("mensagemDashboard");
        }

        if (projetoUsuario == null) {
            ServletUtil.addErro(req, "Seu utilizador nao tem projetos associados. Entre em contacto com o administrador.");
        }

        req.getRequestDispatcher("/WEB-INF/dashboard/dashboard.jsp")
           .forward(req, resp);
    }
}
