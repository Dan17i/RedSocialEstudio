package co.edu.uniquindio.redsocial;

public class ArchivoMultimedia {

    private String nombreArchivo;
    private String rutaRelativa;
    private String tipoMime;
    private long tamanio;


    public ArchivoMultimedia(String nombreArchivo, String rutaRelativa, String tipoMime, long tamanio) {
        this.nombreArchivo = nombreArchivo;
        this.rutaRelativa = rutaRelativa;
        this.tipoMime = tipoMime;
        this.tamanio = tamanio;
    }

    public String getRutaRelativa(){
        return "/archivos/" + rutaRelativa;
    }

    public void setRutaRelativa(String rutaRelativa) {
        this.rutaRelativa = rutaRelativa;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public String getTipoMime() {
        return tipoMime;
    }

    public void setTipoMime(String tipoMime) {
        this.tipoMime = tipoMime;
    }

    public long getTamanio() {
        return tamanio;
    }

    public void setTamanio(long tamanio) {
        this.tamanio = tamanio;
    }
}
