package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.GrupoEstudio;
import co.edu.uniquindio.redsocial.models.Usuario;
import co.edu.uniquindio.redsocial.models.services.implement.SistemaAutenticacion;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@WebServlet("/grupos/formar")
public class FormarGruposServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 1) Obtener todos los estudiantes registrados
        SistemaAutenticacion auth = (SistemaAutenticacion)
                getServletContext().getAttribute("sistemaAutenticacion");
        ListaEnlazada<Usuario> todosUsuarios = auth.getUsuariosRegistrados();
        ListaEnlazada<Estudiante> estudiantes = new ListaEnlazada<>();
        for (int i = 0; i < todosUsuarios.getTamanio(); i++) {
            Usuario u = todosUsuarios.obtener(i);
            if (u instanceof Estudiante) {
                estudiantes.agregar((Estudiante) u);
            }
        }

        // 2) Reunir todos los intereses únicos (minúsculas, sin espacios)
        Set<String> interesesUnicos = new HashSet<>();
        for (int i = 0; i < estudiantes.getTamanio(); i++) {
            Estudiante e = estudiantes.obtener(i);
            for (int j = 0; j < e.getIntereses().getTamanio(); j++) {
                String interes = e.getIntereses().obtener(j)
                        .trim()
                        .toLowerCase();
                if (!interes.isBlank()) {
                    interesesUnicos.add(interes);
                }
            }
        }

        // 3) Por cada interés, si ≥2 estudiantes lo comparten, crear un grupo vacío
        ListaEnlazada<GrupoEstudio> todosGrupos = new ListaEnlazada<>();
        for (String interes : interesesUnicos) {
            int count = 0;
            for (int i = 0; i < estudiantes.getTamanio(); i++) {
                Estudiante e = estudiantes.obtener(i);
                for (int k = 0; k < e.getIntereses().getTamanio(); k++) {
                    if (interes.equalsIgnoreCase(e.getIntereses().obtener(k))) {
                        count++;
                        break;
                    }
                }
            }
            if (count > 1) {
                String tema = Character.toUpperCase(interes.charAt(0))
                        + interes.substring(1);
                GrupoEstudio g = new GrupoEstudio(
                        UUID.randomUUID().toString(), tema
                );
                // NOTA: no agregamos miembros aquí; nacen vacíos
                todosGrupos.agregar(g);
            }
        }

        // 4) Guardar la lista global de grupos en el ServletContext
        getServletContext().setAttribute("todosGrupos", todosGrupos);

        // 5) Redirigir a la sección de sugeridos
        resp.sendRedirect(req.getContextPath()
                + "/inicio.jsp?seccion=sugerencias");
    }
}
