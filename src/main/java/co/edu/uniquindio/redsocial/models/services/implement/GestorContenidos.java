package co.edu.uniquindio.redsocial.models.services.implement;

import co.edu.uniquindio.redsocial.models.Contenido;
import co.edu.uniquindio.redsocial.models.structures.ArbolBinarioBusqueda;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import co.edu.uniquindio.redsocial.models.structures.NodoLista;

import java.util.HashMap;

/**
 * Clase que representa al gestor de contenidos dentro de la red social educativa.
 * Esta clase maneja la gestión de contenidos mediante un Árbol Binario de Búsqueda
 * y una lista enlazada para los contenidos destacados. Permite agregar, eliminar,
 * buscar y listar contenidos, así como marcar contenidos como destacados.
 *
 * @author Daniel Jurado
 * @author Sebastian Torres
 * @author Juan Soto
 * @since 2025-05-12
 */
public class GestorContenidos {
    private ArbolBinarioBusqueda<Contenido> arbolContenidos;
    private ListaEnlazada<Contenido> contenidoDestacado;
    private static GestorContenidos instancia;


    /**
     * Constructor del gestor de contenidos.
     *
     * @param arbolContenidos    Árbol binario donde se almacenan los contenidos por tema.
     * @param contenidoDestacado Lista de contenidos destacados.
     */

    public GestorContenidos(ArbolBinarioBusqueda<Contenido> arbolContenidos,
                            ListaEnlazada<Contenido> contenidoDestacado) {
        this.arbolContenidos = arbolContenidos;
        this.contenidoDestacado = contenidoDestacado;
    }


    public static GestorContenidos getInstancia() {
        if (instancia == null) {
            instancia = new GestorContenidos(null, null);
        }
        return instancia;
    }

    /**
     * Agrega un nuevo contenido al árbol de contenidos.
     *
     * @param contenido Contenido a agregar.
     */

    public void agregarContenido(Contenido contenido) {

        arbolContenidos.insertar(contenido.getTema(), contenido);
    }


    /**
     * Elimina un contenido del árbol de contenidos por ID.
     * (Esta implementación debe buscar por ID manualmente)
     *
     * @param id ID del contenido a eliminar.
     * @return true si el contenido fue eliminado, false si no se encontró.
     */
    public boolean eliminarContenido(String id) {
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
     * Busca contenidos que coincidan con el tema dado.
     *
     * @param tema Tema del contenido.
     * @return Lista de contenidos encontrados.
     */
    public ListaEnlazada<Contenido> buscarPorTema(String tema) {
        return arbolContenidos.listarContenidosPorTema(tema);
    }
    /**
     * Busca todos los contenidos cuyo autor coincida con el proporcionado.
     *
     * @param autor Autor del contenido.
     * @return Lista de contenidos encontrados.
     */
    public ListaEnlazada<Contenido> buscarPorAutor(String autor) {
        ListaEnlazada<Contenido> resultados = new ListaEnlazada<>();
        ListaEnlazada<Contenido> todos = arbolContenidos.listarTodos();
        NodoLista<Contenido> actual = todos.getCabeza();
        while (actual != null) {
            Contenido c = actual.getDato();
            if (c.getAutor().equalsIgnoreCase(autor)) {
                resultados.agregar(c);
            }
            actual = actual.getSiguiente();
        }
        return resultados;
    }
    /**
     * Marca un contenido como destacado.
     *
     * @param contenido Contenido a destacar.
     */
    public void marcarComoDestacado(Contenido contenido) {
        contenidoDestacado.agregar(contenido);
    }
    /**
     * Genera estadísticas básicas, como la cantidad de contenidos por tipo.
     *
     * @return Mapa con el tipo como clave y el número de contenidos como valor.
     */
    public HashMap<String, Integer> generarEstadisticas() {
        HashMap<String, Integer> stats = new HashMap<>();
        ListaEnlazada<Contenido> todos = arbolContenidos.listarTodos();
        NodoLista<Contenido> actual = todos.getCabeza();
        while (actual != null) {
            String tipo = actual.getDato().getTipo();
            stats.put(tipo, stats.getOrDefault(tipo, 0) + 1);
            actual = actual.getSiguiente();
        }
        return stats;
    }
    /**
     * Busca contenidos que coincidan con tema, autor y tipo.
     *
     * @param tema Tema a buscar.
     * @param autor Autor del contenido.
     * @param tipo Tipo del contenido.
     * @return Lista de contenidos que cumplen con todos los criterios.
     */
    public ListaEnlazada<Contenido> buscarPorTemaAutorTipo(String tema, String autor, String tipo) {
        ListaEnlazada<Contenido> resultados = new ListaEnlazada<>();
        ListaEnlazada<Contenido> todos = arbolContenidos.listarTodos();
        NodoLista<Contenido> actual = todos.getCabeza();
        while (actual != null) {
            Contenido c = actual.getDato();
            if (c.getTema().equalsIgnoreCase(tema)
                    && c.getAutor().equalsIgnoreCase(autor)
                    && c.getTipo().equalsIgnoreCase(tipo)) {
                resultados.agregar(c);
            }
            actual = actual.getSiguiente();
        }
        return resultados;
    }


    // Getters y Setters
    public ArbolBinarioBusqueda<Contenido> getArbolContenidos() { return arbolContenidos; }
    public void setArbolContenidos(ArbolBinarioBusqueda<Contenido> arbolContenidos) {
        this.arbolContenidos = arbolContenidos;
    }
    public ListaEnlazada<Contenido> getContenidoDestacado() { return contenidoDestacado; }
    public void setContenidoDestacado(ListaEnlazada<Contenido> contenidoDestacado) {
        this.contenidoDestacado = contenidoDestacado;
    }
}
