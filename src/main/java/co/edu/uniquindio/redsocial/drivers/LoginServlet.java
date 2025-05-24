package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Simula autenticación
        String email = request.getParameter("email");
        String password = request.getParameter("contrasenia");

        // Simulamos un estudiante con datos de ejemplo
        Estudiante estudiante = new Estudiante(
                "123", "Juan Esteban", "Juan@gmail", "123",
                new ListaEnlazada<>(), new ListaEnlazada<>(),
                new ListaEnlazada<>(), null, null
        );

        estudiante.getIntereses().agregar("Seguridad Informática");
        estudiante.getIntereses().agregar("Redes");
        // Puedes agregar contenidos al historial si quieres probar

        // Guarda en sesión
        request.getSession().setAttribute("usuarioActual", estudiante);

        // Redirige al perfil
        response.sendRedirect("inicio.jsp");
    }
}
