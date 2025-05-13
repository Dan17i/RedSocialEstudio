package co.edu.uniquindio.redsocial.models;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Representa un mensaje enviado entre estudiantes o a un grupo de estudiantes.
 * @author Daniel Jurado
 * @author Sebastian Torres
 * @author Juan Soto
 * @since 2025-05-12
 */
class Mensaje {
    private Estudiante remitente;
    private Object destinatario; // Puede ser un Estudiante o un Grupo
    private String texto;
    private LocalDateTime fecha;

    /**
     * Constructor para crear un mensaje con los parámetros proporcionados.
     *
     * @param remitente   El estudiante que envía el mensaje.
     * @param destinatario El destinatario, que puede ser un Estudiante o un Grupo.
     * @param texto       El contenido del mensaje.
     * @param fecha       La fecha y hora en que el mensaje fue enviado.
     */
    public Mensaje(Estudiante remitente, Object destinatario, String texto, LocalDateTime fecha) {
        if (remitente == null || destinatario == null || texto == null || texto.isEmpty()) {
            throw new IllegalArgumentException("Los parámetros no pueden ser nulos o vacíos");
        }
        this.remitente = remitente;
        this.destinatario = destinatario;
        this.texto = texto;
        this.fecha = fecha;
    }
    /**
     * Envía el mensaje al destinatario.
     * Si el destinatario es un grupo, se envía a todos los miembros del grupo.
     */
    public void enviar() {
        if (destinatario instanceof Estudiante) {
            // Enviar mensaje a un solo estudiante
            System.out.println("Mensaje enviado a " + ((Estudiante) destinatario).getNombre() + ": " + this.texto);
        } else if (destinatario instanceof GrupoEstudio) {
            // Enviar mensaje a todos los miembros del grupo de estudio
            GrupoEstudio grupo = (GrupoEstudio) destinatario;
            for (Estudiante estudiante : grupo.getMiembros()) {
                System.out.println("Mensaje enviado a " + estudiante.getNombre() + ": " + this.texto);
            }
        } else {
            throw new IllegalArgumentException("El destinatario debe ser un Estudiante o un GrupoEstudio");
        }
    }


    /**
     * Obtiene una representación de cadena del mensaje.
     *
     * @return La representación en cadena del mensaje.
     */
    @Override
    public String toString() {
        String destinatarioStr = destinatario instanceof Estudiante ?
                ((Estudiante) destinatario).getNombre() :
                "Grupo";

        return "De: " + remitente.getNombre() + "\n" +
                "Para: " + destinatarioStr + "\n" +
                "Fecha: " + fecha + "\n" +
                "Mensaje: " + texto;
    }

    /**
     * Compara dos mensajes para verificar si son iguales.
     *
     * @param obj El objeto a comparar con el mensaje actual.
     * @return true si los mensajes son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Mensaje mensaje = (Mensaje) obj;
        return remitente.equals(mensaje.remitente) &&
                destinatario.equals(mensaje.destinatario) &&
                texto.equals(mensaje.texto) &&
                fecha.equals(mensaje.fecha);
    }

    /**
     * Genera un código hash para el mensaje.
     *
     * @return El código hash del mensaje.
     */
    @Override
    public int hashCode() {
        return Objects.hash(remitente, destinatario, texto, fecha);
    }

    // Getters y Setters
    public Estudiante getRemitente() { return remitente; }
    public void setRemitente(Estudiante remitente) { this.remitente = remitente; }
    public Object getDestinatario() { return destinatario; }
    public void setDestinatario(Object destinatario) { this.destinatario = destinatario; }
    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}
