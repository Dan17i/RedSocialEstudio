package co.edu.uniquindio.redsocial.models.services.interf;

import co.edu.uniquindio.redsocial.models.Contenido;
import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

/**
 * Interfaz para el sistema de recomendaciones.
 * Define los métodos necesarios para recomendar contenidos y compañeros de estudio.
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
