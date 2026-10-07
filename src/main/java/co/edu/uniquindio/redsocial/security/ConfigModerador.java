package co.edu.uniquindio.redsocial.security;

import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;

/**
 * Credenciales del moderador del sistema.
 * Se leen de las variables de entorno {@code REDSOCIAL_MOD_EMAIL} y {@code REDSOCIAL_MOD_PASS}.
 * Si no están definidas se usan valores de DEMOSTRACIÓN (solo para desarrollo local);
 * en cualquier despliegue real deben definirse las variables de entorno.
 */
public final class ConfigModerador {

    private static final String EMAIL_DEMO = "moderador@redsocial.com";
    private static final String CLAVE_DEMO = "moderador123";

    private ConfigModerador() {
    }

    public static String email() {
        return valor("REDSOCIAL_MOD_EMAIL", EMAIL_DEMO);
    }

    /** @return true si las credenciales corresponden al moderador configurado. */
    public static boolean esModerador(String email, String contrasena) {
        if (email == null || contrasena == null) return false;
        boolean emailOk = email().equalsIgnoreCase(email);
        // Comparación en tiempo constante para no filtrar información por tiempos de respuesta.
        boolean claveOk = MessageDigest.isEqual(
                valor("REDSOCIAL_MOD_PASS", CLAVE_DEMO).getBytes(StandardCharsets.UTF_8),
                contrasena.getBytes(StandardCharsets.UTF_8));
        return emailOk && claveOk;
    }

    /** @return true si se están usando las credenciales de demostración. */
    public static boolean usaCredencialesDemo() {
        return System.getenv("REDSOCIAL_MOD_PASS") == null || System.getenv("REDSOCIAL_MOD_PASS").isBlank();
    }

    private static String valor(String variable, String predeterminado) {
        String v = System.getenv(variable);
        return (v == null || v.isBlank()) ? predeterminado : v;
    }
}
