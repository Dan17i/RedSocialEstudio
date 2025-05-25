package co.edu.uniquindio.redsocial.models;

import co.edu.uniquindio.redsocial.ArchivoMultimedia;
import co.edu.uniquindio.redsocial.models.Enums.TipoContenido;
import co.edu.uniquindio.redsocial.models.services.interf.Tematico;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import co.edu.uniquindio.redsocial.models.structures.NodoLista;

import java.io.File;
import java.time.LocalDateTime;

/**
 * Representa un contenido publicado por un estudiante dentro de la red social.
 * Un contenido tiene un identificador único, tema, autor, tipo y una lista de valoraciones.
 * Esta clase proporciona métodos de acceso para sus atributos y evita modificaciones posteriores.
 *
 * @author
 * Daniel Jurado, Sebastián Torres, Juan Soto
 * @since 2025-05-12
 */
public class Contenido implements Tematico {

    /** Identificador único del contenido. */
    private final String id;

    /** Tema o título del contenido. */
    private final String tema;

    /** Descripción del contenido. */
    private final String descripcion;

    /** Estudiante que publicó el contenido. */
    private final Estudiante autor;

    /** Tipo de contenido (ej: Texto, Video, Imagen, etc.). */
    private final TipoContenido tipo;

    /** Fecha de creación del contenido. */
    private final LocalDateTime fechaCreacion;

    /** Lista de valoraciones que ha recibido el contenido. */
    private final ListaEnlazada<Valoracion> valoraciones;

    /** archivos multimedia para ser obtenidos   */
    private ArchivoMultimedia archivomultimedia;

    private String rutaArchivo;


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
    public Contenido(String id, String tema, String descripcion, Estudiante autor, TipoContenido tipo, LocalDateTime fechaCreacion, ListaEnlazada<Valoracion> valoraciones, ArchivoMultimedia archivomultimedia) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("El id no puede ser nulo ni vacío");
        }
        if (tema == null || tema.isBlank()) {
            throw new IllegalArgumentException("El tema no puede ser nulo ni vacío");
        }
        if (autor == null) {
            throw new IllegalArgumentException("El autor no puede ser nulo");
        }
        if (tipo == null || tipo.name().isBlank()) {
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
        this.archivomultimedia = archivomultimedia;
    }

    /**
     * Calcula el promedio de las valoraciones del contenido.
     * Recorre la lista enlazada de valoraciones, suma los puntajes y devuelve el promedio.
     *
     * @return El promedio de los puntajes de las valoraciones como un valor float. Devuelve 0.0f si no hay valoraciones.
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

    /** @return Descripción del contenido. */
    public String getDescripcion() {
        return descripcion;
    }

    /** @return Estudiante autor del contenido. */
    public Estudiante getAutor() {
        return autor;
    }

    /** @return Tipo de contenido (ej: Texto, Video, etc.). */
    public TipoContenido getTipo() {
        return tipo;
    }

    /** @return Fecha de creación del contenido. */
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    /** @return Lista de valoraciones del contenido. */
    public ListaEnlazada<Valoracion> getValoraciones() {
        return valoraciones;
    }

    /** @return Archivos multimedia de contenido*/
    public ArchivoMultimedia getArchivoMultimedia(){return archivomultimedia;}

    public void setArchivomultimedia(ArchivoMultimedia archivomultimedia) {
        this.archivomultimedia = archivomultimedia;
    }

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    public String getTipoMime(){
        if(tipo==null)
            return "application/octet-stream";
        switch(tipo.name().toLowerCase()){
            case "imagen": return "image/jpeg";
            case "video": return "video/mp4";
            case "audio": return "audio/mpeg";
            case "pdf": return "application/pdf";
            case "texto": return "text/plain";
            default: return "application/octet-stream";
        }
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
     * Calcula el promedio de las valoraciones recibidas por este contenido.
     *
     * @return El promedio de valoraciones, o 0 si no hay valoraciones.
     */
    public double promedioValoraciones() {
        if (valoraciones.isEmpty()) return 0.0;
        double suma = 0.0;
        int contador = 0;
        for (Valoracion v : valoraciones) {
            suma += v.getPuntuacion(); // Asumiendo que este método existe
            contador++;
        }
        return suma / contador;
    }

   public boolean esValido(){
        if (rutaArchivo == null || rutaArchivo.isEmpty() || tipo==null)
            return false;
        File archivo= new File(rutaArchivo);
        return  archivo.exists();
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
