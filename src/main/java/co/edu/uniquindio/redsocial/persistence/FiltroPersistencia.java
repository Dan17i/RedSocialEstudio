package co.edu.uniquindio.redsocial.persistence;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Guarda el estado después de cada petición que puede modificar datos (cualquiera que no sea GET/HEAD/OPTIONS).
 * Así no hace falta tocar cada servlet: todas las modificaciones de la aplicación se hacen con formularios POST.
 * Si el guardado falla se registra el error pero no se rompe la respuesta al usuario; como la persistencia
 * recuerda lo último que pudo guardar, el siguiente guardado vuelve a intentar lo pendiente.
 */
@WebFilter("/*")
public class FiltroPersistencia implements Filter {

    private static final Logger LOG = Logger.getLogger(FiltroPersistencia.class.getName());

    /** @return true si el método HTTP puede modificar datos. */
    public static boolean modifica(String metodo) {
        return !("GET".equalsIgnoreCase(metodo) || "HEAD".equalsIgnoreCase(metodo)
                || "OPTIONS".equalsIgnoreCase(metodo));
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        try {
            chain.doFilter(req, res);
        } finally {
            if (modifica(((HttpServletRequest) req).getMethod())) {
                guardar(req.getServletContext());
            }
        }
    }

    private void guardar(ServletContext contexto) {
        Persistencia persistencia = (Persistencia) contexto.getAttribute("persistencia");
        EstadoAplicacion estado = (EstadoAplicacion) contexto.getAttribute("estadoAplicacion");
        if (persistencia == null || estado == null) return;
        try {
            persistencia.guardar(estado);
        } catch (RuntimeException e) {
            LOG.log(Level.SEVERE, "No se pudo guardar el estado; se reintentará en la próxima modificación", e);
        }
    }
}
