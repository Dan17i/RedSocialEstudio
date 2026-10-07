package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.Moderador;
import co.edu.uniquindio.redsocial.models.Reporte;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Reporte del moderador: estudiantes con más conexiones en el grafo de afinidad.
 */
@WebServlet("/EstudiantesConectadosServlet")
public class EstudiantesConectadosServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object actual = request.getSession().getAttribute("usuarioActual");
        if (!(actual instanceof Moderador)) {
            response.sendRedirect(request.getContextPath() + "/inicioSesion.jsp");
            return;
        }
        Moderador moderador = (Moderador) actual;
        Reporte<Estudiante> reporte = moderador.generarReporteEstudiantesMasConectados();
        request.setAttribute("reporte", reporte);
        request.getRequestDispatcher("/estudiantesConectados.jsp").forward(request, response);
    }
}
