package co.edu.uniquindio.redsocial.persistence;

import co.edu.uniquindio.redsocial.models.Conversacion;
import co.edu.uniquindio.redsocial.models.GrupoEstudio;
import co.edu.uniquindio.redsocial.models.services.implement.GestorContenidos;
import co.edu.uniquindio.redsocial.models.services.implement.RedAfinidad;
import co.edu.uniquindio.redsocial.models.services.implement.SistemaAutenticacion;
import co.edu.uniquindio.redsocial.models.services.implement.SistemaAyuda;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

/**
 * Agrupa los servicios y colecciones en memoria que forman el estado de la aplicación y que
 * la persistencia debe guardar y volver a cargar.
 * <ul>
 *   <li>Usuarios: dentro de {@link SistemaAutenticacion}.</li>
 *   <li>Contenidos: dentro de {@link GestorContenidos}.</li>
 *   <li>Grupos de estudio y conversaciones de chat: listas compartidas con los servlets.</li>
 *   <li>Cola global de ayuda: {@link SistemaAyuda}.</li>
 *   <li>Red de afinidad: se recalcula, no se guarda.</li>
 * </ul>
 */
public class EstadoAplicacion {

    private final SistemaAutenticacion autenticacion;
    private final GestorContenidos contenidos;
    private final RedAfinidad red;
    private final SistemaAyuda ayuda;
    private final ListaEnlazada<GrupoEstudio> grupos;
    private final ListaEnlazada<Conversacion> conversaciones;

    public EstadoAplicacion(SistemaAutenticacion autenticacion,
                            GestorContenidos contenidos,
                            RedAfinidad red,
                            SistemaAyuda ayuda,
                            ListaEnlazada<GrupoEstudio> grupos,
                            ListaEnlazada<Conversacion> conversaciones) {
        this.autenticacion = autenticacion;
        this.contenidos = contenidos;
        this.red = red;
        this.ayuda = ayuda;
        this.grupos = grupos;
        this.conversaciones = conversaciones;
    }

    public SistemaAutenticacion getAutenticacion() { return autenticacion; }

    public GestorContenidos getContenidos() { return contenidos; }

    public RedAfinidad getRed() { return red; }

    public SistemaAyuda getAyuda() { return ayuda; }

    public ListaEnlazada<GrupoEstudio> getGrupos() { return grupos; }

    public ListaEnlazada<Conversacion> getConversaciones() { return conversaciones; }
}
