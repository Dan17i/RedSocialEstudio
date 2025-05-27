package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.ArchivoMultimedia;
import co.edu.uniquindio.redsocial.models.Contenido;
import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.GrupoEstudio;
import co.edu.uniquindio.redsocial.models.Enums.TipoContenido;
import co.edu.uniquindio.redsocial.models.Valoracion;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@WebServlet("/grupos/detalle/publicar")
@MultipartConfig
public class PublicarEnGrupoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String grupoId    = req.getParameter("grupoId");
        String tema       = req.getParameter("tema");
        String descripcion= req.getParameter("descripcion");

        Part filePart = req.getPart("archivo");
        ArchivoMultimedia archivo = ArchivoMultimedia.fromPart(filePart, getServletContext());

        @SuppressWarnings("unchecked")
        ListaEnlazada<GrupoEstudio> todos =
                (ListaEnlazada<GrupoEstudio>) getServletContext().getAttribute("todosGrupos");

        GrupoEstudio grupo = null;
        for (int i = 0; i < todos.getTamanio(); i++) {
            if (todos.obtener(i).getId().equals(grupoId)) {
                grupo = todos.obtener(i);
                break;
            }
        }

        Estudiante autor = (Estudiante) req.getSession().getAttribute("usuarioActual");
        if (grupo != null && autor != null) {
            // 1) Generar ID, fecha y lista de valoraciones vacía
            String idContenido = UUID.randomUUID().toString();
            LocalDateTime ahora = LocalDateTime.now();
            ListaEnlazada<Valoracion> valoraciones = new ListaEnlazada<>();

            // 2) Derivar TipoContenido según MIME
            TipoContenido tipo;
            if (archivo != null) {
                String mime = archivo.getTipoMime();
                if (mime.startsWith("image")) tipo = TipoContenido.IMAGEN;
                else if (mime.startsWith("video")) tipo = TipoContenido.VIDEO;
                else if (mime.startsWith("audio")) tipo = TipoContenido.AUDIO;
                else if ("application/pdf".equals(mime)) tipo = TipoContenido.PDF;
                else tipo = TipoContenido.TEXTO;
            } else {
                tipo = TipoContenido.TEXTO;
            }

            // 3) Crear y publicar el contenido en el grupo
            Contenido c = new Contenido(
                    idContenido,
                    tema,
                    descripcion,
                    autor,
                    tipo,
                    ahora,
                    valoraciones,
                    archivo
            );
            grupo.publicarContenidoGrupo(c);
        }

        resp.sendRedirect(req.getContextPath()
                + "/grupos/detalle?id=" + grupoId + "&message=Publicado correctamente");
    }
}
