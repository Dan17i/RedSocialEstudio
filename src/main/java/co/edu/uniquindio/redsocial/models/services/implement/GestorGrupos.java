package co.edu.uniquindio.redsocial.models.services.implement;

import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.GrupoEstudio;
import co.edu.uniquindio.redsocial.models.services.interf.IGestorGrupos;
import co.edu.uniquindio.redsocial.models.structures.GrafoImpl;
import co.edu.uniquindio.redsocial.models.structures.GrafoNoDirigido;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

import java.util.UUID;

/**
 * Implementación concreta de {@link IGestorGrupos} para crear y gestionar
 * grupos de estudio a partir de un grafo de estudiantes basado en afinidades.
 *
 * @param <T> Tipo de estudiante que conforma los grupos.
 * @author Daniel Jurado, Sebastian Torres y Juan Soto
 * @since 2025-05-20
 */
public class GestorGrupos<T extends Estudiante> implements IGestorGrupos<T> {

    /**
     * GrafoImpl que representa las relaciones entre estudiantes para detectar afinidades.
     */
    private GrafoImpl<T> grafoEstudiantes;

    /**
     * Lista de grupos de estudio creados por el gestor.
     */
    private ListaEnlazada<GrupoEstudio> gruposEstudio;

    /**
     * Establece el grafo de estudiantes que será utilizado para detectar comunidades
     * y crear grupos de estudio.
     *
     * @param grafoimpl Grafo que representa las relaciones entre estudiantes.
     * @throws IllegalArgumentException si el grafo es nulo.
     */
    @Override
    public void setGrafo(GrafoImpl<T> grafoimpl) {
        if (grafoimpl == null) {
            throw new IllegalArgumentException("El grafo no puede ser nulo");
        }
        // Ahora sí guardamos el grafo que recibimos (puede ser GrafoNoDirigido)
        this.grafoEstudiantes = grafoimpl;
    }

    /**
     * Obtiene el grafo de estudiantes utilizado actualmente por el gestor.
     *
     * @return GrafoImpl de estudiantes.
     */
    @Override
    public GrafoImpl<T> getGrafo() {
        return grafoEstudiantes;
    }

    /**
     * Crea grupos de estudio detectando comunidades en el grafo de estudiantes.
     * Cada comunidad detectada genera un grupo de estudio con el tema especificado.
     *
     * @param temaPorDefecto Tema que se asignará a todos los grupos creados.
     * @return Lista de grupos de estudio creados.
     * @throws IllegalStateException    si el grafo no ha sido establecido o no es un grafo no dirigido.
     * @throws IllegalArgumentException si el temaPorDefecto es nulo o vacío.
     */
    @Override
    public ListaEnlazada<GrupoEstudio> crearGruposPorAfinidadConObjetos(String temaPorDefecto) {
        if (grafoEstudiantes == null) {
            throw new IllegalStateException("El grafo no ha sido establecido");
        }
        if (temaPorDefecto == null || temaPorDefecto.isBlank()) {
            throw new IllegalArgumentException("El tema no puede ser nulo ni vacío");
        }
        if (!(grafoEstudiantes instanceof GrafoNoDirigido)) {
            throw new IllegalStateException("El grafo debe ser no dirigido para detectar comunidades");
        }

        GrafoNoDirigido<T> grafoNoDirigido = (GrafoNoDirigido<T>) grafoEstudiantes;
        ListaEnlazada<ListaEnlazada<T>> comunidades = grafoNoDirigido.detectarComunidades();

        gruposEstudio = new ListaEnlazada<>();
        for (ListaEnlazada<T> comunidad : comunidades) {
            String idGrupo = UUID.randomUUID().toString();
            GrupoEstudio grupo = new GrupoEstudio(idGrupo, temaPorDefecto);
            for (T estudiante : comunidad) {
                grupo.agregarMiembro(estudiante);
            }
            gruposEstudio.agregar(grupo);
        }

        return gruposEstudio;
    }

    /**
     * Obtiene la lista de grupos de estudio gestionados actualmente.
     *
     * @return Lista de grupos de estudio; si no se han creado grupos, retorna una lista vacía.
     */
    @Override
    public ListaEnlazada<GrupoEstudio> getGruposEstudio() {
        if (gruposEstudio == null) {
            return new ListaEnlazada<>();
        }
        return gruposEstudio;
    }
}
