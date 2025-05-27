// src/main/java/co/edu/uniquindio/redsocial/drivers/UnirseGrupoServlet.java
package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.GrupoEstudio;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/grupos/unirse")
public class UnirseGrupoServlet extends HttpServlet {
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
