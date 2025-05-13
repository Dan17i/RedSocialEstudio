package co.edu.uniquindio.redsocial.models;

/**
 * Clase que representa un nodo en un grafo dirigido o no dirigido.
 * Cada nodo tiene un valor de tipo T y una lista de nodos adyacentes con sus respectivos pesos.
 *
 * @param <T> Tipo de dato que almacena el nodo.
 * @author Daniel Jurado
 * @author Sebastian Torres
 * @author Juan Soto
 * @since 2025-05-12
 */
class NodoGrafo<T> {
    private T dato;                                  // Valor asociado al nodo
    private ListaEnlazada<NodoGrafo<T>> adyacentes;   // Lista de nodos adyacentes
    private ListaEnlazada<Double> pesos;             // Lista de pesos de las aristas
    /**
     * Constructor que crea un nodo con un valor y inicializa las listas de adyacentes y pesos.
     *
     * @param dato Valor del nodo.
     */
    public NodoGrafo(T dato) {
        this.dato = dato;
        this.adyacentes = new ListaEnlazada<>();
        this.pesos = new ListaEnlazada<>();
    }
    /**
     * Agrega un nodo adyacente con su peso a la lista de adyacentes.
     *
     * @param nodo Nodo adyacente a agregar.
     * @param peso Peso de la arista entre el nodo actual y el nodo adyacente.
     */
    public void agregarAdyacente(NodoGrafo<T> nodo, Double peso) {
        adyacentes.agregar(nodo);
        pesos.agregar(peso);
    }
    /**
     * Actualiza el peso de la arista entre el nodo actual y el nodo adyacente especificado.
     *
     * @param nodo Nodo adyacente cuya arista se actualizará.
     * @param peso Nuevo peso de la arista.
     */
    public void actualizarPeso(NodoGrafo<T> nodo, double peso) {
        // Obtener el índice del nodo adyacente
        int indice = adyacentes.obtenerIndice(nodo);
        if (indice != -1) {  // Si el nodo adyacente existe en la lista
            pesos.set(indice, peso);  // Actualizamos el peso en la lista de pesos
        }
    }

    /**
     * Elimina un nodo adyacente y su peso de las listas correspondientes.
     *
     * @param nodo Nodo adyacente a eliminar.
     */
    public void eliminarAdyacente(NodoGrafo<T> nodo) {
        int indice = adyacentes.obtenerIndice(nodo);
        if (indice != -1) {
            adyacentes.eliminar(indice);  // Eliminar nodo adyacente
            pesos.eliminar(indice);  // Eliminar peso asociado
        }
    }

    /**
     * Obtiene el peso de la arista entre el nodo actual y un nodo adyacente.
     *
     * @param nodo Nodo adyacente cuyo peso se quiere obtener.
     * @return Peso de la arista entre el nodo actual y el nodo adyacente, o -1 si no está adyacente.
     */
    public double getPeso(NodoGrafo<T> nodo) {
        int indice = adyacentes.obtenerIndice(nodo);
        if (indice != -1) {
            return pesos.obtener(indice);  // Devolver peso correspondiente
        }
        return -1;  // Retornar -1 si el nodo no es adyacente
    }

    /**
     * Verifica si un nodo es adyacente al nodo actual.
     *
     * @param nodo Nodo a verificar si es adyacente.
     * @return true si el nodo es adyacente, false de lo contrario.
     */
    public boolean esAdyacente(NodoGrafo<T> nodo) {
        return adyacentes.obtenerIndice(nodo) != -1;  // Si el nodo existe en la lista de adyacentes
    }

    /**
     * Devuelve una representación en cadena del nodo y sus adyacentes con sus pesos.
     *
     * @return Cadena que representa el nodo y sus adyacentes.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nodo: ").append(dato.toString()).append("\nAdyacentes: ");
        for (int i = 0; i < adyacentes.getTamanio(); i++) {
            sb.append(adyacentes.obtener(i).getDato().toString())
                    .append(" (Peso: ").append(pesos.obtener(i)).append(") ");
        }
        return sb.toString();
    }

    // Getters y Setters
    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public ListaEnlazada<NodoGrafo<T>> getAdyacentes() {
        return adyacentes;
    }

    public void setAdyacentes(ListaEnlazada<NodoGrafo<T>> adyacentes) {
        this.adyacentes = adyacentes;
    }

    public ListaEnlazada<Double> getPesos() {
        return pesos;
    }

    public void setPesos(ListaEnlazada<Double> pesos) {
        this.pesos = pesos;
    }
}
