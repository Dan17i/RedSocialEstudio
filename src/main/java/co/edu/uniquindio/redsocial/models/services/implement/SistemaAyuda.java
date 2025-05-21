package co.edu.uniquindio.redsocial.models.services.implement;

import co.edu.uniquindio.redsocial.models.SolicitudAyuda;
import co.edu.uniquindio.redsocial.models.services.interf.ISistemaAyuda;
import co.edu.uniquindio.redsocial.models.structures.ColaPrioridad;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import co.edu.uniquindio.redsocial.models.structures.NodoPrioridad;

public class SistemaAyuda implements ISistemaAyuda {

    private final ColaPrioridad<SolicitudAyuda> solicitudesGlobales;

    public SistemaAyuda() {
        this.solicitudesGlobales = new ColaPrioridad<>();
    }

    public void agregarSolicitud(SolicitudAyuda solicitud) {
        solicitudesGlobales.encolar(solicitud, solicitud.getUrgencia());
    }

    public SolicitudAyuda atenderSolicitud() {
        return solicitudesGlobales.desencolar();
    }

    /**
     * Filtra y retorna todas las solicitudes de ayuda cuyo tema coincida con el proporcionado.
     *
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
