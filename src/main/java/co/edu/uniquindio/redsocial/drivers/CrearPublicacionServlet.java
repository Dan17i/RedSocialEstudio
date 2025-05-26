package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.ArchivoMultimedia;
import co.edu.uniquindio.redsocial.models.Contenido;
import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.Enums.TipoContenido;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.time.LocalDateTime;
import java.util.UUID;

@WebServlet("/CrearPublicacion")
@MultipartConfig(
        fileSizeThreshold = 1024*1024,
        maxFileSize       = 10*1024*1024,
        maxRequestSize    = 20*1024*1024
)
public class CrearPublicacionServlet extends HttpServlet {

    private static final String UPLOAD_DIR = "archivos";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1) Obtener autor de la sesión
        Estudiante autor = (Estudiante) request.getSession().getAttribute("usuarioActual");
        if (autor == null) {
            response.sendRedirect("inicioSesion.jsp");
            return;
        }

        // 2) Leer parámetros
        String tema        = request.getParameter("tema");
        String descripcion = request.getParameter("descripcion");
        String tipoStr     = request.getParameter("tipo");
        TipoContenido tipo = TipoContenido.valueOf(tipoStr.toUpperCase());

        // 3) Guardar archivo en webapp/archivos
        String uploadPath = getServletContext().getRealPath("/" + UPLOAD_DIR);
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        Part filePart = request.getPart("archivo");
        ArchivoMultimedia archivoMultimedia = null;
        if (filePart != null && filePart.getSize() > 0) {
            String original = filePart.getSubmittedFileName();
            String stored   = UUID.randomUUID() + "_" + original;
            String fullPath = uploadPath + File.separator + stored;

            try (InputStream in = filePart.getInputStream();
                 FileOutputStream out = new FileOutputStream(fullPath)) {
                byte[] buf = new byte[1024];
                int r;
                while ((r = in.read(buf)) != -1) {
                    out.write(buf, 0, r);
                }
            }

            archivoMultimedia = new ArchivoMultimedia(
                    original, stored,
                    filePart.getContentType(),
                    filePart.getSize()
            );
        }

        // 4) Construir el nuevo contenido
        Contenido nuevoContenido = new Contenido(
                UUID.randomUUID().toString(),
                tema,
                descripcion,
                autor,
                tipo,
                LocalDateTime.now(),
                new ListaEnlazada<>(),   // valoraciones vacías
                archivoMultimedia
        );

        // 5) **Agrega al historial del autor**
        autor.getHistorialContenidos().agregar(nuevoContenido);

        // 6) Agrega a la lista global
        @SuppressWarnings("unchecked")
        ListaEnlazada<Contenido> publicaciones =
                (ListaEnlazada<Contenido>) getServletContext().getAttribute("publicaciones");
        publicaciones.agregar(nuevoContenido);

        // 7) Redirige al feed
        response.sendRedirect("inicio.jsp?seccion=home");
    }
}
