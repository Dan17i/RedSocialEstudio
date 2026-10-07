package security;

import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.Usuario;
import co.edu.uniquindio.redsocial.models.services.implement.SistemaAutenticacion;
import co.edu.uniquindio.redsocial.security.ClaveHash;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClaveHashTest {

    @Test
    void hash_no_contiene_la_contrasena_en_texto_plano() {
        String hash = ClaveHash.hashear("secreta123");
        assertFalse(hash.contains("secreta123"));
    }

    @Test
    void verificar_acepta_la_correcta_y_rechaza_otra() {
        String hash = ClaveHash.hashear("secreta123");
        assertTrue(ClaveHash.verificar("secreta123", hash));
        assertFalse(ClaveHash.verificar("otra", hash));
    }

    @Test
    void misma_contrasena_produce_hashes_distintos_por_la_sal() {
        assertNotEquals(ClaveHash.hashear("abc"), ClaveHash.hashear("abc"));
    }

    @Test
    void verificar_con_formato_invalido_devuelve_false() {
        assertFalse(ClaveHash.verificar("abc", "texto-plano"));
        assertFalse(ClaveHash.verificar("abc", null));
        assertFalse(ClaveHash.verificar(null, ClaveHash.hashear("abc")));
    }

    @Test
    void sistema_guarda_hash_y_permite_iniciar_sesion() {
        SistemaAutenticacion sistema = new SistemaAutenticacion();
        Estudiante e = sistema.registrarEstudiante("Ana", "ana@correo.com", "clave123");
        assertNotEquals("clave123", e.getContrasena());

        Usuario u = sistema.iniciarSesion("ana@correo.com", "clave123");
        assertEquals(e, u);
        assertThrows(SecurityException.class, () -> sistema.iniciarSesion("ana@correo.com", "mala"));
    }
}
