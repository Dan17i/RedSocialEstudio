package co.edu.uniquindio.redsocial.persistence;

import co.edu.uniquindio.redsocial.ArchivoMultimedia;
import co.edu.uniquindio.redsocial.models.Contenido;
import co.edu.uniquindio.redsocial.models.Conversacion;
import co.edu.uniquindio.redsocial.models.Enums.EstadoSolicitud;
import co.edu.uniquindio.redsocial.models.Enums.TipoContenido;
import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.GrupoEstudio;
import co.edu.uniquindio.redsocial.models.Mensaje;
import co.edu.uniquindio.redsocial.models.SolicitudAyuda;
import co.edu.uniquindio.redsocial.models.Usuario;
import co.edu.uniquindio.redsocial.models.Valoracion;
import co.edu.uniquindio.redsocial.models.structures.ColaPrioridad;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import co.edu.uniquindio.redsocial.models.structures.TablaHash;
import org.bson.Document;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
// java.util.List/ArrayList se usan SOLO en la frontera con BSON: los documentos de MongoDB exigen
// listas estándar. Dentro de la aplicación se siguen usando únicamente las estructuras propias.
import java.util.ArrayList;
import java.util.List;

/**
 * Convierte el estado en memoria a documentos BSON y reconstruye los objetos a partir de ellos.
 * <p>
 * Los objetos se relacionan por {@code id} (no se anidan referencias circulares):
 * <ul>
 *   <li>{@code usuarios}: estudiantes con el hash de su contraseña e intereses.</li>
 *   <li>{@code contenidos}: contenidos globales con su archivo adjunto y sus valoraciones.</li>
 *   <li>{@code grupos}: miembros (ids), publicaciones del grupo, mensajes y solicitudes de ayuda del grupo.</li>
 *   <li>{@code conversaciones}: participantes (ids) y mensajes.</li>
 *   <li>{@code solicitudes}: solicitudes de ayuda de cada estudiante y si siguen en la cola global.</li>
 * </ul>
 * El moderador no se guarda (sus credenciales vienen de la configuración) y el grafo de afinidad se recalcula.
 */
public class MapeadorDocumentos {

    public static final String USUARIOS = "usuarios";
    public static final String CONTENIDOS = "contenidos";
    public static final String GRUPOS = "grupos";
    public static final String CONVERSACIONES = "conversaciones";
    public static final String SOLICITUDES = "solicitudes";

    /** Formato de ancho fijo para que las fechas ordenen igual como texto que como instante. */
    private static final DateTimeFormatter FECHA = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");

    // ------------------------------------------------------------------ objetos -> documentos

    /** @return Un documento por cada estudiante registrado. */
    public ListaEnlazada<Document> usuarios(EstadoAplicacion estado) {
        ListaEnlazada<Document> docs = new ListaEnlazada<>();
        for (Estudiante e : estado.getAutenticacion().getEstudiantesRegistrados()) {
            docs.agregar(new Document("_id", e.getId())
                    .append("nombre", e.getNombre())
                    .append("email", e.getEmail())
                    .append("contrasena", e.getContrasena())
                    .append("intereses", textos(e.getIntereses())));
        }
        return docs;
    }

    /** @return Un documento por cada contenido global. */
    public ListaEnlazada<Document> contenidos(EstadoAplicacion estado) {
        ListaEnlazada<Document> docs = new ListaEnlazada<>();
        for (Contenido c : estado.getContenidos().obtenerTodosLosContenidos()) {
            docs.agregar(contenidoADocumento(c));
        }
        return docs;
    }

    /** @return Un documento por cada grupo de estudio. */
    public ListaEnlazada<Document> grupos(EstadoAplicacion estado) {
        ListaEnlazada<Document> docs = new ListaEnlazada<>();
        for (GrupoEstudio g : estado.getGrupos()) {
            List<String> miembros = new ArrayList<>();
            for (Estudiante e : g.getMiembros()) miembros.add(e.getId());

            List<Document> publicaciones = new ArrayList<>();
            for (Contenido c : g.getPublicaciones()) publicaciones.add(contenidoADocumento(c));

            List<Document> mensajes = new ArrayList<>();
            for (Mensaje m : g.getMensajesGrupo()) mensajes.add(mensajeADocumento(m));

            List<Document> solicitudes = new ArrayList<>();
            for (SolicitudAyuda s : g.getSolicitudesAyudaGrupo().aLista()) {
                solicitudes.add(solicitudADocumento(s, false));
            }

            docs.agregar(new Document("_id", g.getId())
                    .append("tema", g.getTema())
                    .append("miembros", miembros)
                    .append("publicaciones", publicaciones)
                    .append("mensajes", mensajes)
                    .append("solicitudes", solicitudes));
        }
        return docs;
    }

