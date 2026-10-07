package co.edu.uniquindio.redsocial.persistence;

/**
 * Persistencia vacía: no guarda ni carga nada, los datos viven solo mientras la aplicación está en marcha.
 * Se usa en las pruebas y cuando se elige explícitamente con {@code REDSOCIAL_PERSISTENCIA=memoria}.
 */
public class PersistenciaMemoria implements Persistencia {

    @Override
    public void cargar(EstadoAplicacion estado) {
        // Nada que cargar
    }

    @Override
    public void guardar(EstadoAplicacion estado) {
        // Nada que guardar
    }

    @Override
    public void cerrar() {
        // Nada que cerrar
    }
}
