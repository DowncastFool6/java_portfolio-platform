package pt.com.ctrl.vault.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pt.com.ctrl.vault.model.Usuario;
import pt.com.ctrl.vault.service.UsuarioService;
import pt.com.ctrl.vault.util.ServletUtil;

/**
 * Controller para administracao da lista de utilizadores.
 * @author aliceslombardi, CamilaRial, VissolelaCundi
 */
public class AdminUsuariosController extends HttpServlet {

	/**
	 * Carrega a lista de usuarios sem um projeto associado
	 */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Usuario usuarioLogado = ServletUtil.obterUsuarioLogado(req, resp);
        if (usuarioLogado == null) {
            return;
        }

        if (!ServletUtil.usuarioEhAdmin(usuarioLogado)) {
            resp.sendRedirect(req.getContextPath() + "/dashboard");
            return;
        }

        UsuarioService usuarioService = new UsuarioService();
        List<Usuario> usuariosSemProjetoAssociado = usuarioService.listarUsuariosSemAcesso();

        if ("true".equals(req.getParameter("sucesso"))) {
            ServletUtil.addSucesso(req, "Acesso do utilizador atualizado com sucesso.");
        }

        req.setAttribute("usuario", usuarioLogado);
        req.setAttribute("usuariosPendentes", usuariosSemProjetoAssociado);
        ServletUtil.prepararSidePanel(req, usuarioLogado);
        req.getRequestDispatcher("/WEB-INF/admin/users.jsp").forward(req, resp);
    }
}
