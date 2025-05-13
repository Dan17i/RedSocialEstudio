package co.edu.uniquindio.redsocial.models;

import java.util.HashMap;
import java.util.Map;

class GestorContenidos {
    private ArbolBinarioBusqueda<Contenido> arbolContenidos;
    private ListaEnlazada<Contenido> contenidoDestacado;

    public GestorContenidos(ArbolBinarioBusqueda<Contenido> arbolContenidos,
                            ListaEnlazada<Contenido> contenidoDestacado) {
        this.arbolContenidos = arbolContenidos;
        this.contenidoDestacado = contenidoDestacado;
    }

    public void agregarContenido(Contenido contenido) {
        arbolContenidos.insertar(contenido.getTema(), contenido);
    }

    public boolean eliminarContenido(String id) { return false; }
    public ListaEnlazada<Contenido> buscarPorTema(String tema) { return arbolContenidos.listarContenidosPorTema(tema); }
    public ListaEnlazada<Contenido> buscarPorAutor(String autor) { return new ListaEnlazada<>(); }
    public void marcarComoDestacado(Contenido contenido) { contenidoDestacado.agregar(contenido); }
    public HashMap<String, Integer> generarEstadisticas() { return new HashMap<>(); }

    // Getters y Setters
    public ArbolBinarioBusqueda<Contenido> getArbolContenidos() { return arbolContenidos; }
    public void setArbolContenidos(ArbolBinarioBusqueda<Contenido> arbolContenidos) {
        this.arbolContenidos = arbolContenidos;
    }
    public ListaEnlazada<Contenido> getContenidoDestacado() { return contenidoDestacado; }
    public void setContenidoDestacado(ListaEnlazada<Contenido> contenidoDestacado) {
        this.contenidoDestacado = contenidoDestacado;
    }

    public ListaEnlazada<Contenido> buscarPorTemaAutorTipo(String tema, String autor, String tipo) {
        // Buscar en el árbol o lista global de contenidos
        return arbolContenidos.buscar(tema); // Asumimos búsqueda refinada
    }
}
