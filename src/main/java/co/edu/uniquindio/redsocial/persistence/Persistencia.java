package co.edu.uniquindio.redsocial.persistence;

/**
 * Contrato de la capa de persistencia. Las estructuras de datos propias siguen siendo la memoria de
 * trabajo de la aplicación; la persistencia solo guarda su contenido y lo vuelve a cargar al arrancar.
 */
public interface Persistencia {

    /**
     * Carga los datos guardados y los incorpora al estado en memoria (que debe estar vacío).
     *
     * @param estado Estado de la aplicación a rellenar.
     * @throws IllegalStateException si no se pueden leer los datos.
     */
    void cargar(EstadoAplicacion estado);

    /**
     * Guarda el estado actual: añade o actualiza lo que cambió y elimina lo que ya no existe.
     *
     * @param estado Estado de la aplicación a guardar.
     */
    void guardar(EstadoAplicacion estado);

    /** Libera los recursos (conexiones) de la persistencia. */
    void cerrar();
}
