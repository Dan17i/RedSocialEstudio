package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.GrupoEstudio;
import co.edu.uniquindio.redsocial.models.SolicitudAyuda;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;

@WebServlet("/grupos/detalle/ayuda")
public class SolicitarAyudaGrupoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String grupoId       = req.getParameter("grupoId");
        String temaAyuda     = req.getParameter("temaAyuda");
        String urgenciaStr   = req.getParameter("urgencia");
        String descripcion   = req.getParameter("descripcionAyuda");

        HttpSession session = req.getSession(false);
        Estudiante solicitante = (session != null)
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

        if (grupo == null || solicitante == null
                || temaAyuda == null || urgenciaStr == null || descripcion == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        int urgencia;
        try {
            urgencia = Integer.parseInt(urgenciaStr);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // Crear solicitud y encolar
        SolicitudAyuda sol = new SolicitudAyuda(
                temaAyuda,
                urgencia,
                solicitante,
                descripcion,
                LocalDateTime.now());

        grupo.solicitarAyudaEnGrupo(sol);

        resp.sendRedirect(req.getContextPath()
                + "/inicio.jsp?seccion=gruposDetalle&grupoId="
                + URLEncoder.encode(grupoId,"UTF-8"));
    }
}
