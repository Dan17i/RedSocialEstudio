// src/main/java/co/edu/uniquindio/redsocial/drivers/MostrarGruposServlet.java
package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.GrupoEstudio;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

import javax.servlet.DispatcherType;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
/**
 * Servlet que muestra la lista de grupos de estudio a los que pertenece
 * el estudiante actualmente autenticado.
 *
 * <p>
 * - Obtiene la sesión actual y recupera el usuario (Estudiante).
 * - Si no hay usuario autenticado, redirige a la página de inicio de sesión.
 * - Coloca la lista de grupos del estudiante como atributo en la solicitud.
 * - Si la petición es un include, termina la ejecución sin hacer forward.
 * - Si la petición es directa, hace forward a la página JSP para listar grupos.
 * </p>
 *
 * Ruta: /grupos
 *
 * @author Daniel Jurado, Sebastian Torres y Juan Soto
 * @version 1.0
 */
@WebServlet("/grupos")
public class MostrarGruposServlet extends HttpServlet {
    /**
     * Maneja solicitudes GET para mostrar los grupos del estudiante.
     *
     * @param req  solicitud HTTP
     * @param resp respuesta HTTP
     * @throws ServletException si ocurre un error en el servlet
     * @throws IOException      si ocurre un error de E/S
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        Estudiante est = session != null
                ? (Estudiante) session.getAttribute("usuarioActual")
                : null;
        if (est == null) {
            resp.sendRedirect(req.getContextPath() + "/inicioSesion.jsp");
            return;
        }

        // 1) Pongo directamente tu ListaEnlazada de grupos del estudiante
        ListaEnlazada<GrupoEstudio> misGrupos = est.getGruposEstudio();
        req.setAttribute("misGrupos", misGrupos);

        // 2) Si me llamaron mediante include(), salgo (no forward)
        if (req.getDispatcherType() == DispatcherType.INCLUDE) {
            return;
        }

        // 3) Si acceden directo a /grupos en el navegador, muestro el JSP (opcional)
        req.getRequestDispatcher("/grupos/lista.jsp").forward(req, resp);

    }
}
