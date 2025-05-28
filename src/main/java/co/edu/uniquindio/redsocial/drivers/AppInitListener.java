package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.models.Contenido;
import co.edu.uniquindio.redsocial.models.Conversacion;
import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.GrupoEstudio;
import co.edu.uniquindio.redsocial.models.services.implement.GestorContenidos;
import co.edu.uniquindio.redsocial.models.services.implement.GestorGrupos;
import co.edu.uniquindio.redsocial.models.services.implement.SistemaAutenticacion;
import co.edu.uniquindio.redsocial.models.structures.ArbolBinarioBusqueda;
import co.edu.uniquindio.redsocial.models.structures.GrafoNoDirigido;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

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
        ListaEnlazada<Contenido> publicaciones = new ListaEnlazada<>();
        sce.getServletContext().setAttribute("publicaciones", publicaciones);

        // 3) Lista global de conversaciones (inicialmente vacía)
        ListaEnlazada<Conversacion> conversaciones = new ListaEnlazada<>();
        sce.getServletContext().setAttribute("conversaciones", conversaciones);

        // 4) Árbol binario global para publicar y filtrar
        ArbolBinarioBusqueda<Contenido> arbolContenidos = new ArbolBinarioBusqueda<>();
        sce.getServletContext().setAttribute("arbolContenidos", arbolContenidos);

        // 5) Instancia del gestor (opcional si lo usas)
        GestorContenidos gestor = GestorContenidos.getInstancia();
        sce.getServletContext().setAttribute("gestorContenidos", gestor);

        // 6) Gestor de grupos con grafo no dirigido
        GrafoNoDirigido<Estudiante> grafoEstudiantes = new GrafoNoDirigido<>();
        GestorGrupos<Estudiante> gestorGrupos = new GestorGrupos<>();
        gestorGrupos.setGrafo(grafoEstudiantes);
        sce.getServletContext().setAttribute("gestorGrupos", gestorGrupos);

        // 7) Lista global de grupos
        ListaEnlazada<GrupoEstudio> todosGrupos = new ListaEnlazada<>();
        sce.getServletContext().setAttribute("todosGrupos", todosGrupos);
    }
    /**
     * Método que se ejecuta cuando el contexto del servlet es destruido.
     * Actualmente, no realiza ninguna acción.
     *
     * @param sce Evento que notifica la destrucción del contexto del servlet.
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // No es necesario liberar nada en este caso
    }
}
