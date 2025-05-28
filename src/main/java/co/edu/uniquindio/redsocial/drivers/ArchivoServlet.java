package co.edu.uniquindio.redsocial.drivers;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
/**
 * Servlet para servir archivos multimedia almacenados en el servidor.
 *
 * Recibe peticiones GET en la ruta "/archivo/*", donde el path especifica
 * el nombre del archivo a descargar.
 * Funciona leyendo el archivo desde el sistema de archivos local bajo la
 * carpeta "archivos" y enviándolo como respuesta HTTP con el tipo MIME adecuado.
 * Si el archivo no existe o no se especifica un nombre válido, devuelve un error HTTP.
 * @author Daniel Jurado, Sebastian Torre y Juan Soto
 * @since 2025-05-24
 */
@WebServlet("/archivo/*")
public class ArchivoServlet extends HttpServlet {

    private static final String UPLOAD_DIR = "archivos";
    /**
     * Procesa las peticiones GET para entregar un archivo solicitado.
     *
     * @param request La petición HTTP del cliente.
     * @param response La respuesta HTTP que contendrá el archivo solicitado.
     * @throws IOException En caso de error de entrada/salida al leer el archivo.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String path = request.getPathInfo();               // e.g. "/uuid_nombre.ext"
        if (path == null || path.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta nombre de archivo");
            return;
        }

        String uploadPath = getServletContext().getRealPath("/" + UPLOAD_DIR);
        File file = new File(uploadPath, path.substring(1)); // quita la “/”

        if (!file.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Archivo no encontrado");
            return;
        }

        String mime = getServletContext().getMimeType(file.getName());
        if (mime == null) mime = "application/octet-stream";
        response.setContentType(mime);
        response.setContentLengthLong(file.length());

        try (FileInputStream in = new FileInputStream(file);
             OutputStream out = response.getOutputStream()) {
            byte[] buf = new byte[4096];
            int len;
            while ((len = in.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
        }
    }
}
