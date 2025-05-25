package co.edu.uniquindio.redsocial.models.services.implement;

import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.services.interf.IGestorRedSocial;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

public class GestorRedSocial implements IGestorRedSocial {
    /**
     *
     */
    @Override
    public void visualizarGrafoUsuarios() {

    }

    /**
     * @return
     */
    @Override
    public ListaEnlazada<ListaEnlazada<Estudiante>> detectarComunidades() {
        return null;
    }

    /**
     * @return
     */
    @Override
    public ListaEnlazada<Estudiante> obtenerEstudiantesMasConectados() {
        return null;
    }

    /**
     * @param idOrigen  ID del usuario origen
     * @param idDestino ID del usuario destino
     * @return
     */
    @Override
    public ListaEnlazada<String> calcularCaminosMasCortos(String idOrigen, String idDestino) {
        return null;
    }

    /**
     * @return
     */
    @Override
    public ListaEnlazada<String> obtenerNivelesParticipacion() {
        return null;
    }
}
