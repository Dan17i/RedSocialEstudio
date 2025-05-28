package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.models.Moderador;
import co.edu.uniquindio.redsocial.models.Reporte;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ParticipacionServlet extends HttpServlet {
    private Moderador moderador;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Reporte<String> reporte = moderador.generarReporteParticipacion();
        request.setAttribute("reporte", reporte);
        request.getRequestDispatcher("/participacion.jsp").forward(request, response);
    }

    public void setModerador(Moderador moderador) {
        this.moderador = moderador;
    }
}
