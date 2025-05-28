package co.edu.uniquindio.redsocial;
/**
 * Representa un archivo multimedia con sus propiedades básicas,
 * como nombre, ruta relativa, tipo MIME y tamaño.
 * @author Daniel Jurado, Sabastian Torres y Juan Soto
 * @since 2025-05-21
 */
public class ArchivoMultimedia {

    private String nombreArchivo;
    private String rutaRelativa;
    private String tipoMime;
    private long tamanio;
    /**
     * Constructor que inicializa un archivo multimedia con sus atributos.
     *
     * @param nombreArchivo Nombre del archivo.
     * @param rutaRelativa Ruta relativa donde se encuentra el archivo.
     * @param tipoMime Tipo MIME del archivo (por ejemplo, "image/png").
     * @param tamanio Tamaño del archivo en bytes.
     */
    public ArchivoMultimedia(String nombreArchivo, String rutaRelativa, String tipoMime, long tamanio) {
        this.nombreArchivo = nombreArchivo;
        this.rutaRelativa = rutaRelativa;
        this.tipoMime = tipoMime;
        this.tamanio = tamanio;
    }
    /**
     * Obtiene la ruta completa del archivo multimedia, agregando el prefijo "/archivos/".
     *
     * @return Ruta completa del archivo.
     */
    public String getRutaRelativa(){
        return "/archivos/" + rutaRelativa;
    }
    /**
     * Establece la ruta relativa del archivo.
     *
     * @param rutaRelativa Nueva ruta relativa.
     */
    public void setRutaRelativa(String rutaRelativa) {
        this.rutaRelativa = rutaRelativa;
    }
    /**
     * Obtiene el nombre del archivo.
     *
     * @return Nombre del archivo.
     */
    public String getNombreArchivo() {
        return nombreArchivo;
    }
    /**
     * Establece el nombre del archivo.
     *
     * @param nombreArchivo Nuevo nombre del archivo.
     */
    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }
    /**
     * Obtiene el tipo MIME del archivo.
     *
     * @return Tipo MIME.
     */
    public String getTipoMime() {
        return tipoMime;
    }
    /**
     * Establece el tipo MIME del archivo.
     *
     * @param tipoMime Nuevo tipo MIME.
     */
    public void setTipoMime(String tipoMime) {
        this.tipoMime = tipoMime;
    }
    /**
     * Obtiene el tamaño del archivo en bytes.
     *
     * @return Tamaño del archivo.
     */
    public long getTamanio() {
        return tamanio;
    }
    /**
     * Establece el tamaño del archivo en bytes.
     *
     * @param tamanio Nuevo tamaño del archivo.
     */
    public void setTamanio(long tamanio) {
        this.tamanio = tamanio;
    }
}
