package pt.com.ctrl.vault.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import pt.com.ctrl.vault.exception.CampoObrigatorioException;
import pt.com.ctrl.vault.model.Projeto;
import pt.com.ctrl.vault.model.Usuario;
import pt.com.ctrl.vault.service.ConteudoService;
import pt.com.ctrl.vault.service.ProjetoService;
import pt.com.ctrl.vault.util.ServletUtil;

/**
 * Controller legado para criacao de conteudos.
 * @author aliceslombardi, CamilaRial, VissolelaCundi
 */
@MultipartConfig
public class ProjetoNovoConteudoController extends HttpServlet {
    private static final long tamanhoMaximoDoArquivo = 1024 * 1024; //1mb

    /**
     * Carrega tela para criar novo conteudo
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Usuario usuarioLogado = ServletUtil.obterUsuarioLogado(req, resp);
        if (usuarioLogado == null) {
            return;
        }

        if (!ServletUtil.usuarioEstaAtivo(usuarioLogado)) {
            req.getSession().setAttribute("mensagemDashboard", "O seu utilizador esta inativo e apenas pode consultar informacao.");
            resp.sendRedirect(req.getContextPath() + "/projeto?id=" + req.getParameter("idProjeto"));
            return;
        }

        try {
            Integer idProjeto = parseInt(req.getParameter("idProjeto"));
            ProjetoService projetoService = new ProjetoService();
            projetoService.validarProjetoAberto(idProjeto);
            carregarPagina(req, usuarioLogado, idProjeto);
            req.getRequestDispatcher("/WEB-INF/project/content-form.jsp").forward(req, resp);
        } catch (CampoObrigatorioException e) {
            req.getSession().setAttribute("mensagemDashboard", e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/projeto?id=" + req.getParameter("idProjeto"));
        }
    }

    /**
     * Salva novo conteudo
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletUtil.configurarUtf8(req, resp);

        Usuario usuarioLogado = ServletUtil.obterUsuarioLogado(req, resp);
        if (usuarioLogado == null) {
            return;
        }

        Integer idProjeto = parseInt(req.getParameter("idProjeto"));
        String titulo = req.getParameter("titulo");
        String tipoConteudo = req.getParameter("tipoConteudo");
        String texto = req.getParameter("texto");

        try {
            ProjetoService projetoService = new ProjetoService();
            projetoService.validarProjetoAberto(idProjeto);
            Part arquivo = obterTipoArquivo(req, "arquivo");
            ConteudoService conteudoService = new ConteudoService();
            conteudoService.criarConteudo(usuarioLogado, idProjeto, titulo, tipoConteudo, texto, arquivo);
            resp.sendRedirect(req.getContextPath() + "/projeto?id=" + idProjeto + "&sucesso=conteudo-criado");
        } catch (CampoObrigatorioException e) {
            ServletUtil.addErro(req, e.getMessage());
            req.setAttribute("tituloConteudo", titulo);
            req.setAttribute("tipoConteudoSelecionado", tipoConteudo);
            req.setAttribute("textoConteudo", texto);
            carregarPagina(req, usuarioLogado, idProjeto);
            req.getRequestDispatcher("/WEB-INF/project/content-form.jsp").forward(req, resp);
        }
    }

    private void carregarPagina(HttpServletRequest req, Usuario usuarioLogado, Integer idProjeto) {
    	ProjetoService projetoService = new ProjetoService();
    	Projeto projeto = projetoService.buscarProjetoPorUsuarioEProjeto(usuarioLogado.getId(), idProjeto);

        req.setAttribute("usuario", usuarioLogado);
        req.setAttribute("projeto", projeto);
        ServletUtil.prepararSidePanel(req, usuarioLogado);
    }

    private Integer parseInt(String valor) {
        try {
            return valor == null || valor.isBlank() ? null : Integer.valueOf(valor);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Part obterTipoArquivo(HttpServletRequest req, String nome) {
        try {
            Part part = req.getPart(nome);
            if (part != null && part.getSize() > tamanhoMaximoDoArquivo) {
                throw new CampoObrigatorioException("A imagem deve ter no maximo 1 MB.");
            }
            return part;
        } catch (IllegalStateException e) {
            throw new CampoObrigatorioException("A imagem deve ter no maximo 1 MB.");
        } catch (IOException | ServletException e) {
            return null;
        }
    }
}
