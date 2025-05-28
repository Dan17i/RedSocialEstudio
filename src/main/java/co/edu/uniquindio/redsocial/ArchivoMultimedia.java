package co.edu.uniquindio.redsocial;


import javax.servlet.ServletContext;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

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
    /**
     * Crea un ArchivoMultimedia a partir de un Part HTTP (upload), guardando
     * el archivo en el directorio '/archivos' de la aplicación.
     *
     * @param part    Part con el contenido subido
     * @param context ServletContext para resolver rutas y directorios
     * @return Instancia de ArchivoMultimedia representando el archivo guardado
     * @throws IOException si ocurre un error al guardar el archivo
     */
    public static ArchivoMultimedia fromPart(Part part, ServletContext context) throws IOException {
        if (part == null || part.getSize() == 0) {
            return null;
        }
        // Extraer nombre original del upload
        String header = part.getHeader("content-disposition");
        String nombre = null;
        for (String cd : header.split(";")) {
            if (cd.trim().startsWith("filename")) {
                nombre = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                break;
            }
        }
        if (nombre == null) {
            nombre = UUID.randomUUID().toString();
        }
        // Crear nombre único en servidor
        String uniqueName = UUID.randomUUID().toString() + "_" + nombre;
        // Preparar directorio absoluto
        String uploadPath = context.getRealPath("/archivos");
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        File file = new File(uploadDir, uniqueName);
        try (InputStream is = part.getInputStream();
             FileOutputStream fos = new FileOutputStream(file)) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        }
        return new ArchivoMultimedia(nombre, uniqueName, part.getContentType(), part.getSize());
    }
}
