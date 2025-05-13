package co.edu.uniquindio.redsocial.models;

class RedAfinidad {
    private Grafo<Estudiante> grafoEstudiantes;
    private static RedAfinidad instancia;

    public RedAfinidad(Grafo<Estudiante> grafoEstudiantes) {
        this.grafoEstudiantes = grafoEstudiantes;
    }

    public static RedAfinidad getInstancia() {
        if (instancia==null){
            instancia = new RedAfinidad(null);
        }
        return instancia;
    }

    public void agregarEstudiante(Estudiante estudiante) {grafoEstudiantes.agregarNodo(estudiante);}

    public void conectarEstudiantes(Estudiante e1, Estudiante e2) {
        //Calcular peso basado en intereses comunes
        double peso= calcularSimilitud(e1, e2);
        grafoEstudiantes.agregarArista(e1, e2, peso);
    }

    private double calcularSimilitud(Estudiante e1, Estudiante e2) {
        /** TODO: Se debe implementar logica para calcular la similitud basada en intereses*/
        return 0;
    }

    public ListaEnlazada<Estudiante> sugerirCompaneros(Estudiante estudiante) {
        //TODO: Se debe implmentar logica para sugerrir compañeros basados en el grafo
        return null;
    }

    public ListaEnlazada<ListaEnlazada<Estudiante>> detectarComunidades(){
        return grafoEstudiantes.detectarComunidades();
    }

    // Getters y Setters
    public Grafo<Estudiante> getGrafoEstudiantes() { return grafoEstudiantes; }
    public void setGrafoEstudiantes(Grafo<Estudiante> grafoEstudiantes) { this.grafoEstudiantes = grafoEstudiantes; }
}
