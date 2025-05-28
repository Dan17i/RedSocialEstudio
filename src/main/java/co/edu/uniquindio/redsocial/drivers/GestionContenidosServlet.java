package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.models.Contenido;
import co.edu.uniquindio.redsocial.models.Moderador;
import co.edu.uniquindio.redsocial.models.services.implement.GestorContenidos;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/GestionContenidosServlet")
public class GestionContenidosServlet extends HttpServlet {
    private GestorContenidos gestorContenidos;

    @Override
    public void init() throws ServletException {
        super.init();
        gestorContenidos = GestorContenidos.getInstancia();
        if (gestorContenidos == null) {
            throw new ServletException("No se pudo inicializar GestorContenidos");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Moderador moderador = (Moderador) request.getSession().getAttribute("usuario");
        if (moderador == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No se ha iniciado sesión como moderador");
            return;
        }
        ListaEnlazada<Contenido> contenidos = gestorContenidos.obtenerTodosLosContenidos();
        request.setAttribute("contenidos", contenidos);
        request.getRequestDispatcher("/gestionContenidos.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Moderador moderador = (Moderador) request.getSession().getAttribute("usuario");
        if (moderador == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No se ha iniciado sesión como moderador");
            return;
        }
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
}