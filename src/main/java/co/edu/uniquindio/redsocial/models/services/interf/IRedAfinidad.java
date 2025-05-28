package co.edu.uniquindio.redsocial.models.services.interf;

import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
/**
 * Interfaz que define el comportamiento de un sistema de
 * recomendación de compañeros afines basado en intereses compartidos.
 * <p>
 * Proporciona funcionalidades para sugerir estudiantes que tengan afinidad
 * con un estudiante dado, facilitando la creación de conexiones o grupos de estudio.
 * </p>
 *
 * @author Daniel Jurado, Sebastian Torres y Juan Soto
 * @since 2025-05-27
 */
public interface IRedAfinidad {

    /**
     * Sugiere compañeros afines a un estudiante en base al número de intereses en común.
     *
     * @param estudiante estudiante para el cual se desean sugerencias.
     * @return una lista enlazada de estudiantes sugeridos.
     */
    ListaEnlazada<Estudiante> sugerirCompaneros(Estudiante estudiante);
}
