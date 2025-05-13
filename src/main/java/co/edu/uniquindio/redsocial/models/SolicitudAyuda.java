package co.edu.uniquindio.redsocial.models;

/**
 * Representa una solicitud de ayuda realizada por un estudiante.
 * Cada solicitud tiene un tema, un nivel de urgencia y el estudiante solicitante.
 *
 * Puede ser utilizada en una cola de prioridad para gestionar solicitudes por nivel de urgencia.
 *
 * @author Daniel Jurado
 * @author Sebastian Torres
 * @author Juan Soto
 * @since 2025-05-13
 */
public class SolicitudAyuda implements Comparable<SolicitudAyuda> {
    private String tema;
    private int urgencia; // Entre 1 (menos urgente) y 10 (muy urgente)
    private Estudiante estudiante;

    /**
     * Constructor para crear una nueva solicitud de ayuda.
     *
     * @param tema      Tema sobre el cual se requiere ayuda.
     * @param urgencia  Nivel de urgencia (1 a 10).
     * @param estudiante Estudiante que solicita la ayuda.
     */
    public SolicitudAyuda(String tema, int urgencia, Estudiante estudiante) {
        this.tema = tema;
        setUrgencia(urgencia); // Validación
        this.estudiante = estudiante;
    }

    // Getters y Setters
    public String getTema() { return tema; }
    public void setTema(String tema) { this.tema = tema; }

    public int getUrgencia() { return urgencia; }

    public void setUrgencia(int urgencia) {
        if (urgencia < 1 || urgencia > 10) {
            throw new IllegalArgumentException("La urgencia debe estar entre 1 y 10.");
        }
        this.urgencia = urgencia;
    }

    public Estudiante getEstudiante() { return estudiante; }
    public void setEstudiante(Estudiante estudiante) { this.estudiante = estudiante; }

    /**
     * Permite comparar dos solicitudes de ayuda por su urgencia.
     * Se da prioridad a valores más altos de urgencia.
     */
    @Override
    public int compareTo(SolicitudAyuda otra) {
        return Integer.compare(otra.urgencia, this.urgencia); // Descendente
    }

    /**
     * Representación en texto de la solicitud.
     */
    @Override
    public String toString() {
        return "SolicitudAyuda{" +
                "tema='" + tema + '\'' +
                ", urgencia=" + urgencia +
                ", estudiante=" + (estudiante != null ? estudiante.getNombre() : "N/A") +
                '}';
    }
}
