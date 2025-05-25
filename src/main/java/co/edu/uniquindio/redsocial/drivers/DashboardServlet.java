package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.models.Estudiante;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "DashboardServlet", urlPatterns = {"/DashboardServlet"})
public class DashboardServlet extends HttpServlet {

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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
