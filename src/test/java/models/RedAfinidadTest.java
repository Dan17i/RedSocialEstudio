package models;

import co.edu.uniquindio.redsocial.models.Contenido;
import co.edu.uniquindio.redsocial.models.Enums.TipoContenido;
import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.GrupoEstudio;
import co.edu.uniquindio.redsocial.models.services.implement.GestorContenidos;
import co.edu.uniquindio.redsocial.models.services.implement.GestorRedSocial;
import co.edu.uniquindio.redsocial.models.services.implement.RedAfinidad;
import co.edu.uniquindio.redsocial.models.services.implement.SistemaAutenticacion;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class RedAfinidadTest {

    private SistemaAutenticacion sistema;
    private RedAfinidad red;
    private Estudiante ana;
    private Estudiante beto;
    private Estudiante carla;
    private Estudiante diego;

    @BeforeEach
    void preparar() {
        RedAfinidad.reiniciar();
        GestorContenidos.reiniciar();
        sistema = new SistemaAutenticacion();
        red = RedAfinidad.getInstancia();
        ana = sistema.registrarEstudiante("Ana", "ana@correo.com", "x");
        beto = sistema.registrarEstudiante("Beto", "beto@correo.com", "x");
        carla = sistema.registrarEstudiante("Carla", "carla@correo.com", "x");
        diego = sistema.registrarEstudiante("Diego", "diego@correo.com", "x");
    }

    @AfterEach
    void limpiar() {
        RedAfinidad.reiniciar();
        GestorContenidos.reiniciar();
    }

    @Test
    void registrar_un_estudiante_lo_agrega_a_la_red() {
        assertEquals(4, red.getGrafo().tamano());
    }

    @Test
    void sin_valoraciones_ni_grupos_no_hay_aristas() {
        red.actualizarConexiones();
        assertTrue(red.getGrafo().obtenerVecinos(ana).isEmpty());
    }

    @Test
    void compartir_grupo_conecta_a_los_estudiantes() {
        GrupoEstudio grupo = new GrupoEstudio("G1", "ciencia");
        ana.unirseAGrupo(grupo);
        beto.unirseAGrupo(grupo);
        red.actualizarConexiones();

        assertTrue(red.getGrafo().obtenerVecinos(ana).contiene(beto));
        assertTrue(red.getGrafo().obtenerVecinos(beto).contiene(ana));
        assertFalse(red.getGrafo().obtenerVecinos(ana).contiene(carla));
    }

    @Test
    void valorar_el_mismo_contenido_conecta_a_los_estudiantes() {
        Contenido c = new Contenido("C1", "Tema", "Desc", diego, TipoContenido.values()[0],
                LocalDateTime.now(), new ListaEnlazada<>(), null);
        diego.publicarContenido(c, sistema.getGestorContenidos());
        ana.valorarContenido(c, 5, "bueno");
        carla.valorarContenido(c, 4, "ok");
        red.actualizarConexiones();

        assertTrue(red.getGrafo().obtenerVecinos(ana).contiene(carla));
    }

    @Test
    void salir_del_grupo_ya_no_se_conserva_al_recalcular() {
        GrupoEstudio grupo = new GrupoEstudio("G1", "ciencia");
        ana.unirseAGrupo(grupo);
        beto.unirseAGrupo(grupo);
        red.actualizarConexiones();
        assertEquals(1, red.getGrafo().obtenerVecinos(ana).getTamanio());

        ana.getGruposEstudio().eliminar(grupo);
        red.actualizarConexiones();
        assertTrue(red.getGrafo().obtenerVecinos(ana).isEmpty());
    }

    @Test
    void los_reportes_del_moderador_leen_el_mismo_grafo() {
        GrupoEstudio g1 = new GrupoEstudio("G1", "ciencia");
        GrupoEstudio g2 = new GrupoEstudio("G2", "arte");
        ana.unirseAGrupo(g1);
        beto.unirseAGrupo(g1);
        beto.unirseAGrupo(g2);
        carla.unirseAGrupo(g2);

        GestorRedSocial gestor = sistema.getGestorRedSocial() instanceof GestorRedSocial
                ? (GestorRedSocial) sistema.getGestorRedSocial() : null;
        assertNotNull(gestor);

        // Ana - Beto - Carla forman una comunidad; Diego queda aislado.
        assertEquals(2, gestor.detectarComunidades().getTamanio());

        ListaEnlazada<Estudiante> masConectados = gestor.obtenerEstudiantesMasConectados();
        assertEquals(1, masConectados.getTamanio());
        assertEquals(beto, masConectados.obtener(0));

        ListaEnlazada<String> camino = gestor.calcularCaminosMasCortos("Ana", "Carla");
        assertEquals(3, camino.getTamanio());
        assertEquals("Beto", camino.obtener(1));
    }

    @Test
    void el_camino_mas_corto_acepta_el_id_del_estudiante() {
        GrupoEstudio g1 = new GrupoEstudio("G1", "ciencia");
        GrupoEstudio g2 = new GrupoEstudio("G2", "arte");
        ana.unirseAGrupo(g1);
        beto.unirseAGrupo(g1);
        beto.unirseAGrupo(g2);
        carla.unirseAGrupo(g2);

        ListaEnlazada<String> camino = sistema.getGestorRedSocial()
                .calcularCaminosMasCortos(ana.getId(), carla.getId());

        assertEquals(3, camino.getTamanio());
        assertEquals("Ana", camino.obtener(0));
        assertEquals("Carla", camino.obtener(2));
    }
}
