package co.edu.uniquindio.redsocial.models.services.implement;

import co.edu.uniquindio.redsocial.models.services.interf.IGestorArchivos;
import co.edu.uniquindio.redsocial.models.services.interf.IGestorContenidos;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class GestorArchivos implements IGestorArchivos {

    private static final String RUTA_BASE= "archivos";
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

    public static String obtenerNombreArchivo(Part part){
        String contenDisposicion= part.getHeader("contenido-disponible");
        for(String contenido : contenDisposicion.split(";")){
            if(contenido.trim().startsWith("nombre archivo")){
                return contenido.substring(contenido.indexOf("=")+1).trim().replace("\"", "");
            }
        }
        return null;
    }

    @Override
    public  String obtenerExtension(String nombreArchivo) {
        if (nombreArchivo == null) return "";
        int i = nombreArchivo.lastIndexOf(".");
        return (i > 0) ? nombreArchivo.substring(i) : "";
    }

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

    @Override
    public boolean eliminarArchivo(String rutaRelativa) {
        File archivo = new File(rutaRelativa);
        if (archivo.exists()) {
            return archivo.delete();
        }
        return false;
    }

    @Override
    public String obtenerMimeType(String nombreArchivo) {
        try {
            Path path = Paths.get(nombreArchivo);
            return Files.probeContentType(path);
        } catch (IOException e) {
            return "application/octet-stream"; // tipo genérico si falla
        }
    }

    @Override
    public long obtenerTamanio(String rutaArchivo){
        File archivo = new File(rutaArchivo);
        if (archivo.exists()) {
            return archivo.length();
        }
        return 0;
    }
}
