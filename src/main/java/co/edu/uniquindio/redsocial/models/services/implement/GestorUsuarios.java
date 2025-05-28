package co.edu.uniquindio.redsocial.models.services.implement;

import co.edu.uniquindio.redsocial.models.Usuario;
import co.edu.uniquindio.redsocial.models.services.interf.IGestorUsuarios;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

/**
 * Implementación de la interfaz IGestorUsuarios que permite
 * registrar, eliminar, actualizar y listar usuarios en una lista enlazada.
 *
 * Utiliza una estructura de datos personalizada (ListaEnlazada).
 *
 * @author Daniel Jurado, Sebastian Torres y Juan Soto
 * @since 2025-05-27
 */
public class GestorUsuarios implements IGestorUsuarios {

    private final ListaEnlazada<Usuario> usuarios = new ListaEnlazada<>();

    /**
     * Registra un nuevo usuario si no existe otro con el mismo ID.
     *
     * @param usuario Usuario a registrar
     * @throws IllegalArgumentException si el usuario ya existe
     */
    @Override
    public void registrarUsuario(Usuario usuario) {
        if (usuario == null || usuario.getId() == null) {
            throw new IllegalArgumentException("El usuario o su ID no pueden ser nulos.");
        }

        for (int i = 0; i < usuarios.getTamanio(); i++) {
            if (usuarios.obtener(i).getId().equals(usuario.getId())) {
                throw new IllegalArgumentException("Ya existe un usuario con el mismo ID.");
            }
        }

        usuarios.agregar(usuario);
    }

    /**
     * Elimina un usuario por su ID si existe.
     *
     * @param id ID del usuario a eliminar
     */
    @Override
    public void eliminarUsuario(String id) {
        if (id == null) return;

        for (int i = 0; i < usuarios.getTamanio(); i++) {
            if (usuarios.obtener(i).getId().equals(id)) {
                usuarios.eliminar(i);
                return;
            }
        }
    }

    /**
     * Actualiza los datos de un usuario existente (por ID).
     *
     * @param usuario Usuario actualizado
     * @throws IllegalArgumentException si no se encuentra el usuario
     */
    @Override
    public void actualizarUsuario(Usuario usuario) {
        if (usuario == null || usuario.getId() == null) {
            throw new IllegalArgumentException("El usuario o su ID no pueden ser nulos.");
        }

        for (int i = 0; i < usuarios.getTamanio(); i++) {
            if (usuarios.obtener(i).getId().equals(usuario.getId())) {
                usuarios.modificar(i, usuario);
                return;
            }
        }

        throw new IllegalArgumentException("Usuario no encontrado.");
    }

    /**
     * Retorna la lista completa de usuarios registrados.
     *
     * @return ListaEnlazada de usuarios
     */
    @Override
    public ListaEnlazada<Usuario> listarUsuarios() {
        return usuarios;
    }

    /**
     * Busca un usuario por su ID.
     * @param id El identificador único del usuario.
     * @return El objeto Usuario si se encuentra, o null si no existe.
     */
    public Usuario buscarUsuarioPorId(String id) {
        for (int i = 0; i < usuarios.getTamanio(); i++) {
            Usuario usuario = usuarios.obtener(i); // o el método para obtener el elemento i
            if (usuario.getId().equals(id)) {
                return usuario;
            }
        }
        return null; // No se encontró usuario con ese id
    }
}
