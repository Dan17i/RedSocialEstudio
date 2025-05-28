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

        String grupoId = req.getParameter("grupoId");
        HttpSession session = req.getSession();
        Estudiante actual = (Estudiante) session.getAttribute("usuarioActual");

        @SuppressWarnings("unchecked")
        ListaEnlazada<GrupoEstudio> todos =
                (ListaEnlazada<GrupoEstudio>) getServletContext()
                        .getAttribute("todosGrupos");

        if (actual != null && todos != null && grupoId != null) {
            for (int i = 0; i < todos.getTamanio(); i++) {
                GrupoEstudio g = todos.obtener(i);
                if (grupoId.equals(g.getId())) {
                    g.agregarMiembro(actual);
                    break;
                }
            }
        }

        resp.sendRedirect(req.getContextPath() + "/inicio.jsp?seccion=grupos");
    }
}
