package co.edu.uniquindio.redsocial.models;
/**
 * Clase que representa un estudiante en la red social.
 * Un usuario tiene una carrera.
 *
 * @author Daniel Jurado
 * @author Sebastian Torres
 * @author Jaun Soto
 * @since 2025-04-02
 */
public class Estudiante extends Usuario {
    private String carrera;

    public Estudiante(String nombre, String email, String carrera) {
        super(nombre, email);
        this.carrera = carrera;
    }

    public String getCarrera() {
        return carrera;
    }
}
