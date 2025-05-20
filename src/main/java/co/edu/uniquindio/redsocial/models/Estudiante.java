package co.edu.uniquindio.redsocial.models;

import co.edu.uniquindio.redsocial.models.services.implement.GestorContenidos;
import co.edu.uniquindio.redsocial.models.structures.ColaPrioridad;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import co.edu.uniquindio.redsocial.models.structures.NodoLista;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Clase que representa a un estudiante dentro de la red social educativa.
 * Hereda de {@link Usuario} y tiene funcionalidades adicionales como unirse a grupos,
 * valorar contenido, buscar contenidos, publicar y enviar mensajes.
 *
 * @author Daniel Jurado, Sebastian Torres, Juan Soto
 * @since 2025-04-02
 */
public class Estudiante extends Usuario {

    private ColaPrioridad<ColaPrioridad.SolicitudAyuda> solicitudesAyuda;
    private ListaEnlazada<GrupoEstudio> gruposEstudio;

    /**
     * Constructor del estudiante.
     *
     * @param id                 Identificador único del estudiante.
     * @param nombre             Nombre del estudiante.
     * @param email              Correo electrónico del estudiante.
     * @param contrasenia        Contraseña del estudiante.
     * @param intereses          Lista de intereses académicos.
     * @param historialContenidos Lista de contenidos publicados o consumidos.
     * @param valoraciones       Lista de valoraciones realizadas.
     * @param solicitudesAyuda   Cola de solicitudes de ayuda priorizadas. Si es null, se inicializa vacía.
     * @param gruposEstudio      Lista de grupos de estudio a los que pertenece. Si es null, se inicializa vacía.
     */
    public Estudiante(String id, String nombre, String email, String contrasenia,
                      ListaEnlazada<String> intereses, ListaEnlazada<Contenido> historialContenidos,
                      ListaEnlazada<Valoracion> valoraciones,
                      ColaPrioridad<ColaPrioridad.SolicitudAyuda> solicitudesAyuda,
                      ListaEnlazada<GrupoEstudio> gruposEstudio) {
        super(id, nombre, email, contrasenia, intereses, historialContenidos, valoraciones);
        this.solicitudesAyuda = (solicitudesAyuda != null) ? solicitudesAyuda : new ColaPrioridad<>();
        this.gruposEstudio = (gruposEstudio != null) ? gruposEstudio : new ListaEnlazada<>();
    }

    /**
     * Permite al estudiante valorar un contenido.
     * Se registra la valoración tanto en el contenido como en el perfil del estudiante.
     *
     * @param contenido  Contenido a valorar.
     * @param puntuacion Puntuación dada (por ejemplo, de 1 a 5).
     * @param comentario Comentario opcional del estudiante.
     * @throws IllegalArgumentException si contenido es null o puntuacion no está en rango esperado.
     */
    public void valorarContenido(Contenido contenido, int puntuacion, String comentario) {
        if (contenido == null) {
            throw new IllegalArgumentException("El contenido no puede ser null");
        }
        if (puntuacion < 1 || puntuacion > 5) {
            throw new IllegalArgumentException("La puntuación debe estar entre 1 y 5");
        }
        Valoracion nuevaValoracion = new Valoracion(this, puntuacion, comentario);
        contenido.getValoraciones().agregar(nuevaValoracion);
        this.getValoraciones().agregar(nuevaValoracion);
    }

    /**
     * Une al estudiante a un grupo de estudio dado y actualiza ambos registros.
     *
     * @param grupo Grupo de estudio al que desea unirse. No puede ser null.
     * @throws IllegalArgumentException si grupo es null.
     */
    public void unirseAGrupo(GrupoEstudio grupo) {
        if (grupo == null) {
            throw new IllegalArgumentException("El grupo no puede ser null");
        }
        // Solo llamas a agregarMiembro, que se encarga de todo
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
                    && c.getAutor().getNombre().equalsIgnoreCase(autor)
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
     * @throws IllegalArgumentException si destino es null o mensaje es null o vacío.
     */
    public void enviarMensaje(Estudiante destino, String mensaje) {
        if (destino == null) {
            throw new IllegalArgumentException("El destinatario no puede ser null");
        }
        if (mensaje == null || mensaje.isEmpty()) {
            throw new IllegalArgumentException("El mensaje no puede estar vacío");
        }
        Mensaje nuevoMensaje = new Mensaje(this, destino, mensaje, LocalDateTime.now());
        nuevoMensaje.enviar(); // Implementa la lógica en la clase Mensaje
    }

    /**
     * Publica un contenido y lo añade al historial del estudiante.
     *
     * @param contenido Contenido que se desea publicar.
     * @throws IllegalArgumentException si contenido es null.
     */
    public void publicarContenido(Contenido contenido) {
        if (contenido == null) {
            throw new IllegalArgumentException("El contenido no puede ser null");
        }
        GestorContenidos.getInstancia().agregarContenido(contenido);
        this.getHistorialContenidos().agregar(contenido);
    }

    // Getters y setters

    public ListaEnlazada<GrupoEstudio> getGrupos() {
        return gruposEstudio;
    }


    public ColaPrioridad<ColaPrioridad.SolicitudAyuda> getSolicitudesAyuda() {
        return solicitudesAyuda;
    }

    public void setSolicitudesAyuda(ColaPrioridad<ColaPrioridad.SolicitudAyuda> solicitudesAyuda) {
        this.solicitudesAyuda = solicitudesAyuda != null ? solicitudesAyuda : new ColaPrioridad<>();
    }

    public ListaEnlazada<GrupoEstudio> getGruposEstudio() {
        return gruposEstudio;
    }

    public void setGruposEstudio(ListaEnlazada<GrupoEstudio> gruposEstudio) {
        this.gruposEstudio = gruposEstudio != null ? gruposEstudio : new ListaEnlazada<>();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Estudiante)) return false;
        Estudiante that = (Estudiante) obj;
        return this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
