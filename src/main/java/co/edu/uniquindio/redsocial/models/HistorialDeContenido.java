package co.edu.uniquindio.redsocial.models;

import java.time.LocalDateTime;

class HistorialDeContenido {
    private String idUsuario;
    private ListaEnlazada<Contenido> contenidos;
    private ListaEnlazada<LocalDateTime> fechasAcceso;

    public HistorialDeContenido(String idUsuario, ListaEnlazada<Contenido> contenidos,
                                ListaEnlazada<LocalDateTime> fechasAcceso) {
        this.idUsuario = idUsuario;
        this.contenidos = contenidos;
        this.fechasAcceso = fechasAcceso;
    }

    public void agregarContenido(Contenido contenido, LocalDateTime fecha) {
        contenidos.agregar(contenido);
        fechasAcceso.agregar(fecha);
    }

    public ListaEnlazada<Contenido> filtrarPorTema(String tema) {
        ListaEnlazada<Contenido> resultado = new ListaEnlazada<>();
        NodoLista<Contenido> actual = contenidos.getCabeza();
        while (actual != null) {
            if (actual.getDato().getTema().equals(tema)) {
                resultado.agregar(actual.getDato());
            }
            actual = actual.getSiguiente();
        }
        return resultado;
    }

    public ListaEnlazada<Contenido> obtenerUltimos(int n) {
        ListaEnlazada<Contenido> ultimos = new ListaEnlazada<>();
        int count = 0;
        NodoLista<Contenido> actual = contenidos.getCabeza();
        while (actual != null && count < n) {
            ultimos.agregar(actual.getDato());
            actual = actual.getSiguiente();
            count++;
        }
        return ultimos;
    }

    // Getters y Setters
    public String getIdUsuario() { return idUsuario; }
    public void setIdUsuario(String idUsuario) { this.idUsuario = idUsuario; }
    public ListaEnlazada<Contenido> getContenidos() { return contenidos; }
    public void setContenidos(ListaEnlazada<Contenido> contenidos) { this.contenidos = contenidos; }
    public ListaEnlazada<LocalDateTime> getFechasAcceso() { return fechasAcceso; }
    public void setFechasAcceso(ListaEnlazada<LocalDateTime> fechasAcceso) { this.fechasAcceso = fechasAcceso; }
}
