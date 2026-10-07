package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.models.Enums.EstadoSolicitud;
import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.SolicitudAyuda;
import co.edu.uniquindio.redsocial.models.services.implement.SistemaAyuda;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

/**
 * Solicitudes de ayuda académica gestionadas por prioridad (urgencia 1 = más urgente, 10 = menos).
 * <ul>
 *   <li>GET (incluido desde {@code inicio.jsp?seccion=ayuda}): deja en la petición las solicitudes
 *       del estudiante y la cola global de pendientes, ordenada por urgencia.</li>
 *   <li>POST {@code accion=crear}: registra una solicitud nueva (cola personal y cola global).</li>
 *   <li>POST {@code accion=atender}: el estudiante toma una solicitud pendiente de otra persona.</li>
 *   <li>POST {@code accion=resolver}: quien pidió la ayuda la marca como resuelta.</li>
 * </ul>
 */
@WebServlet("/ayuda")
public class AyudaServlet extends HttpServlet {

    private static final String ATRIBUTO_SISTEMA = "sistemaAyuda";

    private SistemaAyuda sistemaAyuda() {
        SistemaAyuda sistema = (SistemaAyuda) getServletContext().getAttribute(ATRIBUTO_SISTEMA);
        if (sistema == null) {
            synchronized (this) {
                sistema = (SistemaAyuda) getServletContext().getAttribute(ATRIBUTO_SISTEMA);
                if (sistema == null) {
                    sistema = new SistemaAyuda();
                    getServletContext().setAttribute(ATRIBUTO_SISTEMA, sistema);
                }
            }
        }
        return sistema;
    }

    private Estudiante estudianteEnSesion(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        Object actual = (session == null) ? null : session.getAttribute("usuarioActual");
        return (actual instanceof Estudiante) ? (Estudiante) actual : null;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Estudiante yo = estudianteEnSesion(req);
        if (yo == null) {
            return;
        }
        req.setAttribute("misSolicitudes", yo.getSolicitudesAyuda().aLista());
        req.setAttribute("solicitudesPendientes", sistemaAyuda().obtenerSolicitudesPendientes());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Estudiante yo = estudianteEnSesion(req);
        if (yo == null) {
            resp.sendRedirect(req.getContextPath() + "/inicioSesion.jsp");
            return;
        }

        String accion = req.getParameter("accion");
        String mensaje;
        boolean exito = true;
        try {
            if ("crear".equals(accion)) {
                mensaje = crear(req, yo);
            } else if ("atender".equals(accion)) {
                mensaje = atender(req, yo);
            } else if ("resolver".equals(accion)) {
                mensaje = resolver(req, yo);
            } else {
                mensaje = "Acción no reconocida";
                exito = false;
            }
        } catch (IllegalArgumentException e) {
            mensaje = e.getMessage();
            exito = false;
        }

        resp.sendRedirect(req.getContextPath() + "/inicio.jsp?seccion=ayuda&ok=" + exito
                + "&msg=" + URLEncoder.encode(mensaje, StandardCharsets.UTF_8));
    }

    private String crear(HttpServletRequest req, Estudiante yo) {
        String tema = req.getParameter("tema");
        String descripcion = req.getParameter("descripcion");
        if (tema == null || tema.isBlank()) {
            throw new IllegalArgumentException("Indica el tema en el que necesitas ayuda");
        }
        int urgencia;
        try {
            urgencia = Integer.parseInt(req.getParameter("urgencia"));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("La urgencia debe ser un número entre 1 y 10");
        }
        SolicitudAyuda solicitud = new SolicitudAyuda(tema.trim(), urgencia, yo,
                descripcion == null ? "" : descripcion.trim(), LocalDateTime.now());
        yo.solicitarAyuda(solicitud);
        sistemaAyuda().agregarSolicitud(solicitud);
        return "Solicitud registrada: te atenderán según su urgencia (" + urgencia + ")";
    }

    private String atender(HttpServletRequest req, Estudiante yo) {
        SolicitudAyuda atendida = sistemaAyuda().atenderSolicitud(req.getParameter("id"), yo);
        if (atendida == null) {
            throw new IllegalArgumentException("Esa solicitud ya no está pendiente");
        }
        return "Ahora ayudas a " + atendida.getEstudiante().getNombre() + " con \""
                + atendida.getTema() + "\". Contáctale desde Chats.";
    }

    private String resolver(HttpServletRequest req, Estudiante yo) {
        String id = req.getParameter("id");
        for (SolicitudAyuda s : yo.getSolicitudesAyuda().aLista()) {
            if (s.getId().equals(id)) {
                s.setEstado(EstadoSolicitud.RESUELTA);
                return "Solicitud marcada como resuelta";
            }
        }
        throw new IllegalArgumentException("No se encontró esa solicitud");
    }
}
