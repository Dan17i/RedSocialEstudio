package co.edu.uniquindio.redsocial.models;
/**
 * Clase que representa un estudiante en la red social.
 * Un usuario tiene una carrera.
 *
 * @author Daniel Jurado
 * @since 2025-04-02
 */
public class Estudiante extends Usuario {

    public Estudiante(String nombre, String id, ListaEnlazada<String> intereses, ListaEnlazada<Contenido> historialContenidos, ListaEnlazada<Valoracion> valoraciones) {
        super(nombre, id, intereses, historialContenidos, valoraciones);

    }


}
