package co.edu.uniquindio.redsocial.drivers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/Registro")
public class RegistroUsuarioServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener datos del formulario
        String nombre = request.getParameter("nombre");
        String id = request.getParameter("id");
        String correo = request.getParameter("correo");
        String contrasena = request.getParameter("contrasena");

        // Puedes almacenar los datos en una base de datos o en memoria aquí

        // Por ahora, mostramos una respuesta simple
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html lang='es'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>Registro Exitoso</title>");
        out.println("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet'>");
        out.println("</head>");
        out.println("<body class='bg-light'>");

        out.println("<div class='container mt-5'>");
        out.println("<div class='alert alert-success'>");
        out.println("<h4>Registro exitoso</h4>");
        out.println("<p>Bienvenido, <strong>" + nombre + "</strong> (ID: " + id + ")</p>");
        out.println("<a href='InicioSesion.jsp' class='btn btn-primary mt-3'>Iniciar sesión</a>");
        out.println("</div>");
        out.println("</div>");

        out.println("</body></html>");
    }
}
