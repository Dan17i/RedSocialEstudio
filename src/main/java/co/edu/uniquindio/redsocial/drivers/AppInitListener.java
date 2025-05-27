package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.models.Contenido;
import co.edu.uniquindio.redsocial.models.Conversacion;
import co.edu.uniquindio.redsocial.models.services.implement.GestorContenidos;
import co.edu.uniquindio.redsocial.models.services.implement.SistemaAutenticacion;
import co.edu.uniquindio.redsocial.models.structures.ArbolBinarioBusqueda;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Listener que inicializa en el ServletContext:
 *  - El sistema de autenticación
 *  - La lista global de publicaciones
 *  - La lista global de conversaciones (vacía)
 */
@WebListener
public class AppInitListener implements ServletContextListener {

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

        // Árbol binario global para publicar y filtrar
        ArbolBinarioBusqueda<Contenido> arbolContenidos = new ArbolBinarioBusqueda<>();
        sce.getServletContext().setAttribute("arbolContenidos", arbolContenidos);

        // Instancia del gestor (opcional si lo usas)
        GestorContenidos gestor = GestorContenidos.getInstancia();
        sce.getServletContext().setAttribute("gestorContenidos", gestor);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // No es necesario liberar nada en este caso
    }
}
