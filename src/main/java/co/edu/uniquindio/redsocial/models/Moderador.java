package co.edu.uniquindio.redsocial.models;

import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;


import java.time.LocalDateTime;

/**
 * Clase que representa un moderador en la red social.
 * Un moderador tiene acceso a gestionar usuarios, eliminar contenido y generar reportes.
 *
 * @author Daniel Jurado
 * @author Sebastian Torres
 * @author Juan Soto
 * @since 2025-04-02
 */
public class Moderador extends Usuario {
    private boolean accesoCompleto;
    private ListaEnlazada<String> areasResponsabilidad;

    /**
     * Constructor del moderador.
     *
     * @param id                  ID del moderador
     * @param nombre              Nombre del moderador
     * @param email               Correo electrónico del moderador
     * @param contraseña          Contraseña del moderador
     * @param intereses           Lista de intereses del moderador
     * @param historialContenidos Historial de contenidos vistos por el moderador
     * @param valoraciones        Valoraciones realizadas por el moderador
     * @param accesoCompleto      Indica si tiene acceso total a todas las áreas
     * @param areasResponsabilidad Lista de áreas que el moderador puede gestionar
     */
    public Moderador(String id, String nombre, String email, String contraseña,
                     ListaEnlazada<String> intereses,
                     ListaEnlazada<Contenido> historialContenidos,
                     ListaEnlazada<Valoracion> valoraciones,
                     boolean accesoCompleto, ListaEnlazada<String> areasResponsabilidad) {
        super(id, nombre, email, contraseña, intereses, historialContenidos, valoraciones);
        this.accesoCompleto = accesoCompleto;
        this.areasResponsabilidad = (areasResponsabilidad != null) ? areasResponsabilidad : new ListaEnlazada<>();
    }

    /**
     * Método genérico para gestionar usuarios.
     */
    public void gestionarUsuarios() {
        System.out.println("[Moderador] Gestión general de usuarios en progreso...");
    }

    /**
     * Da de alta a un usuario nuevo en la red social.
     *
     * @param usuario Usuario a dar de alta
     */
    public void darAltaUsuario(Usuario usuario) {
        System.out.println("[Moderador] Usuario dado de alta: " + usuario.getNombre());
    }

    /**
     * Da de baja a un usuario existente de la red social.
     *
     * @param usuario Usuario a dar de baja
     */
    public void darBajaUsuario(Usuario usuario) {
        System.out.println("[Moderador] Usuario dado de baja: " + usuario.getNombre());
    }

    /**
     * Modifica la información de un usuario.
     *
     * @param usuario     Usuario a modificar
     * @param nuevoNombre Nuevo nombre del usuario
     */
    public void modificarUsuario(Usuario usuario, String nuevoNombre) {
        System.out.println("[Moderador] Usuario modificado: " + usuario.getNombre() + " a " + nuevoNombre);
    }

    /**
     * Elimina un contenido de la red social.
     *
     * @param contenido Contenido a eliminar
     */
    public void eliminarContenido(Contenido contenido) {
        System.out.println("[Moderador] Contenido eliminado: " + contenido);
    }

    /**
     * Visualiza el grafo de relaciones entre los usuarios.
     */
    public void visualizarGrafo() {
        System.out.println("[Moderador] Visualizando el grafo de relaciones...");
    }

    /**
     * Genera un reporte de las comunidades detectadas en la red.
     *
     * @return Lista de comunidades, cada una representada como una lista de estudiantes
     */
    public ListaEnlazada<ListaEnlazada<Estudiante>> generarReporteComunidades() {
        return new ListaEnlazada<>();
    }

    /**
     * Genera un reporte genérico basado en el tipo proporcionado.
     *
     * @param tipo Tipo de reporte a generar
     * @return Reporte generado
     */
    public <T> Reporte<T> generarReporte(TipoReporte tipo) {
        return new Reporte<>("2", tipo, LocalDateTime.now(), new ListaEnlazada<>());
    }



    /**
     * Genera un reporte de los contenidos más valorados.
     *
     * @return Reporte de contenidos más valorados
     */
    public Reporte<Contenido> generarReporteContenidosMasValorados() {
        return new Reporte<>("2", TipoReporte.CONTENIDOS_VALORADOS, LocalDateTime.now(), new ListaEnlazada<>());
    }

    /**
     * Genera un reporte de los estudiantes más conectados.
     *
     * @return Reporte de estudiantes más conectados
     */
    public Reporte<Estudiante> generarReporteEstudiantesMasConectados() {
        return new Reporte<>("3", TipoReporte.ESTUDIANTES_CONECTADOS, LocalDateTime.now(), new ListaEnlazada<>());
    }

    /**
     * Genera un reporte de los caminos más cortos entre dos estudiantes.
     *
     * @param idOrigen  ID del estudiante origen
     * @param idDestino ID del estudiante destino
     * @return Reporte con caminos más cortos
     */
    public Reporte<String> generarReporteCaminosMasCortos(String idOrigen, String idDestino) {
        ListaEnlazada<String> datos = new ListaEnlazada<>();
        datos.agregar("Desde: " + idOrigen);
        datos.agregar("Hasta: " + idDestino);
        // Aquí podrías agregar la lógica real de caminos más cortos.
        return new Reporte<>("4", TipoReporte.CONTENIDOS_VALORADOS, LocalDateTime.now(), datos);
    }

    /**
     * Genera un reporte de niveles de participación de los estudiantes.
     *
     * @return Reporte de participación
     */
    public Reporte<String> generarReporteParticipacion() {
        return new Reporte<>("5", TipoReporte.INFORME, LocalDateTime.now(), new ListaEnlazada<>());
    }


    /**
     * Verifica si el moderador tiene acceso completo.
     *
     * @return true si tiene acceso completo, false de lo contrario
     */
    public boolean tieneAccesoCompleto() {
        return accesoCompleto;
    }

    /**
     * Establece si el moderador tiene acceso completo.
     *
     * @param accesoCompleto true si se desea acceso completo, false en caso contrario
     */
    public void setAccesoCompleto(boolean accesoCompleto) {
        this.accesoCompleto = accesoCompleto;
    }

    /**
     * Obtiene la lista de áreas de responsabilidad del moderador.
     *
     * @return Lista de áreas
     */
    public ListaEnlazada<String> getAreasResponsabilidad() {
        return areasResponsabilidad;
    }

    /**
     * Establece las áreas de responsabilidad del moderador.
     *
     * @param areasResponsabilidad Lista de áreas a asignar
     * @throws IllegalArgumentException si la lista es nula o vacía
     */
    public void setAreasResponsabilidad(ListaEnlazada<String> areasResponsabilidad) {
        if (areasResponsabilidad != null && !areasResponsabilidad.isEmpty()) {
            this.areasResponsabilidad = areasResponsabilidad;
        } else {
            throw new IllegalArgumentException("Las áreas de responsabilidad no pueden ser nulas o vacías");
        }
    }

    /**
     * Verifica si el moderador puede gestionar una determinada área.
     *
     * @param area Área a comprobar
     * @return true si puede gestionar el área, false de lo contrario
     */
    public boolean puedeGestionarArea(String area) {
        return accesoCompleto || (areasResponsabilidad != null && areasResponsabilidad.contiene(area));
    }
}
