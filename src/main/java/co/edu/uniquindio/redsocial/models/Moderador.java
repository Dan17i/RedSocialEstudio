package co.edu.uniquindio.redsocial.models;

import co.edu.uniquindio.redsocial.models.Enums.TipoReporte;
import co.edu.uniquindio.redsocial.models.services.interf.IGestorRedSocial;
import co.edu.uniquindio.redsocial.models.services.interf.IGestorUsuarios;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import co.edu.uniquindio.redsocial.models.services.implement.GestorContenidos;


import java.time.LocalDateTime;

/**
 * Clase que representa un moderador en la red social.
 * Un moderador tiene acceso a gestionar usuarios, eliminar contenido y generar reportes.
 * Aplica principios de separación de responsabilidades, acoplamiento controlado y eficiencia en generación de reportes.
 *
 * @author Daniel Jurado, Sebastian Torres y Juan Soto
 * @since 2025-04-02
 */
public class Moderador extends Usuario {

    private boolean accesoCompleto;
    private ListaEnlazada<String> areasResponsabilidad;

    private IGestorUsuarios gestorUsuarios;
    private GestorContenidos gestorContenidos;
    private IGestorRedSocial gestorRedSocial;

    /**
     * Constructor del moderador con inyección de gestores.
     *
     * @param id                    iD del moderador
     * @param nombre                Nombre del moderador
     * @param email                 Correo electrónico
     * @param contraseña            Contraseña
     * @param intereses             Intereses
     * @param historialContenidos  Historial de contenidos
     * @param valoraciones          Valoraciones realizadas
     * @param accesoCompleto        Acceso total
     * @param areasResponsabilidad  Áreas que puede gestionar
     * @param gestorUsuarios        Referencia al gestor de usuarios
     * @param gestorContenidos      Referencia al gestor de contenidos
     * @param gestorRedSocial       Referencia al gestor de la red social
     */
    public Moderador(String id, String nombre, String email, String contraseña,
                     ListaEnlazada<String> intereses,
                     ListaEnlazada<Contenido> historialContenidos,
                     ListaEnlazada<Valoracion> valoraciones,
                     boolean accesoCompleto,
                     ListaEnlazada<String> areasResponsabilidad,
                     IGestorUsuarios gestorUsuarios,
                     GestorContenidos gestorContenidos,
                     IGestorRedSocial gestorRedSocial) {

        super(id, nombre, email, contraseña, intereses, historialContenidos, valoraciones);
        this.accesoCompleto = accesoCompleto;
        this.areasResponsabilidad = (areasResponsabilidad != null) ? areasResponsabilidad : new ListaEnlazada<>();
        this.gestorUsuarios = gestorUsuarios;
        this.gestorContenidos = gestorContenidos;
        this.gestorRedSocial = gestorRedSocial;
    }

    public void gestionarUsuarios() {
        gestorUsuarios.listarUsuarios().forEach(usuario ->
                System.out.println("Usuario: " + usuario.getNombre()));
    }

    public void darAltaUsuario(Usuario usuario) {
        gestorUsuarios.registrarUsuario(usuario);
        System.out.println("[Moderador] Usuario dado de alta: " + usuario.getNombre());
    }

    public void darBajaUsuario(Usuario usuario) {
        gestorUsuarios.eliminarUsuario(usuario.getId());
        System.out.println("[Moderador] Usuario dado de baja: " + usuario.getNombre());
    }

    public void modificarUsuario(Usuario usuario, String nuevoNombre) {
        usuario.setNombre(nuevoNombre);
        gestorUsuarios.actualizarUsuario(usuario);
        System.out.println("[Moderador] Usuario modificado a: " + nuevoNombre);
    }

    public void eliminarContenido(Contenido contenido) {
        gestorContenidos.eliminarContenido(contenido.getId());
        System.out.println("[Moderador] Contenido eliminado: " + contenido.getTema());
    }

    public void visualizarGrafo() {
        gestorRedSocial.visualizarGrafoUsuarios(); // método asumido en gestorRedSocial
    }

