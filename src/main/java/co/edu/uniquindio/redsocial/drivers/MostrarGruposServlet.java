// src/main/java/co/edu/uniquindio/redsocial/drivers/MostrarGruposServlet.java
package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.GrupoEstudio;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

import javax.servlet.DispatcherType;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/grupos")
public class MostrarGruposServlet extends HttpServlet {
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

        // 1) Pongo directamente tu ListaEnlazada de grupos del estudiante
        ListaEnlazada<GrupoEstudio> misGrupos = est.getGruposEstudio();
        req.setAttribute("misGrupos", misGrupos);

        // 2) Si me llamaron mediante include(), salgo (no forward)
        if (req.getDispatcherType() == DispatcherType.INCLUDE) {
            return;
        }

        // 3) Si acceden directo a /grupos en el navegador, muestro el JSP (opcional)
        req.getRequestDispatcher("/grupos/lista.jsp").forward(req, resp);

    }
}
