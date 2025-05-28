package co.edu.uniquindio.redsocial.models.services.implement;

import co.edu.uniquindio.redsocial.models.services.interf.IGestorArchivos;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

/**
 * Clase que implementa la gestión de archivos, permitiendo guardar,
 * eliminar y obtener información relevante de los archivos enviados
 * mediante formularios multipart.
 *
 * @author Daniel Jurado
 * @author Sebastian Torres
 * @author Juan Soto
 * @since 2025-05-25
 */
public class GestorArchivos implements IGestorArchivos {

    /**
     * Ruta base por defecto donde se almacenarán los archivos.
     */
    private static final String RUTA_BASE = "archivos";

    /**
     * Guarda un archivo enviado por un formulario web en una ruta específica.
     *
     * @param archivo   Objeto Part que representa el archivo cargado.
     * @param rutaBase  Ruta donde se almacenará el archivo.
     * @return Ruta absoluta del archivo guardado o null si el archivo es inválido.
     * @throws IOException Si ocurre un error al guardar el archivo.
     */
    public String guardarArchivo(Part archivo, String rutaBase) throws IOException {
        if (archivo == null || archivo.getSize() == 0)
            return null;

        String nombreOriginal = obtenerNombreArchivo(archivo);
        String extension = obtenerExtension(nombreOriginal);
        String nombreFinal = UUID.randomUUID().toString() + extension;

        File carpeta = new File(rutaBase);
        if (!carpeta.exists()) carpeta.mkdirs();

        File archivoDestino = new File(carpeta, nombreFinal);
        try (InputStream input = archivo.getInputStream()) {
            Files.copy(input, archivoDestino.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
        return archivoDestino.getAbsolutePath();
    }

    /**
     * Válida el tipo MIME del archivo según un tipo esperado.
     *
     * @param archivo      Objeto Part que representa el archivo cargado.
     * @param tipoEsperado Tipo esperado (imagen, video, audio, pdf, texto).
     * @return true si el tipo coincide, false en caso contrario.
     */
    public static boolean validarTipo(Part archivo, String tipoEsperado) {
        String mime = archivo.getContentType();
        switch (tipoEsperado.toLowerCase()) {
            case "imagen": return mime.startsWith("image/");
            case "video": return mime.startsWith("video/");
            case "audio": return mime.startsWith("audio/");
            case "pdf": return mime.equals("application/pdf");
            case "texto": return mime.startsWith("text/");
            default: return false;
        }
    }


    /**
     * Extrae el nombre original del archivo desde el encabezado del formulario.
     *
     * @param part Objeto Part del archivo.
     * @return Nombre original del archivo o null si no se encuentra.
     */
    public static String obtenerNombreArchivo(Part part) {
        String contenDisposicion = part.getHeader("contenido-disponible");
        for (String contenido : contenDisposicion.split(";")) {
            if (contenido.trim().startsWith("nombre archivo")) {
                return contenido.substring(contenido.indexOf("=") + 1).trim().replace("\"", "");
            }
        }
        return null;
    }

    /**
     * Obtiene la extensión de un nombre de archivo.
     *
     * @param nombreArchivo Nombre del archivo.
     * @return Extensión incluyendo el punto (por ejemplo,".jpg").
     */
    @Override
    public String obtenerExtension(String nombreArchivo) {
        if (nombreArchivo == null) return "";
        int i = nombreArchivo.lastIndexOf(".");
        return (i > 0) ? nombreArchivo.substring(i) : "";
    }


    /**
     * Guarda un archivo recibido como flujo de entrada.
     *
     * @param inputStream   Flujo de entrada del archivo.
     * @param nombreArchivo Nombre con el que se guardará el archivo.
     * @return Ruta relativa del archivo guardado.
     * @throws IOException Si ocurre un error al guardar.
     */
    @Override
    public String guardarArchivo(InputStream inputStream, String nombreArchivo) throws IOException {
        File carpeta = new File(RUTA_BASE);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }

        String rutaRelativa = RUTA_BASE + File.separator + nombreArchivo;
        File archivoDestino = new File(rutaRelativa);

        Files.copy(inputStream, archivoDestino.toPath(), StandardCopyOption.REPLACE_EXISTING);
        return rutaRelativa;
    }

    /**
     * Elimina un archivo de la ruta especificada.
     *
     * @param rutaRelativa Ruta relativa del archivo a eliminar.
     * @return true si se eliminó correctamente, false en caso contrario.
     */
    @Override
    public boolean eliminarArchivo(String rutaRelativa) {
        File archivo = new File(rutaRelativa);
        if (archivo.exists()) {
            return archivo.delete();
        }
        return false;
    }

    /**
     * Obtiene el tipo MIME de un archivo dado su nombre.
     *
     * @param nombreArchivo Nombre o ruta del archivo.
     * @return Tipo MIME correspondiente o un tipo genérico si no se puede detectar.
     */
    @Override
    public String obtenerMimeType(String nombreArchivo) {
        try {
            Path path = Paths.get(nombreArchivo);
            return Files.probeContentType(path);
        } catch (IOException e) {
            return "application/octet-stream";
        }
    }

    /**
     * Retorna el tamaño de un archivo en bytes.
     *
     * @param rutaArchivo Ruta del archivo.
     * @return Tamaño del archivo en bytes o 0 si no existe.
     */
    @Override
    public long obtenerTamanio(String rutaArchivo) {
        File archivo = new File(rutaArchivo);
        if (archivo.exists()) {
            return archivo.length();
        }
        return 0;
    }
}
