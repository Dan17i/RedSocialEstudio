package models;

import co.edu.uniquindio.redsocial.models.Contenido;
import co.edu.uniquindio.redsocial.models.Enums.TipoContenido;
import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.services.implement.GestorContenidos;
import co.edu.uniquindio.redsocial.models.services.implement.RedAfinidad;
import co.edu.uniquindio.redsocial.models.services.implement.SistemaAutenticacion;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class GestorContenidosUnicoTest {

    @BeforeEach
    @AfterEach
    void reiniciar() {
        RedAfinidad.reiniciar();
        GestorContenidos.reiniciar();
    }

    private Contenido contenido(String id, Estudiante autor) {
        return new Contenido(id, "Tema " + id, "Desc", autor, TipoContenido.values()[0],
                LocalDateTime.now(), new ListaEnlazada<>(), null);
    }

    @Test
    void el_sistema_de_autenticacion_comparte_el_gestor_global() {
        SistemaAutenticacion sistema = new SistemaAutenticacion();
        assertSame(GestorContenidos.getInstancia(), sistema.getGestorContenidos());
    }

    @Test
    void agregar_contenido_lo_deja_en_el_arbol_y_en_la_lista() {
        SistemaAutenticacion sistema = new SistemaAutenticacion();
        Estudiante ana = sistema.registrarEstudiante("Ana", "ana@correo.com", "x");
        GestorContenidos gestor = GestorContenidos.getInstancia();
        gestor.agregarContenido(contenido("1", ana));

        assertEquals(1, gestor.obtenerTodosLosContenidos().getTamanio());
        assertEquals(1, gestor.getArbolContenidos().listarTodos().getTamanio());
    }

    @Test
    void eliminar_contenido_lo_quita_tambien_de_la_lista() {
        SistemaAutenticacion sistema = new SistemaAutenticacion();
        Estudiante ana = sistema.registrarEstudiante("Ana", "ana@correo.com", "x");
        GestorContenidos gestor = GestorContenidos.getInstancia();
        gestor.agregarContenido(contenido("1", ana));

        assertTrue(gestor.eliminarContenido("1"));
        assertEquals(0, gestor.obtenerTodosLosContenidos().getTamanio());
        assertEquals(0, gestor.getArbolContenidos().listarTodos().getTamanio());
    }
}
