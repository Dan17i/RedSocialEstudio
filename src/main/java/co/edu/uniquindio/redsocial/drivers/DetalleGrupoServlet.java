// src/main/java/co/edu/uniquindio/redsocial/drivers/DetalleGrupoServlet.java
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

        // 1) Leer el parámetro "id" de la URL
        String id = req.getParameter("id");
        if (id == null || id.isBlank()) {
            // No hay id → no seteamos nada y el JSP puede mostrar mensaje
            return;
        }

        // 2) Recuperar la lista donde guardaste tus grupos (sugeridos o misGrupos)
        HttpSession session = req.getSession(false);
        @SuppressWarnings("unchecked")
        ListaEnlazada<GrupoEstudio> grupos =
                session != null
                        ? (ListaEnlazada<GrupoEstudio>) session.getAttribute("sugeridos")
                        : null;

        // Si no viene por sugeridos, tal vez lo guardaste en "misGrupos"
        if (grupos == null && session != null) {
            @SuppressWarnings("unchecked")
            ListaEnlazada<GrupoEstudio> otro =
                    (ListaEnlazada<GrupoEstudio>) session.getAttribute("misGrupos");
            grupos = otro;
        }

        // 3) Buscar el grupo con ese id
        GrupoEstudio encontrado = null;
        if (grupos != null) {
            for (int i = 0; i < grupos.getTamanio(); i++) {
                GrupoEstudio g = grupos.obtener(i);
                if (id.equals(g.getId())) {
                    encontrado = g;
                    break;
                }
            }
        }

        // 4) Poner en request para que detalle.jsp lo use
        req.setAttribute("grupo", encontrado);
        // no forward aquí: como usamos .include en inicio.jsp, basta con setearlo
    }
}
