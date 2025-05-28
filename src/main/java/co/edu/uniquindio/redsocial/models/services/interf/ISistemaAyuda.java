package co.edu.uniquindio.redsocial.models.services.interf;

import co.edu.uniquindio.redsocial.models.SolicitudAyuda;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

/**
 * Interfaz que define los métodos para gestionar un sistema de ayuda
 * basado en solicitudes priorizadas por nivel de urgencia.
 * Permite agregar solicitudes de ayuda, atenderlas según su prioridad,
 * y filtrar solicitudes por tema específico.
 * Esta interfaz abstrae la lógica de manejo de solicitudes de ayuda
 * dentro de la plataforma, facilitando su implementación y uso.
 *
 * @author Daniel Jurado, Sebastian Torres y Juan Soto
 * @version 1.0
 * @since 2025-05-27
 */
public interface ISistemaAyuda {

    /**
     * Agrega una nueva solicitud de ayuda a la cola prioritaria.
     *
     * @param solicitud Solicitud de ayuda con su nivel de urgencia.
     */
    void agregarSolicitud(SolicitudAyuda solicitud);

    /**
     * Atiende la solicitud más urgente disponible.
     *
     * @return Solicitud de ayuda atendida o null si no hay solicitudes.
     */
    SolicitudAyuda atenderSolicitud();

    /**
     * Obtiene todas las solicitudes de ayuda relacionadas con un tema específico.
     *
     * @param tema Tema a filtrar.
     * @return Lista de solicitudes relacionadas con el tema.
     */
    ListaEnlazada<SolicitudAyuda> obtenerSolicitudesPorTema(String tema);
}
