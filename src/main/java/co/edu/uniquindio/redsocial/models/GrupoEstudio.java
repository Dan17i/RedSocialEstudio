package co.edu.uniquindio.redsocial.models;

import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

/**
 * Representa un grupo de estudio compuesto por varios estudiantes y un tema específico.
 * Se asegura la integridad al inicializar correctamente la lista de miembros y evita
 * modificaciones externas directas no controladas.
 *
 * @autor Daniel Jurado, Sebastian Torres, Juan Soto
 * @since 2025-05-12
 */
public class GrupoEstudio {

    private final String id;  // Se recomienda que id sea inmutable para evitar problemas
    private String tema;
    private final ListaEnlazada<Estudiante> miembros; // lista inmutable desde afuera, solo se modifica internamente

    /**
     * Constructor que inicializa un grupo de estudio con ID y tema.
     * La lista de miembros se inicializa vacía.
     *
     * @param id Identificador único e inmutable del grupo.
     * @param tema Tema central del grupo de estudio.
     * @throws IllegalArgumentException si id o tema son nulos o vacíos.
     */
    public GrupoEstudio(String id, String tema) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("El id no puede ser nulo ni vacío");
        }
        if (tema == null || tema.isBlank()) {
            throw new IllegalArgumentException("El tema no puede ser nulo ni vacío");
        }
        this.id = id;
        this.tema = tema;
        this.miembros = new ListaEnlazada<>();
    }

    /**
     * Agrega un estudiante al grupo si no está ya presente.
     *
     * @param estudiante Estudiante a agregar.
     * @throws IllegalArgumentException si el estudiante es nulo.
     */
    public void agregarMiembro(Estudiante estudiante) {
        if (estudiante == null) {
            throw new IllegalArgumentException("El estudiante no puede ser nulo");
        }
        if (!miembros.contiene(estudiante)) {
            miembros.agregar(estudiante);
            estudiante.unirseAGrupo(this);
        }
    }

    /**
     * Obtiene un subconjunto de miembros del grupo de estudio.
     *
     * @param desde Índice de inicio (inclusive).
     * @param hasta Índice de fin (inclusive).
     * @return Sublista de miembros.
     * @throws IndexOutOfBoundsException si los índices no son válidos.
     */
    public ListaEnlazada<Estudiante> obtenerSubgrupo(int desde, int hasta) {
        // Se podría validar rango aquí para evitar errores en la sublista
        if (desde < 0 || hasta >= miembros.getTamanio() || desde > hasta) {
            throw new IndexOutOfBoundsException("Rango inválido para obtener subgrupo");
        }
        return miembros.sublista(desde, hasta);
    }

    public void publicarContenidoGrupo(Contenido contenido) {
        /** TODO: Implementar lógica para publicar contenido visible solo para miembros del grupo */
    }

    // Getters

    /**
     * Obtiene el identificador del grupo.
     * @return id del grupo.
     */
    public String getId() {
        return id;
    }

    /**
     * Obtiene el tema del grupo.
     * @return Tema del grupo.
     */
    public String getTema() {
        return tema;
    }

    /**
     * Modifica el tema del grupo.
     * @param tema Nuevo tema.
     * @throws IllegalArgumentException si el tema es nulo o vacío.
     */
    public void setTema(String tema) {
        if (tema == null || tema.isBlank()) {
            throw new IllegalArgumentException("El tema no puede ser nulo ni vacío");
        }
        this.tema = tema;
    }

    /**
     * Obtiene una copia de la lista de miembros para evitar modificaciones externas.
     *
     * @return Nueva lista enlazada con los miembros actuales.
     */
    public ListaEnlazada<Estudiante> getMiembros() {
        // Para evitar exposición de la lista interna, devolvemos copia superficial
        ListaEnlazada<Estudiante> copia = new ListaEnlazada<>();
        for (int i = 0; i < miembros.getTamanio(); i++) {
            copia.agregar(miembros.obtener(i));
        }
        return copia;
    }


    @Override
    public String toString() {
        return "GrupoEstudio{id='" + id + "', tema='" + tema + "', miembros=" + miembros.getTamanio() + "}";
    }
}
