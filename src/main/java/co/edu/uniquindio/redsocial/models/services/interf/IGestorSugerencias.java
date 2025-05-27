package co.edu.uniquindio.redsocial.models.services.interf;

import co.edu.uniquindio.redsocial.models.Estudiante;
import java.util.List;

/**
 * Interfaz para definir el comportamiento del gestor de sugerencias de amigos.
 */
public interface IGestorSugerencias {

    /**
     * Genera una lista de sugerencias de amigos (compañeros de estudio)
     * para el estudiante, basada en amigos de amigos.
     *
     * @param estudiante Estudiante al que se le quiere sugerir amigos.
     * @return Lista de estudiantes sugeridos.
     */
    List<Estudiante> sugerirAmigos(Estudiante estudiante);
}
