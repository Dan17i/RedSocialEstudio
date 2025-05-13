package co.edu.uniquindio.redsocial.models;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Clase que representa un moderador en la red social.
 * Un moderador tiene acceso a gestionar usuarios, eliminar contenido y generar reportes.
 *
 * @author Daniel Jurado
 * @author Sebastian Torres
 * @author Juan Soto
 * @since 2025-04-02
 */
class Moderador extends Usuario {
    private boolean accesoCompleto;  // Cambiado a booleano
    private ListaEnlazada<String> areasResponsabilidad;

    /**
     * Constructor para inicializar un moderador.
     *
     * @param id Identificador del moderador.
     * @param nombre Nombre del moderador.
     * @param email Correo electrónico del moderador.
     * @param contraseña Contraseña del moderador.
     * @param intereses Lista de intereses del moderador.
     * @param historialContenidos Historial de contenidos del moderador.
     * @param valoraciones Lista de valoraciones del moderador.
     * @param accesoCompleto Booleano que indica si tiene acceso completo.
     * @param areasResponsabilidad Lista de áreas de responsabilidad del moderador.
     */
    public Moderador(String id, String nombre, String email, String contraseña,
                     ListaEnlazada<String> intereses,
                     ListaEnlazada<Contenido> historialContenidos,
                     ListaEnlazada<Valoracion> valoraciones,
                     boolean accesoCompleto, ListaEnlazada<String> areasResponsabilidad) {
        super(id, nombre, email, contraseña, intereses, historialContenidos, valoraciones);
        this.accesoCompleto = accesoCompleto;
        this.areasResponsabilidad = areasResponsabilidad != null ? areasResponsabilidad : new ListaEnlazada<>();
    }

    // Métodos específicos de Moderador

    /**
     * Método para gestionar usuarios. (Por implementar)
     * Se espera que este método gestione el alta, baja o modificación de usuarios.
     */
    public void gestionarUsuarios() {
        // Lógica de gestión TODO TORRES
    }

    /**
     * Método para eliminar contenido inapropiado.
     *
     * @param contenido Contenido a eliminar.
     */
    public void eliminarContenido(Contenido contenido) {
        // Lógica de eliminación TODO TORRES
    }

    /**
     * Método para generar un reporte de comunidades.
     *
     * @return Lista de listas de estudiantes.
     */
    public ListaEnlazada<ListaEnlazada<Estudiante>> generarReporteComunidades() {
        return new ListaEnlazada<>();
    }

    /**
     * Método para generar un reporte de tipo específico.
     *
     * @param tipo Tipo de reporte que se desea generar.
     * @return Un objeto de tipo Reporte con la información solicitada.
     */
    public Reporte generarReporte(String tipo) {
        return new Reporte("1", tipo, LocalDateTime.now(), new ListaEnlazada<>());
    }

    /**
     * Método para visualizar el grafo de relaciones entre usuarios.
     * (Por implementar)
     */
    public void visualizarGrafo() {
        // Lógica de visualización TODO TORRES
    }

    // Getters y Setters específicos

    /**
     * Verifica si el moderador tiene acceso completo.
     *
     * @return true si tiene acceso completo, false de lo contrario.
     */
    public boolean tieneAccesoCompleto() {
        return accesoCompleto;
    }

    /**
     * Establece si el moderador tiene acceso completo.
     *
     * @param accesoCompleto true si debe tener acceso completo, false de lo contrario.
     */
    public void setAccesoCompleto(boolean accesoCompleto) {
        this.accesoCompleto = accesoCompleto;
    }

    /**
     * Obtiene las áreas de responsabilidad del moderador.
     *
     * @return Lista de áreas de responsabilidad.
     */
    public ListaEnlazada<String> getAreasResponsabilidad() {
        return areasResponsabilidad;
    }

    /**
     * Establece las áreas de responsabilidad del moderador.
     *
     * @param areasResponsabilidad Lista de áreas de responsabilidad.
     */
    public void setAreasResponsabilidad(ListaEnlazada<String> areasResponsabilidad) {
        if (areasResponsabilidad != null && !areasResponsabilidad.isEmpty()) {
            this.areasResponsabilidad = areasResponsabilidad;
        } else {
            throw new IllegalArgumentException("Las áreas de responsabilidad no pueden ser nulas o vacías");
        }
    }
}
