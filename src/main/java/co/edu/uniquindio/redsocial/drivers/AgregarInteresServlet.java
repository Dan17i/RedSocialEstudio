package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.models.Estudiante;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
/**
 * Servlet que permite agregar un nuevo interés a la lista de intereses
 * de un estudiante autenticado en la sesión actual.
 * Recibe la información del interés mediante una petición POST,
 * válida que el usuario esté autenticado y que el interés no esté duplicado
 * antes de agregarlo a la lista.
 * Finalmente, redirige al perfil del usuario.
 * @author Daniel Jurado, Sebasian Torres y Juan Soto
 * @since 2025-05-23
 */
@WebServlet("/AgregarInteresServlet")
public class AgregarInteresServlet extends HttpServlet {
    /**
     * Procesa la solicitud POST para agregar un interés al estudiante.
     * Verifica que la sesión sea válida y que el usuario esté autenticado.
     * Válida que el interés recibido no sea vacío ni ya exista en la lista de intereses.
     *
     * @param request Objeto HttpServletRequest con los datos de la petición.
     * @param response Objeto HttpServletResponse para la respuesta HTTP.
     * @throws ServletException Si ocurre un error en el servlet.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
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
