package co.edu.uniquindio.redsocial.models;

import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

/**
 * Representa un usuario registrado en la red social, incluyendo sus intereses,
 * historial de contenidos y valoraciones.
 *
 * Esta clase puede ser extendida por tipos de usuario como Estudiante.
 *
 * @author Daniel Jurado
 * @author Sebastian Torres
 * @author Juan Soto
 * @since 2025-05-13
 */
public class Usuario {

    private String id;
    private String nombre;
    private String email;
    private String contraseña;
    private ListaEnlazada<String> intereses;
    private ListaEnlazada<Contenido> historialContenidos;
    private ListaEnlazada<Valoracion> valoraciones;

    /**
     * Constructor principal del Usuario.
     *
     * @param id                  Identificador único.
     * @param nombre              Nombre del usuario.
     * @param email               Email único.
     * @param contraseña          Contraseña para autenticación.
     * @param intereses           Lista de intereses del usuario.
     * @param historialContenidos Contenidos visualizados por el usuario.
     * @param valoraciones        Valoraciones realizadas por el usuario.
     */
    public Usuario(String id, String nombre, String email, String contraseña,
                   ListaEnlazada<String> intereses,
                   ListaEnlazada<Contenido> historialContenidos,
                   ListaEnlazada<Valoracion> valoraciones) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.contraseña = contraseña;
        this.intereses = intereses;
        this.historialContenidos = historialContenidos;
        this.valoraciones = valoraciones;
    }
    /**
     * Agrega un interés al usuario si no lo tiene ya.
     *
     * @param interes El nuevo interés a añadir.
     */
    public void agregarInteres(String interes) {
        for (int i = 0; i < intereses.getTamanio(); i++) {
            if (intereses.obtener(i).equalsIgnoreCase(interes)) {
                return; // Ya existe, no lo agrega
            }
        }
        intereses.agregar(interes);
    }
    /**
     * Permite al estudiante valorar un contenido.
     * Se registra la valoración tanto en el contenido como en el perfil del estudiante.
     *
     * @param contenido  Contenido a valorar.
     * @param puntuacion Puntuación dada (por ejemplo, de 1 a 5).
     * @param comentario Comentario opcional del estudiante.
     */
    public void valorarContenido(Contenido contenido, int puntuacion, String comentario) {
        // Crear una nueva valoración
        Valoracion nuevaValoracion = new Valoracion((Estudiante) this, puntuacion, comentario);

        // Agregar la valoración al contenido
        contenido.getValoraciones().agregar(nuevaValoracion);

        // Agregar la valoración al perfil del estudiante
        this.getValoraciones().agregar(nuevaValoracion);
    }


    /**
     * Retorna una representación en texto del historial de contenidos vistos por el usuario.
     *
     * @return Historial de contenidos como cadena de texto.
     */
    public String verHistorial() {
        if (historialContenidos.isEmpty()) {
            return "El historial de contenidos está vacío.";
        }

        StringBuilder sb = new StringBuilder("Historial de Contenidos:\n");
        for (int i = 0; i < historialContenidos.getTamanio(); i++) {
            Contenido contenido = historialContenidos.obtener(i);
            sb.append("- ").append(contenido.toString()).append("\n");
        }

        return sb.toString();
    }


    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getContraseña() { return contraseña; }
    public void setContraseña(String contraseña) { this.contraseña = contraseña; }

    public ListaEnlazada<String> getIntereses() { return intereses; }
    public void setIntereses(ListaEnlazada<String> intereses) { this.intereses = intereses; }

    public ListaEnlazada<Contenido> getHistorialContenidos() { return historialContenidos; }
    public void setHistorialContenidos(ListaEnlazada<Contenido> historialContenidos) {
        this.historialContenidos = historialContenidos;
    }

    public ListaEnlazada<Valoracion> getValoraciones() { return valoraciones; }
    public void setValoraciones(ListaEnlazada<Valoracion> valoraciones) {
        this.valoraciones = valoraciones;
    }



    /**
     * Representación en texto del usuario, sin mostrar la contraseña.
     */
    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", intereses=" + intereses +
                ", historialContenidos=" + historialContenidos +
                ", valoraciones=" + valoraciones +
                '}';
    }

    /**
     * Compara usuarios por su ID único.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Usuario otro = (Usuario) obj;
        return id != null && id.equals(otro.id);
    }

    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }
}
