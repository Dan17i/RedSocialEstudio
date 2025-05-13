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
    }
    /**
     * Lista todos los contenidos relacionados con un tema específico.
     * Internamente reutiliza la búsqueda por clave.
     *
     * @param tema Tema por el cual se listan los contenidos.
     * @return Lista de contenidos relacionados con el tema.
     */
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
        this.raiz = raiz;
    }
}
