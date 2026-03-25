package pt.com.ctrl.vault.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pt.com.ctrl.vault.exception.CampoObrigatorioException;
import pt.com.ctrl.vault.model.Conteudo;
import pt.com.ctrl.vault.model.Usuario;
import pt.com.ctrl.vault.service.ConteudoService;
import pt.com.ctrl.vault.util.ServletUtil;

/**
 * Controller para entrega de ficheiros associados aos conteudos.
 * @author aliceslombardi, CamilaRial, VissolelaCundi
 */
public class ConteudoArquivoController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Usuario usuarioLogado = ServletUtil.obterUsuarioLogado(req, resp);
        if (usuarioLogado == null) {
            return;
        }

        Integer idProjeto = parseInt(req.getParameter("idProjeto"));
        Integer idConteudo = parseInt(req.getParameter("idConteudo"));

        try {
            ConteudoService conteudoService = new ConteudoService();
            Conteudo conteudo = conteudoService.buscarConteudoDoProjeto(usuarioLogado, idProjeto, idConteudo);

            if (conteudo.getArquivo() == null || conteudo.getArquivo().length == 0) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            resp.setContentType(conteudo.getTipoMime() == null ? "application/octet-stream" : conteudo.getTipoMime());
            if (conteudo.getNomeArquivo() != null) {
                resp.setHeader("Content-Disposition", "inline; filename=\"" + conteudo.getNomeArquivo() + "\"");
            }
            resp.getOutputStream().write(conteudo.getArquivo());
        } catch (CampoObrigatorioException e) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN);
        }
    }

    private Integer parseInt(String valor) {
        try {
            return valor == null || valor.isBlank() ? null : Integer.valueOf(valor);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
