package co.edu.uniquindio.redsocial.models.services.interf;

import co.edu.uniquindio.redsocial.models.Contenido;
import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

/**
 * Interfaz que define los métodos para el sistema de recomendaciones
 * dentro de la plataforma educativa.
 * Proporciona funcionalidades para recomendar contenidos relevantes
 * y compañeros de estudio afines a un estudiante según sus intereses
 * y conexiones.
 * Esta interfaz facilita la implementación de estrategias de recomendación
 * personalizadas y adaptativas.
 *
 * @author Daniel Jurado, Sebastian Torres y Juan Soto
 * @version 1.0
 * @since 2025-05-27
 */

public interface ISistemaRecomendaciones {

    /**
     * Recomienda contenidos basados en los intereses del estudiante.
     *
     * @param estudiante Estudiante que recibirá recomendaciones.
     * @return Lista enlazada de contenidos recomendados.
     */
    ListaEnlazada<Contenido> recomendarCOntenidos(Estudiante estudiante);

    /**
     * Recomienda compañeros de estudio utilizando la red de afinidad.
     *
     * @param estudiante Estudiante que recibirá sugerencias de compañeros.
     * @return Lista enlazada de estudiantes sugeridos.
     */
    ListaEnlazada<Estudiante> recomendarCompanieros(Estudiante estudiante);
}
