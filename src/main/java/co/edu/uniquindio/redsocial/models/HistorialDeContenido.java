package co.edu.uniquindio.redsocial.models;

import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import co.edu.uniquindio.redsocial.models.structures.NodoLista;

import java.time.LocalDateTime;

/**
 * Clase que representa el historial de acceso a contenidos por parte de un usuario,
 * manteniendo sincronizadas las listas de contenidos y fechas de acceso correspondientes.
 *
 * Cada contenido accedido está vinculado a su respectiva fecha en la misma posición de ambas listas.
 *
 * @author
 * Daniel Jurado, Sebastian Torres, Juan Soto
 * @since 2025-05-12
 */
public class HistorialDeContenido {

    private String idUsuario;
    private ListaEnlazada<Contenido> contenidos;
    private ListaEnlazada<LocalDateTime> fechasAcceso;

    /**
     * Constructor del historial de contenido.
     *
     * @param idUsuario     ID del usuario asociado.
     * @param contenidos    Lista de contenidos accedidos.
     * @param fechasAcceso  Lista de fechas correspondientes al acceso de cada contenido.
     */
    public HistorialDeContenido(String idUsuario,
                                ListaEnlazada<Contenido> contenidos,
                                ListaEnlazada<LocalDateTime> fechasAcceso) {
        this.idUsuario = idUsuario;
        this.contenidos = (contenidos != null) ? contenidos : new ListaEnlazada<>();
        this.fechasAcceso = (fechasAcceso != null) ? fechasAcceso : new ListaEnlazada<>();
    }

    /**
     * Agrega un nuevo contenido al historial con su fecha de acceso,
     * asegurando que ambas listas mantengan sincronización.
     *
     * @param contenido Contenido accedido.
     * @param fecha     Fecha de acceso.
     */
    public void agregarContenido(Contenido contenido, LocalDateTime fecha) {
        if (contenido != null && fecha != null) {
            contenidos.agregar(contenido);
            fechasAcceso.agregar(fecha);
        }
    }

    /**
     * Elimina un contenido específico del historial, junto con su fecha correspondiente.
     * Si el contenido aparece varias veces, solo se elimina la primera ocurrencia.
     *
     * @param contenido Contenido a eliminar.
     * @return true si se eliminó, false si no se encontró.
     */
    public boolean eliminarContenido(Contenido contenido) {
        if (contenido == null) return false;

        NodoLista<Contenido> actual = contenidos.getCabeza();
        NodoLista<LocalDateTime> actualFecha = fechasAcceso.getCabeza();
        int posicion = 0;

        while (actual != null && actualFecha != null) {
            if (contenido.equals(actual.getDato())) {
                contenidos.eliminarEn(posicion);
                fechasAcceso.eliminarEn(posicion);
                return true;
            }
            actual = actual.getSiguiente();
            actualFecha = actualFecha.getSiguiente();
            posicion++;
        }

        return false;
    }


    /**
     * Filtra los contenidos del historial por tema.
     *
     * @param tema Tema a filtrar.
     * @return Lista con los contenidos que coinciden con el tema.
     */
    public ListaEnlazada<Contenido> filtrarPorTema(String tema) {
        ListaEnlazada<Contenido> resultado = new ListaEnlazada<>();
        NodoLista<Contenido> actual = contenidos.getCabeza();
        while (actual != null) {
            Contenido c = actual.getDato();
            if (c != null && c.getTema().equalsIgnoreCase(tema)) {
                resultado.agregar(c);
            }
            actual = actual.getSiguiente();
        }
        return resultado;
    }

    /**
     * Invierte el orden de los contenidos y sus fechas de acceso, manteniendo la correspondencia entre ambos.
     */
    public void invertirHistorial() {
        contenidos.invertir();
        fechasAcceso.invertir();
    }

    /**
     * Clona el historial de contenido.
     *
     * @return Un nuevo objeto de tipo {@link HistorialDeContenido} con los mismos datos,
     * pero con listas independientes.
     */
    public HistorialDeContenido clonarHistorial() {
        return new HistorialDeContenido(idUsuario, contenidos.clonar(), fechasAcceso.clonar());
    }

    /**
     * Devuelve los últimos 'n' contenidos accedidos por el usuario.
     *
     * @param cantidad Número máximo de contenidos a recuperar.
     * @return Lista con los últimos contenidos accedidos.
     */
    public ListaEnlazada<Contenido> obtenerContenidosRecientes(int cantidad) {
        ListaEnlazada<Contenido> recientes = new ListaEnlazada<>();

        int total = contenidos.getTamanio();
        int desde = Math.max(0, total - cantidad);

        for (int i = desde; i < total; i++) {
            recientes.agregar(contenidos.obtener(i));
        }

        return recientes;
    }

    /**
     * Verifica que ambas listas del historial tengan el mismo tamaño.
     *
     * @return true si están sincronizadas, false si hay una discrepancia.
     */
    public boolean validarSincronizacion() {
        return contenidos.getTamanio() == fechasAcceso.getTamanio();
    }
    /**
     * Obtiene una lista con los últimos n contenidos agregados al historial.
     * Si n es mayor que el número total de contenidos, devuelve todos los contenidos.
     *
     * @param n el número de contenidos recientes a obtener
     * @return una ListaEnlazada con los últimos n contenidos en orden de inserción
     */
    public ListaEnlazada<Contenido> obtenerUltimos(int n) {
        ListaEnlazada<Contenido> ultimos = new ListaEnlazada<>();

        int total = this.contenidos.getTamanio();  // supongo que 'contenidos' es la lista interna

        if (n > total) {
            n = total;
        }

        // Obtener los últimos n elementos
        for (int i = total - n; i < total; i++) {
            ultimos.agregar(this.contenidos.obtener(i));
        }

        return ultimos;
    }

    // Getters y Setters

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public ListaEnlazada<Contenido> getContenidos() {
        return contenidos;
    }

    public void setContenidos(ListaEnlazada<Contenido> contenidos) {
        this.contenidos = (contenidos != null) ? contenidos : new ListaEnlazada<>();
    }

    public ListaEnlazada<LocalDateTime> getFechasAcceso() {
        return fechasAcceso;
    }

    public void setFechasAcceso(ListaEnlazada<LocalDateTime> fechasAcceso) {
        this.fechasAcceso = (fechasAcceso != null) ? fechasAcceso : new ListaEnlazada<>();
    }

    @Override
    public String toString() {
        return "HistorialDeContenido{" +
                "idUsuario='" + idUsuario + '\'' +
                ", totalContenidos=" + contenidos.getTamanio() +
                '}';
    }
}
