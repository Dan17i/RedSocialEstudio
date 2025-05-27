package co.edu.uniquindio.redsocial.models;

import co.edu.uniquindio.redsocial.models.Enums.TipoReporte;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return "Reporte [" + tipo + "] generado el " + fechaGeneracion.format(formatter) + " con " + datos.getTamanio() + " entradas.";
    }

    /**
     * Representación legible del reporte.
     */
    public String generarContenido() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        StringBuilder sb = new StringBuilder();
        sb.append("Reporte ID: ").append(id)
                .append("\nTipo: ").append(tipo)
                .append("\nFecha: ").append(fechaGeneracion.format(formatter))
                .append("\nContenido:\n");

        for (int i = 0; i < datos.getTamanio(); i++) {
            sb.append("- ").append(datos.obtener(i)).append("\n");
        }

        return sb.toString();
    }

    /**
     * Exporta el contenido a archivo.
     *
     * @param formato     Formato del archivo: "TXT", "CSV", o "JSON".
     * @param rutaArchivo Ruta donde se guardará el archivo.
     * @throws IOException Si hay errores de escritura.
     */
    public void exportar(String formato, String rutaArchivo) throws IOException {
        if (formato == null || rutaArchivo == null)
            throw new IllegalArgumentException("Formato y rutaArchivo no pueden ser nulos");

        switch (formato.toUpperCase()) {
            case "TXT" -> exportarTxt(rutaArchivo);
            case "CSV" -> exportarCsv(rutaArchivo);
            case "JSON" -> exportarJson(rutaArchivo);
            default -> throw new IllegalArgumentException("Formato no soportado: " + formato);
        }
    }

    private void exportarTxt(String rutaArchivo) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            writer.write(generarContenido());
        }
    }

    private void exportarCsv(String rutaArchivo) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            writer.write("ID,Tipo,Fecha,Dato\n");
            for (int i = 0; i < datos.getTamanio(); i++) {
                T dato = datos.obtener(i);
                writer.write(String.format("\"%s\",\"%s\",\"%s\",\"%s\"\n",
                        id, tipo, fechaGeneracion.format(formatter), dato.toString().replace("\"", "\"\"")));
            }
        }
    }
    /**
     * Exporta el reporte actual al formato JSON, escribiéndolo en un archivo en disco.
     * El contenido incluye todos los atributos del objeto Reporte serializados de forma legible.
     * Utiliza la biblioteca Gson para la conversión a JSON.
     *
     * <p>Ejemplo de contenido generado:</p>
     * <pre>
     * {
     *   "id": "GEN-ESTUDIANTES_CONECTADOS",
     *   "tipo": "ESTUDIANTES_CONECTADOS",
     *   "fechaGeneracion": "2025-05-25T14:30:00",
     *   "datos": [
     *     {"nombre": "Juan", "valor": 5},
     *     {"nombre": "Ana", "valor": 4}
     *   ]
     * }
     * </pre>
     *
     * @param rutaArchivo Ruta completa donde se guardará el archivo JSON.
     * @throws IOException Si ocurre un error de escritura en el archivo.
     * @throws IllegalArgumentException Si la ruta del archivo es nula o vacía.
     *
     * @see com.google.gson.Gson
     * @see com.google.gson.GsonBuilder
     */
    private void exportarJson(String rutaArchivo) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            writer.write(gson.toJson(this));
        }
    }


    /* Getters y Setters */

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reporte<?> reporte = (Reporte<?>) o;
        return Objects.equals(id, reporte.id) && tipo == reporte.tipo && Objects.equals(fechaGeneracion, reporte.fechaGeneracion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tipo, fechaGeneracion);
    }
}
