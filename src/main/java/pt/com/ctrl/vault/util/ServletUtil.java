package pt.com.ctrl.vault.util;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pt.com.ctrl.vault.model.Usuario;

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
    
}
