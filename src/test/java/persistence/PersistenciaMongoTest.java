package persistence;

import co.edu.uniquindio.redsocial.models.Contenido;
import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.services.implement.GestorContenidos;
import co.edu.uniquindio.redsocial.models.services.implement.RedAfinidad;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import co.edu.uniquindio.redsocial.persistence.ConfigPersistencia;
import co.edu.uniquindio.redsocial.persistence.EstadoAplicacion;
import co.edu.uniquindio.redsocial.persistence.MapeadorDocumentos;
import co.edu.uniquindio.redsocial.persistence.PersistenciaMemoria;
import co.edu.uniquindio.redsocial.persistence.PersistenciaMongo;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * Pruebas de la persistencia contra un MongoDB real. Usan una base temporal que se borra al terminar y
 * se omiten solas si no hay MongoDB disponible (por ejemplo: docker start redsocial-mongo).
 */
class PersistenciaMongoTest {

    private static final String URI = ConfigPersistencia.URI_POR_DEFECTO;

    private final MapeadorDocumentos mapeador = new MapeadorDocumentos();
    private String baseTemporal;
    private PersistenciaMongo persistencia;

    private static boolean mongoDisponible() {
        try {
            new PersistenciaMongo(URI, "prueba_disponibilidad").cerrar();
            return true;
        } catch (IllegalStateException e) {
            return false;
        }
    }

    @BeforeEach
    void preparar() {
        RedAfinidad.reiniciar();
        GestorContenidos.reiniciar();
        baseTemporal = "redsocial_test_" + UUID.randomUUID().toString().replace("-", "");
    }

    @AfterEach
    void limpiar() {
        if (persistencia != null) persistencia.cerrar();
        try (MongoClient c = MongoClients.create(URI)) {
            c.getDatabase(baseTemporal).drop();
        } catch (RuntimeException ignorado) {
            // sin Mongo no hay nada que borrar
        }
        RedAfinidad.reiniciar();
        GestorContenidos.reiniciar();
    }

    private String volcado(EstadoAplicacion e) {
        StringBuilder sb = new StringBuilder();
        for (ListaEnlazada<Document> col : new ListaEnlazada[]{mapeador.usuarios(e), mapeador.contenidos(e),
                mapeador.grupos(e), mapeador.conversaciones(e), mapeador.solicitudes(e)}) {
            for (Document d : col) sb.append(d.toJson()).append('\n');
            sb.append("--\n");
        }
        return sb.toString();
    }

    @Test
    void sin_conexion_falla_con_un_mensaje_claro_y_sin_mostrar_la_contrasena() {
        IllegalStateException e = assertThrows(IllegalStateException.class,
                () -> new PersistenciaMongo("mongodb://usuario:secreto@localhost:1", "x"));
        assertTrue(e.getMessage().contains("No se pudo conectar a MongoDB"));
        assertTrue(e.getMessage().contains("REDSOCIAL_PERSISTENCIA=memoria"));
        assertFalse(e.getMessage().contains("secreto"));
    }

    @Test
    void la_persistencia_en_memoria_no_guarda_ni_carga_nada() {
        EstadoAplicacion estado = MapeadorDocumentosTest.estadoConDatos();
        String antes = volcado(estado);
        PersistenciaMemoria memoria = new PersistenciaMemoria();
        memoria.guardar(estado);
        memoria.cargar(estado);
        assertEquals(antes, volcado(estado));
    }

    @Test
    void lo_guardado_se_recupera_identico_con_otra_conexion() {
        assumeTrue(mongoDisponible(), "MongoDB no disponible en " + URI);

        EstadoAplicacion original = MapeadorDocumentosTest.estadoConDatos();
        String antes = volcado(original);
        persistencia = new PersistenciaMongo(URI, baseTemporal);
        persistencia.guardar(original);
        persistencia.guardar(original); // guardar de nuevo sin cambios no debe alterar nada
        persistencia.cerrar();

        // "Reinicio": estado vacío y una conexión nueva a la misma base
        EstadoAplicacion nuevo = MapeadorDocumentosTest.estadoVacio();
        persistencia = new PersistenciaMongo(URI, baseTemporal);
        persistencia.cargar(nuevo);

        assertEquals(antes, volcado(nuevo));
        Estudiante ana = (Estudiante) nuevo.getAutenticacion().iniciarSesion("ana@correo.com", "clave-ana");
        assertEquals("Ana", ana.getNombre());
    }

    @Test
    void los_cambios_y_las_bajas_se_reflejan_en_la_base() {
        assumeTrue(mongoDisponible(), "MongoDB no disponible en " + URI);

        EstadoAplicacion estado = MapeadorDocumentosTest.estadoConDatos();
        persistencia = new PersistenciaMongo(URI, baseTemporal);
        persistencia.guardar(estado);

        // Cambios: nuevo interés, una valoración más, baja de un usuario y un contenido eliminado
        Estudiante ana = (Estudiante) estado.getAutenticacion().iniciarSesion("ana@correo.com", "clave-ana");
        ana.agregarInteres("mongodb");
        Contenido c1 = estado.getContenidos().obtenerTodosLosContenidos().obtener(0);
        estado.getContenidos().eliminarContenido(c1.getId());
        estado.getAutenticacion().eliminarUsuario("carla@correo.com");
        persistencia.guardar(estado);
        String esperado = volcado(estado);
        persistencia.cerrar();

        EstadoAplicacion nuevo = MapeadorDocumentosTest.estadoVacio();
        persistencia = new PersistenciaMongo(URI, baseTemporal);
        persistencia.cargar(nuevo);

        assertEquals(esperado, volcado(nuevo));
        assertEquals(2, nuevo.getAutenticacion().getEstudiantesRegistrados().getTamanio());
        assertTrue(nuevo.getContenidos().obtenerTodosLosContenidos().isEmpty());
        Estudiante anaCargada = (Estudiante) nuevo.getAutenticacion().iniciarSesion("ana@correo.com", "clave-ana");
        assertTrue(anaCargada.getIntereses().contiene("mongodb"));
        assertThrows(SecurityException.class,
                () -> nuevo.getAutenticacion().iniciarSesion("carla@correo.com", "clave-carla"));
    }

    @Test
    void una_base_vacia_se_carga_sin_errores() {
        assumeTrue(mongoDisponible(), "MongoDB no disponible en " + URI);

        persistencia = new PersistenciaMongo(URI, baseTemporal);
        EstadoAplicacion estado = MapeadorDocumentosTest.estadoVacio();
        persistencia.cargar(estado);
        assertTrue(estado.getAutenticacion().getEstudiantesRegistrados().isEmpty());
        assertTrue(estado.getGrupos().isEmpty());
    }
}
