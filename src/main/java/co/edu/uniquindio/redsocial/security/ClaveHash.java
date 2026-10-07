package co.edu.uniquindio.redsocial.security;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Utilidad para almacenar y verificar contraseñas sin guardarlas en texto plano.
 * Usa PBKDF2 (HMAC-SHA256) con sal aleatoria por contraseña.
 * Formato almacenado: {@code iteraciones$salBase64$hashBase64}.
 */
public final class ClaveHash {

    private static final int ITERACIONES = 65_536;
    private static final int LONGITUD_SAL = 16;
    private static final int LONGITUD_HASH_BITS = 256;
    private static final String ALGORITMO = "PBKDF2WithHmacSHA256";
    private static final SecureRandom ALEATORIO = new SecureRandom();

    private ClaveHash() {
    }

    /**
     * Genera el hash (con sal) de una contraseña.
     *
     * @param contrasena Contraseña en texto plano (no nula).
     * @return Cadena con iteraciones, sal y hash lista para almacenar.
     */
    public static String hashear(String contrasena) {
        if (contrasena == null) throw new IllegalArgumentException("La contraseña no puede ser nula");
        byte[] sal = new byte[LONGITUD_SAL];
        ALEATORIO.nextBytes(sal);
        byte[] hash = derivar(contrasena, sal, ITERACIONES);
        return ITERACIONES + "$" + Base64.getEncoder().encodeToString(sal)
                + "$" + Base64.getEncoder().encodeToString(hash);
    }

    /**
     * Verifica una contraseña contra un valor almacenado generado por {@link #hashear(String)}.
     *
     * @return true si coincide; false si no coincide o el valor almacenado no tiene el formato esperado.
     */
    public static boolean verificar(String contrasena, String almacenado) {
        if (contrasena == null || almacenado == null) return false;
        String[] partes = almacenado.split("[$]");
        if (partes.length != 3) return false;
        try {
            int iteraciones = Integer.parseInt(partes[0]);
            byte[] sal = Base64.getDecoder().decode(partes[1]);
            byte[] esperado = Base64.getDecoder().decode(partes[2]);
            byte[] obtenido = derivar(contrasena, sal, iteraciones);
            return MessageDigest.isEqual(esperado, obtenido);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private static byte[] derivar(String contrasena, byte[] sal, int iteraciones) {
        try {
            PBEKeySpec spec = new PBEKeySpec(contrasena.toCharArray(), sal, iteraciones, LONGITUD_HASH_BITS);
            return SecretKeyFactory.getInstance(ALGORITMO).generateSecret(spec).getEncoded();
        } catch (GeneralSecurityException e) {
            throw new IllegalStateException("No se pudo calcular el hash de la contraseña", e);
        }
    }
}
