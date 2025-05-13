package co.edu.uniquindio.redsocial.models;
/**
 * Clase que representa un Árbol Binario de Búsqueda (ABB) genérico,
 * utilizado para almacenar pares clave-valor donde la clave es un String
 * y el valor es de tipo genérico Contenido.
 *
 * @author Daniel Jurado
 * @author Sebastian Torres
 * @author Juan Soto
 * @since 2025-05-12
 * @param <T> El tipo de los valores almacenados en el árbol.
 */
 
class ArbolBinarioBusqueda<T> {
    /**
     * Nodo raíz del árbol.
     */
    private NodoABB<T> raiz;
    /**
     * Constructor que inicializa el árbol con una raíz dada.
     *
     * @param raiz Nodo raíz del árbol.
     */
    public ArbolBinarioBusqueda(NodoABB<T> raiz) {
        this.raiz = raiz;
    }
    /**
     * Inserta un nuevo contenido en el árbol asociándolo a una clave.
     *
     * @param clave Clave única con la que se indexará el contenido.
     * @param valor Contenido a insertar en el árbol.
     */
    public void insertar(String clave, T valor) {
        raiz= insertarRec(raiz,clave,valor);
    }

    private NodoABB<T> insertarRec(NodoABB<T> nodo,String clave, T valor) {
        if (nodo == null) {
            return new NodoABB<>(clave, valor, null,null);
        }
        if(clave.compareTo(nodo.getClave())<0) {
            nodo.setIzquierda(insertarRec(nodo.getIzquierda(),clave,valor));
        } else if (clave.compareTo(nodo.getClave())>0) {
            nodo.setDerecha(insertarRec(nodo.getDerecha(),clave,valor));
        }
        return nodo;
    }
    /**
     * Busca y devuelve una lista de contenidos asociados a una clave dada.
     *
     * @param clave Clave de búsqueda.
     * @return Lista de contenidos encontrados (puede estar vacía).
     */
    public ListaEnlazada<T> buscar(String clave) {
        ListaEnlazada<T> resultados = new ListaEnlazada<>();
        buscarRec(raiz,clave,resultados);
        return resultados;
    }

    private void buscarRec(NodoABB<T> nodo, String clave, ListaEnlazada<T> resultados) {

        if (nodo == null) return;

        if(nodo.getClave().startsWith(clave)) {
            resultados.agregar(nodo.getValor());
        }

        if(clave.compareTo(nodo.getClave())<0) {
            buscarRec(nodo.getIzquierda(),clave,resultados);
        }

        if(clave.compareTo(nodo.getClave())>0) {
            buscarRec(nodo.getDerecha(),clave,resultados);
        }

public class ArbolBinarioBusqueda<Contenido> {

    private NodoABB<Contenido> raiz;

    public ArbolBinarioBusqueda(NodoABB<Contenido> raiz) {
        this.raiz = raiz;
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

    public ListaEnlazada<Contenido> listarContenidosPorTema(String tema) {
        ListaEnlazada<Contenido> resultados = new ListaEnlazada<>();
        listarContenidosRec(raiz,tema,resultados);
        return resultados;
    }

    private void listarContenidosRec(NodoABB<T> nodo, String tema, ListaEnlazada<Contenido> resultados) {
        if (nodo == null) return;

        Contenido contenido = (Contenido) nodo.getValor();
        if (contenido.getTema().toLowerCase().contains(tema.toLowerCase())){
            resultados.agregar(contenido);
        }

        listarContenidosRec(nodo.getIzquierda(),tema,resultados);
        listarContenidosRec(nodo.getDerecha(),tema,resultados);
    }

   
    /**
     @param autor Autor por el cual se listan los contenidos.
     @return Lista de contenidos relacionados con el autor.
     */
    public ListaEnlazada<T> listarContenidosPorAutor(String autor) {return buscar(autor);}
    /**
     * Devuelve la raíz del árbol.
     *
     * @return Nodo raíz del árbol.
     */
    public NodoABB<T> getRaiz() {
        return raiz;
    }
    /**
     * Establece la raíz del árbol.
     *
     * @param raiz Nueva raíz del árbol.
     */
    public void setRaiz(NodoABB<T> raiz) {

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
}
