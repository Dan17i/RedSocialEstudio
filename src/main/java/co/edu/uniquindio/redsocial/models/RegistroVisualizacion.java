package co.edu.uniquindio.redsocial.models;

import java.time.LocalDateTime;

public class RegistroVisualizacion {
    private final Contenido contenido;
    private final LocalDateTime fechaVisualizacion;

    public RegistroVisualizacion(Contenido contenido, LocalDateTime fechaVisualizacion) {
        this.contenido = contenido;
        this.fechaVisualizacion = fechaVisualizacion;
    }

    public Contenido getContenido() {
        return contenido;
    }

    public LocalDateTime getFechaVisualizacion() {
        return fechaVisualizacion;
    }

    @Override
    public String toString() {
        return contenido.toString() + " (visto el " + fechaVisualizacion.toString() + ")";
    }
}

