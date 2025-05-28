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
 * Implementación del servicio {@link IGestorArchivos} que permite gestionar operaciones
 * relacionadas con archivos como guardar, eliminar, validar tipos MIME, y obtener información
 * como el tamaño o tipo de contenido.
 * Este servicio es utilizado para el manejo de archivos en el contexto de una red social educativa.
 *
 * @author Daniel Jurado, Sebastian Torres y Juan Soto
 * @since 2025-05-20
 */
public class GestorArchivos implements IGestorArchivos {
    /**
     * Ruta base donde se almacenan los archivos por defecto.
     */
    private static final String RUTA_BASE= "archivos";
    /**
     * Guarda un archivo recibido como parte de una solicitud HTTP (multipart) en una ruta específica.
     *
     * @param archivo   Parte del archivo a guardar.
     * @param rutaBase  Ruta base donde se almacenará el archivo.
     * @return Ruta absoluta del archivo guardado o {@code null} si no se guarda.
     * @throws IOException sí ocurre un error durante la escritura del archivo.
     */
    public String guardarArchivo(Part archivo, String rutaBase) throws IOException{
        if(archivo==null || archivo.getSize()==0)
            return null;

        String nombreoriginal= obtenerNombreArchivo(archivo);
        String extension = obtenerExtension(nombreoriginal);
        String nombreFinal= UUID.randomUUID().toString()+ extension;

        File carpeta= new File(rutaBase);
        if(!carpeta.exists()) carpeta.mkdirs();

        File archivoDestino=new File (carpeta, nombreFinal);
        try(InputStream input= archivo.getInputStream()){
            Files.copy(input, archivoDestino.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
        return archivoDestino.getAbsolutePath();
    }
    /**
     * Válida si el tipo MIME del archivo corresponde al tipo esperado.
     *
     * @param archivo       Parte del archivo a validar.
     * @param tipoEsperado  Tipo esperado (ej. "imagen", "video", "pdf", etc.).
     * @return {@code true} si el tipo coincide, {@code false} en caso contrario.
     */
    public static boolean validarTipo(Part archivo, String tipoEsperado){
        String mime = archivo.getContentType();
        switch (tipoEsperado.toLowerCase()){
            case "imagen": return mime.startsWith("image/");
            case "video": return mime.startsWith("video/");
            case "audio": return mime.startsWith("audio/");
            case "pdf": return mime.equals("application/pdf");
            case "texto": return mime.startsWith("text/");
            default: return false;
        }
    }
    /**
     * Extrae el nombre original del archivo desde el encabezado "content-disposition".
     *
     * @param part Parte del archivo.
     * @return Nombre original del archivo o {@code null} si no se encuentra.
     */
    public static String obtenerNombreArchivo(Part part){
        String contenDisposicion= part.getHeader("contenido-disponible");
        for(String contenido : contenDisposicion.split(";")){
            if(contenido.trim().startsWith("nombre archivo")){
                return contenido.substring(contenido.indexOf("=")+1).trim().replace("\"", "");
            }
        }
        return null;
    }

    /**
     * Obtiene la extensión de un nombre de archivo.
     *
     * @param nombreArchivo Nombre del archivo.
     * @return Extensión del archivo (incluyendo el punto) o cadena vacía si no tiene.
     */
    @Override
    public  String obtenerExtension(String nombreArchivo) {
        if (nombreArchivo == null) return "";
        int i = nombreArchivo.lastIndexOf(".");
        return (i > 0) ? nombreArchivo.substring(i) : "";
    }
    /**
     * Guarda un archivo a partir de un {@link InputStream} y un nombre de archivo.
     *
     * @param inputStream   Flujo de entrada del archivo.
     * @param nombreArchivo Nombre del archivo destino.
     * @return Ruta relativa del archivo guardado.
     * @throws IOException sí ocurre un error durante la escritura del archivo.
     */
    @Override
    public String guardarArchivo(InputStream inputStream, String nombreArchivo) throws IOException {
        File carpeta = new File(RUTA_BASE);
        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }

        // Ruta completa de destino
        String rutaRelativa = RUTA_BASE + File.separator + nombreArchivo;
        File archivoDestino = new File(rutaRelativa);

        // Guardar el archivo
        Files.copy(inputStream, archivoDestino.toPath(), StandardCopyOption.REPLACE_EXISTING);
        return rutaRelativa;
    }

    /**
     * Elimina un archivo de la ruta especificada.
     *
     * @param rutaRelativa Ruta relativa del archivo a eliminar.
     * @return {@code true} si el archivo fue eliminado, {@code false} si no existe o falla la eliminación.
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
     * Obtiene el tipo MIME de un archivo según su ruta.
     *
     * @param nombreArchivo Ruta del archivo.
     * @return Tipo MIME detectado o {@code application/octet-stream} si falla la detección.
     */
    @Override
    public String obtenerMimeType(String nombreArchivo) {
        try {
            Path path = Paths.get(nombreArchivo);
            return Files.probeContentType(path);
        } catch (IOException e) {
            return "application/octet-stream"; // tipo genérico si falla
        }
    }
    /**
     * Obtiene el tamaño en bytes de un archivo.
     *
     * @param rutaArchivo Ruta del archivo.
     * @return Tamaño en bytes o 0 si el archivo no existe.
     */
    @Override
    public long obtenerTamanio(String rutaArchivo){
        File archivo = new File(rutaArchivo);
        if (archivo.exists()) {
            return archivo.length();
        }
        return 0;
    }
}
