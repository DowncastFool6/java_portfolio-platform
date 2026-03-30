package pt.com.ctrl.vault.controller;

import java.io.IOException;
import java.util.ArrayList;
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
import pt.com.ctrl.vault.service.ProjetoService;
import pt.com.ctrl.vault.service.UsuarioService;
import pt.com.ctrl.vault.util.ServletUtil;

/**
 * Controller para administracao de acessos de utilizadores.
 * @author aliceslombardi, CamilaRial, VissolelaCundi
 */
public class AdminUsuarioAcessoController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Usuario usuarioLogado = ServletUtil.obterUsuarioLogado(req, resp);
        if (usuarioLogado == null) {
        	req.getRequestDispatcher("/WEB-INF/error/error-401.jsp").forward(req, resp);
        	return;
        }

        if (!ServletUtil.isUsuarioAdmin(usuarioLogado)) {
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
        	req.getRequestDispatcher("/WEB-INF/error/error-401.jsp").forward(req, resp);
        	return;
        }

        if (!ServletUtil.isUsuarioAdmin(usuarioLogado)) {
            resp.sendRedirect(req.getContextPath() + "/dashboard");
            return;
        }

        Integer idUsuario = parseInt(req.getParameter("idUsuario"));
        List<Integer> idsProjetos = parseIntList(req.getParameterValues("idProjeto"));
        Integer idTipoUsuario = parseInt(req.getParameter("idTipoUsuario"));

        UsuarioService usuarioService = new UsuarioService();

        try {
            usuarioService.atualizarAcessoUsuario(idUsuario, idsProjetos, idTipoUsuario);
            resp.sendRedirect(req.getContextPath() + "/admin/usuarios?sucesso=true");
        } catch (CampoObrigatorioException | UsuarioNaoEncontradoException e) {
            ServletUtil.addErro(req, e.getMessage());
            carregarFormulario(req, idUsuario, idsProjetos, idTipoUsuario);
            req.getRequestDispatcher("/WEB-INF/admin/user-access.jsp").forward(req, resp);
        }
    }

    private void carregarFormulario(HttpServletRequest req, Integer idUsuario, List<Integer> idsProjetosSelecionados, Integer tipoUsuarioSelecionadoId) throws ServletException {
        ProjetoService projetoService = new ProjetoService();
        List<Projeto> projetosDoUsuario = projetoService.listarProjetosDoUsuario(idUsuario);
        List<Projeto> todosOsProjetos = projetoService.listarProjetos();

        UsuarioService usuarioService = new UsuarioService();
        Usuario usuario = usuarioService.buscarPorId(idUsuario);
        List<TipoUsuario> tiposUsuario = usuarioService.listarTiposUsuario();

        List<Integer> projetoSelecionadoIdsList = idsProjetosSelecionados;
        if (projetoSelecionadoIdsList == null || projetoSelecionadoIdsList.isEmpty()) {
            projetoSelecionadoIdsList = new ArrayList<>();
            for (Projeto projetoAtual : projetosDoUsuario) {
                projetoSelecionadoIdsList.add(projetoAtual.getId());
            }
        }

        Integer tipoUsuarioId = tipoUsuarioSelecionadoId;
        if (tipoUsuarioId == null && usuario.getTipoUsuario() != null) {
            tipoUsuarioId = usuario.getTipoUsuario().getId();
        }

        req.setAttribute("usuarioSelecionado", usuario);
        req.setAttribute("projetosAtuais", projetosDoUsuario);
        req.setAttribute("projetos", todosOsProjetos);
        req.setAttribute("tiposUsuario", tiposUsuario);
        req.setAttribute("projetoSelecionadoIds", projetoSelecionadoIdsList);
        req.setAttribute("tipoSelecionadoId", tipoUsuarioId);
        ServletUtil.prepararSidePanel(req, (Usuario) req.getSession(false).getAttribute("usuarioLogado"));
    }

    private Integer parseInt(String valor) {
        try {
            return valor == null || valor.isBlank() ? null : Integer.valueOf(valor);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private List<Integer> parseIntList(String[] valores) {
        List<Integer> idsList = new ArrayList<>();

        if (valores == null) {
            return idsList;
        }

        for (String valor : valores) {
            Integer id = parseInt(valor);
            if (id != null) {
                idsList.add(id);
            }
        }

        return idsList;
    }
}
