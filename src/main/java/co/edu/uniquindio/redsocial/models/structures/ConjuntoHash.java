package co.edu.uniquindio.redsocial.models.structures;

/**
 * Conjunto genérico propio (sin elementos repetidos) construido sobre {@link TablaHash}.
 * Reemplaza el uso de {@code java.util.HashSet}.
 *
 * @param <T> Tipo de los elementos (debe implementar equals/hashCode de forma consistente).
 */
public class ConjuntoHash<T> {

    private final TablaHash<T, Boolean> tabla = new TablaHash<>();

    /** @return true si el elemento se agregó, false si ya estaba en el conjunto. */
    public boolean agregar(T elemento) {
        return tabla.ponerSiAusente(elemento, Boolean.TRUE);
    }

    public boolean contiene(T elemento) {
        return tabla.contieneClave(elemento);
    }

    /** @return true si el elemento estaba y fue eliminado. */
    public boolean eliminar(T elemento) {
        return tabla.contieneClave(elemento) && tabla.eliminar(elemento) != null;
    }

    public int tamanio() {
        return tabla.tamanio();
    }

    public boolean estaVacio() {
        return tabla.estaVacia();
    }

    /** @return Lista enlazada con los elementos del conjunto (sin orden garantizado). */
    public ListaEnlazada<T> elementos() {
        return tabla.claves();
    }
}
