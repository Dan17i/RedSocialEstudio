package co.edu.uniquindio.redsocial.models.services.interf;

import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.Usuario;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

/**
 * Interfaz que define los métodos para la gestión de la autenticación
 * y el registro de usuarios en el sistema.
 * Proporciona funcionalidades para registrar nuevos estudiantes,
 * iniciar sesión, verificar la existencia de usuarios,
 * eliminar usuarios y obtener la lista de usuarios registrados.
 * Esta interfaz está diseñada para abstraer la lógica de autenticación
 * y manejo de usuarios en la plataforma de red social educativa.
 *
 * @author Daniel Jurado, Sebastian Torres y juan Soto
 * @version 1.0
 * @since 2025-05-27
 */
public interface ISistemaAutenticacion {

    /**
     * Registra un nuevo estudiante en el sistema.
     *
     * @param nombre     Nombre completo del estudiante.
     * @param email      Correo electrónico del estudiante (debe ser único).
     * @param contraseña Contraseña del estudiante.
     * @return El estudiante creado y registrado.
     * @throws IllegalArgumentException Si el correo ya está registrado.
     */
    Estudiante registrarEstudiante(String nombre, String email, String contraseña);

    /**
     * Inicia sesión de un usuario mediante email y contraseña.
     *
     * @param email      Correo electrónico del usuario.
     * @param contraseña Contraseña del usuario.
     * @return Usuario autenticado si las credenciales coinciden.
     * @throws SecurityException Si las credenciales son incorrectas.
     */
    Usuario iniciarSesion(String email, String contraseña);

    /**
     * Verifica si un usuario con el email dado ya está registrado.
     *
     * @param email Email del usuario.
     * @return true si existe, false si no.
     */
    boolean existeUsuario(String email);

    /**
     * Elimina un usuario registrado por su email.
     *
     * @param email Email del usuario a eliminar.
     * @return true si se eliminó, false si no se encontró.
     */
    boolean eliminarUsuario(String email);

    /**
     * Obtiene la lista de usuarios registrados (lectura).
     *
     * @return Lista enlazada de usuarios.
     */
    ListaEnlazada<Usuario> getUsuariosRegistrados();
}
