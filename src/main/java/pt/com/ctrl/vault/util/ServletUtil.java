package pt.com.ctrl.vault.util;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pt.com.ctrl.vault.model.Usuario;
import pt.com.ctrl.vault.service.ContatoService;
import pt.com.ctrl.vault.service.UsuarioService;

/**
 * Classe com metodos uteis para utilizar nas Servlets
 * @author aliceslombardi
 * @since 01/03/2026
 */
public class ServletUtil {
        
    public static Usuario obterUsuarioLogado(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("usuarioLogado") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return null;
        }

        return (Usuario) session.getAttribute("usuarioLogado");
    }
    
    public static void addErro(HttpServletRequest req, String mensagem) {
        req.setAttribute("erro", mensagem);
    }

    public static void addSucesso(HttpServletRequest req, String mensagem) {
        req.setAttribute("mensagem", mensagem);
    }

    public static boolean usuarioEhAdmin(Usuario usuario) {
        return usuario != null
                && usuario.getTipoUsuario() != null
                && usuario.getTipoUsuario().getDescricao() != null
                && usuario.getTipoUsuario().getDescricao().trim().toUpperCase().contains("ADMIN");
    }

    public static boolean usuarioEhGestor(Usuario usuario) {
        return usuario != null
                && usuario.getTipoUsuario() != null
                && usuario.getTipoUsuario().getDescricao() != null
                && usuario.getTipoUsuario().getDescricao().trim().toUpperCase().contains("GESTOR");
    }

    public static boolean usuarioEstaAtivo(Usuario usuario) {
        return usuario != null && Boolean.TRUE.equals(usuario.getAtivo());
    }

    public static boolean usuarioPodeGerirProjeto(Usuario usuario) {
        return usuarioEstaAtivo(usuario) && (usuarioEhGestor(usuario) || usuarioEhAdmin(usuario));
    }

    public static void prepararHeader(HttpServletRequest req, Usuario usuario) {
        if (req.getAttribute("mostrarBotaoHome") == null) {
            req.setAttribute("mostrarBotaoHome", true);
        }

        if (req.getAttribute("mostrarBotaoVoltar") == null) {
            req.setAttribute("mostrarBotaoVoltar", true);
        }

        boolean mostrarBotaoContatos = false;
        boolean temContatosPendentes = false;

        if (usuario != null && usuario.getId() != null) {
            UsuarioService usuarioService = new UsuarioService();
            mostrarBotaoContatos = !usuarioService.listarProjetosDoUsuario(usuario.getId()).isEmpty();
        }

        if (mostrarBotaoContatos) {
            ContatoService contatoService = new ContatoService();
            temContatosPendentes = contatoService.usuarioTemContatosPendentes(usuario);
        }

        req.setAttribute("mostrarBotaoContatos", mostrarBotaoContatos);
        req.setAttribute("temContatosPendentes", temContatosPendentes);
    }
    
}
