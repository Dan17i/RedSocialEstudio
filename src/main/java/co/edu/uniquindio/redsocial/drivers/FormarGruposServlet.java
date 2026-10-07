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

@WebServlet("/grupos/formar")
public class FormarGruposServlet extends HttpServlet {

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

        // 1) Lista de todos los usuarios
        SistemaAutenticacion auth = (SistemaAutenticacion)
                getServletContext().getAttribute("sistemaAutenticacion");
        ListaEnlazada<Usuario> todosUsuarios = auth.getUsuariosRegistrados();

        // 2) Construyo lista y grafo
        GrafoNoDirigido<Estudiante> grafo = new GrafoNoDirigido<>();
        ListaEnlazada<Estudiante> listaEst = new ListaEnlazada<>();
        for (int i = 0; i < todosUsuarios.getTamanio(); i++) {
            Estudiante e = (Estudiante) todosUsuarios.obtener(i);
            grafo.agregarNodo(e);
            listaEst.agregar(e);
        }

        // 3) Conecto según intereses
        for (int i = 0; i < listaEst.getTamanio(); i++) {
            Estudiante a = listaEst.obtener(i);
            for (int j = i + 1; j < listaEst.getTamanio(); j++) {
                Estudiante b = listaEst.obtener(j);
                if (interesesSeCruzan(a.getIntereses(), b.getIntereses())) {
                    grafo.agregarArista(a, b);
                }
            }
        }

        // 4) Creo los grupos
        IGestorGrupos<Estudiante> gestor = new GestorGrupos<>();
        gestor.setGrafo(grafo);
        ListaEnlazada<GrupoEstudio> creados =
                gestor.crearGruposPorAfinidadConObjetos("Grupo de Estudio");

        // 5) Se añaden al contexto SIN reemplazar los grupos que ya existen; se omiten las
        //    comunidades de un solo estudiante y las que ya coinciden con un grupo existente
        @SuppressWarnings("unchecked")
        ListaEnlazada<GrupoEstudio> todosGrupos =
                (ListaEnlazada<GrupoEstudio>) getServletContext().getAttribute("todosGrupos");
        if (todosGrupos == null) {
            todosGrupos = new ListaEnlazada<>();
            getServletContext().setAttribute("todosGrupos", todosGrupos);
        }
        for (GrupoEstudio g : creados) {
            if (g.getMiembros().getTamanio() < 2 || existeGrupoConLosMismosMiembros(todosGrupos, g)) {
                // El grupo se descarta: se desvincula de sus miembros para no dejar referencias huérfanas
                for (Estudiante e : g.getMiembros().clonar()) {
                    g.eliminarMiembro(e);
                }
                continue;
            }
            todosGrupos.agregar(g);
        }

        // 6) Redirijo a "Grupos sugeridos" para que se vean inmediatamente
        String ctx = req.getContextPath();
        resp.sendRedirect(ctx + "/inicio.jsp?seccion=sugerencias"
                + "&message=Grupos formados automáticamente");
    }

    /** @return true si ya hay un grupo con exactamente los mismos miembros (evita duplicados al repetir). */
    private boolean existeGrupoConLosMismosMiembros(ListaEnlazada<GrupoEstudio> existentes, GrupoEstudio nuevo) {
        for (GrupoEstudio g : existentes) {
            if (g.getMiembros().getTamanio() != nuevo.getMiembros().getTamanio()) continue;
            boolean iguales = true;
            for (Estudiante e : nuevo.getMiembros()) {
                if (!g.getMiembros().contiene(e)) {
                    iguales = false;
                    break;
                }
            }
            if (iguales) return true;
        }
        return false;
    }
}
