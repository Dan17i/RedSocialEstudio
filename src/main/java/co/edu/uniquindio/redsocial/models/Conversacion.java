package co.edu.uniquindio.redsocial.models;

import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

import java.util.UUID;

/**
 * Representa un hilo de chat entre uno o varios estudiantes.
 */
public class Conversacion {

    private final String id;
    private final String nombre; // Nombre visible (p. ej. nombre del compañero o nombre del grupo)
    private final ListaEnlazada<Mensaje> mensajes = new ListaEnlazada<>();

    public Conversacion(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre de la conversación no puede estar vacío");
        }
        this.id     = UUID.randomUUID().toString();
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public ListaEnlazada<Mensaje> getMensajes() {
        return mensajes;
    }

    public void agregarMensaje(Mensaje m) {
        mensajes.agregar(m);
    }

    public Mensaje getUltimoMensaje() {
        int size = mensajes.getTamanio();
        return size > 0 ? mensajes.obtener(size - 1) : null;
    }

    /**
     * Stub para mensajes no leídos (implementa según tu lógica de lectura).
     */
    public ListaEnlazada<Mensaje> getMensajesNoLeidosPara(Estudiante usuario) {
        return new ListaEnlazada<>();
    }

    /**
     * Indica si la conversación está “online”.
     * En un futuro podrías consultar un estado real.
     */
    public boolean estaOnline() {
        return true;
    }
}
