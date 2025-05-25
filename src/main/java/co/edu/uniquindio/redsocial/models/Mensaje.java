package co.edu.uniquindio.redsocial.models;

import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import co.edu.uniquindio.redsocial.models.structures.NodoLista;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Representa un mensaje enviado entre estudiantes o a un grupo de estudiantes.
 * Incluye manejo de bandeja de entrada y validación robusta del destinatario.
 * @author Daniel Jurado
 * @author Sebastian Torres
 * @author Juan Soto
 * @since 2025-05-12
 */
public class Mensaje {

    private Estudiante remitente;
    private Object destinatario; // Puede ser un Estudiante o un GrupoEstudio
    private String texto;
    private LocalDateTime fecha;

    /**
     * Constructor para crear un mensaje con los parámetros proporcionados.
     *
     * @param remitente El estudiante que envía el mensaje.
     * @param destinatario El destinatario, que puede ser un Estudiante o un GrupoEstudio.
     * @param texto El contenido del mensaje.
     * @param fecha La fecha y hora en que el mensaje fue enviado.
     */
    public Mensaje(Estudiante remitente, Object destinatario, String texto, LocalDateTime fecha) {
        if (remitente == null || destinatario == null || texto == null || texto.isEmpty()) {
            throw new IllegalArgumentException("Los parámetros no pueden ser nulos o vacíos");
        }
        if (!(destinatario instanceof Estudiante || destinatario instanceof GrupoEstudio)) {
            throw new IllegalArgumentException("El destinatario debe ser un Estudiante o un GrupoEstudio");
        }
        this.remitente = remitente;
        this.destinatario = destinatario;
        this.texto = texto;
        this.fecha = fecha;
    }

    /**
     * Envía el mensaje al destinatario.
     * Si el destinatario es un estudiante, se añade a su bandeja de entrada.
     * Si el destinatario es un grupo, se envía a todos los miembros del grupo.
     */
    public void enviar() {
        if (destinatario instanceof Estudiante estudiante) {
            estudiante.recibirMensaje(this);
        } else if (destinatario instanceof GrupoEstudio grupo) {
            ListaEnlazada<Estudiante> miembros = grupo.getMiembros(); // ← Tipo correcto
            NodoLista<Estudiante> actual = miembros.getCabeza();
            while (actual != null) {
                actual.getDato().recibirMensaje(this);
                actual = actual.getSiguiente();
            }
        }
    }

    /**
     * Devuelve una representación en cadena del mensaje.
     *
     * @return Cadena que describe el mensaje.
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
     * Compara si dos mensajes son iguales por contenido.
     *
     * @param obj El objeto a comparar.
     * @return true si son iguales, false si no.
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
     * Genera el hash del mensaje.
     *
     * @return Código hash basado en los campos del mensaje.
     */
    @Override
    public int hashCode() {
        return Objects.hash(remitente, destinatario, texto, fecha);
    }

    // Getters y Setters
    public Estudiante getRemitente() { return remitente; }
    public void setRemitente(Estudiante remitente) { this.remitente = remitente; }

    public Object getDestinatario() { return destinatario; }
    public void setDestinatario(Object destinatario) {
        if (!(destinatario instanceof Estudiante || destinatario instanceof GrupoEstudio)) {
            throw new IllegalArgumentException("El destinatario debe ser un Estudiante o un GrupoEstudio");
        }
        this.destinatario = destinatario;
    }

    public String getTexto() { return texto; }
    public void setTexto(String texto) {
        if (texto == null || texto.isEmpty()) {
            throw new IllegalArgumentException("El texto no puede ser nulo o vacío");
        }
        this.texto = texto;
    }

    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}
