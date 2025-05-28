package co.edu.uniquindio.redsocial.models.services.implement;

import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.structures.GrafoImpl;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import co.edu.uniquindio.redsocial.models.structures.NodoGrafo;
import co.edu.uniquindio.redsocial.models.services.interf.IRedAfinidad;
import java.util.Set;
import java.util.HashSet;

import co.edu.uniquindio.redsocial.models.Valoracion;
import co.edu.uniquindio.redsocial.models.GrupoEstudio;
import co.edu.uniquindio.redsocial.models.Contenido;
import co.edu.uniquindio.redsocial.models.Estudiante;


import java.util.HashSet;
import java.util.Set;

/**
 * Clase que representa una red de afinidad entre estudiantes,
 * utilizando un grafo para establecer relaciones y sugerencias
 * de compañeros potenciales.
 *
 * Esta clase se encarga de analizar las relaciones y atributos de
 * los estudiantes para sugerir conexiones relevantes dentro de la red.
 * Utiliza el patrón Singleton.
 *
 * @author Daniel Jurado
 * @author Sebastian Torres
 * @author Juan Soto
 * @since 2025-05-12
 */

public class RedAfinidad implements IRedAfinidad {
    public final GrafoImpl<Estudiante> grafoEstudiantes;
    private static RedAfinidad instancia;

    /**
     * Constructor privado para garantizar el patrón Singleton.
     *
     * @param grafoEstudiantes grafo que contiene los estudiantes y sus relaciones.
     */
    private RedAfinidad(GrafoImpl<Estudiante> grafoEstudiantes) {
        this.grafoEstudiantes = grafoEstudiantes;
    }

    /**
     * Devuelve la instancia única de RedAfinidad (patrón Singleton).
     *
     * @return instancia única de RedAfinidad.
     */
    public static RedAfinidad getInstancia() {
        if (instancia == null) {
            instancia = new RedAfinidad(new GrafoImpl<>());
        }
        return instancia;
    }

    /**
     * Agrega un estudiante a la red de afinidad.
     *
     * @param estudiante el estudiante a agregar.
     */
    public void agregarEstudiante(Estudiante estudiante) {
        grafoEstudiantes.agregarNodo(estudiante);
    }

    /**
     * Sugiere compañeros afines a un estudiante en base al número de intereses en común.
     *
     * @param estudiante estudiante para el cual se desean sugerencias.
     * @return una lista enlazada de estudiantes sugeridos.
     */
    public ListaEnlazada<Estudiante> sugerirCompaneros(Estudiante estudiante) {
        ListaEnlazada<Estudiante> sugerencias = new ListaEnlazada<>();

        if (estudiante == null || estudiante.getIntereses() == null) {
            return sugerencias;
        }

        NodoGrafo<Estudiante> nodoEstudiante = grafoEstudiantes.buscarNodo(estudiante);
        if (nodoEstudiante == null) {
            return sugerencias; // El estudiante no está en el grafo
        }

        ListaEnlazada<NodoGrafo<Estudiante>> todos = grafoEstudiantes.getNodos();

        for (int i = 0; i < todos.getTamanio(); i++) {
            Estudiante candidato = todos.obtener(i).getDato();

            if (!candidato.equals(estudiante) && tieneAfinidad(estudiante, candidato, 2)) {
                sugerencias.agregar(candidato);
            }
        }

        return sugerencias;
    }

    /**
     * Determina si dos estudiantes tienen una cantidad mínima de intereses en común.
     *
     * @param e1      primer estudiante
     * @param e2      segundo estudiante
     * @param umbral  mínimo de intereses en común requeridos
     * @return true si cumplen el umbral de afinidad, false en caso contrario
     */
    private boolean tieneAfinidad(Estudiante e1, Estudiante e2, int umbral) {
        return contarCoincidencias(e1.getIntereses(), e2.getIntereses()) >= umbral;
    }

