package pt.com.ctrl.vault.error;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * TestErrorServlet
 *
 * Um único servlet para testar as páginas de erro 401, 403, 404 e 500.
 * Use no seu navegador da seguinte forma:
 *   /test-error?code=401
 *   /test-error?code=403
 *   /test-error?code=404
 *   /test-error?code=500
 */

public class TestErrorServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the 'code' parameter from the URL
        String code = request.getParameter("code");

        if (code == null) {
            response.getWriter().println("Usage: /test-error?code=401|403|404|500");
            return;
        }

        switch (code) {
            case "401":
                // Unauthorized
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "401 Unauthorized Test");
                break;

            case "403":
                // Forbidden
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "403 Forbidden Test");
                break;

            case "404":
                // Not Found
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "404 Not Found Test");
                break;

            case "500":
                // Internal Server Error – throw a runtime exception
                throw new RuntimeException("500 Internal Server Error Test");

            default:
                response.getWriter().println("Invalid code parameter. Use 401, 403, 404, or 500.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward POST requests to doGet
        doGet(request, response);
    }
}
