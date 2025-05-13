package co.edu.uniquindio.redsocial.models;

public class SistemaAyuda {

    private ColaPrioridad<SolicitudAyuda> solicitudesGlobales;

    public SistemaAyuda() {
        this.solicitudesGlobales = new ColaPrioridad<>();
    }

    public void agregarSolicitud(SolicitudAyuda solicitud) {
        solicitudesGlobales.encolar(solicitud, solicitud.getUrgencia());
    }

    public SolicitudAyuda atenderSolicitud() {
        return solicitudesGlobales.desencolar();
    }

    public ListaEnlazada<SolicitudAyuda> obtenerSolicitudesPorTema(String tema) {
        /** TODO : Implementar lógica para filtrar soluciones por tema*/
        return null;
    }
}

