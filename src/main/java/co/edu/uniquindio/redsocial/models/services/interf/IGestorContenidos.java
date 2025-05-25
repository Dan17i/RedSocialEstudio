package co.edu.uniquindio.redsocial.models.services.interf;

import co.edu.uniquindio.redsocial.models.Contenido;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

import java.util.HashMap;

public interface IGestorContenidos {

    void agregarContenido(Contenido contenido);
    boolean eliminarContenido(String id);
    ListaEnlazada<Contenido> buscarPorTema(String tema);
    ListaEnlazada<Contenido> buscarPorAutor(String autor);
    void marcarComoDestacado(Contenido contenido);
    HashMap<String, Integer> generarEstadisticas();
    ListaEnlazada<Contenido> buscarPorTemaAutorTipo(String tema, String autor, String tipo);
    ListaEnlazada<Contenido> obtenerContenidosMasValorados();
    ListaEnlazada<Contenido> getContenidoDestacado();

}
