package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.models.services.implement.SistemaAutenticacion;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppInitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        SistemaAutenticacion sistema = new SistemaAutenticacion();
        sce.getServletContext().setAttribute("sistemaAutenticacion", sistema);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Limpieza si es necesario
    }
}
