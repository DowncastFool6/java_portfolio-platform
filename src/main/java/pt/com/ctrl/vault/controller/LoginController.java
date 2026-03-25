package pt.com.ctrl.vault.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pt.com.ctrl.vault.exception.CampoObrigatorioException;
import pt.com.ctrl.vault.exception.SenhaInvalidaException;
import pt.com.ctrl.vault.exception.UsuarioNaoEncontradoException;
import pt.com.ctrl.vault.model.Usuario;
import pt.com.ctrl.vault.service.UsuarioService;
import pt.com.ctrl.vault.util.ServletUtil;

/**
 * Controller de login do sistema
 * @author aliceslombardi, CamilaRial, VissolelaCundi
 * @since 28/02/2026
 */
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        verificaSeNovoRegisto(req);
        req.getRequestDispatcher("/WEB-INF/login/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String email = req.getParameter("email");
        String senha = req.getParameter("senha");

        try {
            UsuarioService usuarioService = new UsuarioService();
            Usuario usuario = usuarioService.autenticar(email, senha);

            HttpSession session = req.getSession();
            session.setAttribute("usuarioLogado", usuario);

            resp.sendRedirect(req.getContextPath() + "/dashboard");

        } catch (CampoObrigatorioException e) {
            devolverErro(req, resp, e);
        } catch (UsuarioNaoEncontradoException e) {
            devolverErro(req, resp, e);
        } catch (SenhaInvalidaException e){
            devolverErro(req, resp, e);
        }
        
    }
    
    private void devolverErro(HttpServletRequest req, HttpServletResponse resp, Exception e) throws ServletException, IOException {
        ServletUtil.addErro(req, e.getMessage());
        req.getRequestDispatcher("/WEB-INF/login/login.jsp").forward(req, resp);
    }
    
    private void verificaSeNovoRegisto(HttpServletRequest req) {
        String registo = req.getParameter("registo");

        if ("true".equals(registo)) {
            req.setAttribute("mensagem","Registo realizado com sucesso. Faça login.");
        }
    }
    
}
