    package co.edu.uniquindio.redsocial.models;

    class SolicitudAyuda {
        private String tema;
        private int urgencia;
        private Estudiante estudiante;

        public SolicitudAyuda(String tema, int urgencia, Estudiante estudiante) {
            this.tema = tema;
            this.urgencia = urgencia;
            this.estudiante = estudiante;
        }

        // Getters y Setters
        public String getTema() { return tema; }
        public void setTema(String tema) { this.tema = tema; }
        public int getUrgencia() { return urgencia; }
        public void setUrgencia(int urgencia) { this.urgencia = urgencia; }
        public Estudiante getEstudiante() { return estudiante; }
        public void setEstudiante(Estudiante estudiante) { this.estudiante = estudiante; }
    }
