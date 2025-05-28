// src/main/java/co/edu/uniquindio/redsocial/drivers/SugerirGruposServlet.java
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
 * Servlet que sugiere grupos de estudio para un estudiante.
 *
 * <p>
 * Obtiene todos los grupos disponibles y filtra aquellos en los que el estudiante
 * aún no participa. Los grupos sugeridos se envían a la vista para mostrar recomendaciones.
 * </p>
 *
 * Ruta: /grupos/sugeridos
 *
 * @author Daniel Jurado, Sebastian Torres y Juan Soto
 * @version 1.0
 */
@WebServlet("/grupos/sugeridos")
public class SugerirGruposServlet extends HttpServlet {
    /**
     * Procesa solicitudes GET para mostrar grupos sugeridos al estudiante actual.
     *
     * @param req  solicitud HTTP
     * @param resp respuesta HTTP
     * @throws ServletException en caso de error en el servlet
     * @throws IOException      en caso de error de entrada/salida
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

        @SuppressWarnings("unchecked")
        ListaEnlazada<GrupoEstudio> todos =
                (ListaEnlazada<GrupoEstudio>)
                        getServletContext().getAttribute("todosGrupos");

        ListaEnlazada<GrupoEstudio> sugeridos = new ListaEnlazada<>();
        for (int i = 0; i < todos.getTamanio(); i++) {
            GrupoEstudio g = todos.obtener(i);
            if (!est.getGruposEstudio().contiene(g)) {
                sugeridos.agregar(g);
            }
        }
        req.setAttribute("sugeridos", sugeridos);

        if (req.getDispatcherType() == DispatcherType.INCLUDE) {
            return;
        }
        req.getRequestDispatcher("/grupos/sugeridos.jsp").forward(req, resp);

    }
}
