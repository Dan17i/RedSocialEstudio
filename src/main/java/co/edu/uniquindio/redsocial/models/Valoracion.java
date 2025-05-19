package co.edu.uniquindio.redsocial.models;

/**
 * Clase que representa una valoración realizada por un estudiante a un contenido.
 * Una valoración incluye la puntuación dada, un comentario opcional, y el estudiante que realizó la valoración.
 */
public class Valoracion {

    // Atributos de la clase
    private Estudiante estudiante;  // Estudiante que realiza la valoración
    private int puntuacion;         // Puntuación del contenido (de 1 a 5)
    private String comentario;      // Comentario opcional sobre el contenido

    /**
     * Constructor de la clase Valoracion.
     *
     * @param estudiante El estudiante que realiza la valoración.
     * @param puntuacion La puntuación otorgada (de 1 a 5).
     * @param comentario Un comentario opcional sobre el contenido valorado.
     * @throws IllegalArgumentException Si la puntuación no está entre 1 y 5.
     */
    public Valoracion(Estudiante estudiante, int puntuacion, String comentario) {
        // Validar que la puntuación esté en el rango correcto
        if (puntuacion < 1 || puntuacion > 5) {
            throw new IllegalArgumentException("La puntuación debe estar entre 1 y 5.");
        }

        this.estudiante = estudiante;
        this.puntuacion = puntuacion;
        this.comentario = comentario != null && !comentario.trim().isEmpty() ? comentario : "Sin comentario";  // Evitar null o vacío
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
     * Establece el estudiante que realizó la valoración.
     *
     * @param estudiante El estudiante que hizo la valoración.
     */
    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
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
     * Establece la puntuación de la valoración.
     *
     * @param puntuacion La nueva puntuación (entre 1 y 5).
     * @throws IllegalArgumentException Si la puntuación no está entre 1 y 5.
     */
    public void setPuntuacion(int puntuacion) {
        if (puntuacion < 1 || puntuacion > 5) {
            throw new IllegalArgumentException("La puntuación debe estar entre 1 y 5.");
        }
        this.puntuacion = puntuacion;
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
        this.comentario = comentario;
    }

    @Override
    public String toString() {
        return "Valoración de " + estudiante.getNombre() + ": " + puntuacion + " estrellas. Comentario: " + comentario;
    }
}
