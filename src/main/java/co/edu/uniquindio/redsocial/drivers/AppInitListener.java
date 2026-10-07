package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.models.Conversacion;
import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.GrupoEstudio;
import co.edu.uniquindio.redsocial.models.services.implement.GestorContenidos;
import co.edu.uniquindio.redsocial.models.services.implement.GestorGrupos;
import co.edu.uniquindio.redsocial.models.services.implement.RedAfinidad;
import co.edu.uniquindio.redsocial.models.services.implement.SistemaAyuda;
import co.edu.uniquindio.redsocial.models.services.implement.SistemaAutenticacion;
import co.edu.uniquindio.redsocial.models.structures.GrafoNoDirigido;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import co.edu.uniquindio.redsocial.persistence.ConfigPersistencia;
import co.edu.uniquindio.redsocial.persistence.EstadoAplicacion;
import co.edu.uniquindio.redsocial.persistence.Persistencia;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Listener para la inicialización del contexto de la aplicación web.
 * Al iniciar el contexto del servlet, crea e inicializa los objetos globales
 * necesarios para el funcionamiento del sistema, tales como:
 * <ul>
 *   <li>Sistema de autenticación</li>
 *   <li>Lista global de publicaciones</li>
 *   <li>Lista global de conversaciones</li>
 *   <li>Árbol binario para manejar contenidos</li>
 *   <li>Gestor de contenidos</li>
 *   <li>Gestor de grupos con grafo no dirigido de estudiantes</li>
 *   <li>Lista global de grupos de estudio</li>
 * </ul>
 * Estos objetos se almacenan como atributos en el ServletContext para ser accesibles
 * durante todo el ciclo de vida de la aplicación web.
 * @author Daniel Jurado, Sebasian Torres y Juan Soto
 * @since 2025-05-23
 */
@WebListener
public class AppInitListener implements ServletContextListener {
    /**
     * Método que se ejecuta al inicializar el contexto de la aplicación.
     * Inicializa y registra en el contexto los objetos y estructuras globales.
     *
     * @param sce Evento que notifica la inicialización del contexto del servlet.
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // 1) Sistema de autenticación
        SistemaAutenticacion sistema = new SistemaAutenticacion();
        sce.getServletContext().setAttribute("sistemaAutenticacion", sistema);

        // 2) Lista global de publicaciones
        // (misma lista que mantiene el gestor de contenidos: una sola fuente de verdad)
        GestorContenidos gestor = GestorContenidos.getInstancia();
        sce.getServletContext().setAttribute("publicaciones", gestor.obtenerTodosLosContenidos());

        // 3) Lista global de conversaciones (inicialmente vacía)
        ListaEnlazada<Conversacion> conversaciones = new ListaEnlazada<>();
        sce.getServletContext().setAttribute("conversaciones", conversaciones);

        // 4) Árbol binario global para publicar y filtrar
        sce.getServletContext().setAttribute("arbolContenidos", gestor.getArbolContenidos());

        // 5) Instancia del gestor
        sce.getServletContext().setAttribute("gestorContenidos", gestor);

        // 6) Gestor de grupos con grafo no dirigido
        // Mismo grafo de afinidad que usan el moderador y la visualización
        GrafoNoDirigido<Estudiante> grafoEstudiantes = RedAfinidad.getInstancia().getGrafo();
        GestorGrupos<Estudiante> gestorGrupos = new GestorGrupos<>();
        gestorGrupos.setGrafo(grafoEstudiantes);
        sce.getServletContext().setAttribute("gestorGrupos", gestorGrupos);

        // 7) Cola global de solicitudes de ayuda (por urgencia)
        SistemaAyuda sistemaAyuda = new SistemaAyuda();
        sce.getServletContext().setAttribute("sistemaAyuda", sistemaAyuda);

        // 8) Lista global de grupos
        ListaEnlazada<GrupoEstudio> todosGrupos = new ListaEnlazada<>();
        sce.getServletContext().setAttribute("todosGrupos", todosGrupos);

        // 9) Persistencia: carga lo guardado en la base de datos (si MongoDB no responde, el contexto
        //    no arranca) y deja disponible el estado para que FiltroPersistencia lo guarde.
        EstadoAplicacion estado = new EstadoAplicacion(sistema, gestor, RedAfinidad.getInstancia(),
                sistemaAyuda, todosGrupos, conversaciones);
        Persistencia persistencia = ConfigPersistencia.crear();
        persistencia.cargar(estado);
        sce.getServletContext().setAttribute("persistencia", persistencia);
        sce.getServletContext().setAttribute("estadoAplicacion", estado);
    }
    /**
     * Método que se ejecuta cuando el contexto del servlet es destruido:
     * guarda por última vez el estado y cierra la conexión con la base de datos.
     *
     * @param sce Evento que notifica la destrucción del contexto del servlet.
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Persistencia persistencia = (Persistencia) sce.getServletContext().getAttribute("persistencia");
        EstadoAplicacion estado = (EstadoAplicacion) sce.getServletContext().getAttribute("estadoAplicacion");
        if (persistencia == null) return;
        try {
            if (estado != null) persistencia.guardar(estado);
        } catch (RuntimeException e) {
            sce.getServletContext().log("No se pudo guardar el estado al detener la aplicación", e);
        } finally {
            persistencia.cerrar();
        }
    }
}
