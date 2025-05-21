package co.edu.uniquindio.redsocial.models;

import co.edu.uniquindio.redsocial.models.services.implement.GestorContenidos;
import co.edu.uniquindio.redsocial.models.structures.ColaPrioridad;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import co.edu.uniquindio.redsocial.models.structures.NodoLista;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Representa a un estudiante dentro de la red social educativa.
 * Extiende de {@link Usuario} y proporciona operaciones para:
 * <ul>
 *   <li>Valorar contenidos.</li>
 *   <li>Publicar contenidos.</li>
 *   <li>Buscar contenidos globalmente.</li>
 *   <li>Enviar mensajes a otros estudiantes.</li>
 *   <li>Gestionar solicitudes de ayuda mediante una cola de prioridad.</li>
 *   <li>Unirse a grupos de estudio con coherencia bidireccional.</li>
 * </ul>
 *
 * <p>Las estructuras internas se inicializan por defecto si se reciben nulas en el constructor,
 * garantizando el correcto funcionamiento de todos los métodos.</p>
 *
 * @author Daniel
 * @author Sebastian
 * @author Juan
 * @since 2025-04-02
 */
public class Estudiante extends Usuario {

    private ColaPrioridad<SolicitudAyuda> solicitudesAyuda;
    private ListaEnlazada<GrupoEstudio> gruposEstudio;

    /**
     * Construye un estudiante con sus datos y colecciones asociadas.
     *
     * @param id                 Identificador único.
     * @param nombre             Nombre completo.
     * @param email              Correo válido.
     * @param contrasenia        Contraseña.
     * @param intereses          Lista de intereses (nullable).
     * @param historialContenidos Historial de contenidos (nullable).
     * @param valoraciones       Valoraciones realizadas (nullable).
     * @param solicitudesAyuda   Cola de solicitudes (nullable).
     * @param gruposEstudio      Grupos de estudio (nullable).
     */
    public Estudiante(
            String id,
            String nombre,
            String email,
            String contrasenia,
            ListaEnlazada<String> intereses,
            ListaEnlazada<Contenido> historialContenidos,
            ListaEnlazada<Valoracion> valoraciones,
            ColaPrioridad<SolicitudAyuda> solicitudesAyuda,
            ListaEnlazada<GrupoEstudio> gruposEstudio
    ) {
        super(id, nombre, email, contrasenia, intereses, historialContenidos, valoraciones);
        this.solicitudesAyuda = (solicitudesAyuda != null) ? solicitudesAyuda : new ColaPrioridad<>();
        this.gruposEstudio    = (gruposEstudio    != null) ? gruposEstudio    : new ListaEnlazada<>();
    }

    /**
     * Registra una valoración sobre un contenido.
     *
     * @param contenido Contenido a valorar; no null.
     * @param puntuacion Valor entre 1 y 5.
     * @param comentario Comentario opcional.
     * @throws IllegalArgumentException en caso de parámetros inválidos.
     */
    public void valorarContenido(Contenido contenido, int puntuacion, String comentario) {
        if (contenido == null) {
            throw new IllegalArgumentException("El contenido no puede ser null");
        }
        if (puntuacion < 1 || puntuacion > 5) {
            throw new IllegalArgumentException("La puntuación debe estar entre 1 y 5");
        }
        Valoracion v = new Valoracion(this, puntuacion, comentario);
        contenido.getValoraciones().agregar(v);
        getValoraciones().agregar(v);
    }

    /**
     * Publica un contenido en la plataforma y lo añade al historial.
     *
     * @param contenido Contenido a publicar; no null.
     * @throws IllegalArgumentException si contenido es null.
     */
    public void publicarContenido(Contenido contenido) {
        if (contenido == null) {
            throw new IllegalArgumentException("El contenido no puede ser null");
        }
        GestorContenidos.getInstancia().agregarContenido(contenido);
        getHistorialContenidos().agregar(contenido);
    }