    public ListaEnlazada<ListaEnlazada<Estudiante>> generarReporteComunidades() {
        return gestorRedSocial.detectarComunidades();
    }
    /**
     * Genera un reporte genérico basado en el tipo especificado.
     *
     * @param <T>  Tipo de los datos que contendrá el reporte.
     * @param tipo Tipo de reporte a generar, según los valores del enum {@link TipoReporte}.
     *             Puede ser uno de:
     *             <ul>
     *               <li>ESTUDIANTES_CONECTADOS: Reporte con los estudiantes más conectados.</li>
     *               <li>CONTENIDOS_VALORADOS: Reporte con los contenidos más valorados.</li>
     *               <li>INFORME: Reporte genérico sin datos específicos (vacío por ahora).</li>
     *             </ul>
     * @return Un objeto {@link Reporte} que contiene un identificador, tipo, fecha de generación y la lista de datos.
     * @throws IllegalArgumentException Si se pasa un tipo de reporte no soportado.
     */
    public <T> Reporte<T> generarReporte(TipoReporte tipo) {
        ListaEnlazada<T> datos = switch (tipo) {
            case ESTUDIANTES_CONECTADOS -> (ListaEnlazada<T>) gestorRedSocial.obtenerEstudiantesMasConectados();
            case CONTENIDOS_VALORADOS -> (ListaEnlazada<T>) gestorContenidos.obtenerContenidosMasValorados();
            case INFORME -> new ListaEnlazada<>();
            default -> throw new IllegalArgumentException("Tipo de reporte no soportado: " + tipo);
        };
        return new Reporte<>("GEN-" + tipo.name(), tipo, LocalDateTime.now(), datos);
    }

    public Reporte<Contenido> generarReporteContenidosMasValorados() {
        ListaEnlazada<Contenido> datos = gestorContenidos.obtenerContenidosMasValorados();
        return new Reporte<>("CONT-01", TipoReporte.CONTENIDOS_VALORADOS, LocalDateTime.now(), datos);
    }

    public Reporte<Estudiante> generarReporteEstudiantesMasConectados() {
        ListaEnlazada<Estudiante> datos = gestorRedSocial.obtenerEstudiantesMasConectados();
        if (datos == null) {
            datos = new ListaEnlazada<>();
        }
        return new Reporte<>("EST-01", TipoReporte.ESTUDIANTES_CONECTADOS, LocalDateTime.now(), datos);
    }


    public Reporte<String> generarReporteCaminosMasCortos(String idOrigen, String idDestino) {
        ListaEnlazada<String> caminos = gestorRedSocial.calcularCaminosMasCortos(idOrigen, idDestino);
        if (caminos == null) {
            caminos = new ListaEnlazada<>();
        }
        return new Reporte<>("CAM-01", TipoReporte.INFORME, LocalDateTime.now(), caminos);
    }


    public Reporte<String> generarReporteParticipacion() {
        ListaEnlazada<String> niveles = gestorRedSocial.obtenerNivelesParticipacion();
        if (niveles == null) {
            niveles = new ListaEnlazada<>();
        }
        return new Reporte<>("PAR-01", TipoReporte.INFORME, LocalDateTime.now(), niveles);
    }


    public boolean tieneAccesoCompleto() {
        return accesoCompleto;
    }

    public void setAccesoCompleto(boolean accesoCompleto) {
        this.accesoCompleto = accesoCompleto;
    }

    public ListaEnlazada<String> getAreasResponsabilidad() {
        return areasResponsabilidad;
    }

    public void setAreasResponsabilidad(ListaEnlazada<String> areasResponsabilidad) {
        if (areasResponsabilidad != null && !areasResponsabilidad.isEmpty()) {
            this.areasResponsabilidad = areasResponsabilidad;
        } else {
            throw new IllegalArgumentException("Las áreas de responsabilidad no pueden ser nulas o vacías");
        }
    }

    public boolean puedeGestionarArea(String area) {
        return accesoCompleto || (areasResponsabilidad != null && areasResponsabilidad.contiene(area));
    }
}
