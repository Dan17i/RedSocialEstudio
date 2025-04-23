package co.edu.uniquindio.redsocial.models;

public class Valoracion {

    Estudiante estudiante;
    int puntuacion;
    String comentario;

    public Valoracion(int puntuacion, Estudiante estudiante, String comentario) {
        this.puntuacion = puntuacion;
        this.estudiante = estudiante;
        this.comentario = comentario;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
