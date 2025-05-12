package co.edu.uniquindio.redsocial.models;

class Valoracion {
    private Estudiante estudiante;
    private int puntuacion;
    private String comentario;

    public Valoracion(Estudiante estudiante, int puntuacion, String comentario) {
        this.estudiante = estudiante;
        this.puntuacion = puntuacion;
        this.comentario = comentario;
    }

    // Getters y Setters
    public Estudiante getEstudiante() { return estudiante; }
    public void setEstudiante(Estudiante estudiante) { this.estudiante = estudiante; }
    public int getPuntuacion() { return puntuacion; }
    public void setPuntuacion(int puntuacion) { this.puntuacion = puntuacion; }
    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }
}
