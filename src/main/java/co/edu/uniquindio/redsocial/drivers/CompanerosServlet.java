package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.services.implement.GestorSugerencias;
import co.edu.uniquindio.redsocial.models.services.implement.RedAfinidad;
import co.edu.uniquindio.redsocial.models.services.implement.SistemaAutenticacion;
import co.edu.uniquindio.redsocial.models.services.implement.SistemaRecomendaciones;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Sección "Descubrir" del estudiante: prepara los datos de su red de afinidad.
 * <ul>
 *   <li>Sus conexiones actuales en el grafo.</li>
 *   <li>Sugerencias de compañeros "amigos de amigos" con intereses en común.</li>
 *   <li>Compañeros afines por intereses.</li>
 *   <li>Contenidos recomendados según sus intereses.</li>
 *   <li>Ruta más corta (por conexiones) hacia otro estudiante, si se indica {@code destino}.</li>
 * </ul>
 * Se incluye desde {@code inicio.jsp?seccion=companeros}, que luego muestra {@code companeros.jsp}.
 */
@WebServlet("/companeros")
public class CompanerosServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        Object actual = (session == null) ? null : session.getAttribute("usuarioActual");
        if (!(actual instanceof Estudiante)) {
            return;
        }
        Estudiante yo = (Estudiante) actual;

        RedAfinidad red = RedAfinidad.getInstancia();
        red.agregarEstudiante(yo);
        red.actualizarConexiones();

        req.setAttribute("misConexiones", red.getGrafo().obtenerVecinos(yo));
        req.setAttribute("amigosDeAmigos", new GestorSugerencias(red.getGrafo()).sugerirAmigos(yo));
        req.setAttribute("afines", red.sugerirCompaneros(yo));
        req.setAttribute("recomendados", new SistemaRecomendaciones().recomendarContenidos(yo));

        SistemaAutenticacion auth = (SistemaAutenticacion) getServletContext().getAttribute("sistemaAutenticacion");
        ListaEnlazada<Estudiante> otros = new ListaEnlazada<>();
        if (auth != null) {
            for (Estudiante e : auth.getEstudiantesRegistrados()) {
                if (!e.equals(yo)) otros.agregar(e);
            }
        }
        req.setAttribute("otrosEstudiantes", otros);

        String idDestino = req.getParameter("destino");
        if (idDestino != null && !idDestino.isBlank()) {
            for (Estudiante e : otros) {
                if (e.getId().equals(idDestino)) {
                    req.setAttribute("destinoSeleccionado", e);
                    req.setAttribute("ruta", calcularRuta(red, yo, e));
                    break;
                }
            }
        }
    }

    /** @return Estudiantes de la ruta más corta (incluye origen y destino) o null si no hay camino. */
    private ListaEnlazada<Estudiante> calcularRuta(RedAfinidad red, Estudiante origen, Estudiante destino) {
        try {
            return red.getGrafo().buscarRutaCorta(origen, destino);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
