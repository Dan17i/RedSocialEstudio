package co.edu.uniquindio.redsocial.models;

class Contenido {
    private String id;
    private String tema;
    private String autor;
    private String tipo;
    private ListaEnlazada<Valoracion> valoraciones;

    public Contenido(String id, String tema, String autor, String tipo, ListaEnlazada<Valoracion> valoraciones) {
        this.id = id;
        this.tema = tema;
        this.autor = autor;
        this.tipo = tipo;
        this.valoraciones = valoraciones;
    }

    public float calcularValoracionPromedio() {
        if (valoraciones.estaVacia()) return 0;
        int suma = 0;
        NodoLista<Valoracion> actual = valoraciones.getCabeza();
        while (actual != null) {
            suma += actual.getDato().getPuntuacion();
            actual = actual.getSiguiente();
        }
        return (float) suma / valoraciones.getTamanio();
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTema() { return tema; }
    public void setTema(String tema) { this.tema = tema; }
    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public ListaEnlazada<Valoracion> getValoraciones() { return valoraciones; }
    public void setValoraciones(ListaEnlazada<Valoracion> valoraciones) { this.valoraciones = valoraciones; }
}
