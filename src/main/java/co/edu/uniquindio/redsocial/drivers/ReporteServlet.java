package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.models.Moderador;
import co.edu.uniquindio.redsocial.models.services.implement.SistemaAutenticacion;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ReporteServlet")
public class ReporteServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Moderador moderador = (Moderador) req.getSession().getAttribute("usuario");
        String tipo = req.getParameter("tipo");

        switch (tipo) {
            case "comunidades":
                req.setAttribute("datos", moderador.generarReporteComunidades());
                break;
            case "contenidosMasValorados":
                req.setAttribute("datos", moderador.generarReporteContenidosMasValorados().getDatos());
                break;
            case "estudiantesMasConectados":
                req.setAttribute("datos", moderador.generarReporteEstudiantesMasConectados().getDatos());
                break;
            case "nivelesParticipacion":
                req.setAttribute("datos", moderador.generarReporteParticipacion().getDatos());
                break;
        }
        req.getRequestDispatcher("reporte.jsp").forward(req, resp);
    }
}

