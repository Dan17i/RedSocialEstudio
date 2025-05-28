package co.edu.uniquindio.redsocial.models.services.implement;


import co.edu.uniquindio.redsocial.models.Contenido;
import co.edu.uniquindio.redsocial.models.services.interf.IGestorContenidos;
import co.edu.uniquindio.redsocial.models.structures.ArbolBinarioBusqueda;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import co.edu.uniquindio.redsocial.models.structures.NodoLista;

import java.util.HashMap;

/**
 * Clase que implementa la lógica de gestión de contenidos de la red social educativa.
 * Permite agregar, eliminar, buscar, y generar estadísticas de los contenidos.
 * Utiliza un árbol binario de búsqueda y una lista enlazada para la gestión eficiente.
 *
 * @author Daniel Jurado, Sebastian Torres y Juan Soto
 * @since 2025-05-20
 */
public class GestorContenidos implements IGestorContenidos {

    private final ArbolBinarioBusqueda<Contenido> arbolContenidos;
    private final ListaEnlazada<Contenido> contenidoDestacado;
    private static GestorContenidos instancia;
    private ListaEnlazada<Contenido> listaDeContenidos = new ListaEnlazada<>();
    private String rutaArchivo;
    private String mimeType;
    private long tamanioArchivo;
    /**
     * Constructor que recibe las estructuras necesarias para gestionar contenidos.
     *
     * @param arbolContenidos     Árbol binario de búsqueda que almacena los contenidos por tema.
     * @param contenidoDestacado  Lista enlazada que almacena contenidos marcados como destacados.
     */
    public GestorContenidos(ArbolBinarioBusqueda<Contenido> arbolContenidos,
                            ListaEnlazada<Contenido> contenidoDestacado) {
        this.arbolContenidos = arbolContenidos;
        this.contenidoDestacado = contenidoDestacado;
    }
    /**
     * Retorna una instancia singleton del gestor de contenidos.
     *
     * @return Instancia única de {@code GestorContenidos}.
     */
    public static GestorContenidos getInstancia() {
        if (instancia == null) {
            instancia = new GestorContenidos(new ArbolBinarioBusqueda<>(), new ListaEnlazada<>());
        }
        return instancia;
    }
    /**
     * Agrega un contenido al sistema.
     *
     * @param contenido Contenido a agregar.
     */
    @Override
    public void agregarContenido(Contenido contenido) {
        if (contenido != null) {
            arbolContenidos.insertar(contenido.getTema(), contenido);
            listaDeContenidos.agregar(contenido);
        }
    }
    /**
     * Elimina un contenido del sistema dado su identificador único.
     *
     * @param id Identificador del contenido a eliminar.
     * @return {@code true} si fue eliminado, {@code false} si no se encontró.
     */
    @Override
    public boolean eliminarContenido (String id){
        ListaEnlazada<Contenido> todos = arbolContenidos.listarTodos();
        NodoLista<Contenido> actual = todos.getCabeza();
        while (actual != null) {
            Contenido c = actual.getDato();
            if (c.getId().equals(id)) {
                arbolContenidos.eliminar(c.getTema());
                return true;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }
    /**
     * Busca contenidos por tema específico.
     *
     * @param tema Tema a buscar.
     * @return Lista enlazada de contenidos que coinciden con el tema.
     */
    @Override
    public ListaEnlazada<Contenido> buscarPorTema(String tema) {
        return arbolContenidos.listarContenidosPorTema(tema);  // Ahora devuelve la lista filtrada por tema
    }
    /**
     * Busca contenidos por nombre del autor.
     *
     * @param autor Nombre del autor a buscar.
     * @return Lista enlazada de contenidos que coinciden con el autor.
     */
    @Override
    public ListaEnlazada<Contenido> buscarPorAutor(String autor) {
        ListaEnlazada<Contenido> resultados = new ListaEnlazada<>();
        ListaEnlazada<Contenido> todos = arbolContenidos.listarTodos();
        NodoLista<Contenido> actual = todos.getCabeza();
        while (actual != null) {
            Contenido c = actual.getDato();
            // Suponiendo que comparas por nombre del autor
            if (c.getAutor().getNombre().equalsIgnoreCase(autor)) {
                resultados.agregar(c);
            }
            actual = actual.getSiguiente();
        }
        return resultados;
    }

    /**
     * Marca un contenido como destacado, agregándolo a la lista especial.
     *
     * @param contenido Contenido a destacar.
     */
    @Override
    public void marcarComoDestacado(Contenido contenido) {
        contenidoDestacado.agregar(contenido);
    }
    /**
     * Genera estadísticas del número de contenidos por tipo.
     *
     * @return Mapa con claves como el nombre del tipo y valores como la cantidad.
     */
    @Override
    public HashMap<String, Integer> generarEstadisticas() {
        HashMap<String, Integer> stats = new HashMap<>();
        ListaEnlazada<Contenido> todos = arbolContenidos.listarTodos();
        NodoLista<Contenido> actual = todos.getCabeza();
        while (actual != null) {
            String tipo = actual.getDato().getTipo().name();
            stats.put(tipo, stats.getOrDefault(tipo, 0) + 1);
            actual = actual.getSiguiente();
        }
        return stats;
    }
    /**
     * Busca contenidos por tema, autor y tipo simultáneamente.
     *
     * @param tema Tema del contenido.
     * @param autor Nombre del autor.
     * @param tipo Tipo de contenido.
     * @return Lista enlazada de contenidos que coincidan con todos los criterios.
     */
    @Override
    public ListaEnlazada<Contenido> buscarPorTemaAutorTipo(String tema, String autor, String tipo) {
        ListaEnlazada<Contenido> resultados = new ListaEnlazada<>();
        ListaEnlazada<Contenido> todos = arbolContenidos.listarTodos();
        NodoLista<Contenido> actual = todos.getCabeza();
        while (actual != null) {
            Contenido c = actual.getDato();
            if (c.getTema().equalsIgnoreCase(tema)
                    && c.getAutor().getNombre().equalsIgnoreCase(autor) // corregido aquí
                    && c.getTipo().name().equalsIgnoreCase(tipo)) {
                resultados.agregar(c);
            }
            actual = actual.getSiguiente();
        }
        return resultados;
    }
    /**
     * Retorna la lista de contenidos marcados como destacados.
     *
     * @return Lista enlazada de contenidos destacados.
     */
    @Override
    public ListaEnlazada<Contenido> getContenidoDestacado() {
        return contenidoDestacado;
    }
    /**
     * Obtiene la lista de contenidos ordenados por su promedio de valoraciones, de mayor a menor.
     *
     * @return Lista enlazada con los contenidos más valorados primero.
     */
    @Override
    public ListaEnlazada<Contenido> obtenerContenidosMasValorados() {
        ListaEnlazada<Contenido> todos = obtenerTodosLosContenidos(); // Método asumido
        todos.ordenar((c1, c2) -> Double.compare(c2.promedioValoraciones(), c1.promedioValoraciones()));
        return todos;
    }
    /**
     * Retorna todos los contenidos almacenados en el sistema.
     *
     * @return Lista enlazada con todos los contenidos disponibles.
     */
    public ListaEnlazada<Contenido> obtenerTodosLosContenidos() {
        return listaDeContenidos; // Asegúrate de tener esta lista como atributo interno.
    }

}
