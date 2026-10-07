package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.Moderador;
import co.edu.uniquindio.redsocial.models.services.implement.RedAfinidad;
import co.edu.uniquindio.redsocial.models.services.implement.SistemaAutenticacion;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Reporte del moderador: camino más corto (en número de conexiones) entre dos estudiantes.
 * Sin parámetros muestra el formulario; con {@code origen} y {@code destino} (ids) calcula la ruta.
 */
@WebServlet("/RutaMasCortaServlet")
public class RutaMasCortaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Object actual = req.getSession().getAttribute("usuarioActual");
        if (!(actual instanceof Moderador)) {
            resp.sendRedirect(req.getContextPath() + "/inicioSesion.jsp");
            return;
        }
        Moderador moderador = (Moderador) actual;

        SistemaAutenticacion auth = (SistemaAutenticacion) getServletContext().getAttribute("sistemaAutenticacion");
        ListaEnlazada<Estudiante> estudiantes = (auth == null) ? new ListaEnlazada<>() : auth.getEstudiantesRegistrados();
        req.setAttribute("estudiantes", estudiantes);

        String origen = req.getParameter("origen");
        String destino = req.getParameter("destino");
        if (origen != null && destino != null && !origen.isBlank() && !destino.isBlank()) {
            RedAfinidad.getInstancia().actualizarConexiones();
            req.setAttribute("origenSel", origen);
            req.setAttribute("destinoSel", destino);
            req.setAttribute("ruta", moderador.generarReporteCaminosMasCortos(origen, destino).getDatos());
            req.setAttribute("consultado", Boolean.TRUE);
        }
        req.getRequestDispatcher("/rutaMasCorta.jsp").forward(req, resp);
    }
}
