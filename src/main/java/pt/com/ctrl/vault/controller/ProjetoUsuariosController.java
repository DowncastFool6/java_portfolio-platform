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
import pt.com.ctrl.vault.service.ProjetoService;
import pt.com.ctrl.vault.util.ServletUtil;

/**
 * Controller para gestao de utilizadores do projeto.
 * 
 * @author aliceslombardi, CamilaRial, VissolelaCundi
 */
public class ProjetoUsuariosController extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Usuario usuarioLogado = ServletUtil.obterUsuarioLogado(req, resp);
		if (usuarioLogado == null) {
        	req.getRequestDispatcher("/WEB-INF/error/error-401.jsp").forward(req, resp);
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
        	req.getRequestDispatcher("/WEB-INF/error/error-401.jsp").forward(req, resp);
        	return;
		}

		Integer idProjeto = parseInt(req.getParameter("idProjeto"));
		Integer idUsuario = parseInt(req.getParameter("idUsuario"));
		boolean ativo = "ativar".equalsIgnoreCase(req.getParameter("acao"));

		try {
			validarAcessoGestao(usuarioLogado, idProjeto);
			ProjetoService projetoService = new ProjetoService();
			projetoService.atualizarStatusUsuarioNoProjeto(idProjeto, idUsuario, ativo);
			resp.sendRedirect(
					req.getContextPath() + "/projeto/usuarios?idProjeto=" + idProjeto + "&sucesso=status-atualizado");
		} catch (CampoObrigatorioException e) {
			ServletUtil.addErro(req, e.getMessage());
			carregarPagina(req, usuarioLogado, idProjeto);
			req.getRequestDispatcher("/WEB-INF/project/project-users.jsp").forward(req, resp);
		}
	}

	private void carregarPagina(HttpServletRequest req, Usuario usuarioLogado, Integer idProjeto) {
		ProjetoService projetoService = new ProjetoService();
		Projeto projeto = projetoService.buscarProjetoPorUsuarioEProjeto(usuarioLogado.getId(), idProjeto);
		List<Usuario> usuariosProjeto = projetoService.listarUsuariosDoProjeto(idProjeto);

		req.setAttribute("projeto", projeto);
		req.setAttribute("usuariosProjeto", usuariosProjeto);
		req.setAttribute("usuario", usuarioLogado);
		ServletUtil.prepararSidePanel(req, usuarioLogado);
	}

	private void validarAcessoGestao(Usuario usuarioLogado, Integer idProjeto) {
		if (!ServletUtil.usuarioPodeGerirProjeto(usuarioLogado)) {
			throw new CampoObrigatorioException("Apenas gestores ativos podem gerir utilizadores do projeto.");
		}

		ProjetoService projetoService = new ProjetoService();
		projetoService.verificaSeUsuarioPercenteAoProjeto(usuarioLogado.getId(), idProjeto);
	}

	private Integer parseInt(String valor) {
		try {
			return valor == null || valor.isBlank() ? null : Integer.valueOf(valor);
		} catch (NumberFormatException e) {
			return null;
		}
	}
}
