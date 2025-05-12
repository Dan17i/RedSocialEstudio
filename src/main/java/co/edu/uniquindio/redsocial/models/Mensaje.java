package co.edu.uniquindio.redsocial.models;

import java.time.LocalDateTime;

class Mensaje {
    private Estudiante remitente;
    private Estudiante destinatario;
    private String texto;
    private LocalDateTime fecha;

    public Mensaje(Estudiante remitente, Estudiante destinatario, String texto, LocalDateTime fecha) {
        this.remitente = remitente;
        this.destinatario = destinatario;
        this.texto = texto;
        this.fecha = fecha;
    }

    public void enviar() {
        // Lógica real: almacenar en base de datos, notificar al destinatario, etc.
        System.out.println("Mensaje enviado: " + this.texto);
    }

    // Getters y Setters
    public Estudiante getRemitente() { return remitente; }
    public void setRemitente(Estudiante remitente) { this.remitente = remitente; }
    public Estudiante getDestinatario() { return destinatario; }
    public void setDestinatario(Estudiante destinatario) { this.destinatario = destinatario; }
    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}
