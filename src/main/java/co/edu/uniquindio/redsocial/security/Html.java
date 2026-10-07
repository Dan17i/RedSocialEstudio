package co.edu.uniquindio.redsocial.security;

/**
 * Utilidad para escapar texto antes de escribirlo en HTML (previene XSS).
 * Escapa los caracteres que permiten inyectar etiquetas o salir de un atributo.
 * Uso en JSP: {@code <%= co.edu.uniquindio.redsocial.security.Html.esc(texto) %>}.
 */
public final class Html {

    private Html() {
    }

    /**
     * Escapa {@code & < > " '} para mostrar el texto de forma segura en contenido o atributos HTML.
     *
     * @param texto Texto a escapar (puede ser null).
     * @return Texto escapado; cadena vacía si {@code texto} es null.
     */
    public static String esc(String texto) {
        if (texto == null) return "";
        StringBuilder sb = new StringBuilder(texto.length() + 16);
        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);
            switch (c) {
                case '&':  sb.append("&amp;");  break;
                case '<':  sb.append("&lt;");   break;
                case '>':  sb.append("&gt;");   break;
                case '"':  sb.append("&quot;"); break;
                case '\'': sb.append("&#39;");  break;
                default:   sb.append(c);
            }
        }
        return sb.toString();
    }
}
