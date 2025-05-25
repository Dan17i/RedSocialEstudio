package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.Usuario;
import co.edu.uniquindio.redsocial.models.services.implement.SistemaAutenticacion;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private SistemaAutenticacion sistemaAutenticacion;

    // Credenciales fijas del moderador
    private static final String MODERADOR_EMAIL = "moderador@redsocial.com";
    private static final String MODERADOR_PASS = "moderador123";

    @Override
    public void init() throws ServletException {
        sistemaAutenticacion = (SistemaAutenticacion) getServletContext().getAttribute("sistemaAutenticacion");
        if (sistemaAutenticacion == null) {
            sistemaAutenticacion = new SistemaAutenticacion();
            getServletContext().setAttribute("sistemaAutenticacion", sistemaAutenticacion);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("correo");
        String password = request.getParameter("contrasena");

        try {
            // Verificar primero si es moderador fijo
            if (MODERADOR_EMAIL.equalsIgnoreCase(email) && MODERADOR_PASS.equals(password)) {
                // Creamos un Usuario para moderador
                Estudiante moderador = new Estudiante(
                        "MOD-0001",                    // id (puedes asignar un id fijo)
                        "Moderador Principal",            // nombre
                        "moderador@redsocial.com",        // email
                        "moderador123",                   // contraseña
                        new ListaEnlazada<>(),            // intereses (vacío)
                        new ListaEnlazada<>(),            // historial (vacío)
                        new ListaEnlazada<>(),            // valoraciones (vacío)
                        null,                             // cola de solicitudes (según constructor Estudiante)
                        new ListaEnlazada<>(),            // grupos (vacío)
                        new ListaEnlazada<>()
                );

                // Guardamos en sesión
                request.getSession().setAttribute("usuarioActual", moderador);

                // Redirigimos a página especial del moderador
                response.sendRedirect("dashboardModerador.jsp");
                return;
            }

            // Si no es moderador, validar usuario normal
            Usuario usuario = sistemaAutenticacion.iniciarSesion(email, password);

            // Guardar usuario en sesión
            request.getSession().setAttribute("usuarioActual", usuario);

            // Redirigir al perfil normal
            response.sendRedirect("inicio.jsp");

        } catch (SecurityException e) {
            request.setAttribute("error", "Credenciales inválidas");
            request.getRequestDispatcher("inicioSesion.jsp").forward(request, response);
        }
    }
}
