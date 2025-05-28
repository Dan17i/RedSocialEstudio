// src/main/java/co/edu/uniquindio/redsocial/drivers/UnirseGrupoServlet.java
package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.GrupoEstudio;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
/**
 * Servlet que permite a un estudiante unirse a un grupo de estudio.
 *
 * <p>
 * Recibe el ID del grupo mediante una solicitud POST, busca el grupo
 * en la lista de grupos disponibles y agrega al estudiante al grupo.
 * </p>
 *
 * Ruta: /grupos/unirse
 *
 * @author Daniel Jurado, Sebastian Torres y Juan Soto
 * @version 1.0
 */
@WebServlet("/grupos/unirse")
public class UnirseGrupoServlet extends HttpServlet {
    /**
     * Procesa solicitudes POST para que un estudiante se una a un grupo específico.
     *
     * @param req  solicitud HTTP con parámetro "grupoId"
     * @param resp respuesta HTTP que redirige a la página principal con mensaje
     * @throws ServletException en caso de error en el servlet
     * @throws IOException      en caso de error de entrada/salida
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        Estudiante est = session != null
                ? (Estudiante) session.getAttribute("usuarioActual")
                : null;
        if (est == null) {
            resp.sendRedirect(req.getContextPath() + "/inicioSesion.jsp");
            return;
        }

        String grupoId = req.getParameter("grupoId");

        @SuppressWarnings("unchecked")
        ListaEnlazada<GrupoEstudio> todos =
                (ListaEnlazada<GrupoEstudio>)
                        getServletContext().getAttribute("todosGrupos");

        // Busco y me uno
        for (int i = 0; i < todos.getTamanio(); i++) {
            GrupoEstudio g = todos.obtener(i);
            if (g.getId().equals(grupoId)) {
                est.unirseAGrupo(g);
                break;
            }
        }

        String ctx = req.getContextPath();
        resp.sendRedirect(ctx + "/inicio.jsp?seccion=grupos"
                + "&message=Te has unido al grupo");
    }
}
