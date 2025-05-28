package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.services.implement.SistemaAutenticacion;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/GestionUsuariosServlet")
public class GestionUsuariosServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Obtener lista de estudiantes registrados
        ServletContext ctx = getServletContext();
        SistemaAutenticacion sistema = (SistemaAutenticacion) ctx.getAttribute("sistemaAutenticacion");
        ListaEnlazada<Estudiante> estudiantes = sistema.getEstudiantesRegistrados();
        request.setAttribute("usuarios", estudiantes);
        request.getRequestDispatcher("/gestionarUsuarios.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Eliminar estudiante por email
        String accion = request.getParameter("accion");
        String email  = request.getParameter("email");
        if ("eliminar".equals(accion) && email != null) {
            SistemaAutenticacion sistema = (SistemaAutenticacion) getServletContext()
                    .getAttribute("sistemaAutenticacion");
            sistema.eliminarUsuario(email);
        }
        // Recargar la lista tras la acción
        response.sendRedirect(request.getContextPath() + "/GestionUsuariosServlet");
    }
}
