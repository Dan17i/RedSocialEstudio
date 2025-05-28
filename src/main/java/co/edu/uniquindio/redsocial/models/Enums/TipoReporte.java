package co.edu.uniquindio.redsocial.models.Enums;
/**
 * Enumeración que define los diferentes tipos de reporte que pueden generarse
 * dentro del sistema de red social educativa.
 * Esta enumeración se utiliza para evitar errores relacionados con el uso
 * de cadenas mal escritas al seleccionar el tipo de reporte a generar.
 * @author Daniel Jurado, Sebastian Torres y Juan Soto
 * @since 2025-05-22
 */
public enum TipoReporte {
    /**
     * Reporte relacionado con la actividad general de los usuarios,
     * como publicaciones realizadas, interacciones, etc.
     */
    ACTIVIDAD,
    /**
     * Reporte generado con fines analíticos o de evaluación,
     * que puede incluir métricas agregadas y resultados estadísticos.
     */
    INFORME,
    /**
     * Reporte que contiene información sobre los contenidos que han sido valorados
     * por los estudiantes, incluyendo puntuaciones, comentarios, etc.
     */
    CONTENIDOS_VALORADOS,
    /**
     * Reporte que muestra qué estudiantes han estado conectados o activos
     * en un periodo determinado.
     */
    ESTUDIANTES_CONECTADOS
}