    /** @return Un documento por cada conversación de chat. */
    public ListaEnlazada<Document> conversaciones(EstadoAplicacion estado) {
        ListaEnlazada<Document> docs = new ListaEnlazada<>();
        for (Conversacion c : estado.getConversaciones()) {
            List<String> participantes = new ArrayList<>();
            for (Estudiante e : c.getParticipantes()) participantes.add(e.getId());

            List<Document> mensajes = new ArrayList<>();
            for (Mensaje m : c.getMensajes()) mensajes.add(mensajeADocumento(m));

            docs.agregar(new Document("_id", c.getId())
                    .append("participantes", participantes)
                    .append("mensajes", mensajes));
        }
        return docs;
    }

    /** @return Un documento por cada solicitud de ayuda de los estudiantes (marcando las de la cola global). */
    public ListaEnlazada<Document> solicitudes(EstadoAplicacion estado) {
        TablaHash<String, Boolean> enGlobal = new TablaHash<>();
        for (SolicitudAyuda s : estado.getAyuda().obtenerSolicitudesPendientes()) {
            enGlobal.poner(s.getId(), Boolean.TRUE);
        }
        ListaEnlazada<Document> docs = new ListaEnlazada<>();
        for (Estudiante e : estado.getAutenticacion().getEstudiantesRegistrados()) {
            for (SolicitudAyuda s : e.getSolicitudesAyuda().aLista()) {
                docs.agregar(solicitudADocumento(s, enGlobal.contieneClave(s.getId())));
            }
        }
        return docs;
    }

    private Document contenidoADocumento(Contenido c) {
        List<Document> valoraciones = new ArrayList<>();
        for (Valoracion v : c.getValoraciones()) {
            valoraciones.add(new Document("_id", v.getId())
                    .append("estudianteId", v.getEstudiante().getId())
                    .append("puntuacion", v.getPuntuacion())
                    .append("comentario", v.getComentario())
                    .append("fecha", FECHA.format(v.getFechaValoracion())));
        }
        Document doc = new Document("_id", c.getId())
                .append("tema", c.getTema())
                .append("descripcion", c.getDescripcion() == null ? "" : c.getDescripcion())
                .append("autorId", c.getAutor().getId())
                .append("tipo", c.getTipo().name())
                .append("fecha", FECHA.format(c.getFechaCreacion()))
                .append("valoraciones", valoraciones);
        ArchivoMultimedia a = c.getArchivoMultimedia();
        if (a != null) {
            doc.append("archivo", new Document("nombreArchivo", a.getNombreArchivo())
                    .append("rutaRelativa", a.getRutaGuardada())
                    .append("tipoMime", a.getTipoMime())
                    .append("tamanio", a.getTamanio()));
        }
        return doc;
    }

    private Document mensajeADocumento(Mensaje m) {
        return new Document("remitenteId", m.getRemitente().getId())
                .append("texto", m.getTexto())
                .append("fecha", FECHA.format(m.getFecha()));
    }

    private Document solicitudADocumento(SolicitudAyuda s, boolean enGlobal) {
        return new Document("_id", s.getId())
                .append("tema", s.getTema())
                .append("urgencia", s.getUrgencia())
                .append("estudianteId", s.getEstudiante().getId())
                .append("descripcion", s.getDescripcion())
                .append("fecha", FECHA.format(s.getFechaSolicitud()))
                .append("estado", s.getEstado().name())
                .append("enGlobal", enGlobal);
    }

    private List<String> textos(ListaEnlazada<String> lista) {
        List<String> resultado = new ArrayList<>();
        for (String t : lista) resultado.add(t);
        return resultado;
    }

    // ------------------------------------------------------------------ documentos -> objetos

