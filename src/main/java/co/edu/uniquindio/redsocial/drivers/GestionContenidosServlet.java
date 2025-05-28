package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.models.services.implement.GestorContenidos;
import co.edu.uniquindio.redsocial.models.services.interf.IGestorUsuarios;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/GestionContenidosServlet")
public class GestionContenidosServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GestorContenidos gestorContenidos = GestorContenidos.getInstancia();
        req.setAttribute("contenidos", gestorContenidos.obtenerTodosLosContenidos());
        req.getRequestDispatcher("gestionContenidos.jsp").forward(req, resp);
    }
}
