package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.services.implement.SistemaAutenticacion;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/Registro")
public class RegistroServlet extends HttpServlet {

    private SistemaAutenticacion sistemaAutenticacion;

    @Override
    public void init() throws ServletException {
        sistemaAutenticacion = (SistemaAutenticacion) getServletContext().getAttribute("sistemaAutenticacion");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombre = request.getParameter("nombre");
        String correo = request.getParameter("correo");
        String contrasena = request.getParameter("contrasena");

        try {
            Estudiante estudiante = sistemaAutenticacion.registrarEstudiante(nombre, correo, contrasena);
            // Mensaje de éxito
            request.setAttribute("mensaje", "Registro exitoso. Por favor, inicia sesión.");
            request.getRequestDispatcher("inicioSesion.jsp").forward(request, response);
        } catch (IllegalArgumentException e) {
            // Mensaje de error si ya está registrado
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("registro.jsp").forward(request, response);
        }
    }
}
