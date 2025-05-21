package co.edu.uniquindio.redsocial.models.structures;

import java.util.HashMap;
import java.util.Map;
/**
 * Clase que representa un nodo dentro de un grafo (dirigido o no dirigido).
 * Cada nodo tiene un dato genérico y un mapa de adyacencias, donde se almacenan
 * los nodos vecinos junto con el peso de la arista que los conecta.
 *
 * @param <T> Tipo de dato que almacena el nodo.
 * @author Daniel Jurado
 * @author Sebastian Torres
 * @author Juan Soto
 * @since 2025-05-12
 */
public class NodoGrafo<T> {

    private T dato;  // Valor asociado al nodo
    private Map<NodoGrafo<T>, Double> adyacentes;  // Mapa de nodos adyacentes y sus pesos

    /**
     * Constructor que crea un nodo con el dato especificado.
     *
     * @param dato Valor o entidad que representa este nodo.
     */
    public NodoGrafo(T dato) {
        this.dato = dato;
        this.adyacentes = new HashMap<>();
    }

    /**
     * Agrega una adyacencia desde este nodo hacia otro nodo con un peso determinado.
     *
     * @param nodo Nodo adyacente.
     * @param peso Peso de la arista entre los nodos.
     */
    public void agregarAdyacente(NodoGrafo<T> nodo, Double peso) {
        adyacentes.put(nodo, peso);
    }

    /**
     * Elimina la conexión entre este nodo y el nodo especificado.
     *
     * @param nodo Nodo adyacente a eliminar.
     */
    public boolean eliminarAdyacente(NodoGrafo<T> nodo) {
        // remove() devuelve el peso (Double) si existía, o null si no
        return adyacentes.remove(nodo) != null;
    }

    /**
     * Actualiza el peso de la arista hacia un nodo adyacente.
     * Si no existe la conexión, se agregará.
     *
     * @param nodo Nodo adyacente.
     * @param peso Nuevo peso de la arista.
     */
    public void actualizarPeso(NodoGrafo<T> nodo, double peso) {
        adyacentes.put(nodo, peso);
    }

    /**
     * Obtiene el peso de la conexión hacia un nodo adyacente.
     *
     * @param nodo Nodo adyacente.
     * @return Peso de la arista o -1 si no existe conexión.
     */
    public double getPeso(NodoGrafo<T> nodo) {
        return adyacentes.getOrDefault(nodo, -1.0);
    }

    /**
     * Verifica si el nodo especificado es adyacente a este nodo.
     *
     * @param nodo Nodo a verificar.
     * @return true si es adyacente, false en caso contrario.
     */
    public boolean esAdyacente(NodoGrafo<T> nodo) {
        return adyacentes.containsKey(nodo);
    }

    /**
     * Devuelve una representación en cadena del nodo y sus adyacentes.
     *
     * @return Cadena con el dato y los vecinos con sus pesos.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Nodo: ").append(dato).append("\nAdyacentes: ");
        for (Map.Entry<NodoGrafo<T>, Double> entry : adyacentes.entrySet()) {
            sb.append(entry.getKey().dato)
                    .append(" (Peso: ").append(entry.getValue()).append("), ");
        }
        return sb.toString();
    }
    /**
     * Compara este nodo con otro objeto para verificar si son equivalentes.
     * Dos nodos son iguales si contienen el mismo dato.
     *
     * @param obj El objeto a comparar.
     * @return true si ambos objetos representan el mismo nodo; false en caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        NodoGrafo<?> otro = (NodoGrafo<?>) obj;
        return dato.equals(otro.dato);
    }
    /**
     * Genera un código hash para este nodo basado en su dato.
     *
     * @return El código hash del dato contenido en el nodo.
     */
    @Override
    public int hashCode() {
        return dato.hashCode();
    }

    // Getters y Setters

    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public Map<NodoGrafo<T>, Double> getAdyacentes() {
        return adyacentes;
    }

    public void setAdyacentes(Map<NodoGrafo<T>, Double> adyacentes) {
        this.adyacentes = adyacentes;
    }

    /**
     * Devuelve el peso de la arista entre este nodo y el nodo dado,
     * o -1 si no existe conexión.
     */
    public double getPesoArista(NodoGrafo<T> nodo) {
        // redirige a tu método getPeso existente
        return getPeso(nodo);
    }

    /**
     * Calcula el grado (ponderado) de este nodo,
     * es decir, la suma de los pesos de todas sus aristas salientes.
     */
    public double getGrado() {
        double suma = 0.0;
        for (Double peso : adyacentes.values()) {
            suma += peso;
        }
        return suma;
    }

}
