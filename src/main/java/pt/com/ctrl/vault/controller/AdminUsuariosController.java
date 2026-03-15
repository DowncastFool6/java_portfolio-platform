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

public class AdminUsuariosController extends HttpServlet {

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
        List<Usuario> usuariosPendentes = usuarioService.listarUsuariosSemAcesso();

        if ("true".equals(req.getParameter("sucesso"))) {
            ServletUtil.addSucesso(req, "Acesso do utilizador atualizado com sucesso.");
        }

        req.setAttribute("usuario", usuarioLogado);
        req.setAttribute("usuariosPendentes", usuariosPendentes);
        ServletUtil.prepararHeader(req, usuarioLogado);
        req.getRequestDispatcher("/WEB-INF/admin/users.jsp").forward(req, resp);
    }
}
