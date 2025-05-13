package co.edu.uniquindio.redsocial.models;
import co.edu.uniquindio.redsocial.models.Valoracion;

// CLASE USUARIO MODIFICADA (con email y contraseña)
class Usuario {
    private String id;
    private String nombre;
    private String email;
    private String contraseña;
    private ListaEnlazada<String> intereses;
    private ListaEnlazada<Contenido> historialContenidos;
    private ListaEnlazada<Valoracion> valoraciones;

    public Usuario(String id, String nombre, String email, String contraseña,
                   ListaEnlazada<String> intereses, ListaEnlazada<Contenido> historialContenidos,
                   ListaEnlazada<Valoracion> valoraciones) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.contraseña = contraseña;
        this.intereses = intereses;
        this.historialContenidos = historialContenidos;
        this.valoraciones = valoraciones;
    }

    // Getters y Setters

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public ListaEnlazada<String> getIntereses() {
        return intereses;
    }

    public void setIntereses(ListaEnlazada<String> intereses) {
        this.intereses = intereses;
    }

    public ListaEnlazada<Contenido> getHistorialContenidos() {
        return historialContenidos;
    }

    public void setHistorialContenidos(ListaEnlazada<Contenido> historialContenidos) {
        this.historialContenidos = historialContenidos;
    }

    public ListaEnlazada<Valoracion> getValoraciones() {
        return valoraciones;
    }

    public void setValoraciones(ListaEnlazada<Valoracion> valoraciones) {
        this.valoraciones = valoraciones;
    }


    // ... otros getters/setters del diagrama
}
