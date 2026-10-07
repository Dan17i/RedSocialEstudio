package security;

import co.edu.uniquindio.redsocial.security.Html;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HtmlTest {

    @Test
    void escapa_etiquetas_y_comillas() {
        assertEquals("&lt;script&gt;alert(&#39;x&#39;)&lt;/script&gt;", Html.esc("<script>alert('x')</script>"));
        assertEquals("&quot;&amp;", Html.esc("\"&"));
    }

    @Test
    void texto_normal_no_cambia_y_null_es_vacio() {
        assertEquals("Hola Ana, ¿cómo estás?", Html.esc("Hola Ana, ¿cómo estás?"));
        assertEquals("", Html.esc(null));
    }
}
