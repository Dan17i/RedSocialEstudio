package co.edu.uniquindio.redsocial.models.services.implement;

import co.edu.uniquindio.redsocial.models.structures.ColaPrioridad;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

public class SistemaAyuda {

    private ColaPrioridad<ColaPrioridad.SolicitudAyuda> solicitudesGlobales;

    public SistemaAyuda() {
        this.solicitudesGlobales = new ColaPrioridad<>();
    }

    public void agregarSolicitud(ColaPrioridad.SolicitudAyuda solicitud) {
        solicitudesGlobales.encolar(solicitud, solicitud.getUrgencia());
    }

    public ColaPrioridad.SolicitudAyuda atenderSolicitud() {
        return solicitudesGlobales.desencolar();
    }

    public ListaEnlazada<ColaPrioridad.SolicitudAyuda> obtenerSolicitudesPorTema(String tema) {
        /** TODO : Implementar lógica para filtrar soluciones por tema*/
        return null;
    }
}

