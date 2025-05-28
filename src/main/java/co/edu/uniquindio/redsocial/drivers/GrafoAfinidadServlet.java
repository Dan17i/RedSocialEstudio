package co.edu.uniquindio.redsocial.drivers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.servlet.RequestDispatcher;

import co.edu.uniquindio.redsocial.models.services.implement.SistemaAutenticacion;
import co.edu.uniquindio.redsocial.models.services.implement.RedAfinidad;
import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

@WebServlet("/GrafoAfinidadServlet")
public class GrafoAfinidadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Recuperar todos los estudiantes y la instancia del grafo
        SistemaAutenticacion sistemaAuth = (SistemaAutenticacion)
                getServletContext().getAttribute("sistemaAutenticacion");
        ListaEnlazada<Estudiante> estudiantes =
                sistemaAuth.getEstudiantesRegistrados();

        RedAfinidad redAfinidad = RedAfinidad.getInstancia();

        // Pasar al JSP
        req.setAttribute("estudiantes", estudiantes);
        req.setAttribute("redAfinidad", redAfinidad);

        RequestDispatcher rd = req.getRequestDispatcher("grafoAfinidad.jsp");
        rd.forward(req, resp);
    }
}
