package co.edu.uniquindio.redsocial.models;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Representa una solicitud de ayuda realizada por un estudiante dentro
 * de la red social educativa. Cada solicitud tiene un tema, nivel de urgencia,
 * descripción del problema, fecha y hora de creación, un estado de seguimiento,
 * y está asociada a un estudiante solicitante.
 */
public class SolicitudAyuda implements Comparable<SolicitudAyuda> {

    /**
     * Identificador único de la solicitud.
     */
    private final String id;

    /**
     * Tema sobre el cual se solicita la ayuda.
     */
    private final String tema;

    /**
     * Nivel de urgencia de la solicitud (1 a 10).
     */
    private final int urgencia;

    /**
     * Fecha y hora en la que se creó la solicitud.
     */
    private final LocalDateTime fechaSolicitud;

    /**
     * Estudiante que realiza la solicitud de ayuda.
     */
    private final Estudiante estudiante;

    /**
     * Descripción detallada del problema o consulta.
     */
    private final String descripcion;

    /**
     * Estado actual de la solicitud.
     */
    private EstadoSolicitud estado;

    /**
     * Enum que representa los estados posibles de una solicitud.
     */
    public enum EstadoSolicitud {
        PENDIENTE, EN_PROGRESO, RESUELTA
    }

    /**
     * Crea una nueva solicitud de ayuda.
     *
     * @param tema       Tema específico de la solicitud.
     * @param urgencia   Nivel de urgencia (1 a 10).
     * @param estudiante Estudiante que realiza la solicitud.
     * @param descripcion Descripción detallada del problema.
     * @throws IllegalArgumentException si algún parámetro es inválido.
     */
    public SolicitudAyuda(String tema, int urgencia, Estudiante estudiante, String descripcion) {
        if (tema == null || tema.isEmpty()) {
            throw new IllegalArgumentException("El tema no puede ser nulo o vacío");
        }
        if (urgencia < 1 || urgencia > 10) {
            throw new IllegalArgumentException("La urgencia debe estar entre 1 y 10");
        }
        if (estudiante == null) {
            throw new IllegalArgumentException("El estudiante no puede ser nulo");
        }
        if (descripcion == null || descripcion.isEmpty()) {
            throw new IllegalArgumentException("La descripción no puede ser nula o vacía");
        }

        this.id = UUID.randomUUID().toString();
        this.tema = tema;
        this.urgencia = urgencia;
        this.estudiante = estudiante;
        this.descripcion = descripcion;
        this.fechaSolicitud = LocalDateTime.now();
        this.estado = EstadoSolicitud.PENDIENTE;
    }

    /**
     * Obtiene el identificador único de la solicitud.
     *
     * @return ID único generado automáticamente.
     */
    public String getId() {
        return id;
    }

    /**
     * Obtiene el tema de la solicitud.
     *
     * @return Tema del problema.
     */
    public String getTema() {
        return tema;
    }

    /**
     * Obtiene el nivel de urgencia.
     *
     * @return Urgencia (1-10).
     */
    public int getUrgencia() {
        return urgencia;
    }

    /**
     * Obtiene el estudiante que hizo la solicitud.
     *
     * @return Estudiante asociado.
     */
    public Estudiante getEstudiante() {
        return estudiante;
    }

    /**
     * Obtiene la descripción detallada del problema.
     *
     * @return Descripción del problema.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Obtiene la fecha y hora en que se creó la solicitud.
     *
     * @return Fecha y hora de creación.
     */
    public LocalDateTime getFechaSolicitud() {
        return fechaSolicitud;
    }

    /**
     * Obtiene el estado actual de la solicitud.
     *
     * @return Estado de la solicitud (PENDIENTE, EN_PROGRESO, RESUELTA).
     */
    public EstadoSolicitud getEstado() {
        return estado;
    }

    /**
     * Cambia el estado de la solicitud.
     *
     * @param estado Nuevo estado que se desea asignar.
     * @throws IllegalArgumentException si el estado es nulo.
     */
    public void setEstado(EstadoSolicitud estado) {
        if (estado == null) {
            throw new IllegalArgumentException("El estado no puede ser nulo");
        }
        this.estado = estado;
    }

    /**
     * Compara esta solicitud con otra según el nivel de urgencia,
     * en orden descendente (mayor urgencia tiene prioridad).
     *
     * @param otra Otra solicitud a comparar.
     * @return Valor negativo si esta tiene más urgencia, positivo si menos, cero si igual.
     */
    @Override
    public int compareTo(SolicitudAyuda otra) {
        return Integer.compare(otra.urgencia, this.urgencia);
    }

    /**
     * Devuelve una representación en forma de cadena de la solicitud.
     *
     * @return Cadena con todos los campos relevantes.
     */
    @Override
    public String toString() {
        return String.format(
                "SolicitudAyuda{id='%s', tema='%s', urgencia=%d, estudiante='%s', estado=%s, fecha=%s, descripcion='%s'}",
                id, tema, urgencia, estudiante.getNombre(), estado, fechaSolicitud, descripcion
        );
    }

    /**
     * Verifica si esta solicitud es igual a otra basada en el ID único.
     *
     * @param o Objeto a comparar.
     * @return true si tienen el mismo ID, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SolicitudAyuda)) return false;
        SolicitudAyuda that = (SolicitudAyuda) o;
        return Objects.equals(id, that.id);
    }

    /**
     * Calcula el código hash basado en el ID.
     *
     * @return Código hash del objeto.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
