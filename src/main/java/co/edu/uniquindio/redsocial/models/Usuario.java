package co.edu.uniquindio.redsocial.models;
/**
 * Clase que representa un usuario en la red social.
 * Un usuario tiene un nombre y un email.
 *
 * @author Daniel Jurado
 * @since 2025-04-02
 */
public class Usuario {
    private String nombre;
    private String email;

    public Usuario(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }
}
