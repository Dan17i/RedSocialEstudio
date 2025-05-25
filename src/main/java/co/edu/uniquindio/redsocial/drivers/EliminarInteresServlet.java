package co.edu.uniquindio.redsocial.drivers;


import co.edu.uniquindio.redsocial.models.Estudiante;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/EliminarInteresServlet")
public class EliminarInteresServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioActual") == null) {
            response.sendRedirect("inicioSesion.jsp");
            return;
        }

        String interes = request.getParameter("interes");

        if (interes != null && !interes.trim().isEmpty()) {
            Estudiante estudiante = (Estudiante) session.getAttribute("usuarioActual");

            if (estudiante.getIntereses() != null) {
                for (int i = 0; i < estudiante.getIntereses().getTamanio(); i++) {
                    if (estudiante.getIntereses().obtener(i).equalsIgnoreCase(interes)) {
                        estudiante.getIntereses().eliminar(i);
                        break;
                    }
                }
            }
        }

        response.sendRedirect("inicio.jsp?seccion=perfil");
    }
}
