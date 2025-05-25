package co.edu.uniquindio.redsocial.models;

import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import co.edu.uniquindio.redsocial.models.structures.NodoLista;

import java.time.LocalDateTime;

/**
 * Clase que representa el historial de acceso a contenidos por parte de un usuario.
 * @author Daniel Jurado
 * @author Sebastian Torres
 * @author Juan Soto
 * @since 2025-05-12
 */
public class HistorialDeContenido {

    private String idUsuario;
    private ListaEnlazada<Contenido> contenidos;
    private ListaEnlazada<LocalDateTime> fechasAcceso;

    /**
     * Constructor del historial de contenido.
     *
     * @param idUsuario ID del usuario asociado.
     * @param contenidos Lista de contenidos accedidos.
     * @param fechasAcceso Lista de fechas correspondientes al acceso de cada contenido.
     */
    public HistorialDeContenido(String idUsuario,
                                ListaEnlazada<Contenido> contenidos,
                                ListaEnlazada<LocalDateTime> fechasAcceso) {
        this.idUsuario = idUsuario;
        this.contenidos = (contenidos != null) ? contenidos : new ListaEnlazada<>();
        this.fechasAcceso = (fechasAcceso != null) ? fechasAcceso : new ListaEnlazada<>();
    }

    /**
     * Agrega un nuevo contenido al historial con su fecha de acceso.
     *
     * @param contenido Contenido accedido.
     * @param fecha Fecha de acceso.
     */
    public void agregarContenido(Contenido contenido, LocalDateTime fecha) {
        if (contenido != null && fecha != null) {
            contenidos.agregar(contenido);
            fechasAcceso.agregar(fecha);
        }
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
     * Devuelve los últimos 'n' contenidos accedidos por el usuario.
     *
     * @param n Número de contenidos a recuperar.
     * @return Lista con los últimos 'n' contenidos.
     */
    public ListaEnlazada<Contenido> obtenerUltimos(int n) {
        ListaEnlazada<Contenido> resultado = new ListaEnlazada<>();
        int total = contenidos.getTamanio();
        int inicio = Math.max(0, total - n);
        NodoLista<Contenido> actual = contenidos.getCabeza();
        int index = 0;
        while (actual != null) {
            if (index >= inicio) {
                resultado.agregar(actual.getDato());
            }
            actual = actual.getSiguiente();
            index++;
        }
        return resultado;
    }

    /**
     * Invierte el orden de los contenidos y sus fechas de acceso.
     * <p>
     * Este método invierte la lista de contenidos, así como la lista de fechas de acceso
     * para reflejar un orden cronológico inverso. Es útil cuando se desea ver los contenidos
     * más recientes primero, como un historial en orden descendente.
     */
    public void invertirHistorial() {
        contenidos.invertir();   // Invierte la lista de contenidos
        fechasAcceso.invertir(); // Invierte la lista de fechas
    }

    /**
     * Clona el historial de contenido.
     * <p>
     * Este método crea una copia exacta del historial de contenido, incluyendo los contenidos
     * y las fechas de acceso, para que puedan ser manipulados independientemente del original.
     *
     * @return Un nuevo objeto de tipo {@link HistorialDeContenido} con los mismos datos
     */
    public HistorialDeContenido clonarHistorial() {
        return new HistorialDeContenido(idUsuario, contenidos.clonar(), fechasAcceso.clonar());
    }

    /**
     * Obtiene una lista enlazada con los contenidos más recientes almacenados.
     *
     * Este método retorna los últimos {@code cantidad} contenidos agregados, o
     * todos los contenidos si la cantidad solicitada es mayor que el total disponible.
     * La lista se construye en orden cronológico desde el contenido más antiguo de los recientes
     * hasta el más nuevo.
     *
     * @param cantidad el número máximo de contenidos recientes a obtener.
     * @return una {@link ListaEnlazada} que contiene los contenidos recientes.
     */
    public ListaEnlazada<Contenido> obtenerContenidosRecientes(int cantidad) {
        ListaEnlazada<Contenido> recientes = new ListaEnlazada<>();

        int total = contenidos.getTamanio(); // total de elementos en el historial

        // Si la cantidad pedida es mayor al total, ajusta para no dar error
        int desde = Math.max(0, total - cantidad);

        for (int i = desde; i < total; i++) {
            recientes.agregar(contenidos.obtener(i));
        }

        return recientes;
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
