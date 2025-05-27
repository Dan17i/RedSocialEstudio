package co.edu.uniquindio.redsocial.models;

import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import java.util.UUID;

/**
 * Representa un hilo de conversación (chat) entre dos {@link Estudiante}s.
 * Permite almacenar los mensajes intercambiados entre ambos participantes
 * y gestionar funcionalidades básicas como obtener el último mensaje,
 * los mensajes no leídos, o el nombre visible del otro participante.
 *
 * @author Daniel Jurado
 * @author Sebastian Torres
 * @author Juan Soto
 * @since 2025-05-24
 */
public class Conversacion {
    /**
     * Identificador único de la conversación.
     */
    private final String id;
    /**
     * Lista de participantes (siempre dos: origen y destino).
     */
    private final ListaEnlazada<Estudiante> participantes = new ListaEnlazada<>();
    /**
     * Lista de mensajes que se han enviado en la conversación.
     */
    private final ListaEnlazada<Mensaje> mensajes = new ListaEnlazada<>();

    /**
     * Crea una conversación entre dos estudiantes (origen y destino).
     *
     * @param origen  Estudiante que inicia la conversación.
     * @param destino Estudiante con quien se inicia la conversación.
     * @throws IllegalArgumentException si alguno de los participantes es {@code null}.
     */
    public Conversacion(Estudiante origen, Estudiante destino) {
        if (origen == null || destino == null) {
            throw new IllegalArgumentException("Los participantes no pueden ser null");
        }
        this.id = UUID.randomUUID().toString();
        participantes.agregar(origen);
        participantes.agregar(destino);
    }

    public String getId() {
        return id;
    }

    /**
     * Devuelve el nombre que debe mostrarse al usuarioActual,
     * es decir, el nombre del otro participante.
     */
    public String getNombrePara(Estudiante usuarioActual) {
        for (int i = 0; i < participantes.getTamanio(); i++) {
            Estudiante e = participantes.obtener(i);
            if (!e.getId().equals(usuarioActual.getId())) {
                return e.getNombre();
            }
        }
        // Fallback si algo falla:
        return "Conversación";
    }
    /**
     * Obtiene la lista de mensajes asociados a esta conversación.
     *
     * @return Lista enlazada de mensajes.
     */
    public ListaEnlazada<Mensaje> getMensajes() {
        return mensajes;
    }
    /**
     * Agrega un nuevo mensaje a la conversación.
     *
     * @param m Mensaje a agregar.
     */
    public void agregarMensaje(Mensaje m) {
        mensajes.agregar(m);
    }
    /**
     * Obtiene el último mensaje enviado en la conversación.
     *
     * @return Último mensaje o {@code null} si no hay mensajes.
     */
    public Mensaje getUltimoMensaje() {
        int size = mensajes.getTamanio();
        return size > 0 ? mensajes.obtener(size - 1) : null;
    }
    /**
     * Devuelve una lista de mensajes no leídos para un usuario dado.
     * Actualmente implementado como stub (vacío).
     *
     * @param usuario Estudiante para el cual se consultan los mensajes no leídos.
     * @return Lista vacía (stub).
     */
    public ListaEnlazada<Mensaje> getMensajesNoLeidosPara(Estudiante usuario) {
        return new ListaEnlazada<>();
    }

    /**
     * Indica si la conversación está activa u online.
     * Actualmente es un stub que siempre devuelve {@code true}.
     *
     * @return {@code true} si la conversación está online.
     */
    public boolean estaOnline() {
        return true;
    }

    /**
     * Obtiene la lista de participantes en la conversación.
     * Útil para futuras funcionalidades de gestión de usuarios.
     *
     * @return Lista enlazada con los dos participantes.
     */
    public ListaEnlazada<Estudiante> getParticipantes() {
        return participantes;
    }
}
