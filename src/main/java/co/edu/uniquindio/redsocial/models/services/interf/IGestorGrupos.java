package co.edu.uniquindio.redsocial.models.services.interf;


import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.GrupoEstudio;
import co.edu.uniquindio.redsocial.models.structures.GrafoImpl;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Interfaz que define las operaciones básicas para un gestor de grupos de estudio.
 *
 * @param <T> Tipo de estudiante que conforma los grupos.
 */
public interface IGestorGrupos<T extends Estudiante> {

    /**
     * Establece el grafo de estudiantes usado para detectar afinidades y crear grupos.
     *
     * @param grafo Grafo que representa las relaciones entre estudiantes.
     * @throws IllegalArgumentException si el grafo es nulo.
     */
    void setGrafo(GrafoImpl<T> grafo);

    /**
     * Obtiene el grafo de estudiantes utilizado por el gestor.
     *
     * @return Grafo de estudiantes.
     */
    GrafoImpl<T> getGrafo();

    /**
     * Crea grupos de estudio basados en comunidades detectadas dentro del grafo.
     *
     * @param temaPorDefecto Tema que se asignará a todos los grupos creados.
     * @return Lista de grupos de estudio creados.
     * @throws IllegalStateException    si el grafo no ha sido establecido o no es un grafo no dirigido.
     * @throws IllegalArgumentException si el temaPorDefecto es nulo o vacío.
     */
    ListaEnlazada<GrupoEstudio> crearGruposPorAfinidadConObjetos(String temaPorDefecto);

    /**
     * Obtiene la lista de grupos de estudio gestionados actualmente.
     *
     * @return Lista de grupos de estudio; puede ser vacía si no se han creado grupos.
     */
    ListaEnlazada<GrupoEstudio> getGruposEstudio();

}


