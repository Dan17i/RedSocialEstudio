package co.edu.uniquindio.redsocial.models;

class GrupoEstudio {
    private String id;
    private String tema;
    private ListaEnlazada<Estudiante> miembros;

    public GrupoEstudio(String id, String tema, ListaEnlazada<Estudiante> miembros) {
        this.id = id;
        this.tema = tema;
        this.miembros = miembros;
    }

    public void agregarMiembro(Estudiante estudiante) {
        if (!miembros.contiene(estudiante)) {
            miembros.agregar(estudiante);
            estudiante.unirseAGrupo(this);
        }
    }

    public void publicarContenidoGrupo(Contenido contenido) {
        /**TODO: Implementar logica para publicar contenido visible solo para miembros del grupo*/
    }

    // Getters y Setters

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getTema() { return tema; }
    public void setTema(String tema) { this.tema = tema; }
    public ListaEnlazada<Estudiante> getMiembros() { return miembros; }
    public void setMiembros(ListaEnlazada<Estudiante> miembros) { this.miembros = miembros; }
}