    /**
     * Busca contenidos en toda la plataforma según filtros opcionales.
     * Si un filtro es null o vacío, se ignora.
     *
     * @param tema  Filtro de tema (nullable).
     * @param autor Filtro de autor (nullable).
     * @param tipo  Filtro de tipo (nullable).
     * @return Lista de contenidos que coinciden.
     */
    public ListaEnlazada<Contenido> buscarContenido(String tema, String autor, String tipo) {
        GestorContenidos gestor = GestorContenidos.getInstancia();
        boolean fTema  = tema  != null && !tema.isEmpty();
        boolean fAutor = autor != null && !autor.isEmpty();
        boolean fTipo  = tipo  != null && !tipo.isEmpty();

        if (fTema && fAutor && fTipo) {
            return gestor.buscarPorTemaAutorTipo(tema, autor, tipo);
        }
        if (fTema && !fAutor && !fTipo) {
            return gestor.buscarPorTema(tema);
        }
        if (!fTema && fAutor && !fTipo) {
            return gestor.buscarPorAutor(autor);
        }
        // Otros casos: filtrar manualmente sobre todos los contenidos destacados
        ListaEnlazada<Contenido> todos = gestor.getContenidoDestacado();
        ListaEnlazada<Contenido> res = new ListaEnlazada<>();
        NodoLista<Contenido> curr = todos.getCabeza();
        while (curr != null) {
            Contenido c = curr.getDato();
            boolean mTema  = !fTema  || c.getTema().equalsIgnoreCase(tema);
            boolean mAutor = !fAutor || c.getAutor().getNombre().equalsIgnoreCase(autor);
            boolean mTipo  = !fTipo  || c.getTipo().equalsIgnoreCase(tipo);
            if (mTema && mAutor && mTipo) {
                res.agregar(c);
            }
            curr = curr.getSiguiente();
        }
        return res;
    }

    /**
     * Envía un mensaje a otro estudiante.
     *
     * @param destino Destinatario; no null.
     * @param texto   Mensaje; no null/no vacío.
     * @throws IllegalArgumentException si parámetros inválidos.
     */
    public void enviarMensaje(Estudiante destino, String texto) {
        if (destino == null) {
            throw new IllegalArgumentException("El destinatario no puede ser null");
        }
        if (texto == null || texto.isEmpty()) {
            throw new IllegalArgumentException("El mensaje no puede estar vacío");
        }
        Mensaje msg = new Mensaje(this, destino, texto, LocalDateTime.now());
        msg.enviar();
    }

    /**
     * Solicita ayuda en un tema, encolando según urgencia.
     *
     * @param solicitud Instancia de {@link SolicitudAyuda}; no null.
     * @throws IllegalArgumentException si solicitud es null.
     */
    public void solicitarAyuda(SolicitudAyuda solicitud) {
        if (solicitud == null) {
            throw new IllegalArgumentException("Solicitud no puede ser null");
        }
        solicitudesAyuda.encolar(solicitud, solicitud.getUrgencia());
    }

    /**
     * Procesa la siguiente solicitud más urgente.
     *
     * @return solicitud de ayuda o null si no hay.
     */
    public SolicitudAyuda procesarSiguienteSolicitud() {
        return solicitudesAyuda.desencolar();
    }
    /**
     * Se une a un grupo de estudio. Si el grupo no está en su lista, lo agrega.
     * Además, si el estudiante no está en el grupo, lo agrega también.
     *
     * @param grupo grupo al que desea unirse.
     */
    public void unirseAGrupo(GrupoEstudio grupo) {
        if (grupo == null) {
            throw new IllegalArgumentException("El grupo no puede ser null");
        }

        if (!gruposEstudio.buscar(grupo)) {
            gruposEstudio.agregar(grupo);
        }

        if (!grupo.getMiembros().buscar(this)) {
            grupo.getMiembros().agregar(this);
        }
    }


    // =====================================
    // Getters y setters de colecciones
    // =====================================

    public ListaEnlazada<GrupoEstudio> getGruposEstudio() {
        return gruposEstudio;
    }

    public ColaPrioridad<SolicitudAyuda> getSolicitudesAyuda() {
        return solicitudesAyuda;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Estudiante)) return false;
        Estudiante e = (Estudiante) o;
        return Objects.equals(getId(), e.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