    /**
     * Cuenta cuántos elementos tienen en común entre dos listas de intereses.
     * Utiliza un Set para mayor eficiencia.
     *
     * @param lista1 primera lista
     * @param lista2 segunda lista
     * @return número de coincidencias
     */
    private int contarCoincidencias(ListaEnlazada<String> lista1, ListaEnlazada<String> lista2) {
        Set<String> conjunto = new HashSet<>();
        for (int i = 0; i < lista1.getTamanio(); i++) {
            conjunto.add(lista1.obtener(i).toLowerCase());
        }

        int contador = 0;
        for (int i = 0; i < lista2.getTamanio(); i++) {
            if (conjunto.contains(lista2.obtener(i).toLowerCase())) {
                contador++;
            }
        }

        return contador;
    }
    /**
     * Cuenta cuántos contenidos han sido valorados por ambos estudiantes.
     * @param e1 primer estudiante
     * @param e2 segundo estudiante
     * @return número de contenidos comunes valorados
     */
    private int contarValoracionesSimilares(Estudiante e1, Estudiante e2) {
        // Obtener ids de contenidos valorados por e1
        Set<String> contenidosE1 = new HashSet<>();
        for (Valoracion v : e1.getValoraciones()) {
            contenidosE1.add(v.getContenido().getId());
        }
        // Contar cuántos de e2 están en ese set
        int contador = 0;
        for (Valoracion v : e2.getValoraciones()) {
            if (contenidosE1.contains(v.getContenido().getId())) {
                contador++;
            }
        }
        return contador;
    }

    /**
     * Cuenta en cuántos grupos de estudio coinciden ambos estudiantes.
     * @param e1 primer estudiante
     * @param e2 segundo estudiante
     * @return número de grupos en común
     */
    private int contarGruposCompartidos(Estudiante e1, Estudiante e2) {
        Set<String> gruposE1 = new HashSet<>();
        for (GrupoEstudio g : e1.getGruposEstudio()) {
            gruposE1.add(g.getId());  // asume que GrupoEstudio tiene getId()
        }
        int contador = 0;
        for (GrupoEstudio g : e2.getGruposEstudio()) {
            if (gruposE1.contains(g.getId())) {
                contador++;
            }
        }
        return contador;
    }

    /**
     * Versión ampliada de afinidad que combina:
     *   - intereses en común (umbralIntereses)
     *   - valoraciones similares (umbralValoraciones)
     *   - grupos compartidos (umbralGrupos)
     *
     * @return true si se cumplen al menos dos de los tres criterios
     */
    private boolean tieneAfinidadAvanzada(Estudiante e1, Estudiante e2,
                                          int umbralIntereses,
                                          int umbralValoraciones,
                                          int umbralGrupos) {
        boolean interesOK = contarCoincidencias(e1.getIntereses(), e2.getIntereses()) >= umbralIntereses;
        boolean valoracionOK = contarValoracionesSimilares(e1, e2) >= umbralValoraciones;
        boolean grupoOK = contarGruposCompartidos(e1, e2) >= umbralGrupos;

        // Por ejemplo, al menos 2 de los 3 criterios deben cumplirse:
        int cumplidos = 0;
        if (interesOK)       cumplidos++;
        if (valoracionOK)     cumplidos++;
        if (grupoOK)          cumplidos++;

        return cumplidos >= 2;
    }

    /**
     * Modifica tu método sugerirCompaneros para usar la versión avanzada:
     */
    public ListaEnlazada<Estudiante> sugerirCompanerosAvanzado(Estudiante estudiante) {
        ListaEnlazada<Estudiante> sugerencias = new ListaEnlazada<>();
        NodoGrafo<Estudiante> nodoEst = grafoEstudiantes.buscarNodo(estudiante);
        for (NodoGrafo<Estudiante> nodo : grafoEstudiantes.getNodos()) {
            Estudiante candidato = nodo.getDato();
            if (!candidato.equals(estudiante)
                    && tieneAfinidadAvanzada(estudiante, candidato,
                    2, // intereses
                    1, // valoraciones
                    1  // grupos
            )) {
                sugerencias.agregar(candidato);
            }
        }
        return sugerencias;
    }

}




