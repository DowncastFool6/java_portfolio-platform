package pt.com.ctrl.vault.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pt.com.ctrl.vault.exception.CampoObrigatorioException;
import pt.com.ctrl.vault.exception.EmailJaRegistadoException;
import pt.com.ctrl.vault.service.UsuarioService;
import pt.com.ctrl.vault.util.ServletUtil;

/**
 * Controller do resgistro de um novo usuario no sistema
 * @author aliceslombardi
 * @since 28/02/2026
 */
public class RegisterController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/register/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nome = req.getParameter("nome");
        String email = req.getParameter("email");
        String senha = req.getParameter("senha");
        
        try {
            UsuarioService usuarioService = new UsuarioService();
            usuarioService.registarUsuario(nome, email, senha);

            resp.sendRedirect(req.getContextPath() + "/login?registo=true");
            
        } catch (CampoObrigatorioException e) {
            devolverErro(req, resp, e);
        } catch (EmailJaRegistadoException e) {
            devolverErro(req, resp, e);
        }
    }
    
    private void devolverErro(HttpServletRequest req, HttpServletResponse resp, Exception e) throws ServletException, IOException {
        ServletUtil.addErro(req, e.getMessage());
        req.getRequestDispatcher("/WEB-INF/register/register.jsp").forward(req, resp);
    }
    
}
