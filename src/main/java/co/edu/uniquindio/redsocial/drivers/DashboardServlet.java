package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.models.Estudiante;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * Servlet encargado de redirigir al usuario autenticado hacia su panel principal (dashboard).
 * Verifica si hay una sesión activa con un objeto {@link Estudiante} y, en caso afirmativo,
 * pasa su nombre a la vista principal para personalizar la experiencia del usuario.
 * Ruta: /DashboardServlet
 * Atributos establecidos en el request:
 * - "nombreUsuario": Nombre del estudiante autenticado, usado en la vista de bienvenida.
 * Redirección:
 * - Si hay sesión activa: /inicio.jsp
 * - Si no hay sesión: inicioSesion.jsp
 *
 * @author Daniel Jurado, Sebastian Torres y Juan Soto
 * @version 1.0
 */
@WebServlet(name = "DashboardServlet", urlPatterns = {"/DashboardServlet"})
public class DashboardServlet extends HttpServlet {
    /**
     * Maneja las solicitudes GET al dashboard del estudiante.
     * Verifica si existe un usuario autenticado en sesión. Si no lo hay, redirige al login.
     * Si lo hay, obtiene su nombre y lo envía a la vista principal.
     *
     * @param request  solicitud HTTP del cliente
     * @param response respuesta HTTP para redirigir o mostrar la vista
     * @throws ServletException si ocurre un error en el reenvío a la vista
     * @throws IOException      si ocurre un error de entrada/salida
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener la sesión actual sin crear una nueva si no existe
        HttpSession session = request.getSession(false);

        // Verificar que exista una sesión y un usuario logueado
        Estudiante estudiante = null;
        if (session != null) {
            estudiante = (Estudiante) session.getAttribute("usuarioActual");
        }

        if (estudiante == null) {
            // Si no hay usuario logueado, redirigir al login
            response.sendRedirect("inicioSesion.jsp");
            return;
        }

        // Obtener el nombre del estudiante
        String nombreUsuario = estudiante.getNombre();

        // Pasar el nombre a la vista (JSP)
        request.setAttribute("nombreUsuario", nombreUsuario);

        // Redirigir a la vista principal
        request.getRequestDispatcher("/inicio.jsp").forward(request, response);
    }
    /**
     * Redirige las solicitudes POST al método doGet, ya que ambas operaciones realizan la misma lógica.
     *
     * @param request  solicitud HTTP del cliente
     * @param response respuesta HTTP correspondiente
     * @throws ServletException si ocurre un error durante la ejecución
     * @throws IOException      si ocurre un error de entrada/salida
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
