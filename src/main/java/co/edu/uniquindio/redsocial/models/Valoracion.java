package co.edu.uniquindio.redsocial.models;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Clase que representa una valoración realizada por un estudiante a un contenido.
 * Una valoración incluye la puntuación dada, un comentario opcional, la fecha de la valoración,
 * y el estudiante y contenido asociados a la valoración.
 */
public class Valoracion {

    private final String id;                 // Identificador único de la valoración
    private final Estudiante estudiante;    // Estudiante que realiza la valoración (no puede ser nulo)
    private final Contenido contenido;      // Contenido que está siendo valorado (no puede ser nulo)
    private final int puntuacion;           // Puntuación del contenido (de 1 a 5)
    private String comentario;              // Comentario opcional sobre el contenido
    private final LocalDateTime fechaValoracion;  // Fecha y hora en que se realizó la valoración

    /**
     * Constructor de la clase Valoracion.
     *
     * @param estudiante El estudiante que realiza la valoración (no puede ser nulo).
     * @param contenido El contenido que está siendo valorado (no puede ser nulo).
     * @param puntuacion La puntuación otorgada (de 1 a 5).
     * @param comentario Un comentario opcional sobre el contenido valorado.
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

    @Override
    public String toString() {
        return String.format(
                "Valoración{id='%s', estudiante='%s', contenido='%s', puntuacion=%d, comentario='%s', fecha='%s'}",
                id,
                estudiante.getNombre(),
                contenido.getTema(),   // Asumiendo que Contenido tiene método getTitulo()
                puntuacion,
                comentario,
                fechaValoracion.toString()
        );
    }
}
