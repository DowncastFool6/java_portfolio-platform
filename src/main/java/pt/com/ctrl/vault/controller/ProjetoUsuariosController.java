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
import pt.com.ctrl.vault.service.ConteudoService;
import pt.com.ctrl.vault.service.UsuarioService;
import pt.com.ctrl.vault.util.ServletUtil;

public class ProjetoUsuariosController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Usuario usuarioLogado = ServletUtil.obterUsuarioLogado(req, resp);
        if (usuarioLogado == null) {
            return;
        }

        Integer idProjeto = parseInt(req.getParameter("idProjeto"));

        try {
            validarAcessoGestao(usuarioLogado, idProjeto);
            carregarPagina(req, usuarioLogado, idProjeto);

            if ("status-atualizado".equals(req.getParameter("sucesso"))) {
                ServletUtil.addSucesso(req, "Status do utilizador atualizado.");
            }

            req.getRequestDispatcher("/WEB-INF/project/project-users.jsp").forward(req, resp);
        } catch (CampoObrigatorioException e) {
            resp.sendRedirect(req.getContextPath() + "/projeto?id=" + idProjeto);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Usuario usuarioLogado = ServletUtil.obterUsuarioLogado(req, resp);
        if (usuarioLogado == null) {
            return;
        }

        Integer idProjeto = parseInt(req.getParameter("idProjeto"));
        Integer idUsuario = parseInt(req.getParameter("idUsuario"));
        boolean ativo = "ativar".equalsIgnoreCase(req.getParameter("acao"));

        try {
            validarAcessoGestao(usuarioLogado, idProjeto);
            UsuarioService usuarioService = new UsuarioService();
            usuarioService.atualizarStatusUsuarioNoProjeto(idProjeto, idUsuario, ativo);
            resp.sendRedirect(req.getContextPath() + "/projeto/usuarios?idProjeto=" + idProjeto + "&sucesso=status-atualizado");
        } catch (CampoObrigatorioException e) {
            ServletUtil.addErro(req, e.getMessage());
            carregarPagina(req, usuarioLogado, idProjeto);
            req.getRequestDispatcher("/WEB-INF/project/project-users.jsp").forward(req, resp);
        }
    }

    private void carregarPagina(HttpServletRequest req, Usuario usuarioLogado, Integer idProjeto) {
        ConteudoService conteudoService = new ConteudoService();
        Projeto projeto = conteudoService.buscarProjetoDoUsuario(usuarioLogado, idProjeto);
        UsuarioService usuarioService = new UsuarioService();
        List<Usuario> usuariosProjeto = usuarioService.listarUsuariosDoProjeto(idProjeto);

        req.setAttribute("projeto", projeto);
        req.setAttribute("usuariosProjeto", usuariosProjeto);
        req.setAttribute("usuario", usuarioLogado);
        ServletUtil.prepararHeader(req, usuarioLogado);
    }

    private void validarAcessoGestao(Usuario usuarioLogado, Integer idProjeto) {
        if (!ServletUtil.usuarioPodeGerirProjeto(usuarioLogado)) {
            throw new CampoObrigatorioException("Apenas gestores ativos podem gerir utilizadores do projeto.");
        }

        ConteudoService conteudoService = new ConteudoService();
        conteudoService.buscarProjetoDoUsuario(usuarioLogado, idProjeto);
    }

    private Integer parseInt(String valor) {
        try {
            return valor == null || valor.isBlank() ? null : Integer.valueOf(valor);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
