package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.models.Moderador;
import co.edu.uniquindio.redsocial.models.Usuario;
import co.edu.uniquindio.redsocial.models.services.implement.SistemaAutenticacion;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Gestión de usuarios por parte del moderador: listar, dar de baja y renombrar estudiantes.
 * Trabaja sobre los usuarios realmente registrados en el {@link SistemaAutenticacion} de la aplicación.
 */
@WebServlet("/GestionUsuariosServlet")
public class GestionUsuariosServlet extends HttpServlet {

    private SistemaAutenticacion autenticacion() {
        return (SistemaAutenticacion) getServletContext().getAttribute("sistemaAutenticacion");
    }

    private Moderador moderadorEnSesion(HttpServletRequest request) {
        Object actual = request.getSession().getAttribute("usuarioActual");
        return (actual instanceof Moderador) ? (Moderador) actual : null;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (moderadorEnSesion(request) == null) {
            response.sendRedirect(request.getContextPath() + "/inicioSesion.jsp");
            return;
        }
        request.setAttribute("usuarios", autenticacion().getEstudiantesRegistrados());

        String exito = request.getParameter("success");
        if ("usuario_eliminado".equals(exito)) {
            request.setAttribute("mensaje", "Usuario dado de baja correctamente.");
        } else if ("usuario_modificado".equals(exito)) {
            request.setAttribute("mensaje", "Nombre actualizado correctamente.");
        }
        String error = request.getParameter("error");
        if ("usuario_no_encontrado".equals(error)) {
            request.setAttribute("error", "No se encontró el usuario.");
        } else if ("nombre_invalido".equals(error)) {
            request.setAttribute("error", "El nombre no puede estar vacío.");
        }
        request.getRequestDispatcher("/gestionarUsuarios.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Moderador moderador = moderadorEnSesion(request);
        if (moderador == null) {
            response.sendRedirect(request.getContextPath() + "/inicioSesion.jsp");
            return;
        }

        String accion = request.getParameter("accion");
        String usuarioId = request.getParameter("codigo");
        Usuario usuario = (usuarioId == null) ? null : autenticacion().getGestorUsuarios().buscarUsuarioPorId(usuarioId);
        if (usuario == null) {
            response.sendRedirect("GestionUsuariosServlet?error=usuario_no_encontrado");
            return;
        }

        if ("eliminar".equals(accion)) {
            // Se da de baja desde el sistema de autenticación para limpiar también la red de afinidad
            autenticacion().eliminarUsuario(usuario.getEmail());
            response.sendRedirect("GestionUsuariosServlet?success=usuario_eliminado");
        } else if ("modificar".equals(accion)) {
            String nombre = request.getParameter("nombre");
            if (nombre == null || nombre.isBlank()) {
                response.sendRedirect("GestionUsuariosServlet?error=nombre_invalido");
                return;
            }
            moderador.modificarUsuario(usuario, nombre.trim());
            response.sendRedirect("GestionUsuariosServlet?success=usuario_modificado");
        } else {
            response.sendRedirect("GestionUsuariosServlet");
        }
    }
}
