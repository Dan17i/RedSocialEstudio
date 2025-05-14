package co.edu.uniquindio.redsocial.models.services.implement;

/*
 *
 *
 *
 *
 * */

import co.edu.uniquindio.redsocial.models.Contenido;
import co.edu.uniquindio.redsocial.models.services.interf.IGestorContenidos;
import co.edu.uniquindio.redsocial.models.structures.ArbolBinarioBusqueda;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import co.edu.uniquindio.redsocial.models.structures.NodoLista;

import java.util.HashMap;

public class GestorContenidos implements IGestorContenidos {

    private ArbolBinarioBusqueda<Contenido> arbolContenidos;
    private ListaEnlazada<Contenido> contenidoDestacado;
    private static GestorContenidos instancia;

    public GestorContenidos(ArbolBinarioBusqueda<Contenido> arbolContenidos,
                            ListaEnlazada<Contenido> contenidoDestacado) {
        this.arbolContenidos = arbolContenidos;
        this.contenidoDestacado = contenidoDestacado;
    }

    public static GestorContenidos getInstancia() {
        if (instancia == null) {
            instancia = new GestorContenidos(new ArbolBinarioBusqueda<>(), new ListaEnlazada<>());
        }
        return instancia;
    }

    @Override
    public void agregarContenido(Contenido contenido) {
        arbolContenidos.insertar(contenido.getTema(), contenido);
    }

    @Override
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

    @Override
    public ListaEnlazada<Contenido> buscarPorTema(String tema) {
        return arbolContenidos.listarContenidosPorTema(tema);  // Ahora devuelve la lista filtrada por tema
    }

    @Override
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

    @Override
    public void marcarComoDestacado(Contenido contenido) {
        contenidoDestacado.agregar(contenido);
    }

    @Override
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

    @Override
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

    @Override
    public ListaEnlazada<Contenido> getContenidoDestacado() {
        return contenidoDestacado;
    }

    // Getters y Setters extra si necesitas exponer más control
}
