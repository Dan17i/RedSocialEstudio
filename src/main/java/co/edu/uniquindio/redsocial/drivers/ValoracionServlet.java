package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.models.Contenido;
import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.Valoracion;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
/**
 * Servlet que permite a un estudiante valorar una publicación.
 *
 * <p>
 * Recibe la identificación de la publicación, la puntuación y el comentario
 * mediante una solicitud POST, y registra la valoración asociada al estudiante
 * y la publicación correspondiente.
 * </p>
 *
 * Ruta: /Valorar
 *
 * @author Daniel Jurado, Sebastian Torres y Juan Soto
 * @since 2025-05-25
 * @version 1.0
 */
@WebServlet("/Valorar")
public class ValoracionServlet extends HttpServlet {
    /**
     * Procesa solicitudes POST para agregar una valoración a una publicación.
     *
     * @param request  solicitud HTTP con parámetros: idContenido, puntuacion y comentario
     * @param response respuesta HTTP que redirige a la página principal
     * @throws ServletException en caso de error en el servlet
     * @throws IOException      en caso de error de entrada/salida
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Estudiante est = (Estudiante) request.getSession().getAttribute("usuarioActual");
        if (est == null) {
            response.sendRedirect("inicioSesion.jsp");
            return;
        }

        String id   = request.getParameter("idContenido");
        int    pts  = Integer.parseInt(request.getParameter("puntuacion"));
        String cm   = request.getParameter("comentario");

        @SuppressWarnings("unchecked")
        ListaEnlazada<Contenido> pubs =
                (ListaEnlazada<Contenido>) getServletContext().getAttribute("publicaciones");

        Contenido target = null;
        for (int i=0; i<pubs.getTamanio(); i++) {
            if (pubs.obtener(i).getId().equals(id)) {
                target = pubs.obtener(i);
                break;
            }
        }

        if (target != null) {
            Valoracion v = new Valoracion(est, target, pts, cm);
            target.agregarValoracion(v);
        }

        response.sendRedirect("inicio.jsp?seccion=home");
    }
}
