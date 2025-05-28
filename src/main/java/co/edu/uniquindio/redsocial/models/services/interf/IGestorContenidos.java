package co.edu.uniquindio.redsocial.models.services.interf;

import co.edu.uniquindio.redsocial.models.Contenido;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

import java.util.HashMap;
/**
 * Interfaz que define las operaciones para la gestión de contenidos
 * en la plataforma de la red social educativa.
 * <p>
 * Permite agregar, eliminar, buscar y gestionar contenidos, además de
 * generar estadísticas y manejar contenidos destacados.
 * </p>
 *
 * @author Daniel Jurado, Sebastia Torres y juan Soto
 * @since 2025-05-27
 */
public interface IGestorContenidos {
    /**
     * Agrega un nuevo contenido al sistema.
     *
     * @param contenido objeto Contenido a agregar.
     */
    void agregarContenido(Contenido contenido);
    /**
     * Elimina un contenido identificado por su ID.
     *
     * @param id identificador único del contenido a eliminar.
     * @return true si el contenido fue eliminado correctamente; false si no se encontró o no pudo eliminarse.
     */
    boolean eliminarContenido(String id);
    /**
     * Busca y retorna una lista de contenidos que coinciden con el tema dado.
     *
     * @param tema tema por el cual se desea filtrar los contenidos.
     * @return lista enlazada con contenidos que corresponden al tema especificado.
     */
    ListaEnlazada<Contenido> buscarPorTema(String tema);
    /**
     * Busca y retorna una lista de contenidos que fueron creados por el autor dado.
     *
     * @param autor nombre o identificador del autor.
     * @return lista enlazada con contenidos asociados al autor especificado.
     */
    ListaEnlazada<Contenido> buscarPorAutor(String autor);
    /**
     * Marca un contenido como destacado, para que pueda ser mostrado en secciones especiales.
     *
     * @param contenido objeto Contenido que se desea marcar como destacado.
     */
    void marcarComoDestacado(Contenido contenido);
    /**
     * Genera estadísticas generales sobre los contenidos,
     * tales como conteos por tipo, autor o cualquier métrica definida.
     *
     * @return un mapa donde la clave es la categoría (ejemplo: tipo de contenido)
     *         y el valor es el conteo o estadística correspondiente.
     */
    HashMap<String, Integer> generarEstadisticas();
    /**
     * Busca contenidos que coincidan simultáneamente con un tema, autor y tipo especificados.
     *
     * @param tema tema para filtrar.
     * @param autor autor para filtrar.
     * @param tipo tipo de contenido para filtrar.
     * @return lista enlazada con contenidos que cumplen con los tres criterios.
     */
    ListaEnlazada<Contenido> buscarPorTemaAutorTipo(String tema, String autor, String tipo);
    /**
     * Obtiene los contenidos con las mejores valoraciones o calificaciones dentro del sistema.
     *
     * @return lista enlazada con los contenidos más valorados.
     */
    ListaEnlazada<Contenido> obtenerContenidosMasValorados();
    /**
     * Obtiene la lista de contenidos que han sido marcados como destacados.
     *
     * @return lista enlazada con contenidos destacados.
     */
    ListaEnlazada<Contenido> getContenidoDestacado();

}
