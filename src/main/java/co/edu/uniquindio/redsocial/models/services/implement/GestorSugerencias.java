package co.edu.uniquindio.redsocial.models.services.implement;

import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.services.interf.IGestorSugerencias;
import co.edu.uniquindio.redsocial.models.structures.GrafoNoDirigido;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

import java.util.*;
/**
 * Implementación del servicio de sugerencias de compañeros de estudio.
 * Esta clase utiliza un grafo no dirigido de estudiantes para generar sugerencias
 * de nuevos amigos basándose en el criterio de amigos con intereses en común.
 * Funcionalidades principales:
 * - Sugerir nuevos compañeros de estudio.
 * - Comparar intereses entre estudiantes para determinar afinidad.
 * Esta clase forma parte del módulo de servicios de la red social educativa.
 *
 * @author Daniel Jurado, Sebastian Torres y Juan Soto
 * @since 2025-05-27
 */
public class GestorSugerencias implements IGestorSugerencias {

    private final GrafoNoDirigido<Estudiante> grafo;

    /**
     * Constructor que inicializa el gestor de sugerencias con un grafo de estudiantes.
     *
     * @param grafo Grafo no dirigido que representa la red de conexiones entre estudiantes.
     */
    public GestorSugerencias(GrafoNoDirigido<Estudiante> grafo) {
        this.grafo = grafo;
    }

    /**
     * Genera una lista de sugerencias de posibles amigos para el estudiante dado.
     * Las sugerencias se basan en el criterio de "amigos de amigos" que no sean ya amigos directos,
     * y que además compartan al menos un interés en común con el estudiante.
     *
     * @param estudiante Estudiante para el cual se desean generar sugerencias de nuevos amigos.
     * @return Lista de estudiantes sugeridos como posibles nuevos amigos.
     */

    @Override
    public List<Estudiante> sugerirAmigos(Estudiante estudiante) {
        Set<Estudiante> sugerencias = new HashSet<>();
        // Vecinos directos (amigos actuales)
        List<Estudiante> amigos = grafo.obtenerVecinos(estudiante);

        // Recorrer amigos de amigos
        for (Estudiante amigo : amigos) {
            for (Estudiante amigoDeAmigo : grafo.obtenerVecinos(amigo)) {
                if (!amigoDeAmigo.equals(estudiante) && !amigos.contains(amigoDeAmigo)) {
                    if (tienenInteresesEnComun(estudiante, amigoDeAmigo)) {
                        sugerencias.add(amigoDeAmigo);
                    }
                }
            }
        }

        return new ArrayList<>(sugerencias);
    }

    /**
     * Verifica si dos estudiantes comparten al menos un interés en común.
     * Compara los intereses de ambos estudiantes utilizando una comparación insensible a mayúsculas.
     *
     * @param e1 Primer estudiante.
     * @param e2 Segundo estudiante.
     * @return true si al menos un interés coincide entre ambos estudiantes, false en caso contrario.
     */

    private boolean tienenInteresesEnComun(Estudiante e1, Estudiante e2) {
        ListaEnlazada<String> intereses1 = e1.getIntereses();
        ListaEnlazada<String> intereses2 = e2.getIntereses();

        for (int i = 0; i < intereses1.getTamanio(); i++) {
            String interes = intereses1.obtener(i);
            for (int j = 0; j < intereses2.getTamanio(); j++) {
                if (interes.equalsIgnoreCase(intereses2.obtener(j))) {
                    return true;
                }
            }
        }
        return false;
    }
}
