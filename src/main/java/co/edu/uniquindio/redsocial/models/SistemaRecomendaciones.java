package co.edu.uniquindio.redsocial.models;

public class SistemaRecomendaciones {

    private RedAfinidad redAfinidad;
    private GestorContenidos gestorContenidos;

    public SistemaRecomendaciones(){
        this.redAfinidad = RedAfinidad.getInstancia();
        this.gestorContenidos = GestorContenidos.getInstancia();
    }

    public ListaEnlazada<Contenido> recomendarCOntenidos(Estudiante estudiante){
        ListaEnlazada<Contenido> recomendaciones = new ListaEnlazada<>();

        ListaEnlazada<String> intereses= estudiante.getIntereses();

        NodoLista<String> actual = intereses.getCabeza();
        while(actual!=null){
            ListaEnlazada<Contenido> contenidosTema= gestorContenidos.buscarPorTema(actual.getDato());

            NodoLista<Contenido> contenidoActual= contenidosTema.getCabeza();
            while(contenidoActual!=null){
                if(!recomendaciones.buscar(contenidoActual.getDato())){
                    recomendaciones.agregar(contenidoActual.getDato());
                }
                contenidoActual= contenidoActual.getSiguiente();
            }
            actual=actual.getSiguiente();
        }
        return recomendaciones;
    }

    public ListaEnlazada<Estudiante> recomendarCompanieros(Estudiante estudiante){
        return redAfinidad.sugerirCompaneros(estudiante);
    }
}

