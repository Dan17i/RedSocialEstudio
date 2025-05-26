package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.models.Contenido;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/Publicacion")
public class PublicacionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String id = req.getParameter("id");
        if (id==null) { resp.sendRedirect("inicio.jsp?seccion=home"); return; }

        @SuppressWarnings("unchecked")
        ListaEnlazada<Contenido> pubs =
                (ListaEnlazada<Contenido>) getServletContext().getAttribute("publicaciones");

        Contenido c = null;
        for (int i=0; i<pubs.getTamanio(); i++) {
            if (pubs.obtener(i).getId().equals(id)) {
                c = pubs.obtener(i);
                break;
            }
        }

        if (c==null) {
            req.setAttribute("error","No existe esa publicación");
            req.getRequestDispatcher("error.jsp").forward(req,resp);
            return;
        }

        req.setAttribute("contenido", c);
        req.getRequestDispatcher("publicacion.jsp").forward(req, resp);
    }
}
