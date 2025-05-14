package co.edu.uniquindio.redsocial.models.services.interf;

import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

public interface IRedAfinidad {

    /**
     * Sugiere compañeros afines a un estudiante en base al número de intereses en común.
     *
     * @param estudiante estudiante para el cual se desean sugerencias.
     * @return una lista enlazada de estudiantes sugeridos.
     */
    ListaEnlazada<Estudiante> sugerirCompaneros(Estudiante estudiante);
}
