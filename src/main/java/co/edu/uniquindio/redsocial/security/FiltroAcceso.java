package co.edu.uniquindio.redsocial.security;

import co.edu.uniquindio.redsocial.models.Moderador;
import co.edu.uniquindio.redsocial.models.Usuario;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Filtro de control de acceso para toda la aplicación.
 * <ul>
 *   <li>Rutas públicas (login, registro, recursos estáticos): acceso libre.</li>
 *   <li>Rutas de moderador (paneles, reportes, gestión, grafo): solo {@link Moderador}.</li>
 *   <li>Cualquier otra ruta: requiere haber iniciado sesión.</li>
 * </ul>
 * Además rechaza los POST cuyo origen no sea el propio servidor (mitigación de CSRF).
 * Los {@code forward} internos no pasan por el filtro (solo se filtran peticiones directas del cliente).
 */
@WebFilter("/*")
public class FiltroAcceso implements Filter {

    /** Resultado de evaluar el acceso a una ruta. */
    public enum Decision { PERMITIR, REQUIERE_LOGIN, PROHIBIDO }

    private static final String[] RUTAS_PUBLICAS = {
            "/", "/index.jsp", "/inicioSesion.jsp", "/registro.jsp",
            "/login", "/Registro", "/CerrarSesionServlet", "/styles.css", "/favicon.ico"
    };

    private static final String[] PREFIJOS_PUBLICOS = {"/css/", "/images/"};

    private static final String[] RUTAS_MODERADOR = {
            "/moderador.jsp", "/rutaMasCorta.jsp", "/gestionarUsuarios.jsp", "/gestionContenido.jsp",
            "/grafoAfinidad.jsp", "/comunidades.jsp", "/contenidosValorados.jsp", "/participacion.jsp",
            "/estudiantesConectados.jsp",
            "/ComunidadesServlet", "/ContenidosValoradosServlet", "/ParticipacionServlet", "/RutaMasCortaServlet",
            "/GestionContenidosServlet", "/GestionUsuariosServlet", "/GrafoAfinidadServlet",
            "/GenerarDatosServlet", "/EstudiantesConectadosServlet"
    };

    /**
     * Decide si un usuario (o ausencia de él) puede acceder a una ruta.
     *
     * @param ruta    Ruta relativa al contexto de la aplicación (por ejemplo {@code /reporte.jsp}).
     * @param usuario Usuario en sesión, o null si no ha iniciado sesión.
     */
    public static Decision evaluar(String ruta, Usuario usuario) {
        String r = (ruta == null || ruta.isEmpty()) ? "/" : ruta;
        if (esPublica(r)) return Decision.PERMITIR;
        if (usuario == null) return Decision.REQUIERE_LOGIN;
        if (esDeModerador(r) && !(usuario instanceof Moderador)) return Decision.PROHIBIDO;
        return Decision.PERMITIR;
    }

    /**
     * Mitigación de CSRF: una petición que modifica estado (POST/PUT/DELETE) solo se acepta si su
     * cabecera Origin (o, si falta, Referer) pertenece al mismo servidor que atiende la petición.
     * Si el cliente no envía ninguna de las dos cabeceras no se puede comprobar y se permite.
     *
     * @param metodo  Método HTTP.
     * @param origin  Cabecera Origin (puede ser null).
     * @param referer Cabecera Referer (puede ser null).
     * @param host    Cabecera Host de la petición (host:puerto).
     * @return true si la petición es aceptable.
     */
    public static boolean origenValido(String metodo, String origin, String referer, String host) {
        if ("GET".equalsIgnoreCase(metodo) || "HEAD".equalsIgnoreCase(metodo)
                || "OPTIONS".equalsIgnoreCase(metodo)) {
            return true;
        }
        String cabecera = (origin != null && !origin.isBlank() && !"null".equals(origin)) ? origin : referer;
        if (cabecera == null || cabecera.isBlank()) return true;
        try {
            String autoridad = new URI(cabecera).getAuthority();
            return autoridad != null && host != null && autoridad.equalsIgnoreCase(host);
        } catch (URISyntaxException e) {
            return false;
        }
    }

    private static boolean esPublica(String ruta) {
        for (String p : RUTAS_PUBLICAS) if (p.equals(ruta)) return true;
        for (String p : PREFIJOS_PUBLICOS) if (ruta.startsWith(p)) return true;
        return false;
    }

    private static boolean esDeModerador(String ruta) {
        for (String p : RUTAS_MODERADOR) if (p.equals(ruta)) return true;
        return false;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        if (!origenValido(request.getMethod(), request.getHeader("Origin"),
                request.getHeader("Referer"), request.getHeader("Host"))) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Origen de la petición no permitido");
            return;
        }

        String ruta = request.getRequestURI().substring(request.getContextPath().length());
        HttpSession session = request.getSession(false);
        Object actual = (session == null) ? null : session.getAttribute("usuarioActual");
        Usuario usuario = (actual instanceof Usuario) ? (Usuario) actual : null;

        switch (evaluar(ruta, usuario)) {
            case PERMITIR:
                chain.doFilter(req, res);
                break;
            case REQUIERE_LOGIN:
                response.sendRedirect(request.getContextPath() + "/inicioSesion.jsp");
                break;
            default:
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Acceso solo para moderadores");
        }
    }
}
