package co.edu.uniquindio.redsocial.models.services.interf;

import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

/**
 * Interfaz para la gestión de operaciones relacionadas con la red social.
 * Incluye análisis de afinidades, caminos mínimos, comunidades y participación.
 *
 */
public interface IGestorRedSocial {
    /**
     * Detecta y retorna las comunidades de estudiantes dentro del grafo social.
     *
     * @return Una lista enlazada donde cada elemento es otra lista enlazada
     *         de estudiantes que pertenecen a la misma comunidad.
     */
    ListaEnlazada<ListaEnlazada<Estudiante>> detectarComunidades();
    /**
     * Visualiza gráficamente el grafo de usuarios de la red social.
     *
     * Implementa la representación visual de las conexiones y nodos
     * para facilitar el análisis de la red.
     */
    void visualizarGrafoUsuarios(); // O el tipo de retorno correcto si no es void
    /**
     * Obtiene una lista con los estudiantes que tienen mayor número de conexiones
     * o interacciones dentro de la red social.
     *
     * @return Lista enlazada con los estudiantes más conectados.
     */
    ListaEnlazada<Estudiante> obtenerEstudiantesMasConectados();
    /**
     * Calcula y retorna los caminos más cortos entre dos usuarios identificados
     * por sus IDs dentro del grafo social.
     *
     * @param inicio  ID del usuario de origen.
     * @param destino ID del usuario destino.
     * @return Lista enlazada con representaciones (por ejemplo, IDs o nombres)
     *         de los caminos más cortos entre ambos usuarios.
     */
    ListaEnlazada<String> calcularCaminosMasCortos(String inicio, String destino);
    /**
     * Obtiene los niveles de participación de los usuarios en la red social,
     * que pueden representar categorías, rangos o estadísticas de actividad.
     *
     * @return Lista enlazada con descripciones o valores que indican
     *         los niveles de participación.
     */
    ListaEnlazada<String> obtenerNivelesParticipacion();
}
