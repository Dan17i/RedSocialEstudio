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

@WebServlet("/grupos/sugeridos")
public class SugerirGruposServlet extends HttpServlet {
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
