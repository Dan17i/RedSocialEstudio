package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.models.Estudiante;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/AgregarInteresServlet")
public class AgregarInteresServlet extends HttpServlet {

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

            if (estudiante.getIntereses() == null) {
                estudiante.setIntereses(new co.edu.uniquindio.redsocial.models.structures.ListaEnlazada<>());
            }

            // Verificar si el interés ya está en la lista
            boolean yaExiste = false;
            for (int i = 0; i < estudiante.getIntereses().getTamanio(); i++) {
                if (estudiante.getIntereses().obtener(i).equalsIgnoreCase(interes)) {
                    yaExiste = true;
                    break;
                }
            }

            if (!yaExiste) {
                estudiante.getIntereses().agregar(interes);
            }
        }

        // Redirige de nuevo a la página de inicio donde está el perfil
        response.sendRedirect("inicio.jsp?seccion=perfil");
    }
}
