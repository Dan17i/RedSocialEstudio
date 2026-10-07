package co.edu.uniquindio.redsocial.models.services.interf;

import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

/**
 * Interfaz para definir el comportamiento del gestor de sugerencias de amigos.
 * @author Daniel Jurado, Sebastia Torres y juan Soto
 * @since 2025-05-27
 */
public interface IGestorSugerencias {

    /**
     * Genera una lista de sugerencias de amigos (compañeros de estudio)
     * para el estudiante, basada en amigos de amigos.
     *
     * @param estudiante Estudiante al que se le quiere sugerir amigos.
     * @return Lista de estudiantes sugeridos.
     */
    ListaEnlazada<Estudiante> sugerirAmigos(Estudiante estudiante);
}
