package co.edu.uniquindio.redsocial.drivers;


import co.edu.uniquindio.redsocial.models.Estudiante;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
/**
 * Servlet encargado de eliminar un interés específico del perfil del estudiante autenticado.
 * Ruta: /EliminarInteresServlet
 * Funcionamiento:
 * - Verifica si el usuario está autenticado.
 * - Obtiene el interés a eliminar desde los parámetros de la petición.
 * - Elimina el interés de la lista de intereses del estudiante si existe.
 * - Redirige al perfil del usuario tras completar la operación.
 * Parámetros esperados:
 * - "interes": nombre del interés a eliminar.
 * Redirección:
 * - Si no hay sesión: inicioSesion.jsp
 * - Si la operación es válida: inicio.jsp?seccion=perfil
 *
 * @author Daniel Jurado, Sebastian Torres y Juan Soto
 * @since 2025-05-23
 * @version 1.0
 */
@WebServlet("/EliminarInteresServlet")
public class EliminarInteresServlet extends HttpServlet {
    /**
     * Maneja las solicitudes POST para eliminar un interés del estudiante autenticado.
     * Verifica la sesión, busca el interés dentro de la lista del estudiante y lo elimina si lo encuentra.
     *
     * @param request  solicitud HTTP con el parámetro "interes" a eliminar
     * @param response respuesta HTTP con la redirección correspondiente
     * @throws ServletException si ocurre un error durante la ejecución
     * @throws IOException      si ocurre un error de entrada/salida
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
