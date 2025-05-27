package co.edu.uniquindio.redsocial.models;

import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import java.util.UUID;

/**
 * Representa un hilo de chat entre dos estudiantes.
 */
public class Conversacion {

    private final String id;
    private final ListaEnlazada<Estudiante> participantes = new ListaEnlazada<>();
    private final ListaEnlazada<Mensaje> mensajes = new ListaEnlazada<>();

    /**
     * Crea una conversación entre dos estudiantes (origen y destino).
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
     * Stub para mensajes no leídos (puedes implementar según tu lógica).
     */
    public ListaEnlazada<Mensaje> getMensajesNoLeidosPara(Estudiante usuario) {
        return new ListaEnlazada<>();
    }

    /**
     * Estado online (stub).
     */
    public boolean estaOnline() {
        return true;
    }

    /**
     * Para posibles usos futuros: acceder a ambos participantes.
     */
    public ListaEnlazada<Estudiante> getParticipantes() {
        return participantes;
    }
}
