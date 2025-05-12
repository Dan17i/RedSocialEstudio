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
    public String getEmail() { return email; }
    public String getContraseña() { return contraseña; }
    public String getNombre() { return nombre; }
    public String getId() { return id; }
    // ... otros getters/setters del diagrama
}
