package co.edu.uniquindio.redsocial.models.Enums;
/**
 * Enumeración que representa los diferentes tipos de contenido que pueden existir
 * dentro del sistema de red social educativa.
 * Cada tipo de contenido puede representar una forma distinta en la que se presenta
 * la información o el material compartido por los usuarios.
 * @author Daniel Jurado, Sebastian Torres y Juan Soto
 * @since 2025-05-22
 */
public enum TipoContenido {
    /**
     * Representa contenido en formato de texto plano o enriquecido.
     */
    TEXTO,

    /**
     * Representa contenido en formato de imagen (fotografías, ilustraciones, etc.).
     */
    IMAGEN,

    /**
     * Representa contenido en formato de video.
     */
    VIDEO,

    /**
     * Representa contenido en formato de audio (grabaciones, podcasts, etc.).
     */
    AUDIO,

    /**
     * Representa contenido en forma de enlace o hipervínculo externo.
     */
    ENLACE
}
