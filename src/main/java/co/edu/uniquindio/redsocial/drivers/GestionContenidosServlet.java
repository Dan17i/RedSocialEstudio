package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.models.Contenido;
import co.edu.uniquindio.redsocial.models.Moderador;
import co.edu.uniquindio.redsocial.models.services.implement.GestorContenidos;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GestionContenidosServlet extends HttpServlet {
    private GestorContenidos gestorContenidos;
    private Moderador moderador;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ListaEnlazada<Contenido> contenidos = gestorContenidos.obtenerTodosLosContenidos();
        request.setAttribute("contenidos", contenidos);
        request.getRequestDispatcher("/gestionContenidos.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        String contenidoId = request.getParameter("contenidoId");

        if ("eliminar".equals(accion)) {
            ListaEnlazada<Contenido> contenidos = gestorContenidos.obtenerTodosLosContenidos();
            for (int i = 0; i < contenidos.getTamanio(); i++) {
                Contenido c = contenidos.obtener(i);
                if (c.getId().equals(contenidoId)) {
                    moderador.eliminarContenido(c);
                    response.sendRedirect("GestionContenidosServlet?success=contenido_eliminado");
                    return;
                }
            }
            response.sendRedirect("GestionContenidosServlet?error=contenido_no_encontrado");
        }
    }

    public void setGestorContenidos(GestorContenidos gestorContenidos) {
        this.gestorContenidos = gestorContenidos;
    }

    public void setModerador(Moderador moderador) {
        this.moderador = moderador;
    }
}
