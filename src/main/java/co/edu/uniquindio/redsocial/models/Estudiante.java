package co.edu.uniquindio.redsocial.models;

import java.time.LocalDateTime;
/**
 * Clase que representa a un estudiante dentro de la red social educativa.
 * Hereda de {@link Usuario} y tiene funcionalidades adicionales como unirse a grupos,
 * valorar contenido, buscar contenidos, publicar y enviar mensajes.
 *
 * @param <T> Tipo genérico (no se utiliza directamente en esta clase, puede omitirse si no es necesario).
 *
 * @author Daniel Jurado
 * @author Sebastian Torres
 * @author Juan Soto
 * @since 2025-04-02
 */
class Estudiante<T> extends Usuario {

    private ColaPrioridad<SolicitudAyuda> solicitudesAyuda;
    private ListaEnlazada<GrupoEstudio> gruposEstudio;
    /**
     * Constructor del estudiante.
     *
     * @param id                 Identificador único del estudiante.
     * @param nombre             Nombre del estudiante.
     * @param email              Correo electrónico del estudiante.
     * @param contrasenia         Contraseña del estudiante.
     * @param intereses          Lista de intereses académicos.
     * @param historialContenidos Lista de contenidos publicados o consumidos.
     * @param valoraciones       Lista de valoraciones realizadas.
     * @param solicitudesAyuda   Cola de solicitudes de ayuda priorizadas.
     * @param gruposEstudio      Lista de grupos de estudio a los que pertenece.
     */
    public Estudiante(String id, String nombre, String email, String contrasenia,
                      ListaEnlazada<String> intereses, ListaEnlazada<Contenido> historialContenidos,
                      ListaEnlazada<Valoracion> valoraciones, ColaPrioridad<SolicitudAyuda> solicitudesAyuda,
                      ListaEnlazada<GrupoEstudio> gruposEstudio) {
        super(id, nombre, email, contrasenia, intereses, historialContenidos, valoraciones);
        this.solicitudesAyuda = solicitudesAyuda;
        this.gruposEstudio = gruposEstudio;
    }
    /**
     * Permite al estudiante valorar un contenido.
     * Se registra la valoración tanto en el contenido como en el perfil del estudiante.
     *
     * @param contenido  Contenido a valorar.
     * @param puntuacion Puntuación dada (por ejemplo, de 1 a 5).
     * @param comentario Comentario opcional del estudiante.
     */
    public void valorarContenido(Contenido contenido, int puntuacion, String comentario) {
        Valoracion nuevaValoracion = new Valoracion(this, puntuacion, comentario);
        contenido.getValoraciones().agregar(nuevaValoracion);
        this.getValoraciones().agregar(nuevaValoracion);
    }
    /**
     * Une al estudiante a un grupo de estudio dado y actualiza ambos registros.
     *
     * @param grupo Grupo de estudio al que desea unirse.
     */
    public void unirseAGrupo(GrupoEstudio grupo) {
        this.gruposEstudio.agregar(grupo);
        grupo.agregarMiembro(this);
    }
    /**
     * Busca contenidos dentro del historial del estudiante que coincidan con los filtros dados.
     *
     * @param tema  Tema del contenido.
     * @param autor Autor del contenido.
     * @param tipo  Tipo del contenido.
     * @return Lista de contenidos que coinciden con los criterios.
     */
    public ListaEnlazada<Contenido> buscarContenido(String tema, String autor, String tipo) {
        ListaEnlazada<Contenido> resultados = new ListaEnlazada<>();
        NodoLista<Contenido> actual = this.getHistorialContenidos().getCabeza();

        while (actual != null) {
            Contenido c = actual.getDato();
            if (c.getTema().equalsIgnoreCase(tema)
                    && c.getAutor().equalsIgnoreCase(autor)
                    && c.getTipo().equalsIgnoreCase(tipo)) {
                resultados.agregar(c);
            }
            actual = actual.getSiguiente();
        }
        return resultados;
    }
    /**
     * Envía un mensaje a otro estudiante.
     *
     * @param destino Estudiante destinatario del mensaje.
     * @param mensaje Contenido textual del mensaje.
     */
    public void enviarMensaje(Estudiante destino, String mensaje) {
        Mensaje nuevoMensaje = new Mensaje(this, destino, mensaje, LocalDateTime.now());
        nuevoMensaje.enviar(); // Requiere implementación de lógica en clase Mensaje
    }
    /**
     * Publica un contenido y lo añade al historial del estudiante.
     *
     * @param contenido Contenido que se desea publicar.
     */
    public void publicarContenido(Contenido contenido) {
        GestorContenidos.getInstancia().agregarContenido(contenido);
        this.getHistorialContenidos().agregar(contenido);
        // Se puede extender para agregarlo al repositorio global de contenidos
    }

    /**
     * @return Cola de solicitudes de ayuda del estudiante.
     */
    public ColaPrioridad<SolicitudAyuda> getSolicitudesAyuda() { return solicitudesAyuda; }
    /**
     * @return Lista de grupos de estudio a los que pertenece el estudiante.
     */
    public ListaEnlazada<GrupoEstudio> getGruposEstudio() { return gruposEstudio; }
    /**
     * @param solicitudesAyuda Nueva cola de solicitudes de ayuda.
     */
    public void setSolicitudesAyuda(ColaPrioridad<SolicitudAyuda> solicitudesAyuda) {
        this.solicitudesAyuda = solicitudesAyuda;
    }
    /**
     * @param gruposEstudio Nueva lista de grupos de estudio.
     */
    public void setGruposEstudio(ListaEnlazada<GrupoEstudio> gruposEstudio) {
        this.gruposEstudio = gruposEstudio;
    }
}
