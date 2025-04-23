package co.edu.uniquindio.redsocial.models;
/**
 * Clase que representa un estudiante en la red social.
 * Un usuario tiene una carrera.
 *
 * @author Daniel Jurado
 * @since 2025-04-02
 */
public class Estudiante extends Usuario {
    private String carrera;

    public Estudiante(String nombre, String id, String carrera) {
        super(nombre, id ,new ListaEnlazada<>(),new ListaEnlazada<>(),new ListaEnlazada<>() );
        this.carrera = carrera;
    }

    public String getCarrera() {
        return carrera;
    }
}
