package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.models.Contenido;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
/**
 * Servlet que muestra una publicación específica según su ID.
 *
 * <p>
 * - Obtiene el parámetro "id" de la solicitud.
 * - Busca la publicación en la lista global almacenada en contexto.
 * - Si no existe, redirige a página de error.
 * - Si existe, envía la publicación a la vista para mostrarla.
 * </p>
 *
 * Ruta: /Publicacion
 *
 * @author Daniel Jurado, Sebastian Torres y Juan Soto
 * @version 1.0
 */
@WebServlet("/Publicacion")
public class PublicacionServlet extends HttpServlet {
    /**
     * Maneja solicitudes GET para mostrar una publicación por su ID.
     *
     * @param req  solicitud HTTP
     * @param resp respuesta HTTP
     * @throws ServletException si ocurre un error en el servlet
     * @throws IOException      si ocurre un error de E/S
     */
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
