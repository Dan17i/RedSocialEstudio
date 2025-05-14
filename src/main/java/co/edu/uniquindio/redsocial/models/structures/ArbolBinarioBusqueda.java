package co.edu.uniquindio.redsocial.models.structures;

/**
 * Clase que representa un Árbol Binario de Búsqueda (ABB) genérico,
 * utilizado para almacenar pares clave-valor donde la clave es un String
 * y el valor es de tipo genérico Contenido.
 *
 * @author Daniel Jurado
 * @author Sebastian Torres
 * @author Juan Soto
 * @since 2025-05-12
 * @param <Contenido>> El tipo de los valores almacenados en el árbol.
 */

public class ArbolBinarioBusqueda<Contenido> {

    private NodoABB<Contenido> raiz;

    public ArbolBinarioBusqueda(NodoABB<Contenido> raiz) {

        this.raiz = raiz;
    }
    public ArbolBinarioBusqueda() {
        this.raiz = null;
    }

    public void insertar(String clave, Contenido valor) {
        raiz = insertarRecursivo(raiz, clave, valor);
    }
    private NodoABB<Contenido> insertarRecursivo(NodoABB<Contenido> nodo, String clave, Contenido valor) {
        if (nodo == null) {
            return new NodoABB<>(clave, valor); // Crear nuevo nodo si llegamos a una hoja nula
        }

        int comparacion = clave.compareTo(nodo.getClave());

        if (comparacion < 0) {
            nodo.setIzquierda(insertarRecursivo(nodo.getIzquierda(), clave, valor));
        } else if (comparacion > 0) {
            nodo.setDerecha(insertarRecursivo(nodo.getDerecha(), clave, valor));
        } else {
            // Si la clave ya existe, se actualiza el valor (opcional)
            nodo.setValor(valor);
        }

        return nodo;
    }


    public ListaEnlazada<Contenido> buscar(String clave) {
        return new ListaEnlazada<>();
        
    }

    public NodoABB<Contenido> getRaiz() {
        return raiz;
    }

    public void setRaiz(NodoABB<Contenido> raiz) {

        this.raiz = raiz;
    }

    public ListaEnlazada<Contenido> listarTodos() {
        ListaEnlazada<Contenido> lista = new ListaEnlazada<>();
        listarEnOrden(raiz, lista);
        return lista;
    }

    private void listarEnOrden(NodoABB<Contenido> nodo, ListaEnlazada<Contenido> lista) {
        if (nodo != null) {
            listarEnOrden(nodo.getIzquierda(), lista);
            lista.agregar(nodo.getValor());
            listarEnOrden(nodo.getDerecha(), lista);
        }
    }

    public void eliminar(String tema) {
        raiz = eliminarRecursivo(raiz, tema);
    }

    private NodoABB<Contenido> eliminarRecursivo(NodoABB<Contenido> nodo, String clave) {
        if (nodo == null) {
            return null;
        }

        int comparacion = clave.compareTo(nodo.getClave());

        if (comparacion < 0) {
            nodo.setIzquierda(eliminarRecursivo(nodo.getIzquierda(), clave));
        } else if (comparacion > 0) {
            nodo.setDerecha(eliminarRecursivo(nodo.getDerecha(), clave));
        } else {
            // Nodo encontrado
            if (nodo.getIzquierda() == null) {
                return nodo.getDerecha();
            } else if (nodo.getDerecha() == null) {
                return nodo.getIzquierda();
            }

            // Nodo con dos hijos
            NodoABB<Contenido> sucesor = encontrarMinimo(nodo.getDerecha());
            nodo.setClave(sucesor.getClave());
            nodo.setValor(sucesor.getValor());
            nodo.setDerecha(eliminarRecursivo(nodo.getDerecha(), sucesor.getClave()));
        }

        return nodo;
    }

    private NodoABB<Contenido> encontrarMinimo(NodoABB<Contenido> nodo) {
        while (nodo.getIzquierda() != null) {
            nodo = nodo.getIzquierda();
        }
        return nodo;
    }

    public ListaEnlazada<Contenido> listarContenidosPorTema(String tema) {
        return buscar(tema);
    }
}
