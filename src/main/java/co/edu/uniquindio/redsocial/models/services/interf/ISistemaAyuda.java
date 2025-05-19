package co.edu.uniquindio.redsocial.models.services.interf;

import co.edu.uniquindio.redsocial.models.structures.ColaPrioridad;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

/**
 * Interfaz para el manejo del sistema de ayuda por solicitudes priorizadas.
 */
public interface ISistemaAyuda {

    /**
     * Agrega una nueva solicitud de ayuda a la cola prioritaria.
     *
     * @param solicitud Solicitud de ayuda con su nivel de urgencia.
     */
    void agregarSolicitud(ColaPrioridad.SolicitudAyuda solicitud);

    /**
     * Atiende la solicitud más urgente disponible.
     *
     * @return Solicitud de ayuda atendida o null si no hay solicitudes.
     */
    ColaPrioridad.SolicitudAyuda atenderSolicitud();

    /**
     * Obtiene todas las solicitudes de ayuda relacionadas con un tema específico.
     *
     * @param tema Tema a filtrar.
     * @return Lista de solicitudes relacionadas con el tema.
     */
    ListaEnlazada<ColaPrioridad.SolicitudAyuda> obtenerSolicitudesPorTema(String tema);
}
