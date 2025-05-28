package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.models.GrupoEstudio;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/grupos/detalle")
public class DetalleGrupoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String id = req.getParameter("grupoId");
        if (id == null || id.isBlank()) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta parámetro grupoId");
            return;
        }

        @SuppressWarnings("unchecked")
        ListaEnlazada<GrupoEstudio> todosGrupos =
                (ListaEnlazada<GrupoEstudio>) getServletContext().getAttribute("todosGrupos");
        if (todosGrupos == null) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "No se configuró la lista de grupos");
            return;
        }

        GrupoEstudio encontrado = null;
        for (int i = 0; i < todosGrupos.getTamanio(); i++) {
            GrupoEstudio g = todosGrupos.obtener(i);
            if (g.getId().equals(id)) {
                encontrado = g;
                break;
            }
        }

        if (encontrado == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND,
                    "No se encontró el grupo especificado");
            return;
        }

        req.setAttribute("grupo", encontrado);
        req.getRequestDispatcher("/grupos/detalle.jsp")
                .forward(req, resp);
    }
}
