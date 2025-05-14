package co.edu.uniquindio.redsocial.models;

import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import co.edu.uniquindio.redsocial.models.structures.NodoLista;

/**
 * Clase que representa un contenido dentro de la red social educativa.
 * Cada contenido tiene un tema, autor, tipo y una lista de valoraciones asociadas.
 *
 * @author Daniel Jurado
 * @author Sebastian Torres
 * @author Juan Soto
 * @since 2025-05-12
 */
public class Contenido {
    private String id;
    private String tema;
    private String autor;
    private String tipo;
    private ListaEnlazada<Valoracion> valoraciones;
    /**
     * Constructor de la clase Contenido.
     *
     * @param id           Identificador único del contenido.
     * @param tema         Tema principal del contenido (e.g., matemáticas, historia).
     * @param autor        Nombre o identificador del autor del contenido.
     * @param tipo         Tipo de contenido (e.g., video, artículo, presentación).
     * @param valoraciones Lista de valoraciones hechas por los usuarios.
     */
    public Contenido(String id, String tema, String autor, String tipo, ListaEnlazada<Valoracion> valoraciones) {
        this.id = id;
        this.tema = tema;
        this.autor = autor;
        this.tipo = tipo;
        this.valoraciones = valoraciones;
    }
    /**
     * Calcula la valoración promedio del contenido con base en las valoraciones asociadas.
     *
     * @return Valoración promedio como número decimal. Devuelve 0 si no hay valoraciones.
     */
    public float calcularValoracionPromedio() {
        if (valoraciones.estaVacia()) return 0;
        int suma = 0;
        NodoLista<Valoracion> actual = valoraciones.getCabeza();
        while (actual != null) {
            suma += actual.getDato().getPuntuacion();
            actual = actual.getSiguiente();
        }
        return (float) suma / valoraciones.getTamanio();
    }
    /**
     * @return ID del contenido.
     */
    public String getId() { return id; }
    /**
     * @param id Nuevo ID del contenido.
     */
    public void setId(String id) { this.id = id; }
    /**
     * @return Tema del contenido.
     */
    public String getTema() { return tema; }
    /**
     * @param tema Nuevo tema del contenido.
     */
    public void setTema(String tema) { this.tema = tema; }
    /**
     * @return Autor del contenido.
     */
    public String getAutor() { return autor; }
    /**
     * @param autor Nuevo autor del contenido.
     */
    public void setAutor(String autor) { this.autor = autor; }
    /**
     * @return Tipo de contenido.
     */
    public String getTipo() { return tipo; }
    /**
     * @param tipo Nuevo tipo de contenido.
     */
    public void setTipo(String tipo) { this.tipo = tipo; }
    /**
     * @return Lista de valoraciones del contenido.
     */
    public ListaEnlazada<Valoracion> getValoraciones() { return valoraciones; }
    /**
     * @param valoraciones Nueva lista de valoraciones.
     */
    public void setValoraciones(ListaEnlazada<Valoracion> valoraciones) { this.valoraciones = valoraciones; }
}