    /**
     * Reconstruye el estado en memoria (que debe estar vacío) a partir de los documentos guardados.
     * El orden importa: primero los usuarios y luego lo que se refiere a ellos.
     */
    public void restaurar(EstadoAplicacion estado,
                          ListaEnlazada<Document> usuarios,
                          ListaEnlazada<Document> contenidos,
                          ListaEnlazada<Document> grupos,
                          ListaEnlazada<Document> conversaciones,
                          ListaEnlazada<Document> solicitudes) {
        TablaHash<String, Estudiante> estudiantes = new TablaHash<>();

        for (Document d : usuarios) {
            ListaEnlazada<String> intereses = new ListaEnlazada<>();
            for (String i : listaDe(d, "intereses", String.class)) intereses.agregar(i);
            Estudiante e = new Estudiante(d.getString("_id"), d.getString("nombre"), d.getString("email"),
                    d.getString("contrasena"), intereses, new ListaEnlazada<>(), new ListaEnlazada<>(),
                    new ColaPrioridad<>(), new ListaEnlazada<>(), new ListaEnlazada<>());
            estado.getAutenticacion().restaurarEstudiante(e);
            estudiantes.poner(e.getId(), e);
        }

        for (Document d : contenidos) {
            Contenido c = restaurarContenido(d, estudiantes);
            if (c != null) {
                estado.getContenidos().agregarContenido(c);
                c.getAutor().getHistorialContenidos().agregar(c);
            }
        }

        for (Document d : grupos) {
            GrupoEstudio g = new GrupoEstudio(d.getString("_id"), d.getString("tema"));
            for (String id : listaDe(d, "miembros", String.class)) {
                Estudiante e = estudiantes.obtener(id);
                if (e != null) g.agregarMiembro(e);
            }
            for (Document p : listaDe(d, "publicaciones", Document.class)) {
                Contenido c = restaurarContenido(p, estudiantes);
                if (c != null) g.publicarContenidoGrupo(c);
            }
            for (Document m : listaDe(d, "mensajes", Document.class)) {
                Estudiante remitente = estudiantes.obtener(m.getString("remitenteId"));
                if (remitente != null) {
                    g.getMensajesGrupo().agregar(new Mensaje(remitente, g, m.getString("texto"), fecha(m, "fecha")));
                }
            }
            for (Document s : ordenadasPorFecha(listaDe(d, "solicitudes", Document.class))) {
                SolicitudAyuda sol = restaurarSolicitud(s, estudiantes);
                if (sol != null) g.getSolicitudesAyudaGrupo().encolar(sol, sol.getUrgencia());
            }
            estado.getGrupos().agregar(g);
        }

        for (Document d : conversaciones) {
            List<String> ids = listaDe(d, "participantes", String.class);
            Estudiante a = ids.size() > 0 ? estudiantes.obtener(ids.get(0)) : null;
            Estudiante b = ids.size() > 1 ? estudiantes.obtener(ids.get(1)) : null;
            if (a == null || b == null) continue;
            Conversacion c = new Conversacion(d.getString("_id"), a, b);
            for (Document m : listaDe(d, "mensajes", Document.class)) {
                Estudiante remitente = estudiantes.obtener(m.getString("remitenteId"));
                if (remitente != null) {
                    // Igual que en la aplicación: el mensaje de un chat se guarda con su remitente
                    c.agregarMensaje(new Mensaje(remitente, remitente, m.getString("texto"), fecha(m, "fecha")));
                }
            }
            estado.getConversaciones().agregar(c);
        }

        for (Document d : ordenadasPorFecha(solicitudes)) {
            SolicitudAyuda s = restaurarSolicitud(d, estudiantes);
            if (s == null) continue;
            s.getEstudiante().getSolicitudesAyuda().encolar(s, s.getUrgencia());
            if (Boolean.TRUE.equals(d.getBoolean("enGlobal"))) {
                estado.getAyuda().agregarSolicitud(s);
            }
        }
    }

    private Contenido restaurarContenido(Document d, TablaHash<String, Estudiante> estudiantes) {
        Estudiante autor = estudiantes.obtener(d.getString("autorId"));
        if (autor == null) return null; // su autor ya no existe

        ArchivoMultimedia archivo = null;
        Document a = d.get("archivo", Document.class);
        if (a != null) {
            Number tamanio = a.get("tamanio", Number.class);
            archivo = new ArchivoMultimedia(a.getString("nombreArchivo"), a.getString("rutaRelativa"),
                    a.getString("tipoMime"), tamanio == null ? 0L : tamanio.longValue());
        }

        ListaEnlazada<Valoracion> valoraciones = new ListaEnlazada<>();
        Contenido contenido = new Contenido(d.getString("_id"), d.getString("tema"), d.getString("descripcion"),
                autor, TipoContenido.valueOf(d.getString("tipo")), fecha(d, "fecha"), valoraciones, archivo);

        for (Document v : listaDe(d, "valoraciones", Document.class)) {
            Estudiante e = estudiantes.obtener(v.getString("estudianteId"));
            if (e == null) continue; // quien valoró ya no existe
            Valoracion valoracion = Valoracion.restaurar(v.getString("_id"), e, contenido,
                    v.getInteger("puntuacion"), v.getString("comentario"), fecha(v, "fecha"));
            valoraciones.agregar(valoracion);
            e.getValoraciones().agregar(valoracion);
        }
        return contenido;
    }

    private SolicitudAyuda restaurarSolicitud(Document d, TablaHash<String, Estudiante> estudiantes) {
        Estudiante e = estudiantes.obtener(d.getString("estudianteId"));
        if (e == null) return null;
        return SolicitudAyuda.restaurar(d.getString("_id"), d.getString("tema"), d.getInteger("urgencia"), e,
                d.getString("descripcion"), fecha(d, "fecha"), EstadoSolicitud.valueOf(d.getString("estado")));
    }

    /** Ordena por fecha (más antigua primero) para conservar el orden de llegada entre igual urgencia. */
    private ListaEnlazada<Document> ordenadasPorFecha(Iterable<Document> docs) {
        ListaEnlazada<Document> lista = new ListaEnlazada<>();
        for (Document d : docs) lista.agregar(d);
        lista.ordenar((x, y) -> x.getString("fecha").compareTo(y.getString("fecha")));
        return lista;
    }

    private LocalDateTime fecha(Document d, String campo) {
        return LocalDateTime.parse(d.getString(campo), FECHA);
    }

    private <T> List<T> listaDe(Document d, String campo, Class<T> tipo) {
        List<T> lista = d.getList(campo, tipo);
        return lista == null ? new ArrayList<>() : lista;
    }
}
