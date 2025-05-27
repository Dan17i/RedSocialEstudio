// src/main/java/co/edu/uniquindio/redsocial/drivers/SugerirGruposServlet.java
package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.models.GrupoEstudio;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

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
        @SuppressWarnings("unchecked")
        ListaEnlazada<GrupoEstudio> sugeridos =
                (session != null)
                        ? (ListaEnlazada<GrupoEstudio>)  session.getAttribute("sugeridos")
                        : null;

        if (sugeridos == null) {
            sugeridos = new ListaEnlazada<>();
        }

        req.setAttribute("sugeridos", sugeridos);
        // No hago forward al JSP aquí porque inicio.jsp usará:
        // request.getRequestDispatcher("/grupos/sugeridos").include(...)
        // <jsp:include page="grupos/sugeridos.jsp" />
    }
}
