package co.edu.uniquindio.redsocial.models;

import co.edu.uniquindio.redsocial.models.structures.ColaPrioridad;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import co.edu.uniquindio.redsocial.models.Mensaje;
import co.edu.uniquindio.redsocial.models.SolicitudAyuda;


import java.time.LocalDateTime;

/**
 * Representa un grupo de estudio conformado por varios estudiantes y un tema específico.
 * Los grupos permiten agregar o eliminar miembros, publicar contenidos y consultar subconjuntos de miembros.
 * La clase mantiene la integridad interna mediante encapsulamiento y control de acceso.
 *
 * @author
 * Daniel Jurado, Sebastián Torres, Juan Soto
 * @since 2025-05-12
 */
public class GrupoEstudio {

    /** Identificador único del grupo (inmutable). */
    private final String id;

    /** Tema central del grupo. */
    private String tema;

    /** Lista interna de miembros del grupo. */
    private final ListaEnlazada<Estudiante> miembros;

    /** Lista interna de publicaciones del grupo. */
    private final ListaEnlazada<Contenido> publicaciones;

    private ListaEnlazada<Mensaje> mensajesGrupo = new ListaEnlazada<>();

    // 2) Solicitudes de ayuda
    private final ColaPrioridad<SolicitudAyuda> solicitudesAyudaGrupo = new ColaPrioridad<>();


