package co.edu.uniquindio.redsocial.drivers;


import co.edu.uniquindio.redsocial.models.Estudiante;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/perfil")
public class PerfilServlet extends HttpServlet {

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
