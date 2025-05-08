package co.edu.uniquindio.redsocial.drivers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/Registro")
public class RegistroUsuarioServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener los parámetros del formulario
        String nombre = request.getParameter("nombre");
        String id = request.getParameter("id");
        String correo = request.getParameter("correo");
        String contrasena = request.getParameter("contrasena");

        // Por ahora solo mostrar en consola
        System.out.println("Nuevo usuario registrado:");
        System.out.println("Nombre: " + nombre);
        System.out.println("ID: " + id);
        System.out.println("Correo: " + correo);
        System.out.println("Contraseña: " + contrasena);

        // Redirigir o mostrar mensaje
        System.out.println("Registro exitoso. Redirigiendo a la página de inicio...");
        response.sendRedirect("InicioSesion.html"); // Cambia esto a la página
    }
}
