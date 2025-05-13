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
 * @param <Contenido> El tipo de los valores almacenados en el árbol.
 */
class ArbolBinarioBusqueda<Contenido> {
    /**
     * Nodo raíz del árbol.
     */
    private NodoABB<Contenido> raiz;
    /**
     * Constructor que inicializa el árbol con una raíz dada.
     *
     * @param raiz Nodo raíz del árbol.
     */
    public ArbolBinarioBusqueda(NodoABB<Contenido> raiz) {
        this.raiz = raiz;
    }
    /**
     * Inserta un nuevo contenido en el árbol asociándolo a una clave.
     *
     * @param clave Clave única con la que se indexará el contenido.
     * @param valor Contenido a insertar en el árbol.
     */
    public void insertar(String clave, Contenido valor) {
        // Lógica de inserción pendiente de implementación
    }
    /**
     * Busca y devuelve una lista de contenidos asociados a una clave dada.
     *
     * @param clave Clave de búsqueda.
     * @return Lista de contenidos encontrados (puede estar vacía).
     */
    public ListaEnlazada<Contenido> buscar(String clave) {
        // Por ahora devuelve una lista vacía
        return new ListaEnlazada<>();
    }
    /**
     * Lista todos los contenidos relacionados con un tema específico.
     * Internamente reutiliza la búsqueda por clave.
     *
     * @param tema Tema por el cual se listan los contenidos.
     * @return Lista de contenidos relacionados con el tema.
     */
    public ListaEnlazada<Contenido> listarContenidosPorTema(String tema) {
        return buscar(tema);
    }
    /**
     * Devuelve la raíz del árbol.
     *
     * @return Nodo raíz del árbol.
     */
    public NodoABB<Contenido> getRaiz() {
        return raiz;
    }
    /**
     * Establece la raíz del árbol.
     *
     * @param raiz Nueva raíz del árbol.
     */
    public void setRaiz(NodoABB<Contenido> raiz) {
        this.raiz = raiz;
    }
}
