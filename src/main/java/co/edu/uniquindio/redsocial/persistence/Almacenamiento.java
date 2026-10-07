package co.edu.uniquindio.redsocial.persistence;

import java.io.File;

/**
 * Carpeta donde se guardan los archivos subidos por los usuarios (imágenes, documentos, etc.).
 * Está FUERA de la aplicación web para que sobreviva a un reinicio o a un nuevo despliegue del WAR,
 * igual que los datos guardados en MongoDB.
 * <p>
 * Se configura con la variable {@code REDSOCIAL_UPLOADS}; por defecto es {@code ~/redsocial-uploads}.
 */
public final class Almacenamiento {

    private Almacenamiento() {
    }

    /** @return La carpeta de archivos subidos (se crea si no existe). */
    public static File directorioSubidas() {
        String configurado = System.getenv("REDSOCIAL_UPLOADS");
        File dir = (configurado == null || configurado.isBlank())
                ? new File(System.getProperty("user.home"), "redsocial-uploads")
                : new File(configurado.trim());
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }
}
