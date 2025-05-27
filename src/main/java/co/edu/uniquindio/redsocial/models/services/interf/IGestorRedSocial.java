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
    ListaEnlazada<ListaEnlazada<Estudiante>> detectarComunidades();
    void visualizarGrafoUsuarios(); // O el tipo de retorno correcto si no es void
    ListaEnlazada<Estudiante> obtenerEstudiantesMasConectados();
    ListaEnlazada<String> calcularCaminosMasCortos(String inicio, String destino);
    ListaEnlazada<String> obtenerNivelesParticipacion();
}
