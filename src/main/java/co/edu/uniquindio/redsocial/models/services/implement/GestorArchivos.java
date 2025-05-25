package co.edu.uniquindio.redsocial.models.services.implement;

import co.edu.uniquindio.redsocial.models.services.interf.IGestorArchivos;
import co.edu.uniquindio.redsocial.models.services.interf.IGestorContenidos;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class GestorArchivos implements IGestorArchivos {

    public static  String guardarArchivo(Part archivo, String  rutaBase) throws IOException{
        if(archivo==null || archivo.getSize()==0)
            return null;

        String nombreoriginal= obtenerNombreAtchivo(archivo);
        String extension = obtenerExtension(nombreoriginal);
        String nombreFinal= UUID.randomUUID().toString()+ extension;

        File carpeta= new File(rutaBase);
        if(!carpeta.exist()) carpeta.mkdirs();

        File archivoDestino=new File (carpeta, nombreFinal);
        try(inputStream input= archivo.getInputStream()){
            Files.copy(input, archivoDestino.toPath(), standardCopyOption,REPLACE_EXISTING);
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
    public static String obtenerExtension(String nombreArchivo) {
        if (nombreArchivo == null) return "";
        int i = nombreArchivo.lastIndexOf(".");
        return (i > 0) ? nombreArchivo.substring(i) : "";
    }
}
