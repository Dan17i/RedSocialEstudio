package co.edu.uniquindio.redsocial.models;

import co.edu.uniquindio.redsocial.models.services.interf.Tematico;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import co.edu.uniquindio.redsocial.models.structures.NodoLista;

import java.time.LocalDateTime;

/**
 * Representa un contenido publicado por un estudiante dentro de la red social.
 * Un contenido tiene un identificador único, tema, autor, tipo y una lista de valoraciones.
 * Esta clase proporciona métodos de acceso y modificación para sus atributos.
 *
 * @author
 * Daniel Jurado, Sebastián Torres, Juan Soto
 * @since 2025-05-12
 */
public class Contenido implements Tematico {

    /** Identificador único del contenido. */
    private final String id;

    /** Tema o título del contenido. */
    private String tema;

    /** Descripción del contenido. */
    private String descripcion;

    /** Estudiante que publicó el contenido. */
    private final Estudiante autor;

    /** Tipo de contenido (ej: Texto, Video, Imagen, etc.). */
    private String tipo;

    /** Fecha de creación del contenido. */
    private final LocalDateTime fechaCreacion;

    /** Lista de valoraciones que ha recibido el contenido. */
    private final ListaEnlazada<Valoracion> valoraciones;

    /**
     * Constructor para crear un contenido con los datos esenciales.
     *
     * @param id Identificador único del contenido. No debe ser nulo ni vacío.
     * @param tema Tema o título del contenido. No debe ser nulo ni vacío.
     * @param descripcion Descripción del contenido. Puede ser nula o vacía.
     * @param autor Estudiante que publica el contenido. No debe ser nulo.
     * @param tipo Tipo de contenido. No debe ser nulo ni vacío.
     * @param fechaCreacion Fecha en que se creó el contenido. No debe ser nula.
     * @param valoraciones Lista de valoraciones asociadas. No debe ser nula (puede estar vacía).
     * @throws IllegalArgumentException Si alguno de los parámetros obligatorios es inválido.
     */
    public Contenido(String id, String tema, String descripcion, Estudiante autor, String tipo, LocalDateTime fechaCreacion, ListaEnlazada<Valoracion> valoraciones) {
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
        if (fechaCreacion == null) {
            throw new IllegalArgumentException("La fecha de creación no puede ser nula");
        }
        if (valoraciones == null) {
            throw new IllegalArgumentException("La lista de valoraciones no puede ser nula");
        }

        this.id = id;
        this.tema = tema;
        this.descripcion = descripcion;
        this.autor = autor;
        this.tipo = tipo;
        this.fechaCreacion = fechaCreacion;
        this.valoraciones = valoraciones;
    }

    /**
     * Calcula el promedio de las valoraciones del contenido.
     *
     * Este método recorre la lista enlazada de valoraciones asociadas al contenido,
     * suma los puntajes de cada valoración y devuelve el promedio.
     * Si no hay valoraciones o la lista es nula, devuelve 0.0f.
     *
     * @return El promedio de los puntajes de las valoraciones como un valor float.
     */
    public float calcularValoracionPromedio() {
        if (valoraciones == null || valoraciones.getTamanio() == 0) {
            return 0.0f;
        }

        float suma = 0;
        NodoLista<Valoracion> actual = valoraciones.getCabeza();

        while (actual != null) {
            suma += actual.getDato().getPuntuacion();
            actual = actual.getSiguiente();
        }

        return suma / valoraciones.getTamanio();
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

    /** @return Descripción del contenido. */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Modifica la descripción del contenido.
     *
     * @param descripcion Nueva descripción. Puede ser vacía.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    /** @return Fecha de creación del contenido. */
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
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
