package co.edu.uniquindio.redsocial.models;

/**
 * Representa una petición de ayuda realizada por un estudiante,
 * con un nivel de urgencia para priorizar en la cola.
 */
public class SolicitudAyuda implements Comparable<SolicitudAyuda> {

    private String tema;
    private int urgencia; // 1 (baja) a 10 (alta)
    private Estudiante estudiante;

    public SolicitudAyuda(String tema, int urgencia, Estudiante estudiante) {
        if (tema == null || tema.isEmpty()) {
            throw new IllegalArgumentException("Tema no puede ser null o vacío");
        }
        setUrgencia(urgencia);
        this.tema = tema;
        this.estudiante = estudiante;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        if (tema == null || tema.isEmpty()) {
            throw new IllegalArgumentException("Tema no puede ser null o vacío");
        }
        this.tema = tema;
    }

    public int getUrgencia() {
        return urgencia;
    }

    public void setUrgencia(int urgencia) {
        if (urgencia < 1 || urgencia > 10) {
            throw new IllegalArgumentException("La urgencia debe estar entre 1 y 10");
        }
        this.urgencia = urgencia;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    @Override
    public int compareTo(SolicitudAyuda otra) {
        return Integer.compare(otra.urgencia, this.urgencia);
    }

    @Override
    public String toString() {
        String nombre = (estudiante != null) ? estudiante.getNombre() : "N/A";
        return String.format("SolicitudAyuda{tema='%s', urgencia=%d, estudiante=%s}", tema, urgencia, nombre);
    }
}