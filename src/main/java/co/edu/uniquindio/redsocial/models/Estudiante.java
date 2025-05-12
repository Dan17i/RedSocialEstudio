package co.edu.uniquindio.redsocial.models;

import java.time.LocalDateTime;

/**
 * Clase que representa un estudiante en la red social.
 * Un usuario tiene una carrera.
 *
 * @author Daniel Jurado
 * @since 2025-04-02
 */
// CLASE ESTUDIANTE COMPLETA (con todos los métodos del diagrama)
class Estudiante<T> extends Usuario {
    private ColaPrioridad<SolicitudAyuda> solicitudesAyuda;
    private ListaEnlazada<GrupoEstudio> gruposEstudio;

    public Estudiante(String id, String nombre, String email, String contraseña,
                      ListaEnlazada<String> intereses, ListaEnlazada<Contenido> historialContenidos,
                      ListaEnlazada<Valoracion> valoraciones, ColaPrioridad<SolicitudAyuda> solicitudesAyuda,
                      ListaEnlazada<GrupoEstudio> gruposEstudio) {
        super(id, nombre, email, contraseña, intereses, historialContenidos, valoraciones);
        this.solicitudesAyuda = solicitudesAyuda;
        this.gruposEstudio = gruposEstudio;
    }

    // Métodos requeridos del diagrama
    /*public void valorarContenido(Contenido contenido, int puntuacion, String comentario) {
        Valoracion nuevaValoracion = new Valoracion(this, puntuacion, comentario);
        contenido.getValoraciones().agregar(nuevaValoracion);
        this.getValoraciones().agregar(nuevaValoracion); // Historial de valoraciones del usuario
    }*/

    public void unirseAGrupo(GrupoEstudio grupo) {
        gruposEstudio.agregar(grupo);
        grupo.agregarMiembro(this);
    }

    /*public ListaEnlazada<Contenido> buscarContenido(String tema, String autor, String tipo) {
        // Implementación básica (requeriría integración con GestorContenidos)
        ListaEnlazada<Contenido> resultados = new ListaEnlazada<>();
        NodoLista<Contenido> actual = this.getHistorialContenidos().getCabeza();

        while (actual != null) {
            Contenido c = actual.getDato();
            if (c.getTema().equalsIgnoreCase(tema)
                    && c.getAutor().equalsIgnoreCase(autor)
                    && c.getTipo().equalsIgnoreCase(tipo)) {
                resultados.agregar(c);
            }
            actual = actual.getSiguiente();
        }
        return resultados;
    }
    */

    public void enviarMensaje(Estudiante destino, String mensaje) {
        Mensaje nuevoMensaje = new Mensaje(this, destino, mensaje, LocalDateTime.now());
        nuevoMensaje.enviar(); // Lógica de envío (implementar en clase Mensaje)
    }

    /*public void publicarContenido(Contenido contenido) {
        this.getHistorialContenidos().agregar(contenido); // Añade al historial
        // Lógica adicional para publicar en el sistema (dependería de GestorContenidos)
    }
    */

    // Getters y Setters (mantenidos del diagrama)
    public ColaPrioridad<SolicitudAyuda> getSolicitudesAyuda() { return solicitudesAyuda; }
    public ListaEnlazada<GrupoEstudio> getGruposEstudio() { return gruposEstudio; }

    public void setSolicitudesAyuda(ColaPrioridad<SolicitudAyuda> solicitudesAyuda) {
        this.solicitudesAyuda = solicitudesAyuda;
    }

    public void setGruposEstudio(ListaEnlazada<GrupoEstudio> gruposEstudio) {
        this.gruposEstudio = gruposEstudio;
    }
}
