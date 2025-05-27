package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.models.SolicitudAyuda;
import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.GrupoEstudio;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/grupos/detalle/ayuda")
public class AyudaGrupoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String grupoId    = req.getParameter("grupoId");
        String tema       = req.getParameter("temaAyuda");
        int urgencia      = Integer.parseInt(req.getParameter("urgencia"));
        String descripcion = req.getParameter("descripcionAyuda");

        @SuppressWarnings("unchecked")
        ListaEnlazada<GrupoEstudio> todos =
                (ListaEnlazada<GrupoEstudio>) getServletContext().getAttribute("todosGrupos");

        GrupoEstudio grupo = null;
        for (int i = 0; i < todos.getTamanio(); i++) {
            if (todos.obtener(i).getId().equals(grupoId)) {
                grupo = todos.obtener(i);
                break;
            }
        }

        Estudiante estudiante = (Estudiante) req.getSession().getAttribute("usuarioActual");
        if (grupo != null && estudiante != null) {
            SolicitudAyuda s = new SolicitudAyuda(tema, urgencia, estudiante, descripcion);
            grupo.solicitarAyudaEnGrupo(s);
        }

        resp.sendRedirect(req.getContextPath() +
                "/grupos/detalle?id=" + grupoId + "#ayuda");
    }
}
