package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.models.Moderador;
import co.edu.uniquindio.redsocial.models.Reporte;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet("/ParticipacionServlet")
public class ParticipacionServlet extends HttpServlet {
    private Moderador moderador;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Moderador moderador = (Moderador) request.getSession().getAttribute("usuarioActual");

        if (moderador == null) {
            response.sendRedirect("inicioSesion.jsp"); //  redirige a moderador.jsp con un mensaje
            return;
        }

        ListaEnlazada participacion = moderador.generarReporteParticipacion().getDatos();
        request.setAttribute("participacion", participacion);
        request.getRequestDispatcher("/participacion.jsp").forward(request, response);
    }
    public void setModerador(Moderador moderador) {
        this.moderador = moderador;
    }
}
