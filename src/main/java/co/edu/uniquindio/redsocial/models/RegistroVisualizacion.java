package co.edu.uniquindio.redsocial.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Representa un registro de visualización de un {@link Contenido} por parte de un {@link Usuario},
 * en una fecha y hora específica.
 * Esta clase es inmutable. Una vez creado el objeto, sus atributos no pueden modificarse.
 *
 * @author Daniel Jurado
 * @author Sebastian Torres
 * @author Juan Soto
 * @since 2025-05-20
 */
public class RegistroVisualizacion {

    /**
     * Usuario que visualizó el contenido.
     */
    private final Usuario usuario;

    /**
     * Contenido visualizado.
     */
    private final Contenido contenido;

    /**
     * Fecha y hora en que el contenido fue visualizado.
     */
    private final LocalDateTime fechaVisualizacion;

    /**
     * Crea un nuevo registro de visualización para un contenido específico por parte de un usuario,
     * en una fecha determinada.
     *
     * @param usuario El usuario que visualizó el contenido.
     * @param contenido El contenido que fue visualizado.
     * @param fechaVisualizacion La fecha y hora en la que ocurrió la visualización.
     * @throws IllegalArgumentException si alguno de los parámetros es {@code null}.
     */
    public RegistroVisualizacion(Usuario usuario, Contenido contenido, LocalDateTime fechaVisualizacion) {
        if (usuario == null || contenido == null || fechaVisualizacion == null) {
            throw new IllegalArgumentException("Usuario, contenido y fecha de visualización no pueden ser nulos");
        }
        this.usuario = usuario;
        this.contenido = contenido;
        this.fechaVisualizacion = fechaVisualizacion;
    }

    /**
     * Obtiene el usuario que visualizó el contenido.
     *
     * @return El usuario asociado a este registro.
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Obtiene el contenido visualizado.
     *
     * @return El contenido asociado a este registro.
     */
    public Contenido getContenido() {
        return contenido;
    }

    /**
     * Obtiene la fecha y hora de la visualización.
     *
     * @return La fecha y hora en que se visualizó el contenido.
     */
    public LocalDateTime getFechaVisualizacion() {
        return fechaVisualizacion;
    }

    /**
     * Retorna una representación textual del registro de visualización, incluyendo
     * el nombre del usuario, la información del contenido y la fecha de visualización.
     *
     * @return Una cadena con el usuario, contenido y la fecha de visualización.
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return usuario.getNombre() + " visualizó: " + contenido.toString() +
                " (el " + fechaVisualizacion.format(formatter) + ")";
    }
}
