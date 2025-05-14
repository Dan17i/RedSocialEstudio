package co.edu.uniquindio.redsocial.models.structures;

import co.edu.uniquindio.redsocial.models.Estudiante;

/**
 * Clase genérica que representa una cola de prioridad basada en una lista enlazada.
 * Los elementos se encolan junto con una prioridad, y se atienden en orden según esa prioridad.
 *
 * @author Daniel Jurado
 * @author Sebastian Torres
 * @author Juan Soto
 * @since 2025-05-12
 * @param <T> Tipo de los elementos almacenados en la cola.
 */
public class ColaPrioridad<T> {
    private ListaEnlazada<T> elementos = new ListaEnlazada<>();
    /**
     * Constructor que inicializa la cola con una lista enlazada existente.
     *
     * @param elementos Lista enlazada con los elementos iniciales de la cola.
     */
    public ColaPrioridad(ListaEnlazada<T> elementos) {
        this.elementos = elementos;
    }

    public ColaPrioridad() {

    }

    /**
     * Agrega un elemento a la cola con una prioridad dada.
     * La lógica de inserción debe asegurarse de mantener el orden según la prioridad.
     *
     * @param elemento  Elemento a agregar.
     * @param prioridad Prioridad del elemento (a menor valor, mayor prioridad, por ejemplo).
     *
     */
    public void encolar(T elemento, int prioridad) {
        NodoPrioridad<T> nuevo = new NodoPrioridad<>(elemento, prioridad);
        int index = 0;
        while (index < elementos.getTamanio() && ((NodoPrioridad<T>) elementos.obtener(index)).getPrioridad() >= prioridad) {
            index++;
        }
        elementos.insertarEn(index, (T) nuevo);
    }
    /**
     * Elimina y retorna el primer elemento de la cola (de mayor prioridad).
     * En esta versión simplificada, simplemente retorna el primer elemento de la lista.
     *
     * @return Elemento de mayor prioridad (o el primero de la lista actual).
     */
    public T desencolar() {
        return elementos.obtener(0);
    }
    /**
     * Retorna la lista enlazada de elementos actuales en la cola.
     *
     * @return Lista de elementos.
     */
    public ListaEnlazada<T> getElementos() {
        return elementos;
    }
    /**
     * Reemplaza la lista de elementos actual por una nueva.
     *
     * @param elementos Nueva lista de elementos.
     */
    public void setElementos(ListaEnlazada<T> elementos) {
        this.elementos = elementos;
    }

    /**
     * Representa una solicitud de ayuda realizada por un estudiante.
     * Cada solicitud tiene un tema, un nivel de urgencia y el estudiante solicitante.
     *
     * Puede ser utilizada en una cola de prioridad para gestionar solicitudes por nivel de urgencia.
     *
     * @author Daniel Jurado
     * @author Sebastian Torres
     * @author Juan Soto
     * @since 2025-05-13
     */
    public static class SolicitudAyuda implements Comparable<SolicitudAyuda> {
        private String tema;
        private int urgencia; // Entre 1 (menos urgente) y 10 (muy urgente)
        private Estudiante estudiante;

        /**
         * Constructor para crear una nueva solicitud de ayuda.
         *
         * @param tema      Tema sobre el cual se requiere ayuda.
         * @param urgencia  Nivel de urgencia (1 a 10).
         * @param estudiante Estudiante que solicita la ayuda.
         */
        public SolicitudAyuda(String tema, int urgencia, Estudiante estudiante) {
            this.tema = tema;
            setUrgencia(urgencia); // Validación
            this.estudiante = estudiante;
        }

        // Getters y Setters
        public String getTema() { return tema; }
        public void setTema(String tema) { this.tema = tema; }

        public int getUrgencia() { return urgencia; }

        public void setUrgencia(int urgencia) {
            if (urgencia < 1 || urgencia > 10) {
                throw new IllegalArgumentException("La urgencia debe estar entre 1 y 10.");
            }
            this.urgencia = urgencia;
        }

        public Estudiante getEstudiante() { return estudiante; }
        public void setEstudiante(Estudiante estudiante) { this.estudiante = estudiante; }

        /**
         * Permite comparar dos solicitudes de ayuda por su urgencia.
         * Se da prioridad a valores más altos de urgencia.
         */
        @Override
        public int compareTo(SolicitudAyuda otra) {
            return Integer.compare(otra.urgencia, this.urgencia); // Descendente
        }

        /**
         * Representación en texto de la solicitud.
         */
        @Override
        public String toString() {
            return "SolicitudAyuda{" +
                    "tema='" + tema + '\'' +
                    ", urgencia=" + urgencia +
                    ", estudiante=" + (estudiante != null ? estudiante.getNombre() : "N/A") +
                    '}';
        }
    }
}
