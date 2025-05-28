package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.models.Contenido;
import co.edu.uniquindio.redsocial.models.Moderador;
import co.edu.uniquindio.redsocial.models.Reporte;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ContenidosValoradosServlet extends HttpServlet {
    private Moderador moderador;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Reporte<Contenido> reporte = moderador.generarReporteContenidosMasValorados();
        request.setAttribute("reporte", reporte);
        request.getRequestDispatcher("/contenidosValorados.jsp").forward(request, response);
    }

    public void setModerador(Moderador moderador) {
        this.moderador = moderador;
    }
}
