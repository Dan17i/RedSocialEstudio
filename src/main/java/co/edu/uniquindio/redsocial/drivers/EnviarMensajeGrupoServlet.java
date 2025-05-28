package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.GrupoEstudio;
import co.edu.uniquindio.redsocial.models.Mensaje;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.net.URLEncoder;

@WebServlet("/grupos/detalle/mensaje")
public class EnviarMensajeGrupoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String grupoId = req.getParameter("grupoId");
        String texto   = req.getParameter("texto");
        HttpSession session = req.getSession(false);
        Estudiante origen = (session != null)
                ? (Estudiante) session.getAttribute("usuarioActual")
                : null;

        @SuppressWarnings("unchecked")
        ListaEnlazada<GrupoEstudio> todosGrupos =
                (ListaEnlazada<GrupoEstudio>) getServletContext().getAttribute("todosGrupos");

        // Buscar grupo
        GrupoEstudio grupo = null;
        if (grupoId != null && todosGrupos != null) {
            for (int i = 0; i < todosGrupos.getTamanio(); i++) {
                GrupoEstudio g = todosGrupos.obtener(i);
                if (g.getId().equals(grupoId)) {
                    grupo = g;
                    break;
                }
            }
        }

        if (grupo == null || origen == null || texto == null || texto.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // Enviar y almacenar mensaje
        grupo.enviarMensajeGrupo(origen, texto.trim());

        // Redirigir de nuevo al detalle
        resp.sendRedirect(req.getContextPath()
                + "/inicio.jsp?seccion=gruposDetalle&grupoId="
                + URLEncoder.encode(grupoId,"UTF-8"));
    }
}
