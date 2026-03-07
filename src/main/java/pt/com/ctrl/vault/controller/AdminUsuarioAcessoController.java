package pt.com.ctrl.vault.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pt.com.ctrl.vault.exception.CampoObrigatorioException;
import pt.com.ctrl.vault.exception.UsuarioNaoEncontradoException;
import pt.com.ctrl.vault.model.Projeto;
import pt.com.ctrl.vault.model.TipoUsuario;
import pt.com.ctrl.vault.model.Usuario;
import pt.com.ctrl.vault.service.UsuarioService;
import pt.com.ctrl.vault.util.ServletUtil;

public class AdminUsuarioAcessoController extends HttpServlet {

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

        try {
            Integer idUsuario = parseInt(req.getParameter("id"));
            carregarFormulario(req, idUsuario, null, null);
            req.getRequestDispatcher("/WEB-INF/admin/user-access.jsp").forward(req, resp);
        } catch (CampoObrigatorioException | UsuarioNaoEncontradoException e) {
            resp.sendRedirect(req.getContextPath() + "/admin/usuarios");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Usuario usuarioLogado = ServletUtil.obterUsuarioLogado(req, resp);
        if (usuarioLogado == null) {
            return;
        }

        if (!ServletUtil.usuarioEhAdmin(usuarioLogado)) {
            resp.sendRedirect(req.getContextPath() + "/dashboard");
            return;
        }

        Integer idUsuario = parseInt(req.getParameter("idUsuario"));
        Integer idProjeto = parseInt(req.getParameter("idProjeto"));
        Integer idTipoUsuario = parseInt(req.getParameter("idTipoUsuario"));

        UsuarioService usuarioService = new UsuarioService();

        try {
            usuarioService.atualizarAcessoUsuario(idUsuario, idProjeto, idTipoUsuario);
            resp.sendRedirect(req.getContextPath() + "/admin/usuarios?sucesso=true");
        } catch (CampoObrigatorioException | UsuarioNaoEncontradoException e) {
            ServletUtil.addErro(req, e.getMessage());
            carregarFormulario(req, idUsuario, idProjeto, idTipoUsuario);
            req.getRequestDispatcher("/WEB-INF/admin/user-access.jsp").forward(req, resp);
        }
    }

    private void carregarFormulario(HttpServletRequest req, Integer idUsuario, Integer idProjetoSelecionado,
            Integer idTipoSelecionado) throws ServletException {
        UsuarioService usuarioService = new UsuarioService();
        Usuario usuario = usuarioService.buscarPorId(idUsuario);
        Projeto projetoAtual = usuarioService.buscarProjetoDoUsuario(idUsuario);
        List<Projeto> projetos = usuarioService.listarProjetos();
        List<TipoUsuario> tiposUsuario = usuarioService.listarTiposUsuario();

        Integer projetoSelecionadoId = idProjetoSelecionado;
        if (projetoSelecionadoId == null && projetoAtual != null) {
            projetoSelecionadoId = projetoAtual.getId();
        }

        Integer tipoSelecionadoId = idTipoSelecionado;
        if (tipoSelecionadoId == null && usuario.getTipoUsuario() != null) {
            tipoSelecionadoId = usuario.getTipoUsuario().getId();
        }

        req.setAttribute("usuarioSelecionado", usuario);
        req.setAttribute("projetoAtual", projetoAtual);
        req.setAttribute("projetos", projetos);
        req.setAttribute("tiposUsuario", tiposUsuario);
        req.setAttribute("projetoSelecionadoId", projetoSelecionadoId);
        req.setAttribute("tipoSelecionadoId", tipoSelecionadoId);
    }

    private Integer parseInt(String valor) {
        try {
            return valor == null || valor.isBlank() ? null : Integer.valueOf(valor);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