    /**
     * Constructor que inicializa el grupo de estudio con un identificador y tema.
     * La lista de miembros y publicaciones se inicializa vacía.
     *
     * @param id Identificador único del grupo. No debe ser nulo ni vacío.
     * @param tema Tema principal del grupo. No debe ser nulo ni vacío.
     * @throws IllegalArgumentException si el id o el tema son inválidos.
     */
    public GrupoEstudio(String id, String tema) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("El id no puede ser nulo ni vacío");
        }
        if (tema == null || tema.isBlank()) {
            throw new IllegalArgumentException("El tema no puede ser nulo ni vacío");
        }
        this.id = id;
        this.tema = tema;
        this.miembros = new ListaEnlazada<>();
        this.publicaciones = new ListaEnlazada<>();
    }

    /** Envía un mensaje de chat a todos los miembros y lo guarda. */
    public void enviarMensajeGrupo(Estudiante remitente, String texto) {
        Mensaje m = new Mensaje(remitente, this, texto, LocalDateTime.now());
        m.enviar();
        mensajesGrupo.agregar(m);
    }

    /** Obtiene el historial de chat del grupo. */
    public ListaEnlazada<Mensaje> getMensajesGrupo() {
        return mensajesGrupo;
    }

    /** Encola una solicitud de ayuda en el grupo. */
    public void solicitarAyudaEnGrupo(SolicitudAyuda solicitud) {
        if (solicitud == null) throw new IllegalArgumentException("La solicitud no puede ser nula");
        solicitudesAyudaGrupo.encolar(solicitud, solicitud.getUrgencia());
    }

    /** Obtiene la cola de solicitudes de ayuda del grupo. */
    public ColaPrioridad<SolicitudAyuda> getSolicitudesAyudaGrupo() {
        return solicitudesAyudaGrupo;
    }
    /**
     * Agrega un estudiante al grupo si no está presente aún.
     * También actualiza al estudiante para que registre su pertenencia a este grupo.
     *
     * Esta operación genera una dependencia bidireccional controlada entre el grupo y el estudiante.
     *
     * @param estudiante Estudiante a agregar. No debe ser nulo.
     * @throws IllegalArgumentException si el estudiante es nulo.
     */
    public void agregarMiembro(Estudiante estudiante) {
        if (estudiante == null) {
            throw new IllegalArgumentException("El estudiante no puede ser nulo");
        }

        if (!miembros.contiene(estudiante)) {
            miembros.agregar(estudiante);
        }

        if (!estudiante.getGruposEstudio().contiene(this)) {
            estudiante.getGruposEstudio().agregar(this);
        }
    }

    /**
     * Elimina un estudiante del grupo, si está presente.
     * También elimina este grupo de la lista de grupos del estudiante.
     *
     * @param estudiante Estudiante a eliminar. No debe ser nulo.
     * @throws IllegalArgumentException si el estudiante es nulo.
     */
    public void eliminarMiembro(Estudiante estudiante) {
        if (estudiante == null) {
            throw new IllegalArgumentException("El estudiante no puede ser nulo");
        }

        if (miembros.eliminar(estudiante)) {
            estudiante.getGruposEstudio().eliminar(this);
        }
    }

    /**
     * Obtiene un subconjunto de miembros del grupo, desde el índice {@code desde} hasta {@code hasta}, ambos inclusivos.
     *
     * Nota: Esta operación tiene complejidad O(n) debido al recorrido de la lista enlazada.
     *
     * @param desde Índice inicial del subgrupo (inclusive).
     * @param hasta Índice final del subgrupo (inclusive).
     * @return Sublista de miembros entre los índices indicados.
     * @throws IndexOutOfBoundsException si los índices son inválidos.
     */
    public ListaEnlazada<Estudiante> obtenerSubgrupo(int desde, int hasta) {
        if (desde < 0 || hasta >= miembros.getTamanio() || desde > hasta) {
            throw new IndexOutOfBoundsException("Rango inválido para obtener subgrupo");
        }
        return miembros.sublista(desde, hasta);
    }

    /**
     * Publica un contenido dentro del grupo, visible solo para sus miembros.
     *
     * @param contenido Contenido a publicar. No debe ser nulo.
     * @throws IllegalArgumentException si el contenido es nulo.
     */
    public void publicarContenidoGrupo(Contenido contenido) {
        if (contenido == null) {
            throw new IllegalArgumentException("El contenido no puede ser nulo");
        }
        publicaciones.agregar(contenido);
    }

    /**
     * Obtiene una copia superficial de las publicaciones actuales del grupo.
     *
     * Nota: Esta operación tiene complejidad O(n) y puede impactar el rendimiento
     * si se invoca con alta frecuencia en listas grandes.
     *
     * @return Lista de publicaciones del grupo.
     */
    public ListaEnlazada<Contenido> getPublicaciones() {
        ListaEnlazada<Contenido> copia = new ListaEnlazada<>();
        for (int i = 0; i < publicaciones.getTamanio(); i++) {
            copia.agregar(publicaciones.obtener(i));
        }
        return copia;
    }

    /**
     * Devuelve una copia superficial de la lista de miembros del grupo,
     * protegiendo la lista interna de modificaciones externas.
     *
     * Nota: Esta operación tiene complejidad O(n).
     *
     * @return Lista de estudiantes miembros del grupo.
     */
    public ListaEnlazada<Estudiante> getMiembros() {
        ListaEnlazada<Estudiante> copia = new ListaEnlazada<>();
        for (int i = 0; i < miembros.getTamanio(); i++) {
            copia.agregar(miembros.obtener(i));
        }
        return copia;
    }

    /**
     * Devuelve el identificador único del grupo.
     *
     * @return ID del grupo.
     */
    public String getId() {
        return id;
    }

    /**
     * Devuelve el tema central del grupo.
     *
     * @return Tema del grupo.
     */
    public String getTema() {
        return tema;
    }

    /**
     * Modifica el tema central del grupo.
     *
     * @param tema Nuevo tema del grupo. No debe ser nulo ni vacío.
     * @throws IllegalArgumentException si el tema es inválido.
     */
    public void setTema(String tema) {
        if (tema == null || tema.isBlank()) {
            throw new IllegalArgumentException("El tema no puede ser nulo ni vacío");
        }
        this.tema = tema;
    }

    /**
     * Solo para uso interno del modelo, no exponer fuera del paquete.
     * Devuelve la referencia directa a la lista interna de miembros del grupo.
     *
     * Usar con extrema precaución para no comprometer el encapsulamiento.
     *
     * @return Lista enlazada interna de miembros.
     */
    ListaEnlazada<Estudiante> getMiembrosInterno() {
        return miembros;
    }

    public void recibirMensaje(Mensaje mensaje) {
        mensajesGrupo.agregar(mensaje);
        System.out.println("Mensaje recibido por grupo: " + mensaje.getTexto());
    }

    /**
     * Representación textual del grupo de estudio.
     *
     * @return Cadena con él, id, tema y cantidad de miembros.
     */
    @Override
    public String toString() {
        return "GrupoEstudio{id='" + id + "', tema='" + tema + "', miembros=" + miembros.getTamanio() + "}";
    }
}

