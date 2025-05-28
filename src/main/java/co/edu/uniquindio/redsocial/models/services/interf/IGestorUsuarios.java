package co.edu.uniquindio.redsocial.models.services.interf;

import co.edu.uniquindio.redsocial.models.Usuario;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

/**
 * Interfaz para la gestión de usuarios dentro del sistema de red social.
 * Define las operaciones básicas de CRUD sobre usuarios.
 * @author Daniel Jurado, Sebastia Torres y juan Soto
 * @since 2025-05-27
 */
public interface IGestorUsuarios {

    /**
     * Registra un nuevo usuario en el sistema.
     *
     * @param usuario Usuario a registrar
     */
    void registrarUsuario(Usuario usuario);

    /**
     * Elimina un usuario del sistema por su ID.
     *
     * @param id ID del usuario a eliminar
     */
    void eliminarUsuario(String id);

    /**
     * Actualiza los datos de un usuario existente.
     *
     * @param usuario Usuario actualizado
     */
    void actualizarUsuario(Usuario usuario);

    /**
     * Lista todos los usuarios registrados en el sistema.
     *
     * @return Lista de usuarios
     */
    ListaEnlazada<Usuario> listarUsuarios();

    /**
     * Busca un usuario por su ID.
     * @param id El identificador único del usuario.
     * @return El usuario encontrado o null si no existe.
     */
    Usuario buscarUsuarioPorId(String id);
}

