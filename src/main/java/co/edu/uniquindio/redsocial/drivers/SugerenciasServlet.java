package co.edu.uniquindio.redsocial.drivers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.Valoracion;
import co.edu.uniquindio.redsocial.models.GrupoEstudio;
import co.edu.uniquindio.redsocial.models.services.implement.RedAfinidad;
import co.edu.uniquindio.redsocial.models.services.implement.SistemaAutenticacion;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import co.edu.uniquindio.redsocial.models.structures.NodoLista;

@WebServlet("/SugerenciasServlet")
public class SugerenciasServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String id = req.getParameter("id");
        SistemaAutenticacion auth = (SistemaAutenticacion)
                getServletContext().getAttribute("sistemaAutenticacion");
        ListaEnlazada<Estudiante> todos = auth.getEstudiantesRegistrados();

        Estudiante origen = null;
        for (NodoLista<Estudiante> n = todos.getCabeza(); n != null; n = n.getSiguiente()) {
            if (n.getDato().getId().equals(id)) {
                origen = n.getDato();
                break;
            }
        }
        if (origen == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Estudiante no encontrado");
            return;
        }

        RedAfinidad red = RedAfinidad.getInstancia();
        ListaEnlazada<Estudiante> sugeridos = red.sugerirCompanerosAvanzado(origen);

        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.write("[");
        boolean first = true;

        for (NodoLista<Estudiante> it = sugeridos.getCabeza(); it != null; it = it.getSiguiente()) {
            Estudiante e = it.getDato();

            int intereses = contarIntereses(origen, e);
            int valoraciones = contarValoraciones(origen, e);
            int grupos = contarGrupos(origen, e);

            if (!first) out.write(",");
            out.write("{\"nombre\":\""+e.getNombre()+"\",");
            out.write("\"intereses\":"+intereses+",");
            out.write("\"valoraciones\":"+valoraciones+",");
            out.write("\"grupos\":"+grupos+"}");
            first = false;
        }
        out.write("]");
        out.flush();
    }

    private int contarIntereses(Estudiante e1, Estudiante e2){
        int c=0;
        for(String i:e1.getIntereses())
            if(e2.getIntereses().contiene(i)) c++;
        return c;
    }

    private int contarValoraciones(Estudiante e1, Estudiante e2){
        Set<String> set=new HashSet<>();
        for(Valoracion v:e1.getValoraciones()) set.add(v.getContenido().getId());
        int c=0;
        for(Valoracion v:e2.getValoraciones())
            if(set.contains(v.getContenido().getId())) c++;
        return c;
    }

    private int contarGrupos(Estudiante e1, Estudiante e2){
        Set<String> set=new HashSet<>();
        for(GrupoEstudio g:e1.getGruposEstudio()) set.add(g.getTema());
        int c=0;
        for(GrupoEstudio g:e2.getGruposEstudio())
            if(set.contains(g.getTema())) c++;
        return c;
    }
}

