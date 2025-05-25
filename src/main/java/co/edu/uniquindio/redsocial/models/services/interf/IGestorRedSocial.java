package co.edu.uniquindio.redsocial.models.services.interf;

import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

/**
 * Interfaz para la gestión de operaciones relacionadas con la red social.
 * Incluye análisis de afinidades, caminos mínimos, comunidades y participación.
 */
public interface IGestorRedSocial {

    /**
     * Visualiza el grafo de conexiones entre usuarios.
     * Puede implementarse como impresión en consola o visualización gráfica.
     */
    void visualizarGrafoUsuarios();

    /**
     * Detecta comunidades dentro de la red de estudiantes.
     *
     * @return Lista de comunidades, cada una representada como una lista de estudiantes
     */
    ListaEnlazada<ListaEnlazada<Estudiante>> detectarComunidades();

    /**
     * Obtiene los estudiantes con mayor nivel de conexión en la red.
     *
     * @return Lista de estudiantes más conectados
     */
    ListaEnlazada<Estudiante> obtenerEstudiantesMasConectados();

    /**
     * Calcula los caminos más cortos entre dos usuarios por su ID.
     *
     * @param idOrigen ID del usuario origen
     * @param idDestino ID del usuario destino
     * @return Lista de cadenas que describen los caminos más cortos
     */
    ListaEnlazada<String> calcularCaminosMasCortos(String idOrigen, String idDestino);

    /**
     * Obtiene los niveles de participación de todos los usuarios.
     * Puede incluir métricas como cantidad de contenidos, valoraciones, etc.
     *
     * @return Lista con la descripción de la participación por usuario
     */
    ListaEnlazada<String> obtenerNivelesParticipacion();
}
