package co.edu.uniquindio.redsocial.models;

import java.time.LocalDate;

public class Reporte {

    String id;
    String tipo;
    LocalDate fechaGeneracion;
    ListaEnlazada<String> datos;

    public Reporte(String id, String tipo, LocalDate fechaGeneracion, ListaEnlazada<String> datos) {
        this.id = id;
        this.tipo = tipo;
        this.fechaGeneracion = fechaGeneracion;
        this.datos = datos;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDate getFechaGeneracion() {
        return fechaGeneracion;
    }

    public void setFechaGeneracion(LocalDate fechaGeneracion) {
        this.fechaGeneracion = fechaGeneracion;
    }

    public ListaEnlazada<String> getDatos() {
        return datos;
    }

    public void setDatos(ListaEnlazada<String> datos) {
        this.datos = datos;
    }
}
