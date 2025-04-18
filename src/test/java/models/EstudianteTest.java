package models;

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
