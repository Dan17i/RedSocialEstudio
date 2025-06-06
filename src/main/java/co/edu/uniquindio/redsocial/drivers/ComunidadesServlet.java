package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.Moderador;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet ("/ComunidadesServlet")
public class ComunidadesServlet extends HttpServlet {
    private Moderador moderador;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Moderador moderador = (Moderador) request.getSession().getAttribute("usuarioActual");

        if (moderador == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        ListaEnlazada<ListaEnlazada<Estudiante>> comunidades = moderador.generarReporteComunidades();
        request.setAttribute("comunidades", comunidades);
        request.getRequestDispatcher("/comunidades.jsp").forward(request, response);
    }

    public void setModerador(Moderador moderador) {
        this.moderador = moderador;
    }
}
