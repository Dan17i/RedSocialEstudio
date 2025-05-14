package co.edu.uniquindio.redsocial.models.services.implement;

import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.structures.Grafo;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import co.edu.uniquindio.redsocial.models.structures.NodoGrafo;
import co.edu.uniquindio.redsocial.models.services.interf.IRedAfinidad;
/**
 * Clase que representa una red de afinidad entre estudiantes,
 * utilizando un grafo para establecer relaciones y sugerencias
 * de compañeros potenciales.
 *
 * Esta clase se encarga de analizar las relaciones y atributos de
 * los estudiantes para sugerir conexiones relevantes dentro de la red.
 *
 * @author Daniel Jurado
 * @author Sebastian Torres
 * @author Juan Soto
 * @since 2025-05-12
 */
public class RedAfinidad implements  IRedAfinidad{
    private Grafo<Estudiante> grafoEstudiantes;
    private static RedAfinidad instancia;
    /**
     * Constructor que inicializa la red con un grafo de estudiantes.
     *
     * @param grafoEstudiantes grafo que contiene los estudiantes y sus relaciones.
     */
    public RedAfinidad(Grafo<Estudiante> grafoEstudiantes) {
        this.grafoEstudiantes = grafoEstudiantes;
    }

    public static RedAfinidad getInstancia() {
        if (instancia==null){
            instancia = new RedAfinidad(new Grafo<>());

        }
        return instancia;
    }
    /**
     * Sugiere compañeros afines a un estudiante en base al número de intereses en común.
     *
     * @param estudiante estudiante para el cual se desean sugerencias.
     * @return una lista enlazada de estudiantes sugeridos.
     */
    public ListaEnlazada<Estudiante> sugerirCompaneros(Estudiante estudiante) {
        ListaEnlazada<Estudiante> sugerencias = new ListaEnlazada<>();

        NodoGrafo<Estudiante> nodoEstudiante = grafoEstudiantes.buscarNodo(estudiante);
        if (nodoEstudiante == null) {
            return sugerencias; // El estudiante no está en el grafo
        }

        ListaEnlazada<NodoGrafo<Estudiante>> todos = grafoEstudiantes.getNodos();
        ListaEnlazada<String> interesesUsuario = estudiante.getIntereses();

        for (int i = 0; i < todos.getTamanio(); i++) {
            Estudiante candidato = todos.obtener(i).getDato();

            if (!candidato.equals(estudiante)) {
                ListaEnlazada<String> interesesCandidato = candidato.getIntereses();
                int afinidad = contarCoincidencias(interesesUsuario, interesesCandidato);

                // Por ahora, sugerimos si tienen al menos 2 intereses en común
                if (afinidad >= 2) {
                    sugerencias.agregar(candidato);
                }
            }
        }

        return sugerencias;
    }

    /**
     * Cuenta cuántos elementos tiene en común entre dos listas de intereses.
     *
     * @param lista1 primera lista
     * @param lista2 segunda lista
     * @return número de coincidencias
     */
    private int contarCoincidencias(ListaEnlazada<String> lista1, ListaEnlazada<String> lista2) {
        int contador = 0;
        for (int i = 0; i < lista1.getTamanio(); i++) {
            String interes = lista1.obtener(i);
            for (int j = 0; j < lista2.getTamanio(); j++) {
                if (interes.equalsIgnoreCase(lista2.obtener(j))) {
                    contador++;
                    break;
                }
            }
        }
        return contador;



    }
}


