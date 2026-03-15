package pt.com.ctrl.vault.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pt.com.ctrl.vault.model.Usuario;
import pt.com.ctrl.vault.util.ServletUtil;

/**
 * Classe com logica apresentacao do dashboard do usuario
 * @author aliceslombardi
 * @since 01/03/2026
 */
public class DashboardController extends HttpServlet{
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Usuario usuarioLogado = ServletUtil.obterUsuarioLogado(req, resp);
        if (usuarioLogado == null) {
            return;
        }
        
        if(usuarioLogado.getTipoUsuario() == null) {
            ServletUtil.addErro(req, "Seu usuário não tem projetos associados, entre em contato com o admin");
        }

        req.getRequestDispatcher("/WEB-INF/dashboard/dashboard.jsp")
           .forward(req, resp);
    }
    
}
