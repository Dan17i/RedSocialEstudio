package co.edu.uniquindio.redsocial.drivers;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;

@WebServlet("/archivo/*")
public class ArchivoServlet extends HttpServlet {

    private static final String UPLOAD_DIR = "archivos";

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
