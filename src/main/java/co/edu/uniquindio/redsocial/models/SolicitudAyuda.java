package co.edu.uniquindio.redsocial.models;

import co.edu.uniquindio.redsocial.models.Enums.EstadoSolicitud;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Representa una solicitud de ayuda realizada por un estudiante dentro
 * de la red social educativa. Cada solicitud tiene un tema, nivel de urgencia,
 * descripción del problema, fecha y hora de creación, un estado de seguimiento,
 * y está asociada a un estudiante solicitante.
 *
 * <p>
 * Esta clase actúa como un modelo de datos y no implementa lógica de negocio
 * como asignaciones o notificaciones. Dicha lógica debería ser gestionada
 * por una clase externa como un gestor o controlador de solicitudes.
 * </p>
 *
 * @author Daniel Jurado
 * @author Sebastian Torres
 * @author Juan Soto
 * @since 2025-05-13
 *
 */
public class SolicitudAyuda implements Comparable<SolicitudAyuda> {

    private final String id;
    private final String tema;
    private final int urgencia;
    private final LocalDateTime fechaSolicitud;
    private final Estudiante estudiante;
    private final String descripcion;
    private EstadoSolicitud estado;

    /**
     * Crea una nueva solicitud de ayuda con la fecha actual.
     *
     * @param tema        Tema específico de la solicitud.
     * @param urgencia    Nivel de urgencia (1 a 10).
     * @param estudiante  Estudiante que realiza la solicitud.
     * @param descripcion Descripción detallada del problema.
     * @throws IllegalArgumentException si algún parámetro es inválido.
     */
    public SolicitudAyuda(String tema, int urgencia, Estudiante estudiante, String descripcion, EstadoSolicitud estadoSolicitud) {
        this(tema, urgencia, estudiante, descripcion, LocalDateTime.now());
        this.estado = estadoSolicitud;
    }

    /**
     * Crea una nueva solicitud de ayuda con una fecha personalizada.
     * Útil para pruebas unitarias o importaciones externas.
     *
     * @param tema            Tema específico de la solicitud.
     * @param urgencia        Nivel de urgencia (1 a 10).
     * @param estudiante      Estudiante que realiza la solicitud.
     * @param descripcion     Descripción detallada del problema.
     * @param fechaSolicitud  Fecha y hora de creación de la solicitud.
     * @throws IllegalArgumentException si algún parámetro es inválido.
     */
    public SolicitudAyuda(String tema, int urgencia, Estudiante estudiante, String descripcion, LocalDateTime fechaSolicitud) {
        if (tema == null || tema.isBlank()) {
            throw new IllegalArgumentException("El tema no puede ser nulo o vacío");
        }
        if (urgencia < 1 || urgencia > 10) {
            throw new IllegalArgumentException("La urgencia debe estar entre 1 y 10");
        }
        if (estudiante == null) {
            throw new IllegalArgumentException("El estudiante no puede ser nulo");
        }
        if (descripcion == null || descripcion.isBlank()) {
            throw new IllegalArgumentException("La descripción no puede ser nula o vacía");
        }
        if (fechaSolicitud == null) {
            throw new IllegalArgumentException("La fecha de solicitud no puede ser nula");
        }

        this.id = UUID.randomUUID().toString();
        this.tema = tema;
        this.urgencia = urgencia;
        this.estudiante = estudiante;
        this.descripcion = descripcion;
        this.fechaSolicitud = fechaSolicitud;
        this.estado = EstadoSolicitud.PENDIENTE;
    }

    public String getId() {
        return id;
    }

    public String getTema() {
        return tema;
    }

    public int getUrgencia() {
        return urgencia;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDateTime getFechaSolicitud() {
        return fechaSolicitud;
    }

    public EstadoSolicitud getEstado() {
        return estado;
    }

    /**
     * Cambia el estado de la solicitud.
     *
     * @param estadoSolicitud Nuevo estado a asignar.
     * @throws IllegalArgumentException si el estado es nulo.
     */
    public void setEstado(EstadoSolicitud estadoSolicitud) {
        if (estado == null) {
            throw new IllegalArgumentException("El estado no puede ser nulo");
        }
        this.estado = estado;
    }

    /**
     * Compara esta solicitud con otra según el nivel de urgencia
     * (orden descendente: mayor urgencia primero).
     *
     * @param otra Otra solicitud a comparar.
     * @return Valor negativo si esta tiene más urgencia, positivo si menos, cero si igual.
     */
    @Override
    public int compareTo(SolicitudAyuda otra) {
        return Integer.compare(otra.urgencia, this.urgencia);
    }

    /**
     * Representación legible de la solicitud.
     *
     * @return Cadena con los campos clave de la solicitud.
     */
    @Override
    public String toString() {
        return String.format(
                "SolicitudAyuda{id='%s', tema='%s', urgencia=%d, estudiante='%s', estado=%s, fecha=%s, descripcion='%s'}",
                id, tema, urgencia, estudiante.getNombre(), estado, fechaSolicitud, descripcion
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SolicitudAyuda)) return false;
        SolicitudAyuda that = (SolicitudAyuda) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
