package co.edu.uniquindio.redsocial.models;

import java.util.Map;

public class GestorContenidos {
    ArbolBinarioBusqueda<Contenido> arbolContenidos;
    ListaEnlazada<Contenido> contenidoDestacado;

    public GestorContenidos(ArbolBinarioBusqueda<Contenido> arbolContenidos, ListaEnlazada<Contenido> contenidoDestacado) {
        this.arbolContenidos = arbolContenidos;
        this.contenidoDestacado = contenidoDestacado;
    }

    public ArbolBinarioBusqueda<Contenido> getArbolContenidos() {
        return arbolContenidos;
    }

    public void setArbolContenidos(ArbolBinarioBusqueda<Contenido> arbolContenidos) {
        this.arbolContenidos = arbolContenidos;
    }

    public ListaEnlazada<Contenido> getContenidoDestacado() {
        return contenidoDestacado;
    }

    public void setContenidoDestacado(ListaEnlazada<Contenido> contenidoDestacado) {
        this.contenidoDestacado = contenidoDestacado;
    }

    public void agregarContenido(Contenido contenido){

    }

    public boolean eliminarContenido(String id){

    }

    public ListaEnlazada<Contenido> buscarPorTema(String tema){


        return null;
    }

    public ListaEnlazada<Contenido> buscarPorAutor(String autor){

        return null;
    }

    public void marcarComoDestacado(Contenido contenido){

    }

    public Map<String,Integer> generarEstadisticas(){

        return Map.of();
    }
}
