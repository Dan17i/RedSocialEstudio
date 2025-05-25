package co.edu.uniquindio.redsocial.models;

import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * Clase genérica que representa un reporte generado por el sistema.
 * Soporta múltiples tipos de datos, formatos de exportación y validación completa.
 *
 * @param <T> Tipo de los datos almacenados en el reporte.
 *
 * Ejemplos de uso:
 * - Reporte<Par<String, Integer>> para contenidos más valorados
 * - Reporte<Par<String, Integer>> para estudiantes con más conexiones
 * - Reporte<String> para logs simples
 *
 * @author Daniel Jurado
 * @author Sebastian Torres
 * @author Juan Soto
 * @since 2025-05-13
 */
public class Reporte<T> {

    private String id;
    private TipoReporte tipo;
    private final LocalDateTime fechaGeneracion;
    private final ListaEnlazada<T> datos;

    /**
     * Constructor principal del reporte.
     *
     * @param id              Identificador único del reporte.
     * @param tipo            Tipo de reporte (ver enum TipoReporte).
     * @param fechaGeneracion Fecha y hora en que se generó el reporte.
     * @param datos           Datos incluidos en el reporte.
     */
    public Reporte(String id, TipoReporte tipo, LocalDateTime fechaGeneracion, ListaEnlazada<T> datos) {
        setId(id);
        setTipo(tipo);
        if (fechaGeneracion == null)
            throw new IllegalArgumentException("La fecha de generación no puede ser nula");
        if (datos == null)
            throw new IllegalArgumentException("La lista de datos no puede ser nula");

        this.fechaGeneracion = fechaGeneracion;
        this.datos = datos;
    }

    /**
     * Agrega un nuevo dato al reporte.
     *
     * @param dato Elemento a agregar.
     */
    public void agregarDato(T dato) {
        if (dato == null)
            throw new IllegalArgumentException("El dato no puede ser nulo");
        datos.agregar(dato);
    }

    /**
     * Devuelve el resumen básico del reporte.
     */
    public String getResumen() {
        return "Reporte [" + tipo + "] generado el " + fechaGeneracion + " con " + datos.getTamanio() + " entradas.";
    }

    /**
     * Representación legible del reporte.
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
     * Exporta el contenido a archivo.
     *
     * @param formato     "TXT", "CSV", o "JSON".
     * @param rutaArchivo Ruta donde se guardará el archivo.
     * @throws IOException sí hay errores de escritura.
     */
    public void exportar(String formato, String rutaArchivo) throws IOException {
        if (formato == null || rutaArchivo == null)
            throw new IllegalArgumentException("Formato y rutaArchivo no pueden ser nulos");

        switch (formato.toUpperCase()) {
            case "TXT" -> exportarTxt(rutaArchivo);
            case "CSV" -> exportarCsv(rutaArchivo);
            default -> throw new IllegalArgumentException("Formato no soportado: " + formato);
        }
    }

    private void exportarTxt(String rutaArchivo) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            writer.write(generarContenido());
        }
    }

    private void exportarCsv(String rutaArchivo) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            writer.write("ID,Tipo,Fecha,Dato\n");
            for (int i = 0; i < datos.getTamanio(); i++) {
                T dato = datos.obtener(i);
                writer.write(String.format("\"%s\",\"%s\",\"%s\",\"%s\"\n",
                        id, tipo, fechaGeneracion, dato.toString().replace("\"", "\"\"")));
            }
        }
    }

    // ======================
    // Getters y Setters
    // ======================

    public String getId() { return id; }

    public void setId(String id) {
        if (id == null || id.isBlank())
            throw new IllegalArgumentException("El id no puede ser nulo ni vacío");
        this.id = id;
    }

    public TipoReporte getTipo() { return tipo; }

    public void setTipo(TipoReporte tipo) {
        if (tipo == null)
            throw new IllegalArgumentException("El tipo no puede ser nulo");
        this.tipo = tipo;
    }

    public LocalDateTime getFechaGeneracion() { return fechaGeneracion; }

    public ListaEnlazada<T> getDatos() { return datos; }
}
