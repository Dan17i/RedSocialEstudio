package co.edu.uniquindio.redsocial.drivers;


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

        if (session == null || session.getAttribute("usuario") == null) {
            // No hay usuario en sesión: redirigir a login
            response.sendRedirect("inicioSesion.jsp"); // Cambia a tu página de login
            return;
        }

        // Obtener el nombre de usuario de la sesión
        String nombreUsuario = (String) session.getAttribute("usuario");

        // Pasar el nombre a la vista (JSP)
        request.setAttribute("nombreUsuario", nombreUsuario);

        // Forward a dashboard.jsp
        request.getRequestDispatcher("/inicio.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
