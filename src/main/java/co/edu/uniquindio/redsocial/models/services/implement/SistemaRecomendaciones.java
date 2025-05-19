package co.edu.uniquindio.redsocial.models.services.implement;

import co.edu.uniquindio.redsocial.models.Contenido;
import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import co.edu.uniquindio.redsocial.models.structures.NodoLista;

/**
 * Clase encargada de gestionar el sistema de recomendaciones para los estudiantes.
 * Ofrece recomendaciones personalizadas de contenidos educativos y posibles compañeros
 * de estudio, en base a los intereses del estudiante y su red de afinidad.
 *
 * Utiliza como soporte las clases RedAfinidad y GestorContenidos.
 *
 * @author Daniel Jurado
 * @author Sebastian Torres
 * @author Juan Soto
 * @since 2025-05-13
 */
public class SistemaRecomendaciones {

    private RedAfinidad redAfinidad;
    private GestorContenidos gestorContenidos;

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
    public ListaEnlazada<Contenido> recomendarCOntenidos(Estudiante estudiante){
        ListaEnlazada<Contenido> recomendaciones = new ListaEnlazada<>();

        ListaEnlazada<String> intereses= estudiante.getIntereses();

        NodoLista<String> actual = intereses.getCabeza();
        while(actual != null){
            ListaEnlazada<Contenido> contenidosTema = gestorContenidos.buscarPorTema(actual.getDato());

            NodoLista<Contenido> contenidoActual = contenidosTema.getCabeza();
            while(contenidoActual != null){
                if(!recomendaciones.buscar(contenidoActual.getDato())){
                    recomendaciones.agregar(contenidoActual.getDato());
                }
                contenidoActual = contenidoActual.getSiguiente();
            }
            actual = actual.getSiguiente();
        }
        return recomendaciones;
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
