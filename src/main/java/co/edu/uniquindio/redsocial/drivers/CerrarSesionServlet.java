package co.edu.uniquindio.redsocial.drivers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * Servlet que permite cerrar la sesión actual del usuario.
 * Este servlet invalida la sesión activa (si existe) y redirige al usuario
 * a la página de inicio de sesión.
 * Se puede invocar mediante una solicitud GET o POST a la ruta "/CerrarSesionServlet".
 * @author Daniel Jurado, Sebastian Torres y Juan Soto
 * @since 2025-05-24
 */
@WebServlet(name = "CerrarSesionServlet", urlPatterns = {"/CerrarSesionServlet"})
public class CerrarSesionServlet extends HttpServlet {
    /**
     * Maneja las solicitudes GET para cerrar la sesión del usuario.
     *
     * @param request  La solicitud HTTP del cliente.
     * @param response La respuesta HTTP que se enviará al cliente.
     * @throws ServletException Si ocurre un error relacionado con el servlet.
     * @throws IOException      Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false); // no crear si no existe
        if (session != null) {
            session.invalidate(); // destruir sesión
        }
        // Redirigir a página de login 
        response.sendRedirect("inicioSesion.jsp");
    }
    /**
     * Maneja las solicitudes POST redirigiéndolas a doGet para cerrar sesión.
     *
     * @param request  La solicitud HTTP del cliente.
     * @param response La respuesta HTTP que se enviará al cliente.
     * @throws ServletException Si ocurre un error relacionado con el servlet.
     * @throws IOException      Si ocurre un error de entrada/salida.
     */

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}

