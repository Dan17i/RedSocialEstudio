package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.models.Contenido;
import co.edu.uniquindio.redsocial.models.Moderador;
import co.edu.uniquindio.redsocial.models.Usuario;
import co.edu.uniquindio.redsocial.models.Valoracion;
import co.edu.uniquindio.redsocial.models.services.implement.GestorContenidos;
import co.edu.uniquindio.redsocial.models.services.implement.GestorRedSocial;
import co.edu.uniquindio.redsocial.models.services.implement.GestorUsuarios;
import co.edu.uniquindio.redsocial.models.services.interf.IGestorRedSocial;
import co.edu.uniquindio.redsocial.models.services.interf.IGestorUsuarios;
import co.edu.uniquindio.redsocial.models.structures.ArbolBinarioBusqueda;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import javax.servlet.annotation.WebServlet;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
<<<<<<< Updated upstream
=======

>>>>>>> Stashed changes
@WebServlet("/GestionUsuariosServlet")
public class GestionUsuariosServlet extends HttpServlet {
    private IGestorUsuarios gestorUsuarios;
    private Moderador moderador;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ListaEnlazada<Usuario> usuarios = gestorUsuarios.listarUsuarios();
        request.setAttribute("usuarios", usuarios);
        request.getRequestDispatcher("/gestionarUsuarios.jsp").forward(request, response);
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

    @Override
    public void init() throws ServletException {
        super.init();

        // Inicializaciones mínimas para pruebas
        gestorUsuarios = new GestorUsuarios();
        ArbolBinarioBusqueda<Contenido> arbolContenidos = new ArbolBinarioBusqueda<>();
        ListaEnlazada<Contenido> contenidoDestacado = new ListaEnlazada<>();
        GestorContenidos gestorContenidos = new GestorContenidos(arbolContenidos, contenidoDestacado);
        IGestorRedSocial gestorRedSocial = new GestorRedSocial();
        ListaEnlazada<String> intereses = new ListaEnlazada<>();
        ListaEnlazada<Contenido> historial = new ListaEnlazada<>();
        ListaEnlazada<Valoracion> valoraciones = new ListaEnlazada<>();
        ListaEnlazada<String> areas = new ListaEnlazada<>();

        moderador = new Moderador(
                "mod001",
                "Moderador",
                "moderador@redsocial.com",
                "moderador123",
                intereses,
                historial,
                valoraciones,
                true,
                areas,
                gestorUsuarios,
                gestorContenidos,
                gestorRedSocial
        );
    }



    // Métodos para inyección de dependencias
    public void setGestorUsuarios(IGestorUsuarios gestorUsuarios) {
        this.gestorUsuarios = gestorUsuarios;
    }

    public void setModerador(Moderador moderador) {
        this.moderador = moderador;
    }
}
