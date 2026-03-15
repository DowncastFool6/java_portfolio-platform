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
import pt.com.ctrl.vault.util.ServletUtil;

@MultipartConfig
public class ProjetoNovoConteudoController extends HttpServlet {

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
            carregarPagina(req, usuarioLogado, idProjeto);
            req.getRequestDispatcher("/WEB-INF/project/content-form.jsp").forward(req, resp);
        } catch (CampoObrigatorioException e) {
            resp.sendRedirect(req.getContextPath() + "/projetos");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Usuario usuarioLogado = ServletUtil.obterUsuarioLogado(req, resp);
        if (usuarioLogado == null) {
            return;
        }

        Integer idProjeto = parseInt(req.getParameter("idProjeto"));
        String titulo = req.getParameter("titulo");
        String tipoConteudo = req.getParameter("tipoConteudo");
        String texto = req.getParameter("texto");
        Part arquivo = obterPartSilencioso(req, "arquivo");

        try {
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
        ConteudoService conteudoService = new ConteudoService();
        Projeto projeto = conteudoService.buscarProjetoDoUsuario(usuarioLogado, idProjeto);

        req.setAttribute("usuario", usuarioLogado);
        req.setAttribute("projeto", projeto);
        ServletUtil.prepararHeader(req, usuarioLogado);
    }

    private Integer parseInt(String valor) {
        try {
            return valor == null || valor.isBlank() ? null : Integer.valueOf(valor);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Part obterPartSilencioso(HttpServletRequest req, String nome) {
        try {
            return req.getPart(nome);
        } catch (IOException | ServletException e) {
            return null;
        }
    }
}
