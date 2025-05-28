package co.edu.uniquindio.redsocial.models.services.implement;

import co.edu.uniquindio.redsocial.models.Contenido;
import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.Usuario;
import co.edu.uniquindio.redsocial.models.services.interf.IGestorRedSocial;
import co.edu.uniquindio.redsocial.models.services.interf.IGestorUsuarios;
import co.edu.uniquindio.redsocial.models.services.interf.ISistemaAutenticacion;
import co.edu.uniquindio.redsocial.models.structures.ArbolBinarioBusqueda;
import co.edu.uniquindio.redsocial.models.structures.ColaPrioridad;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import co.edu.uniquindio.redsocial.models.structures.NodoLista;

import java.util.UUID;

/**
 * Clase encargada del registro e inicio de sesión de usuarios dentro del sistema.
 * Administra una lista enlazada de usuarios registrados.
 * Esta implementación permite registrar estudiantes y validar credenciales para iniciar sesión.
 *
 * @author Daniel Jurado
 * @author Sebastian Torres
 * @author Juan Soto
 * @since 2025-05-13
 */
public class SistemaAutenticacion implements ISistemaAutenticacion {
    private ListaEnlazada<Usuario> usuariosRegistrados = new ListaEnlazada<>();
    private GestorContenidos gestorContenidos;
    private IGestorUsuarios gestorUsuarios;
    private IGestorRedSocial gestorRedSocial;


    public SistemaAutenticacion() {
        this.gestorUsuarios = new GestorUsuarios();
        this.gestorRedSocial = new GestorRedSocial();

        ArbolBinarioBusqueda<Contenido> arbolContenidos = new ArbolBinarioBusqueda<>();
        ListaEnlazada<Contenido> contenidoDestacado = new ListaEnlazada<>();

        this.gestorContenidos = new GestorContenidos(arbolContenidos, contenidoDestacado);
    }

    /**
     * Registra un nuevo estudiante en el sistema.
     *
     * @param nombre      Nombre completo del estudiante.
     * @param email       Correo electrónico del estudiante (debe ser único).
     * @param contraseña  Contraseña del estudiante.
     * @return El estudiante creado y registrado.
     * @throws IllegalArgumentException Si el correo ya está registrado.
     */
    public Estudiante registrarEstudiante(String nombre, String email, String contraseña) {
        validarEmailUnico(email);

        Estudiante nuevo = new Estudiante(
                generarId(),
                nombre,
                email,
                contraseña,
                new ListaEnlazada<>(),  // intereses
                new ListaEnlazada<>(),  // historial
                new ListaEnlazada<>(),  // valoraciones
                new ColaPrioridad<>(new ListaEnlazada<>()),  // solicitudes
                new ListaEnlazada<>(),   // grupos
                new ListaEnlazada<>()
        );

        usuariosRegistrados.agregar(nuevo);
        return nuevo;
    }

    /**
     * Inicia sesión de un usuario mediante email y contraseña.
     *
     * @param email      Correo electrónico del usuario.
     * @param contraseña Contraseña del usuario.
     * @return Usuario autenticado si las credenciales coinciden.
     * @throws SecurityException Si las credenciales son incorrectas.
     */
    public Usuario iniciarSesion(String email, String contraseña) {
        NodoLista<Usuario> actual = usuariosRegistrados.getCabeza();
        while (actual != null) {
            Usuario usuario = actual.getDato();
            if (usuario.getEmail().equals(email) && usuario.getContraseña().equals(contraseña)) {
                return usuario;
            }
            actual = actual.getSiguiente();
        }
        throw new SecurityException("Credenciales inválidas");
    }

    /**
     * Genera un ID único para cada usuario registrado.
     *
     * @return Cadena de texto representando un identificador único.
     */
    private String generarId() {
        return "USR-" + UUID.randomUUID();
    }

    /**
     * Verifica que el correo electrónico no esté ya registrado.
     *
     * @param email Correo electrónico a validar.
     * @throws IllegalArgumentException Si el email ya está en uso.
     */
    private void validarEmailUnico(String email) {
        NodoLista<Usuario> actual = usuariosRegistrados.getCabeza();
        while (actual != null) {
            if (actual.getDato().getEmail().equalsIgnoreCase(email)) {
                throw new IllegalArgumentException("Email ya registrado");
            }
            actual = actual.getSiguiente();
        }
    }

    /**
     * Verifica si un usuario con el email dado ya está registrado.
     *
     * @param email Email del usuario.
     * @return true si existe, false si no.
     */
    public boolean existeUsuario(String email) {
        NodoLista<Usuario> actual = usuariosRegistrados.getCabeza();
        while (actual != null) {
            if (actual.getDato().getEmail().equalsIgnoreCase(email)) {
                return true;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }

    /**
     * Elimina un usuario registrado por su email.
     *
     * @param email Email del usuario a eliminar.
     * @return true si se eliminó, false si no se encontró.
     */
    public boolean eliminarUsuario(String email) {
        ListaEnlazada<Usuario> nuevaLista = new ListaEnlazada<>();
        boolean eliminado = false;

        NodoLista<Usuario> actual = usuariosRegistrados.getCabeza();
        while (actual != null) {
            Usuario usuario = actual.getDato();
            if (!usuario.getEmail().equalsIgnoreCase(email)) {
                nuevaLista.agregar(usuario);
            } else {
                eliminado = true;
            }
            actual = actual.getSiguiente();
        }

        usuariosRegistrados = nuevaLista;
        return eliminado;
    }

    @Override
    public ListaEnlazada<Usuario> getUsuariosRegistrados() {
        return usuariosRegistrados;
    }

    /**
     * Devuelve solo los Estudiantes (filtrando del total de usuarios).
     */
    public ListaEnlazada<Estudiante> getEstudiantesRegistrados() {
        ListaEnlazada<Estudiante> lista = new ListaEnlazada<>();
        NodoLista<Usuario> actual = usuariosRegistrados.getCabeza();
        while (actual != null) {
            Usuario u = actual.getDato();
            if (u instanceof Estudiante) {
                lista.agregar((Estudiante) u);
            }
            actual = actual.getSiguiente();
        }
        return lista;
    }

    public GestorContenidos getGestorContenidos() {
        return gestorContenidos;
    }

    public IGestorUsuarios getGestorUsuarios() {
        return gestorUsuarios;
    }

    public IGestorRedSocial getGestorRedSocial() {
        return gestorRedSocial;
    }
}