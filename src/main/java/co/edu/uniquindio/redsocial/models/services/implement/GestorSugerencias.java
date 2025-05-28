package co.edu.uniquindio.redsocial.models.services.implement;

import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.services.interf.IGestorSugerencias;
import co.edu.uniquindio.redsocial.models.structures.GrafoNoDirigido;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

import java.util.*;

public class GestorSugerencias implements IGestorSugerencias {

    private final GrafoNoDirigido<Estudiante> grafo;

    public GestorSugerencias(GrafoNoDirigido<Estudiante> grafo) {
        this.grafo = grafo;
    }

    /**
     * Genera una lista de sugerencias de compañeros de estudio para un estudiante.
     * Basado en amigos de amigos que tengan intereses en común.
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
