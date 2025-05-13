package co.edu.uniquindio.redsocial.models;
/**
 * Clase genérica que representa una cola de prioridad basada en una lista enlazada.
 * Los elementos se encolan junto con una prioridad, y se atienden en orden según esa prioridad.
 *
 * @author Daniel Jurado
 * @author Sebastian Torres
 * @author Juan Soto
 * @since 2025-05-12
 * @param <T> Tipo de los elementos almacenados en la cola.
 */
public class ColaPrioridad<T> {
    private ListaEnlazada<T> elementos = new ListaEnlazada<>();
    /**
     * Constructor que inicializa la cola con una lista enlazada existente.
     *
     * @param elementos Lista enlazada con los elementos iniciales de la cola.
     */
    public ColaPrioridad(ListaEnlazada<T> elementos) {
        this.elementos = elementos;
    }
    /**
     * Agrega un elemento a la cola con una prioridad dada.
     * La lógica de inserción debe asegurarse de mantener el orden según la prioridad.
     *
     * @param elemento  Elemento a agregar.
     * @param prioridad Prioridad del elemento (a menor valor, mayor prioridad, por ejemplo).
     *
     * TODO: Implementar la lógica de ordenamiento por prioridad al insertar.
     */
    public void encolar(T elemento, int prioridad) {
        NodoPrioridad<T> nuevo = new NodoPrioridad<>(elemento, prioridad);
        int index = 0;
        while (index < elementos.getTamanio() && ((NodoPrioridad<T>) elementos.obtener(index)).getPrioridad() >= prioridad) {
            index++;
        }
        elementos.insertarEn(index, (T) nuevo);
    }
    /**
     * Elimina y retorna el primer elemento de la cola (de mayor prioridad).
     * En esta versión simplificada, simplemente retorna el primer elemento de la lista.
     *
     * @return Elemento de mayor prioridad (o el primero de la lista actual).
     */
    public T desencolar() {
        return elementos.obtener(0);
    }
    /**
     * Retorna la lista enlazada de elementos actuales en la cola.
     *
     * @return Lista de elementos.
     */
    public ListaEnlazada<T> getElementos() {
        return elementos;
    }
    /**
     * Reemplaza la lista de elementos actual por una nueva.
     *
     * @param elementos Nueva lista de elementos.
     */
    public void setElementos(ListaEnlazada<T> elementos) {
        this.elementos = elementos;
    }
}
