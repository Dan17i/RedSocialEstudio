package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.models.GrupoEstudio;
import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/grupos/detalle/mensaje")
public class ChatGrupoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String grupoId = req.getParameter("grupoId");
        String texto = req.getParameter("texto");

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

        Estudiante remitente = (Estudiante) req.getSession().getAttribute("usuarioActual");
        if (grupo != null && remitente != null && texto != null && !texto.isBlank()) {
            grupo.enviarMensajeGrupo(remitente, texto);
        }

        resp.sendRedirect(req.getContextPath() +
                "/grupos/detalle?id=" + grupoId + "#chat");
    }
}
