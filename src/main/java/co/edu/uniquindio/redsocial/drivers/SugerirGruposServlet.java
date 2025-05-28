package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.GrupoEstudio;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/grupos/sugeridos")
public class SugerirGruposServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 1) Obtengo todos los grupos almacenados en el contexto
        @SuppressWarnings("unchecked")
        ListaEnlazada<GrupoEstudio> todosGrupos =
                (ListaEnlazada<GrupoEstudio>) getServletContext()
                        .getAttribute("todosGrupos");
        if (todosGrupos == null) {
            todosGrupos = new ListaEnlazada<>();
        }

        // 2) Recupero al usuario en sesión
        HttpSession session = req.getSession(false);
        Estudiante yo = null;
        if (session != null) {
            Object obj = session.getAttribute("usuarioActual");
            if (obj instanceof Estudiante) {
                yo = (Estudiante) obj;
            }
        }

        // 3) Construyo la lista de sugeridos
        ListaEnlazada<GrupoEstudio> sugeridos = new ListaEnlazada<>();
        if (yo != null) {
            ListaEnlazada<String> misIntereses = yo.getIntereses();
            for (int i = 0; i < todosGrupos.getTamanio(); i++) {
                GrupoEstudio g = todosGrupos.obtener(i);

                boolean yaPertenece = g.getMiembros().contiene(yo);
                boolean coincideTema = misIntereses.contiene(g.getTema());

                if (!yaPertenece && coincideTema) {
                    sugeridos.agregar(g);
                }
            }
        }

        // 4) Lo dejo en request y NO hago forward
        req.setAttribute("sugeridos", sugeridos);
    }
}
