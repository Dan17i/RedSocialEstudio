package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.ArchivoMultimedia;
import co.edu.uniquindio.redsocial.models.Contenido;
import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.Enums.TipoContenido;
import co.edu.uniquindio.redsocial.models.services.implement.GestorContenidos;
import co.edu.uniquindio.redsocial.models.structures.ArbolBinarioBusqueda;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.time.LocalDateTime;
import java.util.UUID;
/**
 * Servlet encargado de procesar la creación de nuevas publicaciones por parte de los estudiantes.
 * Recibe datos del formulario, gestiona la subida de archivos multimedia y actualiza las estructuras
 * globales de contenido (árbol binario de búsqueda y lista enlazada).
 * Ruta: /CrearPublicacion
 * Requiere que el usuario esté autenticado como Estudiante.
 * Parámetros esperados (formulario):
 * - interesSeleccionado: Interés asociado a la publicación.
 * - tema: Título o tema del contenido.
 * - descripcion: Descripción textual del contenido.
 * - tipo: Tipo de contenido (ej. TEXTO, IMAGEN, VIDEO).
 * - archivo: Archivo multimedia adjunto (opcional).
 * Atributos actualizados en el contexto:
 * - "arbolContenidos": Árbol binario de búsqueda donde se indexa el contenido por tema.
 * - "publicaciones": Lista global de contenidos publicados.
 * Redirección final: inicio.jsp?seccion=home
 *
 * @author Daniel Jurado, Sebastian Torres y Juan Soto
 * @version 1.0
 */
@WebServlet("/CrearPublicacion")
@MultipartConfig(
        fileSizeThreshold = 1024*1024,
        maxFileSize       = 10*1024*1024,
        maxRequestSize    = 20*1024*1024
)
public class CrearPublicacionServlet extends HttpServlet {

    private static final String UPLOAD_DIR = "archivos";
    /**
     * Maneja la solicitud POST para crear una nueva publicación en la red social educativa.
     * Verifica si el usuario está autenticado, procesa los parámetros del formulario, guarda
     * el archivo multimedia en el servidor, construye un objeto {@link Contenido} y lo añade
     * a las estructuras de almacenamiento.
     *
     * @param request  solicitud HTTP con los datos del formulario
     * @param response respuesta HTTP para redireccionar al usuario
     * @throws ServletException si ocurre un error en el procesamiento del servlet
     * @throws IOException      si ocurre un error de entrada/salida durante el manejo del archivo
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Estudiante autor = (Estudiante) request.getSession().getAttribute("usuarioActual");
        if (autor == null) {
            response.sendRedirect("inicioSesion.jsp");
            return;
        }

        // Parámetros del formulario
        String interesSeleccionado = request.getParameter("interesSeleccionado");
        String tema        = request.getParameter("tema");
        String descripcion = request.getParameter("descripcion");
        TipoContenido tipo = TipoContenido.valueOf(request.getParameter("tipo").toUpperCase());

        // Guardar archivo
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

        // Construir contenido
        Contenido nuevoContenido = new Contenido(
                UUID.randomUUID().toString(),
                tema,
                descripcion,
                autor,
                tipo,
                LocalDateTime.now(),
                new ListaEnlazada<>(),
                archivoMultimedia
        );

        // Historial del autor
        autor.getHistorialContenidos().agregar(nuevoContenido);

        // Agregar al ABB global
        @SuppressWarnings("unchecked")
        ArbolBinarioBusqueda<Contenido> arbol =
                (ArbolBinarioBusqueda<Contenido>) getServletContext().getAttribute("arbolContenidos");
        arbol.insertar(nuevoContenido.getTema(), nuevoContenido);
        // Si quieres indexar por interés:
        // arbol.insertar(interesSeleccionado, nuevoContenido);

        // Agregar a la lista global
        @SuppressWarnings("unchecked")
        ListaEnlazada<Contenido> publicaciones =
                (ListaEnlazada<Contenido>) getServletContext().getAttribute("publicaciones");
        publicaciones.agregar(nuevoContenido);

        response.sendRedirect("inicio.jsp?seccion=home");
    }
}