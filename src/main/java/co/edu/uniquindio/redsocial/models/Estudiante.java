package co.edu.uniquindio.redsocial.models;

import co.edu.uniquindio.redsocial.models.services.implement.GestorContenidos;
import co.edu.uniquindio.redsocial.models.structures.ColaPrioridad;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import co.edu.uniquindio.redsocial.models.structures.NodoLista;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Clase que representa a un estudiante dentro de la red social educativa.
 * Hereda de {@link Usuario} y permite funcionalidades como valorar contenidos, unirse a grupos de estudio,
 * enviar mensajes, buscar contenidos y gestionar solicitudes de ayuda.
 * @author Daniel Jurado
 * @author Sebastian Torres
 * @author Juan Soto
 * @since 2025-05-12
 */
public class Estudiante extends Usuario {

    private ColaPrioridad<SolicitudAyuda> solicitudesAyuda;
    private ListaEnlazada<GrupoEstudio> gruposEstudio;
    private ListaEnlazada<Mensaje> bandejaEntrada;

    /**
     * Constructor principal para inicializar un estudiante con todos sus atributos.
     *
     * @param id                 Identificador único del estudiante.
     * @param nombre             Nombre del estudiante.
     * @param email              Correo electrónico del estudiante.
     * @param contrasenia        Contraseña de acceso.
     * @param intereses          Lista de intereses del estudiante.
     * @param historialContenidos Historial de contenidos vistos o publicados.
     * @param valoraciones       Lista de valoraciones realizadas por el estudiante.
     * @param solicitudesAyuda   Cola de solicitudes de ayuda priorizadas.
     * @param gruposEstudio      Lista de grupos de estudio en los que participa.
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
            ListaEnlazada<GrupoEstudio> gruposEstudio,
            ListaEnlazada<Mensaje> bandejaEntrada
    ) {
        super(id, nombre, email, contrasenia, intereses, historialContenidos, valoraciones);
        this.solicitudesAyuda = (solicitudesAyuda != null) ? solicitudesAyuda : new ColaPrioridad<>();
        this.gruposEstudio = (gruposEstudio != null) ? gruposEstudio : new ListaEnlazada<>();
        this.bandejaEntrada = (bandejaEntrada != null) ? bandejaEntrada : new ListaEnlazada<>();
    }

    /**
     * Permite al estudiante valorar un contenido.
     *
     * @param contenido  Contenido a valorar.
     * @param puntuacion Puntuación entre 1 y 5.
     * @param comentario Comentario adicional sobre el contenido.
     * @throws IllegalArgumentException si el contenido es nulo o la puntuación está fuera de rango.
     */
    public void valorarContenido(Contenido contenido, int puntuacion, String comentario) {
        if (contenido == null) throw new IllegalArgumentException("El contenido no puede ser null");
        if (puntuacion < 1 || puntuacion > 5) throw new IllegalArgumentException("La puntuación debe estar entre 1 y 5");

        Valoracion v = new Valoracion(this, contenido, puntuacion, comentario);
        contenido.getValoraciones().agregar(v);
        getValoraciones().agregar(v);
    }

    /**
     * Publica un contenido en el sistema mediante el gestor de contenidos.
     *
     * @param contenido Contenido a publicar.
     * @param gestor    Gestor de contenidos que lo administra.
     * @throws IllegalArgumentException si el contenido o el gestor son nulos.
     */
    public void publicarContenido(Contenido contenido, GestorContenidos gestor) {
        if (contenido == null) throw new IllegalArgumentException("El contenido no puede ser null");
        if (gestor == null) throw new IllegalArgumentException("El gestor no puede ser null");

        gestor.agregarContenido(contenido);
        getHistorialContenidos().agregar(contenido);
    }

    /**
     * Busca contenidos destacados según filtros opcionales.
     *
     * @param tema   Tema del contenido (puede ser null o vacío para ignorar).
     * @param autor  Nombre del autor (puede ser null o vacío para ignorar).
     * @param tipo   Tipo del contenido (puede ser null o vacío para ignorar).
     * @param gestor Gestor de contenidos desde donde buscar.
     * @return Lista de contenidos que cumplen los filtros.
     * @throws IllegalArgumentException si el gestor es nulo.
     */
    public ListaEnlazada<Contenido> buscarContenido(String tema, String autor, String tipo, GestorContenidos gestor) {
        if (gestor == null) throw new IllegalArgumentException("El gestor no puede ser null");

        boolean filtrarTema = tema != null && !tema.isEmpty();
        boolean filtrarAutor = autor != null && !autor.isEmpty();
        boolean filtrarTipo = tipo != null && !tipo.isEmpty();

        ListaEnlazada<Contenido> resultados = new ListaEnlazada<>();
        NodoLista<Contenido> actual = gestor.getContenidoDestacado().getCabeza();

        while (actual != null) {
            Contenido c = actual.getDato();
            boolean coincideTema = !filtrarTema || c.getTema().equalsIgnoreCase(tema);
            boolean coincideAutor = !filtrarAutor || c.getAutor().getNombre().equalsIgnoreCase(autor);
            boolean coincideTipo = !filtrarTipo || c.getTipo().name().equalsIgnoreCase(tipo);

            if (coincideTema && coincideAutor && coincideTipo) {
                resultados.agregar(c);
            }

            actual = actual.getSiguiente();
        }

        return resultados;
    }

