package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.models.Moderador;
import co.edu.uniquindio.redsocial.models.Usuario;
import co.edu.uniquindio.redsocial.models.services.interf.IGestorUsuarios;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GestionUsuariosServlet extends HttpServlet {
    private IGestorUsuarios gestorUsuarios;
    private Moderador moderador;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ListaEnlazada<Usuario> usuarios = gestorUsuarios.listarUsuarios();
        request.setAttribute("usuarios", usuarios);
        request.getRequestDispatcher("/gestionUsuarios.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accion = request.getParameter("accion");
        String usuarioId = request.getParameter("usuarioId");

        if ("eliminar".equals(accion)) {
            Usuario usuario = gestorUsuarios.buscarUsuarioPorId(usuarioId);
            if (usuario != null) {
                moderador.darBajaUsuario(usuario);
                response.sendRedirect("GestionUsuariosServlet?success=usuario_eliminado");
            } else {
                response.sendRedirect("GestionUsuariosServlet?error=usuario_no_encontrado");
            }
        } else if ("modificar".equals(accion)) {
            // Implementar lógica para modificar (por ejemplo, redirigir a un formulario)
            Usuario usuario = gestorUsuarios.buscarUsuarioPorId(usuarioId);
            if (usuario != null) {
                request.setAttribute("usuario", usuario);
                request.getRequestDispatcher("/modificarUsuario.jsp").forward(request, response);
            }
        }
    }

    // Métodos para inyección de dependencias
    public void setGestorUsuarios(IGestorUsuarios gestorUsuarios) {
        this.gestorUsuarios = gestorUsuarios;
    }

    public void setModerador(Moderador moderador) {
        this.moderador = moderador;
    }
}
