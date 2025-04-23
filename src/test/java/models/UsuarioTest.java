package models;
/**
 * Clase que representa el test de un usuario en la red social.
 *
 * @author Daniel Jurado
 * @author Sebastian Torres
 * @author Juan Soto
 * @since 2025-04-02
 */
import co.edu.uniquindio.redsocial.models.Usuario;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
 * Pruebas unitarias para la clase Usuario.
 */
public class UsuarioTest {
    /**
     * Verifica que un usuario se cree correctamente y que sus atributos sean accesibles.
     */
    @Test
    public void testUsuarioCreation() {
        Usuario usuario = new Usuario("Juan", "juan@email.com");
        assertEquals("Juan", usuario.getNombre());
        assertEquals("juan@email.com", usuario.getEmail());
    }
}
