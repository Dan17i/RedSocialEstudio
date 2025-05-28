package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.services.implement.SistemaAutenticacion;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
/**
 * Servlet para manejar el registro de nuevos estudiantes en el sistema.
 *
 * <p>
 * Recibe datos del formulario de registro (nombre, correo, contraseña),
 * y utiliza SistemaAutenticacion para crear un nuevo estudiante.
 * En caso de éxito, redirige a la página de inicio de sesión con mensaje de éxito.
 * En caso de error (ej. correo ya registrado), muestra mensaje de error en la página de registro.
 * </p>
 *
 * Ruta: /Registro
 *
 * @author Daniel Jurado, Sebastian Torres y Juan Soto
 * @version 1.0
 */
@WebServlet("/Registro")
public class RegistroServlet extends HttpServlet {

    private SistemaAutenticacion sistemaAutenticacion;
    /**
     * Inicializa el servlet obteniendo el SistemaAutenticacion del contexto.
     */
    @Override
    public void init() throws ServletException {
        sistemaAutenticacion = (SistemaAutenticacion) getServletContext().getAttribute("sistemaAutenticacion");
    }
    /**
     * Maneja solicitudes POST para registrar un nuevo estudiante.
     *
     * @param request  solicitud HTTP
     * @param response respuesta HTTP
     * @throws ServletException si ocurre un error en el servlet
     * @throws IOException      si ocurre un error de E/S
     */
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
