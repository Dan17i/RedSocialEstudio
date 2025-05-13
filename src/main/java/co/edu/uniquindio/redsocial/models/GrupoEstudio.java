package co.edu.uniquindio.redsocial.models;

/**
 * Representa un grupo de estudio compuesto por varios estudiantes y un tema específico.
 *
 * @author Daniel Jurado
 * @author Sebastian Torres
 * @author Juan Soto
 * @since 2025-05-12
 */
public class GrupoEstudio {

    private String id;
    private String tema;
    private ListaEnlazada<Estudiante> miembros;

    /**
     * Constructor que inicializa un grupo de estudio con ID, tema y lista de miembros.
     *
     * @param id Identificador único del grupo.
     * @param tema Tema central del grupo de estudio.
     * @param miembros Lista de estudiantes miembros del grupo.
     */
    public GrupoEstudio(String id, String tema, ListaEnlazada<Estudiante> miembros) {
        this.id = id;
        this.tema = tema;
        this.miembros = (miembros != null) ? miembros : new ListaEnlazada<>();
    }

    /**
     * Constructor alternativo sin lista de miembros.
     *
     * @param id Identificador del grupo.
     * @param tema Tema del grupo.
     */
    public GrupoEstudio(String id, String tema) {
        this(id, tema, new ListaEnlazada<>());
    }

    /**
     * Agrega un estudiante al grupo si no está ya presente.
     *
     * @param estudiante Estudiante a agregar.
     */
    public void agregarMiembro(Estudiante estudiante) {
        if (estudiante != null && !miembros.contiene(estudiante)) {
            miembros.agregar(estudiante);
        }
    }
    /**
     * Obtiene un subconjunto de miembros del grupo de estudio.
     * <p>
     * Este método permite obtener una sublista de los miembros del grupo de estudio, basado
     * en el rango de posiciones que se indiquen, lo que resulta útil para crear subgrupos
     * o realizar tareas específicas con un conjunto limitado de miembros.
     *
     * @param desde Índice de inicio (inclusive) para el subgrupo
     * @param hasta Índice de fin (inclusive) para el subgrupo
     * @return Una sublista de miembros del grupo de estudio, representada como una {@link ListaEnlazada<Estudiante>}
     */
    public ListaEnlazada<Estudiante> obtenerSubgrupo(int desde, int hasta) {
        return miembros.sublista(desde, hasta);
    }

    // Getters y Setters

    /**
     * Obtiene el identificador del grupo.
     *
     * @return ID del grupo.
     */
    public String getId() {
        return id;
    }

    /**
     * Establece el identificador del grupo.
     *
     * @param id Nuevo ID del grupo.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obtiene el tema del grupo.
     *
     * @return Tema del grupo.
     */
    public String getTema() {
        return tema;
    }

    /**
     * Establece el tema del grupo.
     *
     * @param tema Nuevo tema.
     */
    public void setTema(String tema) {
        this.tema = tema;
    }

    /**
     * Obtiene la lista de miembros del grupo.
     *
     * @return Lista enlazada de estudiantes.
     */
    public ListaEnlazada<Estudiante> getMiembros() {
        return miembros;
    }

    /**
     * Establece la lista de miembros del grupo.
     *
     * @param miembros Lista de estudiantes.
     */
    public void setMiembros(ListaEnlazada<Estudiante> miembros) {
        this.miembros = (miembros != null) ? miembros : new ListaEnlazada<>();
    }

    /**
     * Devuelve una representación en texto del grupo de estudio.
     *
     * @return Cadena con información básica del grupo.
     */
    @Override
    public String toString() {
        return "GrupoEstudio{id='" + id + "', tema='" + tema + "', miembros=" + miembros.getTamanio() + "}";
    }
}
