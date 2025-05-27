// src/main/java/co/edu/uniquindio/redsocial/drivers/FormarGruposServlet.java
package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.GrupoEstudio;
import co.edu.uniquindio.redsocial.models.Usuario;
import co.edu.uniquindio.redsocial.models.services.implement.GestorGrupos;
import co.edu.uniquindio.redsocial.models.services.interf.IGestorGrupos;
import co.edu.uniquindio.redsocial.models.services.implement.SistemaAutenticacion;
import co.edu.uniquindio.redsocial.models.structures.GrafoNoDirigido;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

@WebServlet("/grupos/formar")
public class FormarGruposServlet extends HttpServlet {

    /** Devuelve true si las dos listas de intereses comparten al menos un valor. */
    private boolean interesesSeCruzan(ListaEnlazada<String> i1, ListaEnlazada<String> i2) {
        for (int x = 0; x < i1.getTamanio(); x++) {
            String v = i1.obtener(x);
            for (int y = 0; y < i2.getTamanio(); y++) {
                if (v.equalsIgnoreCase(i2.obtener(y))) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 1) Obtener todos los usuarios y quedarnos con los estudiantes
        SistemaAutenticacion auth = (SistemaAutenticacion)
                getServletContext().getAttribute("sistemaAutenticacion");
        ListaEnlazada<Usuario> todosUsuarios = auth.getUsuariosRegistrados();
        ListaEnlazada<Estudiante> listaEst = new ListaEnlazada<>();
        for (int i = 0; i < todosUsuarios.getTamanio(); i++) {
            Usuario u = todosUsuarios.obtener(i);
            if (u instanceof Estudiante) {
                listaEst.agregar((Estudiante) u);
            }
        }

        // 2) Construir un grafo no dirigido y añadir vértices
        GrafoNoDirigido<Estudiante> grafo = new GrafoNoDirigido<>();
        for (int i = 0; i < listaEst.getTamanio(); i++) {
            grafo.agregarNodo(listaEst.obtener(i));
        }

        // 3) Conectar dos estudiantes si comparten intereses
        for (int i = 0; i < listaEst.getTamanio(); i++) {
            Estudiante a = listaEst.obtener(i);
            for (int j = i + 1; j < listaEst.getTamanio(); j++) {
                Estudiante b = listaEst.obtener(j);
                if (interesesSeCruzan(a.getIntereses(), b.getIntereses())) {
                    grafo.agregarArista(a, b);
                }
            }
        }

        // 4) Crear grupos por comunidades detectadas
        IGestorGrupos<Estudiante> gestor = new GestorGrupos<>();
        gestor.setGrafo(grafo);
        ListaEnlazada<GrupoEstudio> creados =
                gestor.crearGruposPorAfinidadConObjetos("Grupo de Estudio");

        // 5) Para cada grupo, calcula la intersección de intereses y úsala como tema
        ListaEnlazada<GrupoEstudio> sugeridos = new ListaEnlazada<>();
        for (GrupoEstudio g : creados) {
            // Obtener intereses comunes
            Set<String> comunes = new HashSet<>();
            if (g.getMiembros().getTamanio() > 0) {
                // inicia con los intereses del primer miembro
                ListaEnlazada<String> primero = g.getMiembros().obtener(0).getIntereses();
                for (int k = 0; k < primero.getTamanio(); k++) {
                    comunes.add(primero.obtener(k).toLowerCase());
                }
                // intersecta con cada miembro siguiente
                for (int m = 1; m < g.getMiembros().getTamanio(); m++) {
                    Estudiante est = g.getMiembros().obtener(m);
                    Set<String> esta = new HashSet<>();
                    ListaEnlazada<String> intereses = est.getIntereses();
                    for (int k = 0; k < intereses.getTamanio(); k++) {
                        esta.add(intereses.obtener(k).toLowerCase());
                    }
                    comunes.retainAll(esta);
                }
            }
            // Si hay intereses comunes, únelos; si no, deja el tema por defecto
            if (!comunes.isEmpty()) {
                StringJoiner joiner = new StringJoiner(", ");
                for (String tema : comunes) {
                    joiner.add(Character.toUpperCase(tema.charAt(0)) + tema.substring(1));
                }
                g.setTema(joiner.toString());
            }
            sugeridos.agregar(g);
        }

        // 6) Guardar en sesión y redirigir a la sección de sugeridos
        HttpSession session = req.getSession();
        session.setAttribute("sugeridos", sugeridos);

        String ctx = req.getContextPath();
        resp.sendRedirect(ctx + "/inicio.jsp?seccion=sugerencias"
                + "&message=Grupos formados automáticamente");
    }
}
