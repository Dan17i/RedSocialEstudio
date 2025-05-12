package co.edu.uniquindio.redsocial.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

class Reporte {
    private String id;
    private String tipo;
    private LocalDateTime fechaGeneracion;
    private ListaEnlazada<String> datos;

    public Reporte(String id, String tipo, LocalDateTime fechaGeneracion, ListaEnlazada<String> datos) {
        this.id = id;
        this.tipo = tipo;
        this.fechaGeneracion = fechaGeneracion;
        this.datos = datos;
    }

    public String generarContenido() { return datos.toString(); }
    public void exportar(String formato) { /* Lógica de exportación TODO TORRES */ }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public LocalDateTime getFechaGeneracion() { return fechaGeneracion; }
    public void setFechaGeneracion(LocalDateTime fechaGeneracion) { this.fechaGeneracion = fechaGeneracion; }
    public ListaEnlazada<String> getDatos() { return datos; }
    public void setDatos(ListaEnlazada<String> datos) { this.datos = datos; }
}