package co.edu.uniquindio.redsocial.persistence;

/**
 * Elige la persistencia según las variables de entorno:
 * <ul>
 *   <li>{@code REDSOCIAL_PERSISTENCIA}: {@code mongo} (por defecto) o {@code memoria} (sin guardar datos).</li>
 *   <li>{@code REDSOCIAL_MONGO_URI}: cadena de conexión (por defecto {@code mongodb://localhost:27017}).</li>
 *   <li>{@code REDSOCIAL_MONGO_DB}: nombre de la base (por defecto {@code redsocialestudio}).</li>
 * </ul>
 * Con {@code mongo}, si la base no responde la aplicación no arranca: no se degrada en silencio a memoria.
 */
public final class ConfigPersistencia {

    public static final String URI_POR_DEFECTO = "mongodb://localhost:27017";
    public static final String BD_POR_DEFECTO = "redsocialestudio";

    private ConfigPersistencia() {
    }

    /**
     * @return La persistencia configurada.
     * @throws IllegalStateException si se pide MongoDB y no está disponible.
     */
    public static Persistencia crear() {
        String modo = valor("REDSOCIAL_PERSISTENCIA", "mongo");
        if ("memoria".equalsIgnoreCase(modo)) {
            return new PersistenciaMemoria();
        }
        return new PersistenciaMongo(valor("REDSOCIAL_MONGO_URI", URI_POR_DEFECTO),
                valor("REDSOCIAL_MONGO_DB", BD_POR_DEFECTO));
    }

    private static String valor(String variable, String predeterminado) {
        String v = System.getenv(variable);
        return (v == null || v.isBlank()) ? predeterminado : v.trim();
    }
}
