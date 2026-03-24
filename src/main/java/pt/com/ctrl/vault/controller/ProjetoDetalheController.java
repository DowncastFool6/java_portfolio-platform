package pt.com.ctrl.vault.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import pt.com.ctrl.vault.exception.CampoObrigatorioException;
import pt.com.ctrl.vault.model.Conteudo;
import pt.com.ctrl.vault.model.Projeto;
import pt.com.ctrl.vault.model.Usuario;
import pt.com.ctrl.vault.service.ConteudoService;
import pt.com.ctrl.vault.service.ProjetoService;
import pt.com.ctrl.vault.util.ServletUtil;

/**
 * Controller para detalhe e ordenacao de conteudos do projeto.
 * @author aliceslombardi, CamilaRial, VissolelaCundi
 */
@MultipartConfig
public class ProjetoDetalheController extends HttpServlet {

	/**
	 * Carrega pagina de detalhe
	 */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Usuario usuarioLogado = ServletUtil.obterUsuarioLogado(req, resp);
        if (usuarioLogado == null) {
            return;
        }

        try {
            Integer idProjeto = parseInt(req.getParameter("id"));	
            carregarPagina(req, usuarioLogado, idProjeto);

            if ("conteudo-criado".equals(req.getParameter("sucesso"))) {
                ServletUtil.addSucesso(req, "Conteudo criado com sucesso.");
            } else if ("conteudos-atualizados".equals(req.getParameter("sucesso"))) {
                ServletUtil.addSucesso(req, "Conteudos atualizados com sucesso.");
            } else if ("status-projeto-atualizado".equals(req.getParameter("sucesso"))) {
                ServletUtil.addSucesso(req, "Status do projeto atualizado com sucesso.");
            }

            Object mensagemDashboard = req.getSession().getAttribute("mensagemDashboard");
            if (mensagemDashboard != null) {
                ServletUtil.addSucesso(req, String.valueOf(mensagemDashboard));
                req.getSession().removeAttribute("mensagemDashboard");
            }

            req.getRequestDispatcher("/WEB-INF/project/project-detail.jsp").forward(req, resp);
        } catch (CampoObrigatorioException e) {
            resp.sendRedirect(req.getContextPath() + "/projetos");
        }
    }

    /**
     * Salva os detalhes do projeto
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletUtil.configurarUtf8(req, resp);

        Usuario usuarioLogado = ServletUtil.obterUsuarioLogado(req, resp);
        if (usuarioLogado == null) {
            return;
        }

        Integer idProjeto = parseInt(req.getParameter("idProjeto"));

        try {
            String acao = req.getParameter("acao");
            ProjetoService projetoService = new ProjetoService();

            if ("fechar-projeto".equalsIgnoreCase(acao) || "reabrir-projeto".equalsIgnoreCase(acao)) {
                validarGestaoProjeto(usuarioLogado, idProjeto);

                if ("fechar-projeto".equalsIgnoreCase(acao)) {
                    projetoService.fecharProjeto(idProjeto);
                } else {
                    projetoService.reabrirProjeto(idProjeto);
                }

                resp.sendRedirect(req.getContextPath() + "/projeto?id=" + idProjeto + "&sucesso=status-projeto-atualizado");
                return;
            }

            ConteudoService conteudoService = new ConteudoService();

            if ("remover".equalsIgnoreCase(acao)) {
                Integer idConteudoRemover = parseInt(req.getParameter("idConteudoRemover"));
                conteudoService.removerConteudo(usuarioLogado, idProjeto, idConteudoRemover);
                resp.sendRedirect(req.getContextPath() + "/projeto?id=" + idProjeto + "&modo=editar&sucesso=conteudos-atualizados");
                return;
            }

            List<Integer> idsConteudo = parseIntList(req.getParameterValues("conteudoId"));
            List<Integer> idsOrdenados = parseIntList(req.getParameterValues("conteudoOrdem"));
            List<Conteudo> atualizacoes = new ArrayList<>();

            for (Integer idConteudo : idsConteudo) {
                String titulo = req.getParameter("titulo_" + idConteudo);
                String texto = req.getParameter("texto_" + idConteudo);
                Part arquivoPart = obterTipoArquivo(req, "arquivo_" + idConteudo);
                Conteudo conteudo = conteudoService.montarAtualizacao(
                        usuarioLogado, idProjeto, idConteudo, titulo, texto, arquivoPart);

                Integer ordem = parseInt(req.getParameter("ordem_" + idConteudo));
                conteudo.setOrdemExibicao(ordem == null ? 0 : ordem);
                atualizacoes.add(conteudo);
            }

            if (idsOrdenados.isEmpty()) {
                idsOrdenados = idsConteudo;
            }

            conteudoService.atualizarConteudos(usuarioLogado, idProjeto, atualizacoes, idsOrdenados);
            resp.sendRedirect(req.getContextPath() + "/projeto?id=" + idProjeto + "&sucesso=conteudos-atualizados");
        } catch (CampoObrigatorioException e) {
            ServletUtil.addErro(req, e.getMessage());
            carregarPagina(req, usuarioLogado, idProjeto);
            req.setAttribute("modoEdicao", true);
            req.getRequestDispatcher("/WEB-INF/project/project-detail.jsp").forward(req, resp);
        }
    }

    private void carregarPagina(HttpServletRequest req, Usuario usuarioLogado, Integer idProjeto) {
    	ProjetoService projetoService = new ProjetoService();
    	Projeto projeto = projetoService.buscarProjetoPorUsuarioEProjeto(usuarioLogado.getId(), idProjeto);
    	
        ConteudoService conteudoService = new ConteudoService();
        List<Conteudo> conteudos = conteudoService.listarConteudosDoProjeto(usuarioLogado, idProjeto);
        boolean usuarioPodeGerirProjeto = ServletUtil.usuarioPodeGerirProjeto(usuarioLogado);
        boolean usuarioPodeEditarProjeto = ServletUtil.usuarioEstaAtivo(usuarioLogado) && projeto != null && projeto.isAberto();
        boolean modoEdicaoSolicitado = "editar".equalsIgnoreCase(req.getParameter("modo"));

        req.setAttribute("usuario", usuarioLogado);
        req.setAttribute("projeto", projeto);
        req.setAttribute("conteudos", conteudos);
        req.setAttribute("usuarioPodeEditarProjeto", usuarioPodeEditarProjeto);
        req.setAttribute("usuarioPodeGerirUsuariosProjeto", usuarioPodeGerirProjeto);
        req.setAttribute("usuarioPodeAlterarStatusProjeto", usuarioPodeGerirProjeto);
        req.setAttribute("projetoFechado", projeto != null && projeto.isFechado());
        req.setAttribute("modoEdicao", modoEdicaoSolicitado && usuarioPodeEditarProjeto);
        ServletUtil.prepararSidePanel(req, usuarioLogado);
    }

    private void validarGestaoProjeto(Usuario usuarioLogado, Integer idProjeto) {
        if (!ServletUtil.usuarioPodeGerirProjeto(usuarioLogado)) {
            throw new CampoObrigatorioException("Apenas gestores ativos podem alterar o status do projeto.");
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

    private List<Integer> parseIntList(String[] valores) {
        List<Integer> ids = new ArrayList<>();

        if (valores == null) {
            return ids;
        }

        for (String valor : valores) {
            Integer id = parseInt(valor);
            if (id != null) {
                ids.add(id);
            }
        }

        return ids;
    }

    private Part obterTipoArquivo(HttpServletRequest req, String nome) {
        try {
            return req.getPart(nome);
        } catch (IOException | ServletException e) {
            return null;
        }
    }
}
