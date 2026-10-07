package models;

import co.edu.uniquindio.redsocial.models.Enums.EstadoSolicitud;
import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.SolicitudAyuda;
import co.edu.uniquindio.redsocial.models.services.implement.RedAfinidad;
import co.edu.uniquindio.redsocial.models.services.implement.SistemaAutenticacion;
import co.edu.uniquindio.redsocial.models.services.implement.SistemaAyuda;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SistemaAyudaTest {

    private SistemaAyuda sistema;
    private Estudiante ana;
    private Estudiante beto;

    @BeforeEach
    void preparar() {
        RedAfinidad.reiniciar();
        SistemaAutenticacion auth = new SistemaAutenticacion();
        ana = auth.registrarEstudiante("Ana", "ana@correo.com", "x");
        beto = auth.registrarEstudiante("Beto", "beto@correo.com", "x");
        sistema = new SistemaAyuda();
    }

    @AfterEach
    void limpiar() {
        RedAfinidad.reiniciar();
    }

    private SolicitudAyuda pedir(Estudiante quien, String tema, int urgencia) {
        SolicitudAyuda s = new SolicitudAyuda(tema, urgencia, quien, "necesito ayuda", LocalDateTime.now());
        quien.solicitarAyuda(s);
        sistema.agregarSolicitud(s);
        return s;
    }

    @Test
    void las_pendientes_salen_ordenadas_por_urgencia() {
        pedir(ana, "derivadas", 7);
        pedir(beto, "grafos", 2);
        pedir(ana, "listas", 5);

        ListaEnlazada<SolicitudAyuda> pendientes = sistema.obtenerSolicitudesPendientes();
        assertEquals("grafos", pendientes.obtener(0).getTema());
        assertEquals("listas", pendientes.obtener(1).getTema());
        assertEquals("derivadas", pendientes.obtener(2).getTema());
    }

    @Test
    void atender_por_id_la_saca_de_la_cola_global_y_la_marca_en_progreso() {
        SolicitudAyuda s = pedir(ana, "derivadas", 3);

        SolicitudAyuda atendida = sistema.atenderSolicitud(s.getId(), beto);

        assertSame(s, atendida);
        assertEquals(EstadoSolicitud.EN_PROGRESO, atendida.getEstado());
        assertTrue(sistema.obtenerSolicitudesPendientes().isEmpty());
        // la solicitante la sigue viendo en su cola personal, ya con el nuevo estado
        assertEquals(1, ana.getSolicitudesAyuda().tamanio());
    }

    @Test
    void no_se_puede_atender_la_propia_solicitud() {
        SolicitudAyuda s = pedir(ana, "derivadas", 3);
        assertThrows(IllegalArgumentException.class, () -> sistema.atenderSolicitud(s.getId(), ana));
        assertEquals(1, sistema.obtenerSolicitudesPendientes().getTamanio());
    }

    @Test
    void atender_una_solicitud_inexistente_devuelve_null() {
        assertNull(sistema.atenderSolicitud("no-existe", beto));
    }
}
