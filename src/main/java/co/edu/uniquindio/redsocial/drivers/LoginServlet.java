package co.edu.uniquindio.redsocial.drivers; // Ajusta el paquete según tu estructura

import co.edu.uniquindio.redsocial.models.Moderador;
import co.edu.uniquindio.redsocial.models.Usuario;
import co.edu.uniquindio.redsocial.models.Valoracion;
import co.edu.uniquindio.redsocial.models.services.implement.GestorContenidos;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import co.edu.uniquindio.redsocial.models.services.implement.SistemaAutenticacion;
import co.edu.uniquindio.redsocial.models.Contenido;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// Define las constantes (ajústalas según necesites)
public class LoginServlet extends HttpServlet {
    private static final String MODERADOR_EMAIL = "moderador@redsocial.com";
    private static final String MODERADOR_PASS = "moderador123";
    private SistemaAutenticacion sistemaAutenticacion; // Asegúrate de inyectar esto

    @Override
    public void init() throws ServletException {
        super.init();
        // Inicializa sistemaAutenticacion si es necesario (puede ser un Singleton)
        sistemaAutenticacion = /* tu implementación */ null; // Ajusta según tu código
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            // Verificar primero si es moderador fijo
            if (MODERADOR_EMAIL.equalsIgnoreCase(email) && MODERADOR_PASS.equals(password)) {
                Moderador moderador = new Moderador(
                        "MOD-0001",
                        "Moderador Principal",
                        "moderador@redsocial.com",
                        "moderador123",
                        new ListaEnlazada<String>(),
                        new ListaEnlazada<Contenido>(),
                        new ListaEnlazada<Valoracion>(),
                        true,
                        new ListaEnlazada<String>(),
                        null, // gestorUsuarios (puedes inyectarlo)
                        GestorContenidos.getInstancia(),
                        null  // gestorRedSocial (puedes inyectarlo)
                );

                // Guardamos en sesión
                request.getSession().setAttribute("usuario", moderador);

                // Redirigimos a página especial del moderador
                response.sendRedirect(request.getContextPath() + "/panelModerador.jsp");
                return;
            }

            // Si no es moderador, validar usuario normal
            Usuario usuario = sistemaAutenticacion.iniciarSesion(email, password);

            // Guardar usuario en sesión
            request.getSession().setAttribute("usuario", usuario);

            // Redirigir al perfil normal
            response.sendRedirect(request.getContextPath() + "/inicio.jsp");

        } catch (SecurityException e) {
            request.setAttribute("error", "Credenciales inválidas");
            request.getRequestDispatcher("inicioSesion.jsp").forward(request, response);
        }
    }
}