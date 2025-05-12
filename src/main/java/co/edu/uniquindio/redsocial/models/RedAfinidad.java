package co.edu.uniquindio.redsocial.models;

class RedAfinidad {
    private Grafo<Estudiante> grafoEstudiantes;

    public RedAfinidad(Grafo<Estudiante> grafoEstudiantes) {
        this.grafoEstudiantes = grafoEstudiantes;
    }

    public ListaEnlazada<Estudiante> sugerirCompaneros(Estudiante estudiante) {
        return new ListaEnlazada<>();
    }

    // Getters y Setters
    public Grafo<Estudiante> getGrafoEstudiantes() { return grafoEstudiantes; }
    public void setGrafoEstudiantes(Grafo<Estudiante> grafoEstudiantes) { this.grafoEstudiantes = grafoEstudiantes; }
}
