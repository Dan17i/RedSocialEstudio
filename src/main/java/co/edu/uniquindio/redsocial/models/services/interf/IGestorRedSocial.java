package co.edu.uniquindio.redsocial.models.services.interf;

import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

import java.util.Map;
import java.util.Set;

/**
 * Interfaz para la gestión de operaciones relacionadas con la red social.
 * Incluye análisis de afinidades, caminos mínimos, comunidades y participación.
 *
 */
public interface IGestorRedSocial {

    /**
     * Registra un estudiante en la red social.
     *
     * @param estudiante El estudiante a registrar.
     */
    void registrarEstudiante(Estudiante estudiante);

    /**
     * Crea una conexión de amistad entre dos estudiantes con un valor de afinidad.
     *
     * @param e1       Primer estudiante.
     * @param e2       Segundo estudiante.
     * @param afinidad Valor numérico que representa la afinidad o peso de la relación.
     */
    void conectarEstudiantes(Estudiante e1, Estudiante e2, double afinidad);

    /**
     * Elimina un estudiante de la red junto con todas sus conexiones.
     *
     * @param estudiante El estudiante a eliminar.
     * @return {@code true} si el estudiante fue eliminado correctamente, de lo contrario {@code false}.
     */
    boolean eliminarEstudiante(Estudiante estudiante);

    /**
     * Elimina una conexión (amistad) entre dos estudiantes.
     *
     * @param e1 Primer estudiante.
     * @param e2 Segundo estudiante.
     * @return {@code true} si la conexión fue eliminada correctamente, de lo contrario {@code false}.
     */
    boolean eliminarConexion(Estudiante e1, Estudiante e2);

    /**
     * Obtiene la ruta más corta (camino mínimo) entre dos estudiantes, si existe.
     *
     * @param origen  Estudiante de origen.
     * @param destino Estudiante de destino.
     * @return Lista enlazada con los estudiantes que conforman la ruta más corta.
     */
    ListaEnlazada<Estudiante> obtenerRutaEntre(Estudiante origen, Estudiante destino);

    /**
     * Sugiere posibles amigos a un estudiante, basados en comunidades o conexiones indirectas.
     *
     * @param estudiante Estudiante al que se le quieren sugerir amigos.
     * @return Lista de estudiantes sugeridos como nuevos amigos.
     */
    ListaEnlazada<Estudiante> sugerirAmigos(Estudiante estudiante);

    /**
     * Obtiene todos los estudiantes registrados en la red.
     *
     * @return Conjunto de estudiantes existentes en la red.
     */
    Set<Estudiante> listarEstudiantes();

    /**
     * Obtiene los amigos directos (conectados) de un estudiante.
     *
     * @param estudiante Estudiante del cual se consultan sus amigos.
     * @return Mapa con estudiantes amigos como clave y su afinidad como valor.
     */
    Map<Estudiante, Double> obtenerAmigosDe(Estudiante estudiante);

}
