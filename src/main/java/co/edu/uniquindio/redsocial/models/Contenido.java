package co.edu.uniquindio.redsocial.models;

public class Contenido {

    String id;
    String nombre;
    String tema;
    String autor;
    String tipo;
    ListaEnlazada<Valoracion> valoraciones;

    public Contenido(String id, String nombre, String autor, String tema, String tipo, ListaEnlazada<Valoracion> valoraciones) {
        this.id = id;
        this.nombre = nombre;
        this.autor = autor;
        this.tema = tema;
        this.tipo = tipo;
        this.valoraciones = valoraciones;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public ListaEnlazada<Valoracion> getValoraciones() {
        return valoraciones;
    }

    public void setValoraciones(ListaEnlazada<Valoracion> valoraciones) {
        this.valoraciones = valoraciones;
    }


}
