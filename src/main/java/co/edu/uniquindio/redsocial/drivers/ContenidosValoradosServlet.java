package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.models.Contenido;
import co.edu.uniquindio.redsocial.models.Moderador;
import co.edu.uniquindio.redsocial.models.Reporte;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ContenidosValoradosServlet")
public class ContenidosValoradosServlet extends HttpServlet {
    private Moderador moderador;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Moderador moderador = (Moderador) request.getSession().getAttribute("usuarioActual");

        if (moderador == null) {
            response.sendRedirect("login.jsp"); // O redirige a moderador.jsp con un mensaje
            return;
        }

        Reporte<Contenido> reporte = moderador.generarReporteContenidosMasValorados();

        if (reporte != null) {
            request.setAttribute("resumen", reporte.getResumen());
            request.setAttribute("contenidos", reporte.getDatos());
        }

        request.getRequestDispatcher("/contenidosValorados.jsp").forward(request, response);
    }


    public void setModerador(Moderador moderador) {
        this.moderador = moderador;
    }
}
