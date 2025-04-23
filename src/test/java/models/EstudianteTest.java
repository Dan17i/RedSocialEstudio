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
        Estudiante estudiante = new Estudiante("Ana", "4551", "Ingeniería");
        assertEquals("Ana", estudiante.getNombre());
        assertEquals("4551", estudiante.getId());
        assertEquals("Ingeniería", estudiante.getCarrera());
    }
}
