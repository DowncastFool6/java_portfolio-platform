package pt.com.ctrl.vault.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pt.com.ctrl.vault.exception.CampoObrigatorioException;
import pt.com.ctrl.vault.model.Contato;
import pt.com.ctrl.vault.model.Usuario;
import pt.com.ctrl.vault.service.ContatoService;
import pt.com.ctrl.vault.util.ServletUtil;

/**
 * Controller para gestao de contatos recebidos pelos utilizadores participantes
 * @author aliceslombardi, CamilaRial, VissolelaCundi
 * @since 08/03/2026
 */
public class ContatosRecebidosController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Usuario usuarioLogado = ServletUtil.obterUsuarioLogado(req, resp);
        if (usuarioLogado == null) {
        	req.getRequestDispatcher("/WEB-INF/error/error-401.jsp").forward(req, resp);
        	return;
        }

        carregarPagina(req, usuarioLogado);

        if ("selecionados".equals(req.getParameter("sucesso"))) {
            ServletUtil.addSucesso(req, "Contatos selecionados marcados como lidos.");
        } else if ("removidos".equals(req.getParameter("sucesso"))) {
            ServletUtil.addSucesso(req, "Contatos selecionados removidos.");
        }

        req.getRequestDispatcher("/WEB-INF/contact/received-contacts.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Usuario usuarioLogado = ServletUtil.obterUsuarioLogado(req, resp);
        if (usuarioLogado == null) {
            return;
        }

        if (!ServletUtil.usuarioEstaAtivo(usuarioLogado)) {
            req.getSession().setAttribute("mensagemDashboard", "O seu utilizador esta inativo e apenas pode consultar informacao.");
            resp.sendRedirect(req.getContextPath() + "/dashboard");
            return;
        }

        ContatoService contatoService = new ContatoService();

        try {
            List<Integer> idsContato = parseIntList(req.getParameterValues("idContato"));
            String acao = req.getParameter("acao");

            if ("remover".equals(acao)) {
                contatoService.removerContatos(usuarioLogado, idsContato);
                resp.sendRedirect(req.getContextPath() + "/contatos/recebidos?sucesso=removidos");
                return;
            }

            contatoService.marcarContatosComoLidos(usuarioLogado, idsContato);
            resp.sendRedirect(req.getContextPath() + "/contatos/recebidos?sucesso=selecionados");

        } catch (CampoObrigatorioException e) {
            ServletUtil.addErro(req, e.getMessage());
            req.setAttribute("idsContatoSelecionados", parseIntList(req.getParameterValues("idContato")));
            carregarPagina(req, usuarioLogado);
            req.getRequestDispatcher("/WEB-INF/contact/received-contacts.jsp").forward(req, resp);
        }
    }

    private void carregarPagina(HttpServletRequest req, Usuario usuarioLogado) {
        ContatoService contatoService = new ContatoService();
        List<Contato> contatos = contatoService.listarContatosRecebidos(usuarioLogado);

        req.setAttribute("usuario", usuarioLogado);
        req.setAttribute("contatosRecebidos", contatos);
        ServletUtil.prepararSidePanel(req, usuarioLogado);
    }

    private List<Integer> parseIntList(String[] valores) {
        List<Integer> ids = new ArrayList<>();

        if (valores == null) {
            return ids;
        }

        for (String valor : valores) {
            try {
                if (valor != null && !valor.isBlank()) {
                    ids.add(Integer.valueOf(valor));
                }
            } catch (NumberFormatException ignored) {}
        }

        return ids;
    }
}
