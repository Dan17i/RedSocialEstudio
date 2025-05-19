package co.edu.uniquindio.redsocial.models;

import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

/**
 * Representa un contenido publicado por un estudiante dentro de la red social.
 * Un contenido tiene un identificador único, tema, autor, tipo y una lista de valoraciones.
 * Esta clase proporciona métodos de acceso y modificación para sus atributos.
 *
 * @author
 * Daniel Jurado, Sebastián Torres, Juan Soto
 * @since 2025-05-12
 */
public class Contenido {

    /** Identificador único del contenido. */
    private final String id;

    /** Tema o título del contenido. */
    private String tema;

    /** Estudiante que publicó el contenido. */
    private final Estudiante autor;

    /** Tipo de contenido (ej: Texto, Video, Imagen, etc.). */
    private String tipo;

    /** Lista de valoraciones que ha recibido el contenido. */
    private final ListaEnlazada<Valoracion> valoraciones;

    /**
     * Constructor para crear un contenido con los datos esenciales.
     *
     * @param id Identificador único del contenido. No debe ser nulo ni vacío.
     * @param tema Tema o título del contenido. No debe ser nulo ni vacío.
     * @param autor Estudiante que publica el contenido. No debe ser nulo.
     * @param tipo Tipo de contenido. No debe ser nulo ni vacío.
     * @param valoraciones Lista de valoraciones asociadas. No debe ser nula.
     * @throws IllegalArgumentException Si alguno de los parámetros es inválido.
     */
    public Contenido(String id, String tema, Estudiante autor, String tipo, ListaEnlazada<Valoracion> valoraciones) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("El id no puede ser nulo ni vacío");
        }
        if (tema == null || tema.isBlank()) {
            throw new IllegalArgumentException("El tema no puede ser nulo ni vacío");
        }
        if (autor == null) {
            throw new IllegalArgumentException("El autor no puede ser nulo");
        }
        if (tipo == null || tipo.isBlank()) {
            throw new IllegalArgumentException("El tipo no puede ser nulo ni vacío");
        }
        if (valoraciones == null) {
            throw new IllegalArgumentException("La lista de valoraciones no puede ser nula");
        }

        this.id = id;
        this.tema = tema;
        this.autor = autor;
        this.tipo = tipo;
        this.valoraciones = valoraciones;
    }

    /** @return Identificador único del contenido. */
    public String getId() {
        return id;
    }

    /** @return Tema o título del contenido. */
    public String getTema() {
        return tema;
    }

    /**
     * Modifica el tema o título del contenido.
     *
     * @param tema Nuevo tema. No debe ser nulo ni vacío.
     */
    public void setTema(String tema) {
        if (tema == null || tema.isBlank()) {
            throw new IllegalArgumentException("El tema no puede ser nulo ni vacío");
        }
        this.tema = tema;
    }

    /** @return Estudiante autor del contenido. */
    public Estudiante getAutor() {
        return autor;
    }

    /** @return Tipo de contenido (ej: Texto, Video, etc.). */
    public String getTipo() {
        return tipo;
    }

    /**
     * Modifica el tipo del contenido.
     *
     * @param tipo Nuevo tipo. No debe ser nulo ni vacío.
     */
    public void setTipo(String tipo) {
        if (tipo == null || tipo.isBlank()) {
            throw new IllegalArgumentException("El tipo no puede ser nulo ni vacío");
        }
        this.tipo = tipo;
    }

    /** @return Lista de valoraciones del contenido. */
    public ListaEnlazada<Valoracion> getValoraciones() {
        return valoraciones;
    }

    /**
     * Agrega una nueva valoración al contenido.
     *
     * @param valoracion Valoración a agregar. No debe ser nula.
     */
    public void agregarValoracion(Valoracion valoracion) {
        if (valoracion == null) {
            throw new IllegalArgumentException("La valoración no puede ser nula");
        }
        valoraciones.agregar(valoracion);
    }

    /**
     * Representación textual del contenido.
     *
     * @return Cadena que representa al contenido.
     */
    @Override
    public String toString() {
        return "Contenido{id='" + id + "', tema='" + tema + "', autor='" + autor.getNombre() +
                "', tipo='" + tipo + "', valoraciones=" + valoraciones.getTamanio() + "}";
    }
}
