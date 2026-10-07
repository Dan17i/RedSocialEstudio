package persistence;

import co.edu.uniquindio.redsocial.ArchivoMultimedia;
import co.edu.uniquindio.redsocial.models.Contenido;
import co.edu.uniquindio.redsocial.models.Conversacion;
import co.edu.uniquindio.redsocial.models.Enums.EstadoSolicitud;
import co.edu.uniquindio.redsocial.models.Enums.TipoContenido;
import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.GrupoEstudio;
import co.edu.uniquindio.redsocial.models.Mensaje;
import co.edu.uniquindio.redsocial.models.SolicitudAyuda;
import co.edu.uniquindio.redsocial.models.services.implement.GestorContenidos;
import co.edu.uniquindio.redsocial.models.services.implement.RedAfinidad;
import co.edu.uniquindio.redsocial.models.services.implement.SistemaAutenticacion;
import co.edu.uniquindio.redsocial.models.services.implement.SistemaAyuda;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import co.edu.uniquindio.redsocial.persistence.EstadoAplicacion;
import co.edu.uniquindio.redsocial.persistence.MapeadorDocumentos;
import org.bson.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MapeadorDocumentosTest {

    private final MapeadorDocumentos mapeador = new MapeadorDocumentos();

    /** Estado vacío con servicios nuevos (reinicia los singletons). */
    static EstadoAplicacion estadoVacio() {
        RedAfinidad.reiniciar();
        GestorContenidos.reiniciar();
        return new EstadoAplicacion(new SistemaAutenticacion(), GestorContenidos.getInstancia(),
                RedAfinidad.getInstancia(), new SistemaAyuda(), new ListaEnlazada<>(), new ListaEnlazada<>());
    }

    /** Estado con un poco de todo: usuarios, contenidos, valoraciones, grupo, chat y ayuda. */
    static EstadoAplicacion estadoConDatos() {
        EstadoAplicacion e = estadoVacio();
        SistemaAutenticacion auth = e.getAutenticacion();
        Estudiante ana = auth.registrarEstudiante("Ana", "ana@correo.com", "clave-ana");
        Estudiante beto = auth.registrarEstudiante("Beto", "beto@correo.com", "clave-beto");
        Estudiante carla = auth.registrarEstudiante("Carla", "carla@correo.com", "clave-carla");
        ana.agregarInteres("grafos");
        ana.agregarInteres("java");
        beto.agregarInteres("grafos");

        // Contenido global con archivo y dos valoraciones
        Contenido c1 = new Contenido("C1", "Grafos en Java", "Introducción", ana, TipoContenido.TEXTO,
                LocalDateTime.of(2026, 1, 5, 10, 30, 15, 123_000_000), new ListaEnlazada<>(),
                new ArchivoMultimedia("grafos.pdf", "abc_grafos.pdf", "application/pdf", 2048));
        ana.publicarContenido(c1, e.getContenidos());
        beto.valorarContenido(c1, 5, "Excelente");
        carla.valorarContenido(c1, 3, "Regular");

        // Grupo con miembros, publicación propia, mensajes y ayuda del grupo
        GrupoEstudio g = new GrupoEstudio("Grupo grafos", "grafos");
        g.agregarMiembro(ana);
        g.agregarMiembro(beto);
        Contenido c2 = new Contenido("C2", "Apuntes del grupo", "Solo para el grupo", beto, TipoContenido.TEXTO,
                LocalDateTime.of(2026, 2, 1, 8, 0, 0), new ListaEnlazada<>(), null);
        g.publicarContenidoGrupo(c2);
        ana.valorarContenido(c2, 4, "Útil");
        g.enviarMensajeGrupo(ana, "Hola grupo");
        g.enviarMensajeGrupo(beto, "Hola Ana");
        g.solicitarAyudaEnGrupo(new SolicitudAyuda("recursión", 2, beto, "No la entiendo",
                LocalDateTime.of(2026, 2, 2, 9, 0, 0)));
        e.getGrupos().agregar(g);

        // Conversación de chat
        Conversacion conv = new Conversacion(ana, beto);
        conv.agregarMensaje(new Mensaje(ana, ana, "Buenas Beto", LocalDateTime.of(2026, 3, 1, 12, 0, 0)));
        conv.agregarMensaje(new Mensaje(beto, beto, "Hola Ana", LocalDateTime.of(2026, 3, 1, 12, 1, 0)));
        e.getConversaciones().agregar(conv);

        // Ayuda: dos solicitudes pendientes de Ana (misma urgencia) y una de Beto ya atendida
        SolicitudAyuda s1 = new SolicitudAyuda("derivadas", 3, ana, "Ayuda 1", LocalDateTime.of(2026, 4, 1, 9, 0, 0));
        SolicitudAyuda s3 = new SolicitudAyuda("integrales", 3, ana, "Ayuda 3", LocalDateTime.of(2026, 4, 1, 9, 5, 0));
        SolicitudAyuda s2 = new SolicitudAyuda("listas", 5, beto, "Ayuda 2", LocalDateTime.of(2026, 4, 1, 9, 2, 0));
        for (SolicitudAyuda s : new SolicitudAyuda[]{s1, s2, s3}) {
            s.getEstudiante().solicitarAyuda(s);
            e.getAyuda().agregarSolicitud(s);
        }
        e.getAyuda().atenderSolicitud(s2.getId(), carla);
        return e;
    }

    @BeforeEach
    @AfterEach
    void limpiar() {
        RedAfinidad.reiniciar();
        GestorContenidos.reiniciar();
    }

    /** Todos los documentos del estado como texto, para compararlos. */
    private String volcado(EstadoAplicacion e) {
        StringBuilder sb = new StringBuilder();
        String[] nombres = {"usuarios", "contenidos", "grupos", "conversaciones", "solicitudes"};
        ListaEnlazada<Document>[] colecciones = coleccionesDe(e);
        for (int i = 0; i < nombres.length; i++) {
            sb.append("## ").append(nombres[i]).append('\n');
            for (Document d : colecciones[i]) sb.append(d.toJson()).append('\n');
        }
        return sb.toString();
    }

    @SuppressWarnings("unchecked")
    private ListaEnlazada<Document>[] coleccionesDe(EstadoAplicacion e) {
        return new ListaEnlazada[]{mapeador.usuarios(e), mapeador.contenidos(e), mapeador.grupos(e),
                mapeador.conversaciones(e), mapeador.solicitudes(e)};
    }

    private EstadoAplicacion reconstruir(EstadoAplicacion origen) {
        ListaEnlazada<Document>[] docs = coleccionesDe(origen);
        EstadoAplicacion nuevo = estadoVacio();
        mapeador.restaurar(nuevo, docs[0], docs[1], docs[2], docs[3], docs[4]);
        return nuevo;
    }

    @Test
    void guardar_y_cargar_deja_el_estado_identico() {
        EstadoAplicacion original = estadoConDatos();
        String antes = volcado(original);

        EstadoAplicacion restaurado = reconstruir(original);

        assertEquals(antes, volcado(restaurado));
    }

    @Test
    void los_usuarios_restaurados_pueden_iniciar_sesion_con_su_contrasena() {
        EstadoAplicacion restaurado = reconstruir(estadoConDatos());
        SistemaAutenticacion auth = restaurado.getAutenticacion();

        Estudiante ana = (Estudiante) auth.iniciarSesion("ana@correo.com", "clave-ana");
        assertEquals("Ana", ana.getNombre());
        assertEquals(2, ana.getIntereses().getTamanio());
        assertThrows(SecurityException.class, () -> auth.iniciarSesion("ana@correo.com", "otra"));
        assertEquals(3, RedAfinidad.getInstancia().getGrafo().tamano());
    }

    @Test
    void contenidos_valoraciones_y_adjuntos_se_restauran_enlazados() {
        EstadoAplicacion restaurado = reconstruir(estadoConDatos());

        Contenido c1 = restaurado.getContenidos().obtenerTodosLosContenidos().obtener(0);
        assertEquals("Grafos en Java", c1.getTema());
        assertEquals("application/pdf", c1.getArchivoMultimedia().getTipoMime());
        assertEquals(2, c1.getValoraciones().getTamanio());
        assertEquals(4.0, c1.promedioValoraciones(), 0.0001);
        assertEquals("Ana", c1.getAutor().getNombre());
        assertTrue(c1.getAutor().getHistorialContenidos().contiene(c1));
        // Las valoraciones también quedan en cada estudiante (las usa el grafo de afinidad)
        Estudiante beto = (Estudiante) restaurado.getAutenticacion().iniciarSesion("beto@correo.com", "clave-beto");
        assertEquals(1, beto.getValoraciones().getTamanio());
    }

    @Test
    void el_grupo_conserva_miembros_publicaciones_mensajes_y_ayuda() {
        EstadoAplicacion restaurado = reconstruir(estadoConDatos());

        GrupoEstudio g = restaurado.getGrupos().obtener(0);
        assertEquals("Grupo grafos", g.getId());
        assertEquals(2, g.getMiembros().getTamanio());
        assertEquals(1, g.getPublicaciones().getTamanio());
        assertEquals(1, g.getPublicaciones().obtener(0).getValoraciones().getTamanio());
        assertEquals(2, g.getMensajesGrupo().getTamanio());
        assertEquals("Hola grupo", g.getMensajesGrupo().obtener(0).getTexto());
        assertEquals(1, g.getSolicitudesAyudaGrupo().tamanio());
        // El estudiante sabe a qué grupos pertenece
        assertEquals(1, g.getMiembros().obtener(0).getGruposEstudio().getTamanio());
    }

    @Test
    void el_chat_conserva_participantes_y_el_orden_de_los_mensajes() {
        EstadoAplicacion restaurado = reconstruir(estadoConDatos());

        Conversacion c = restaurado.getConversaciones().obtener(0);
        assertEquals(2, c.getParticipantes().getTamanio());
        assertEquals("Buenas Beto", c.getMensajes().obtener(0).getTexto());
        assertEquals("Hola Ana", c.getMensajes().obtener(1).getTexto());
        assertEquals("Beto", c.getMensajes().obtener(1).getRemitente().getNombre());
    }

    @Test
    void la_cola_de_ayuda_conserva_estados_y_orden_de_llegada() {
        EstadoAplicacion restaurado = reconstruir(estadoConDatos());

        ListaEnlazada<SolicitudAyuda> pendientes = restaurado.getAyuda().obtenerSolicitudesPendientes();
        // La de Beto (urgencia 5) ya fue atendida: solo quedan las dos de Ana, en orden de llegada
        assertEquals(2, pendientes.getTamanio());
        assertEquals("derivadas", pendientes.obtener(0).getTema());
        assertEquals("integrales", pendientes.obtener(1).getTema());

        Estudiante beto = (Estudiante) restaurado.getAutenticacion().iniciarSesion("beto@correo.com", "clave-beto");
        SolicitudAyuda deBeto = beto.getSolicitudesAyuda().aLista().obtener(0);
        assertEquals(EstadoSolicitud.EN_PROGRESO, deBeto.getEstado());
    }

    @Test
    void un_estado_vacio_se_guarda_y_se_carga_sin_problemas() {
        EstadoAplicacion restaurado = reconstruir(estadoVacio());
        assertTrue(restaurado.getAutenticacion().getEstudiantesRegistrados().isEmpty());
        assertTrue(restaurado.getGrupos().isEmpty());
    }

    @Test
    void se_omiten_los_datos_de_usuarios_que_ya_no_existen() {
        EstadoAplicacion original = estadoConDatos();
        ListaEnlazada<Document>[] docs = coleccionesDe(original);

        // Se pierde el usuario Beto: su valoración, su mensaje y su solicitud se descartan sin fallar
        ListaEnlazada<Document> sinBeto = new ListaEnlazada<>();
        for (Document d : docs[0]) if (!"Beto".equals(d.getString("nombre"))) sinBeto.agregar(d);

        EstadoAplicacion restaurado = estadoVacio();
        mapeador.restaurar(restaurado, sinBeto, docs[1], docs[2], docs[3], docs[4]);

        assertEquals(2, restaurado.getAutenticacion().getEstudiantesRegistrados().getTamanio());
        assertEquals(1, restaurado.getContenidos().obtenerTodosLosContenidos().obtener(0).getValoraciones().getTamanio());
        assertTrue(restaurado.getConversaciones().isEmpty());
    }
}
