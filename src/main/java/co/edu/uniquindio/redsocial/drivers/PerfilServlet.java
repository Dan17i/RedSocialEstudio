package co.edu.uniquindio.redsocial.drivers;


import co.edu.uniquindio.redsocial.models.Estudiante;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
/**
 * Servlet para gestionar la visualización del perfil del estudiante autenticado.
 *
 * <p>
 * - Obtiene la sesión actual y recupera el usuario (Estudiante).
 * - Si no hay usuario autenticado, redirige a la página de login.
 * - Si hay usuario, hace forward a la vista del perfil.
 * </p>
 *
 * Ruta: /perfil
 *
 * @author Daniel Jurado, Sebastian Torres y Juan Soto
 * @version 1.0
 */
@WebServlet("/perfil")
public class PerfilServlet extends HttpServlet {
    /**
     * Maneja solicitudes GET para mostrar el perfil del estudiante autenticado.
     *
     * @param request  solicitud HTTP
     * @param response respuesta HTTP
     * @throws ServletException si ocurre un error en el servlet
     * @throws IOException      si ocurre un error de E/S
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Estudiante estudiante = (session != null) ? (Estudiante) session.getAttribute("usuarioActual") : null;

        if (estudiante == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Redirige a la vista de perfil
        request.getRequestDispatcher("perfil.jsp").forward(request, response);
    }
}
