package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.Moderador;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ComunidadesServlet extends HttpServlet {
    private Moderador moderador;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ListaEnlazada<ListaEnlazada<Estudiante>> comunidades = moderador.generarReporteComunidades();
        request.setAttribute("comunidades", comunidades);
        request.getRequestDispatcher("/comunidades.jsp").forward(request, response);
    }

    public void setModerador(Moderador moderador) {
        this.moderador = moderador;
    }
}
