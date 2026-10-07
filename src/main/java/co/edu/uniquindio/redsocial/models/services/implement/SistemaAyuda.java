package co.edu.uniquindio.redsocial.models.services.implement;

import co.edu.uniquindio.redsocial.models.Enums.EstadoSolicitud;
import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.SolicitudAyuda;
import co.edu.uniquindio.redsocial.models.services.interf.ISistemaAyuda;
import co.edu.uniquindio.redsocial.models.structures.ColaPrioridad;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import co.edu.uniquindio.redsocial.models.structures.NodoPrioridad;
/**
 * Implementación del sistema de ayuda académica de la red social educativa.
 * Esta clase gestiona una cola de prioridad de solicitudes de ayuda hechas por los estudiantes.
 * Permite agregar nuevas solicitudes, atender las más urgentes, y filtrar solicitudes por tema.
 * Funcionalidades principales:
 * <ul>
 *   <li>Agregar solicitudes de ayuda con una prioridad basada en su nivel de urgencia.</li>
 *   <li>Atender solicitudes según su prioridad (urgencia).</li>
 *   <li>Obtener todas las solicitudes que coincidan con un tema específico.</li>
 * </ul>
 *
 * Esta clase implementa la interfaz {@link ISistemaAyuda} y se apoya en estructuras personalizadas
 * como {@link ColaPrioridad} y {@link ListaEnlazada}.
 *
 * @author Daniel Jurado, Sebastian Torres y Juan Soto
 * @version 1.0
 * @since 2025-05-27
 */
public class SistemaAyuda implements ISistemaAyuda {
    /**
     * Cola de prioridad que almacena todas las solicitudes de ayuda registradas en el sistema.
     * Las solicitudes se gestionan según su nivel de urgencia, de modo que las más urgentes se atienden primero.
     */
    private final ColaPrioridad<SolicitudAyuda> solicitudesGlobales;
    /**
     * Constructor del sistema de ayuda.
     * Inicializa la cola de prioridad que almacenará las solicitudes de ayuda.
     */
    public SistemaAyuda() {
        this.solicitudesGlobales = new ColaPrioridad<>();
    }
    /**
     * Agrega una nueva solicitud de ayuda a la cola de prioridad.
     * La prioridad de la solicitud se determina por su nivel de urgencia.
     *
     * @param solicitud La solicitud de ayuda a registrar en el sistema.
     */
    public synchronized void agregarSolicitud(SolicitudAyuda solicitud) {
        solicitudesGlobales.encolar(solicitud, solicitud.getUrgencia());
    }
    /**
     * Atiende la solicitud de ayuda más urgente disponible.
     * Extrae y retorna la solicitud con mayor prioridad (mayor urgencia) de la cola.
     *
     * @return La solicitud de ayuda más urgente, o {@code null} si no hay solicitudes en la cola.
     */
    public synchronized SolicitudAyuda atenderSolicitud() {
        return solicitudesGlobales.desencolar();
    }

    /**
     * Lista las solicitudes pendientes en el orden en que se atenderían (la más urgente primero).
     *
     * @return Nueva lista con las solicitudes pendientes.
     */
    public synchronized ListaEnlazada<SolicitudAyuda> obtenerSolicitudesPendientes() {
        return solicitudesGlobales.aLista();
    }

    /**
     * Un estudiante toma una solicitud concreta para ayudar. La solicitud sale de la cola global
     * (en la cola personal de quien la pidió sigue visible, con su nuevo estado) y queda en estado {@code EN_PROGRESO}.
     *
     * @param idSolicitud Identificador de la solicitud a atender.
     * @param ayudante    Estudiante que ofrece la ayuda (no puede ser quien la solicitó).
     * @return La solicitud atendida, o {@code null} si ya no estaba pendiente.
     * @throws IllegalArgumentException si el ayudante es nulo o es el mismo solicitante.
     */
    public synchronized SolicitudAyuda atenderSolicitud(String idSolicitud, Estudiante ayudante) {
        if (ayudante == null) {
            throw new IllegalArgumentException("El estudiante que ayuda no puede ser nulo");
        }
        for (SolicitudAyuda solicitud : solicitudesGlobales.aLista()) {
            if (solicitud.getId().equals(idSolicitud)) {
                if (solicitud.getEstudiante().equals(ayudante)) {
                    throw new IllegalArgumentException("No puedes atender tu propia solicitud");
                }
                solicitudesGlobales.eliminar(solicitud);
                solicitud.setEstado(EstadoSolicitud.EN_PROGRESO);
                return solicitud;
            }
        }
        return null;
    }

    /**
     * Filtra y retorna todas las solicitudes de ayuda cuyo tema coincida con el proporcionado.
     * Este método recorre la cola de prioridad interna y extrae aquellas solicitudes
     * cuyo campo de tema coincide (ignorando mayúsculas/minúsculas) con el argumento dado.
     *
     * @param tema Tema por el cual se desea filtrar las solicitudes de ayuda.
     * @return Una lista enlazada con todas las solicitudes que coinciden con el tema dado.
     */
    @Override
    public ListaEnlazada<SolicitudAyuda> obtenerSolicitudesPorTema(String tema) {
        ListaEnlazada<SolicitudAyuda> resultado = new ListaEnlazada<>();

        ListaEnlazada<NodoPrioridad<SolicitudAyuda>> nodos = solicitudesGlobales.getElementos();
        for (int i = 0; i < nodos.getTamanio(); i++) {
            NodoPrioridad<SolicitudAyuda> nodo = nodos.obtener(i);
            SolicitudAyuda solicitud = nodo.getDato();
            if (solicitud.getTema().equalsIgnoreCase(tema)) {
                resultado.agregar(solicitud);
            }
        }

        return resultado;
    }
}
