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
 * Controller para listagem de projetos do utilizador.
 * @author aliceslombardi, CamilaRial, VissolelaCundi
 */
public class ProjetosController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Usuario usuarioLogado = ServletUtil.obterUsuarioLogado(req, resp);
        if (usuarioLogado == null) {
            return;
        }

        ProjetoService projetoService = new ProjetoService();
        List<Projeto> projetos = projetoService.listarProjetosDoUsuario(usuarioLogado.getId());

        req.setAttribute("projetos", projetos);
        req.setAttribute("usuario", usuarioLogado);
        ServletUtil.prepararHeader(req, usuarioLogado);
        req.getRequestDispatcher("/WEB-INF/project/projects.jsp").forward(req, resp);
    }
}
