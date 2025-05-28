package co.edu.uniquindio.redsocial.models.services.interf;

import java.io.InputStream;
import java.io.IOException;
/**
 * Interfaz que define las operaciones básicas para la gestión de archivos multimedia
 * en el servidor, tales como guardar, eliminar y obtener información sobre archivos.
 * <p>
 * Implementaciones concretas pueden manejar archivos físicos, almacenamiento en la nube,
 * o cualquier sistema de archivos personalizado.
 * </p>
 *
 * @author Daniel Jurado, Sebastian Torres y Juan Soto
 * @since 2025-05-27
 */
public interface IGestorArchivos {

    /**
     * Guarda un archivo multimedia en el servidor.
     *
     * @param inputStream flujo de entrada del archivo.
     * @param nombreArchivo nombre original del archivo.
     * @return la ruta relativa donde se guardó el archivo.
     * @throws IOException si ocurre un error al guardar.
     */
    String guardarArchivo(InputStream inputStream, String nombreArchivo) throws IOException;

    /**
     * Elimina un archivo del servidor dado su nombre.
     *
     * @param rutaRelativa ruta relativa del archivo a eliminar.
     * @return true si se eliminó correctamente, false en caso contrario.
     */
    boolean eliminarArchivo(String rutaRelativa);

    /**
     * Obtiene el tipo MIME del archivo con base en su nombre.
     *
     * @param nombreArchivo nombre del archivo.
     * @return tipo MIME como string (ej. "image/jpeg").
     */
    String obtenerMimeType(String nombreArchivo);
    /**
     * Obtiene el tamaño en bytes del archivo dado su nombre o ruta.
     *
     * @param rutaRelativa ruta del archivo.
     * @return tamaño del archivo en bytes.
     */
    long obtenerTamanio(String rutaRelativa);
    /**
     * Obtiene la extensión del archivo a partir de su nombre.
     * Por ejemplo, para "foto.jpg" retorna "jpg".
     *
     * @param nombreArchivo nombre del archivo a analizar.
     * @return extensión del archivo sin el punto.
     */
    String obtenerExtension(String nombreArchivo);
}
