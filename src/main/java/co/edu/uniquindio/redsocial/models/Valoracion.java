package co.edu.uniquindio.redsocial.models;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Representa una valoración que un estudiante realiza sobre un contenido dentro
 * de la plataforma de red social educativa.
 * <p>
 * Cada valoración incluye una puntuación del 1 al 5, un comentario opcional,
 * la fecha y hora en que se realizó, y está asociada a un estudiante y un contenido específicos.
 * </p>
 * <p>
 * Esta clase es inmutable excepto por el comentario, que puede modificarse después de la creación.
 * La gestión de la agregación de valoraciones a contenidos y estudiantes
 * y la validación para evitar valoraciones duplicadas
 * debe ser manejada externamente (por ejemplo, en clases de servicio o controladores).
 * </p>
 *
 * @author Daniel Jurado
 * @author Sebastian Torres
 * @author Juan Soto
 * @since 2025-05-13
 * @version 1.0
 * @since 2025-05-18
 */
public class Valoracion {

    private final String id;                 // Identificador único de la valoración
    private final Estudiante estudiante;     // Estudiante que realiza la valoración (no puede ser nulo)
    private final Contenido contenido;       // Contenido que está siendo valorado (no puede ser nulo)
    private final int puntuacion;            // Puntuación del contenido (de 1 a 5)
    private String comentario;               // Comentario opcional sobre el contenido
    private final LocalDateTime fechaValoracion;  // Fecha y hora en que se realizó la valoración

    /**
     * Constructor de la clase Valoracion.
     *
     * @param estudiante  El estudiante que realiza la valoración (no puede ser nulo).
     * @param contenido   El contenido que está siendo valorado (no puede ser nulo).
     * @param puntuacion  La puntuación otorgada (de 1 a 5).
     * @param comentario  Un comentario opcional sobre el contenido valorado.
     * @throws IllegalArgumentException Si la puntuación no está entre 1 y 5,
     *                                  o si estudiante o contenido son nulos.
     */
    public Valoracion(Estudiante estudiante, Contenido contenido, int puntuacion, String comentario) {
        if (estudiante == null) {
            throw new IllegalArgumentException("El estudiante no puede ser nulo.");
        }
        if (contenido == null) {
            throw new IllegalArgumentException("El contenido no puede ser nulo.");
        }
        if (puntuacion < 1 || puntuacion > 5) {
            throw new IllegalArgumentException("La puntuación debe estar entre 1 y 5.");
        }

        this.id = UUID.randomUUID().toString();
        this.estudiante = estudiante;
        this.contenido = contenido;
        this.puntuacion = puntuacion;
        this.comentario = (comentario != null && !comentario.trim().isEmpty()) ? comentario : "Sin comentario";
        this.fechaValoracion = LocalDateTime.now();
    }

    /**
     * Obtiene el identificador único de la valoración.
     *
     * @return El ID único de la valoración.
     */
    public String getId() {
        return id;
    }

    /**
     * Obtiene el estudiante que realizó la valoración.
     *
     * @return El estudiante que hizo la valoración.
     */
    public Estudiante getEstudiante() {
        return estudiante;
    }

    /**
     * Obtiene el contenido que fue valorado.
     *
     * @return El contenido valorado.
     */
    public Contenido getContenido() {
        return contenido;
    }

    /**
     * Obtiene la puntuación otorgada en la valoración.
     *
     * @return La puntuación de la valoración (entre 1 y 5).
     */
    public int getPuntuacion() {
        return puntuacion;
    }

    /**
     * Obtiene el comentario asociado a la valoración.
     *
     * @return El comentario de la valoración.
     */
    public String getComentario() {
        return comentario;
    }

    /**
     * Establece un nuevo comentario para la valoración.
     *
     * @param comentario El nuevo comentario de la valoración.
     */
    public void setComentario(String comentario) {
        this.comentario = (comentario != null && !comentario.trim().isEmpty()) ? comentario : "Sin comentario";
    }

    /**
     * Obtiene la fecha y hora en que se realizó la valoración.
     *
     * @return La fecha y hora de la valoración.
     */
    public LocalDateTime getFechaValoracion() {
        return fechaValoracion;
    }

    /**
     * Devuelve una representación en cadena de la valoración.
     *
     * @return Una cadena que representa la valoración.
     */
    @Override
    public String toString() {
        return String.format(
                "Valoración{id='%s', estudiante='%s', contenido='%s', puntuacion=%d, comentario='%s', fecha='%s'}",
                id,
                estudiante.getNombre(),
                contenido.getTema(),
                puntuacion,
                comentario,
                fechaValoracion
        );
    }

    /**
     * Compara esta valoración con otra para determinar si son iguales,
     * basándose en el identificador único.
     *
     * @param o El objeto a comparar.
     * @return true si los objetos son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Valoracion)) return false;
        Valoracion that = (Valoracion) o;
        return id.equals(that.id);
    }

    /**
     * Calcula el código hash de esta valoración.
     *
     * @return El código hash basado en el identificador único.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
