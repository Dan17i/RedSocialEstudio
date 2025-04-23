package models;
/**
 * Clase que representa el test de un estudiante  en la red social.
 *
 * @author Daniel Jurado
 * @author Sebastian Torres
 * @author Juan Soto
 * @since 2025-04-02
 */
import co.edu.uniquindio.redsocial.models.Estudiante;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
/**
 * Pruebas unitarias para la clase Estudiante.
 */
public class EstudianteTest {
    /**
     * Verifica que un estudiante se cree correctamente y que sus atributos sean accesibles.
     */
    @Test
    public void testEstudianteCreation() {
        Estudiante estudiante = new Estudiante("Ana", "ana@email.com", "Ingeniería");
        assertEquals("Ana", estudiante.getNombre());
        assertEquals("ana@email.com", estudiante.getEmail());
        assertEquals("Ingeniería", estudiante.getCarrera());
    }
}