    /**
     * Busca contenidos en el historial personal usando filtros opcionales.
     *
     * @param tema  Tema del contenido.
     * @param autor Nombre del autor.
     * @param tipo  Tipo de contenido.
     * @return Lista de contenidos filtrados encontrados en el historial.
     */
    public ListaEnlazada<Contenido> buscarEnHistorial(String tema, String autor, String tipo) {
        ListaEnlazada<Contenido> resultados = new ListaEnlazada<>();
        NodoLista<Contenido> actual = getHistorialContenidos().getCabeza();

        while (actual != null) {
            Contenido c = actual.getDato();
            boolean coincideTema = (tema == null || tema.isEmpty()) || c.getTema().equalsIgnoreCase(tema);
            boolean coincideAutor = (autor == null || autor.isEmpty()) || c.getAutor().getNombre().equalsIgnoreCase(autor);
            boolean coincideTipo = (tipo == null || tipo.isEmpty()) || c.getTipo().name().equalsIgnoreCase(tipo);

            if (coincideTema && coincideAutor && coincideTipo) {
                resultados.agregar(c);
            }

            actual = actual.getSiguiente();
        }

        return resultados;
    }

    /**
     * Envía un mensaje a otro estudiante.
     *
     * @param destino Estudiante receptor.
     * @param texto   Contenido del mensaje.
     * @throws IllegalArgumentException si el destino es nulo o el texto está vacío.
     */
    public void enviarMensaje(Estudiante destino, String texto) {
        if (destino == null) throw new IllegalArgumentException("El destinatario no puede ser null");
        if (texto == null || texto.isEmpty()) throw new IllegalArgumentException("El mensaje no puede estar vacío");

        Mensaje msg = new Mensaje(this, destino, texto, LocalDateTime.now());
        msg.enviar();
    }

    /**
     * Añade una nueva solicitud de ayuda a la cola de prioridades.
     *
     * @param solicitud Solicitud a registrar.
     * @throws IllegalArgumentException si la solicitud es nula.
     */
    public void solicitarAyuda(SolicitudAyuda solicitud) {
        if (solicitud == null) throw new IllegalArgumentException("Solicitud no puede ser null");
        solicitudesAyuda.encolar(solicitud, solicitud.getUrgencia());
    }

    /**
     * Procesa y retorna la siguiente solicitud de ayuda con mayor prioridad.
     *
     * @return Solicitud procesada o null si no hay solicitudes.
     */
    public SolicitudAyuda procesarSiguienteSolicitud() {
        return solicitudesAyuda.desencolar();
    }

    @Override
    public void recibirMensaje(Mensaje mensaje) {
        if (mensaje != null) {
            bandejaEntrada.agregar(mensaje);
            System.out.println("Mensaje recibido por " + getNombre() + ": " + mensaje.getTexto());
        }
    }
    /**
     * Añade al estudiante a un grupo de estudio, si aún no pertenece a él.
     *
     * @param grupo Grupo de estudio al que se desea unir.
     * @throws IllegalArgumentException si el grupo es nulo.
     */
    public void unirseAGrupo(GrupoEstudio grupo) {
        if (grupo == null) throw new IllegalArgumentException("El grupo no puede ser null");

        if (!gruposEstudio.buscar(grupo)) {
            gruposEstudio.agregar(grupo);
        }

        if (!grupo.getMiembrosInterno().buscar(this)) {
            grupo.getMiembrosInterno().agregar(this);
        }
    }

    /**
     * Obtiene la lista de grupos de estudio a los que pertenece el estudiante.
     *
     * @return Lista de grupos de estudio.
     */
    public ListaEnlazada<GrupoEstudio> getGruposEstudio() {
        return gruposEstudio;
    }

    /**
     * Obtiene la cola de solicitudes de ayuda del estudiante.
     *
     * @return Cola de solicitudes priorizadas.
     */
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

