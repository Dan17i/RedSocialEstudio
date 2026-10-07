package co.edu.uniquindio.redsocial.models.services.implement;

import co.edu.uniquindio.redsocial.models.Contenido;
import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.Valoracion;
import co.edu.uniquindio.redsocial.models.services.interf.ISistemaRecomendaciones;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

/**
 * Clase encargada de gestionar el sistema de recomendaciones para los estudiantes.
 * Ofrece recomendaciones personalizadas de contenidos educativos y posibles compañeros
 * de estudio, con base en los intereses del estudiante y su red de afinidad.
 *
 * Utiliza como soporte las clases RedAfinidad y GestorContenidos.
 *
 * @author Daniel Jurado
 * @author Sebastian Torres
 * @author Juan Soto
 * @since 2025-05-13
 */
public class SistemaRecomendaciones implements ISistemaRecomendaciones {

    private final RedAfinidad redAfinidad;
    private final GestorContenidos gestorContenidos;

    /**
     * Constructor que inicializa las instancias únicas de RedAfinidad y GestorContenidos.
     */
    public SistemaRecomendaciones(){
        this.redAfinidad = RedAfinidad.getInstancia();
        this.gestorContenidos = GestorContenidos.getInstancia();
    }

    /**
     * Recomienda contenidos al estudiante basándose en sus intereses personales.
     * Por cada tema de interés, se buscan contenidos relacionados y se agregan a la lista
     * de recomendaciones si no han sido previamente añadidos.
     *
     * @param estudiante Estudiante al cual se le recomendarán contenidos.
     * @return Lista de contenidos recomendados sin repeticiones.
     */
    public ListaEnlazada<Contenido> recomendarContenidos(Estudiante estudiante){
        ListaEnlazada<Contenido> recomendaciones = new ListaEnlazada<>();
        if (estudiante == null) return recomendaciones;

        for (Contenido contenido : gestorContenidos.obtenerTodosLosContenidos()) {
            boolean esPropio = contenido.getAutor() != null && contenido.getAutor().equals(estudiante);
            if (esPropio || yaValorado(estudiante, contenido)) continue;

            for (String interes : estudiante.getIntereses()) {
                if (coincideConInteres(contenido.getTema(), interes)) {
                    recomendaciones.agregar(contenido);
                    break;
                }
            }
        }
        return recomendaciones;
    }

    /** El tema del contenido coincide con el interés si lo contiene (sin distinguir mayúsculas). */
    private boolean coincideConInteres(String tema, String interes) {
        if (tema == null || interes == null || interes.isBlank()) return false;
        return tema.toLowerCase().contains(interes.trim().toLowerCase());
    }

    private boolean yaValorado(Estudiante estudiante, Contenido contenido) {
        for (Valoracion v : estudiante.getValoraciones()) {
            if (v.getContenido().equals(contenido)) return true;
        }
        return false;
    }

    /**
     * Recomienda posibles compañeros de estudio al estudiante según su red de afinidad.
     *
     * @param estudiante Estudiante para quien se generarán sugerencias de compañeros.
     * @return Lista de estudiantes sugeridos como compañeros de estudio.
     */
    public ListaEnlazada<Estudiante> recomendarCompanieros(Estudiante estudiante){
        return redAfinidad.sugerirCompaneros(estudiante);
    }
}
