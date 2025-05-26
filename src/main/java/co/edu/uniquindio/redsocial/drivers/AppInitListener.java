package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.models.Contenido;
import co.edu.uniquindio.redsocial.models.services.implement.SistemaAutenticacion;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppInitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        SistemaAutenticacion sistema = new SistemaAutenticacion();
        sce.getServletContext().setAttribute("sistemaAutenticacion", sistema);

        // Lista de contenidos compartida para todos los usuarios
        ListaEnlazada<Contenido> publicaciones = new ListaEnlazada<>();
        sce.getServletContext().setAttribute("publicaciones", publicaciones);
    }


    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Limpieza si es necesario
    }
}
