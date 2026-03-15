package pt.com.ctrl.vault.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.*;

/**
 * Controller de encerramento de sessao.
 * @author aliceslombardi, CamilaRial, VissolelaCundi
 */
public class LogoutController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp
    ) throws ServletException, IOException {

        removerUsuarioDaSessao(req);

        resp.sendRedirect(req.getContextPath() + "/login");
    }

    private void removerUsuarioDaSessao(HttpServletRequest req){
        HttpSession session = req.getSession(false);

        if(session != null){
            session.removeAttribute("usuarioLogado");
            session.invalidate();
        }
    }
}