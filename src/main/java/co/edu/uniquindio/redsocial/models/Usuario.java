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
    private String id;
    private ListaEnlazada<String> intereses;
    private ListaEnlazada<Contenido> historialContenidos;
    private ListaEnlazada<Valoracion> valoraciones;

    public Usuario(String nombre, String id,
                   ListaEnlazada<String> intereses, ListaEnlazada<Contenido> historialContenidos,
                   ListaEnlazada<Valoracion> valoraciones) {
        this.nombre = nombre;
        this.id = id;
        this.intereses = intereses;
        this.historialContenidos = historialContenidos;
        this.valoraciones = valoraciones;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ListaEnlazada<String> getIntereses() {
        return intereses;
    }

    public void setIntereses(ListaEnlazada<String> intereses) {
        this.intereses = intereses;
    }

    public ListaEnlazada<Contenido> getHistorialContenidos() {
        return historialContenidos;
    }

    public void setHistorialContenidos(ListaEnlazada<Contenido> historialContenidos) {
        this.historialContenidos = historialContenidos;
    }

    public ListaEnlazada<Valoracion> getValoraciones() {
        return valoraciones;
    }

    public void setValoraciones(ListaEnlazada<Valoracion> valoraciones) {
        this.valoraciones = valoraciones;
    }
}
