package co.edu.uniquindio.redsocial.models;

import java.util.HashMap;

class GestorContenidos {
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
            instancia = new GestorContenidos(null, null);
        }
        return instancia;
    }

    public void agregarContenido(Contenido contenido) {

        arbolContenidos.insertar(contenido.getTema(), contenido);
    }

    public boolean eliminarContenido(String id) { return false; }
    public ListaEnlazada<Contenido> buscarPorTema(String tema) { return arbolContenidos.listarContenidosPorTema(tema); }
    public ListaEnlazada<Contenido> buscarPorAutor(String autor) { return new ListaEnlazada<>(); }
    public void marcarComoDestacado(Contenido contenido) { if(!contenidoDestacado.buscar(contenido)){contenidoDestacado.agregar(contenido); }}
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
}
