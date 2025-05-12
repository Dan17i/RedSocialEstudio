package co.edu.uniquindio.redsocial.models;

import java.time.LocalDateTime;

/**
 * Clase que representa un usuario en la red social.
 * Un usuario tiene un nombre y un email.
 *
 * @author Daniel Jurado
 * @author Sebastian Torres
 * @author Juan Soto
 * @since 2025-04-02
 */
class Moderador extends Usuario {
    private int nivelAcceso;
    private ListaEnlazada<String> areasResponsabilidad;

    public Moderador(String id, String nombre,  String email, String contraseña,
                     ListaEnlazada<String> intereses,
                     ListaEnlazada<Contenido> historialContenidos, ListaEnlazada<Valoracion> valoraciones,
                     int nivelAcceso, ListaEnlazada<String> areasResponsabilidad) {
        super(id, nombre, email, contraseña, intereses, historialContenidos, valoraciones);
        this.nivelAcceso = nivelAcceso;
        this.areasResponsabilidad = areasResponsabilidad;
    }

    // Métodos específicos de Moderador
    public void gestionarUsuarios() { /* Lógica de gestión TODO TORRES */ }
    public void eliminarContenido(Contenido contenido) { /* Lógica de eliminación TODO TORRES */ }
    public ListaEnlazada<ListaEnlazada<Estudiante>> generarReporteComunidades() { return new ListaEnlazada<>(); }
    public Reporte generarReporte(String tipo) { return new Reporte("1", tipo, LocalDateTime.now(), new ListaEnlazada<>()); }
    public void visualizarGrafo() { /* Lógica de visualización TODO TORRES */ }

    // Getters y Setters específicos
    public int getNivelAcceso() { return nivelAcceso; }
    public void setNivelAcceso(int nivelAcceso) { this.nivelAcceso = nivelAcceso; }
    public ListaEnlazada<String> getAreasResponsabilidad() { return areasResponsabilidad; }
    public void setAreasResponsabilidad(ListaEnlazada<String> areasResponsabilidad) { this.areasResponsabilidad = areasResponsabilidad; }
}
