package co.edu.uniquindio.redsocial.models.Enums;

/**
 * Tipos válidos de reporte para evitar errores por cadenas mal escritas.
 *
 * Representa un contenido publicado por un estudiante dentro de la red social.
 * Un contenido tiene un identificador único, tema, autor, tipo y una lista de valoraciones.
 * Esta clase proporciona métodos de acceso para sus atributos y evita modificaciones posteriores.
 *
 * @author
 * Daniel Jurado, Sebastián Torres, Juan Soto
 *  @since 2025-05-12
 *
 */
public enum TipoReporte {
    ACTIVIDAD,
    INFORME,
    CONTENIDOS_VALORADOS,
    ESTUDIANTES_CONECTADOS
}
