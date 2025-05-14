package co.edu.uniquindio.redsocial.models;

import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

import java.time.LocalDateTime;
/**
 * Clase que representa un reporte generado por el sistema.
 * Contiene un identificador, un tipo de reporte, la fecha de generación
 * y una lista de datos relevantes asociados al mismo.
 *
 * @author Daniel Jurado
 * @author Sebastian Torres
 * @author Juan Soto
 * @since 2025-05-13
 */
public class Reporte {
    private String id;
    private String tipo;
    private LocalDateTime fechaGeneracion;
    private ListaEnlazada<String> datos;

    /**
     * Constructor del reporte.
     *
     * @param id              Identificador único del reporte.
     * @param tipo            Tipo de reporte (por ejemplo: "actividad", "informe", etc).
     * @param fechaGeneracion Fecha y hora en que fue generado el reporte.
     * @param datos           Lista enlazada de datos incluidos en el reporte.
     * @throws IllegalArgumentException si algún parámetro es nulo.
     */
    public Reporte(String id, String tipo, LocalDateTime fechaGeneracion, ListaEnlazada<String> datos) {
        if (id == null || tipo == null || fechaGeneracion == null || datos == null) {
            throw new IllegalArgumentException("Ninguno de los parámetros puede ser nulo");
        }
        this.id = id;
        this.tipo = tipo;
        this.fechaGeneracion = fechaGeneracion;
        this.datos = datos;
    }

    /**
     * Genera una representación legible del contenido del reporte.
     *
     * @return String con cada dato del reporte en una línea separada.
     */
    public String generarContenido() {
        StringBuilder sb = new StringBuilder();
        sb.append("Reporte ID: ").append(id)
                .append("\nTipo: ").append(tipo)
                .append("\nFecha: ").append(fechaGeneracion)
                .append("\nContenido:\n");

        for (int i = 0; i < datos.getTamanio(); i++) {
            sb.append("- ").append(datos.obtener(i)).append("\n");
        }

        return sb.toString();
    }

    /**
     * Método para exportar el reporte en un formato dado (por implementar).
     *
     * @param formato Formato deseado de exportación, por ejemplo: "PDF", "TXT", etc.
     */
    public void exportar(String formato) {
        // TODO: Implementar lógica de exportación en el formato especificado
        System.out.println("Exportando reporte en formato: " + formato + " (funcionalidad pendiente)");
    }

    /**
     * Agrega un nuevo dato al contenido del reporte.
     *
     * @param dato Texto que se desea agregar.
     */
    public void agregarDato(String dato) {
        datos.agregar(dato);
    }

    /**
     * Devuelve un resumen simple del reporte.
     *
     * @return Cadena con resumen.
     */
    public String getResumen() {
        return "Reporte [" + tipo + "] generado el " + fechaGeneracion + " con " + datos.getTamanio() + " entradas.";
    }

    // Getters y Setters

    public String getId() { return id; }

    public void setId(String id) { this.id = id; }

    public String getTipo() { return tipo; }

    public void setTipo(String tipo) { this.tipo = tipo; }

    public LocalDateTime getFechaGeneracion() { return fechaGeneracion; }

    public void setFechaGeneracion(LocalDateTime fechaGeneracion) { this.fechaGeneracion = fechaGeneracion; }

    public ListaEnlazada<String> getDatos() { return datos; }

    public void setDatos(ListaEnlazada<String> datos) {
        if (datos == null) throw new IllegalArgumentException("La lista de datos no puede ser nula");
        this.datos = datos;
    }
}
