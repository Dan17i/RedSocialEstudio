package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.ArchivoMultimedia;
import co.edu.uniquindio.redsocial.models.Contenido;
import co.edu.uniquindio.redsocial.models.Enums.TipoContenido;
import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.GrupoEstudio;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.UUID;

@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,    // 1MB
        maxFileSize = 10 * 1024 * 1024,     // 10MB
        maxRequestSize = 20 * 1024 * 1024   // 20MB
)
@WebServlet("/grupos/detalle/publicar")
public class PublicarContenidoGrupoServlet extends HttpServlet {

    private static final String UPLOAD_DIR = "uploads";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String grupoId = req.getParameter("grupoId");
        String tema    = req.getParameter("tema");
        String desc    = req.getParameter("descripcion");
        Part archivoPart = req.getPart("archivo");

        HttpSession session = req.getSession(false);
        Estudiante autor = (session != null)
                ? (Estudiante) session.getAttribute("usuarioActual")
                : null;

        @SuppressWarnings("unchecked")
        ListaEnlazada<GrupoEstudio> todosGrupos =
                (ListaEnlazada<GrupoEstudio>) getServletContext().getAttribute("todosGrupos");

        // Buscar grupo
        GrupoEstudio grupo = null;
        if (grupoId != null && todosGrupos != null) {
            for (int i = 0; i < todosGrupos.getTamanio(); i++) {
                GrupoEstudio g = todosGrupos.obtener(i);
                if (g.getId().equals(grupoId)) {
                    grupo = g;
                    break;
                }
            }
        }

        if (grupo == null || autor == null || tema == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // Guardar archivo en disco
        ArchivoMultimedia am = null;
        if (archivoPart != null && archivoPart.getSize() > 0) {
            String nombre = UUID.randomUUID() + "_" + archivoPart.getSubmittedFileName();
            String rutaRel = UPLOAD_DIR + File.separator + nombre;
            File uploads = new File(getServletContext().getRealPath("/") + UPLOAD_DIR);
            if (!uploads.exists()) uploads.mkdirs();
            archivoPart.write(uploads.getAbsolutePath() + File.separator + nombre);
            am = new ArchivoMultimedia(
                    archivoPart.getSubmittedFileName(),
                    nombre,
                    archivoPart.getContentType(),
                    archivoPart.getSize()
            );
        }

        // Crear y publicar contenido
        Contenido contenido = new Contenido(
                UUID.randomUUID().toString(),
                tema,
                desc,
                autor,
                // inferir tipo según mime
                TipoContenido.valueOf(am != null
                        ? am.getTipoMime().split("/")[0].toUpperCase()
                        : "TEXTO"),
                LocalDateTime.now(),
                new ListaEnlazada<>(),
                am
        );
        grupo.publicarContenidoGrupo(contenido);

        resp.sendRedirect(req.getContextPath()
                + "/inicio.jsp?seccion=gruposDetalle&grupoId="
                + URLEncoder.encode(grupoId,"UTF-8"));
    }
}
