package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.models.Moderador;
import co.edu.uniquindio.redsocial.models.Usuario;
import co.edu.uniquindio.redsocial.models.services.implement.SistemaAutenticacion;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
/**
 * Servlet que maneja el proceso de inicio de sesión de usuarios.
 * <p>
 * - Verifica si las credenciales corresponden al moderador fijo y crea
 *   un usuario moderador en sesión.
 * - Si no, intenta autenticar un usuario normal usando el sistema de autenticación.
 * - En caso de credenciales inválidas, muestra un mensaje de error.
 * </p>
 * Ruta: /login
 *
 * @author Daniel Jurado, Sebastian Torres y Juan Soto
 * @version 1.0
 */
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
    /**
     * Procesa la petición POST para iniciar sesión.
     * <ul>
     *   <li>Si las credenciales corresponden al moderador fijo, crea un usuario
     *       moderador y redirige a su dashboard.</li>
     *   <li>Si las credenciales son de usuario normal, valida con el sistema de
     *       autenticación y redirige al inicio.</li>
     *   <li>En caso de error de autenticación, retorna a la página de inicio de sesión
     *       con un mensaje de error.</li>
     * </ul>
     *
     * @param request  la solicitud HTTP con parámetros de correo y contraseña
     * @param response la respuesta HTTP con redirección o forward
     * @throws ServletException si ocurre un error en el servlet
     * @throws IOException      si ocurre un error de entrada/salida
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("correo");
        String password = request.getParameter("contrasena");

        try {
            // Verificar primero si es moderador fijo
            if (MODERADOR_EMAIL.equalsIgnoreCase(email) && MODERADOR_PASS.equals(password)) {
                // Creamos un Usuario para moderador
                Moderador moderador = new Moderador(

                        "MOD-0001",                          // id
                        "Moderador Principal",              // nombre
                        MODERADOR_EMAIL,                    // email
                        MODERADOR_PASS,                     // contraseña
                        new ListaEnlazada<>(),              // intereses
                        new ListaEnlazada<>(),              // historial de contenidos
                        new ListaEnlazada<>(),              // valoraciones
                        true,                               // acceso completo
                        new ListaEnlazada<>(),              // áreas de responsabilidad
                        sistemaAutenticacion.getGestorUsuarios(),      // gestorUsuarios
                        sistemaAutenticacion.getGestorContenidos(),    // gestorContenidos
                        sistemaAutenticacion.getGestorRedSocial()      // gestorRedSocial


                );

                // Guardamos en sesión
                request.getSession().setAttribute("usuarioActual", moderador);

                // Redirigimos a página especial del moderador
                response.sendRedirect("moderador.jsp");
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
