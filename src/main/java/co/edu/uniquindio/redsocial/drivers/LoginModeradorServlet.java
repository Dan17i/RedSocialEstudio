package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.models.Contenido;
import co.edu.uniquindio.redsocial.models.Moderador;
import co.edu.uniquindio.redsocial.models.Valoracion;
import co.edu.uniquindio.redsocial.models.services.implement.GestorContenidos;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/LoginModeradorServlet")
public class LoginModeradorServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Simulación de autenticación (reemplaza con tu lógica real)
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Ejemplo de autenticación básica
        if ("admin".equals(username) && "1234".equals(password)) {
            HttpSession session = request.getSession();
            Moderador moderador = new Moderador(
                    "MOD-0001",                    // id
                    "Moderador Principal",            // nombre
                    "moderador@redsocial.com",        // email
                    "moderador123",                   // contraseña
                    new ListaEnlazada<String>(),      // intereses (vacío por ahora)
                    new ListaEnlazada<Contenido>(),   // historialContenidos (vacío por ahora)
                    new ListaEnlazada<Valoracion>(),  // valoraciones (vacío por ahora)
                    true,                             // accesoCompleto
                    new ListaEnlazada<String>(),      // areasResponsabilidad (vacío por ahora)
                    null,                             // gestorUsuarios (puedes inyectarlo después)
                    GestorContenidos.getInstancia(),  // gestorContenidos (usamos el Singleton)
                    null                              // gestorRedSocial (puedes inyectarlo después)
            ); // Ajusta según tu clase Moderador
            moderador.setNombre("Administrador"); // Ejemplo
            session.setAttribute("usuarioActual", moderador);
            response.sendRedirect(request.getContextPath() + "/panelModerador.jsp");
        } else {
            response.sendRedirect(request.getContextPath() + "/loginModerador.jsp?error=Credenciales inválidas");
        }
    }
}
