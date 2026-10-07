package co.edu.uniquindio.redsocial.drivers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.Valoracion;
import co.edu.uniquindio.redsocial.models.GrupoEstudio;
import co.edu.uniquindio.redsocial.models.services.implement.RedAfinidad;
import co.edu.uniquindio.redsocial.models.services.implement.SistemaAutenticacion;
import co.edu.uniquindio.redsocial.models.structures.ConjuntoHash;
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
        JsonArray resultado = new JsonArray();
        for (NodoLista<Estudiante> it = sugeridos.getCabeza(); it != null; it = it.getSiguiente()) {
            Estudiante e = it.getDato();
            JsonObject obj = new JsonObject();
            obj.addProperty("nombre", e.getNombre());
            obj.addProperty("intereses", contarIntereses(origen, e));
            obj.addProperty("valoraciones", contarValoraciones(origen, e));
            obj.addProperty("grupos", contarGrupos(origen, e));
            resultado.add(obj);
        }
        PrintWriter out = resp.getWriter();
        out.write(resultado.toString());
        out.flush();
    }

    private int contarIntereses(Estudiante e1, Estudiante e2){
        int c=0;
        for(String i:e1.getIntereses())
            if(e2.getIntereses().contiene(i)) c++;
        return c;
    }

    private int contarValoraciones(Estudiante e1, Estudiante e2){
        ConjuntoHash<String> set=new ConjuntoHash<>();
        for(Valoracion v:e1.getValoraciones()) set.agregar(v.getContenido().getId());
        int c=0;
        for(Valoracion v:e2.getValoraciones())
            if(set.contiene(v.getContenido().getId())) c++;
        return c;
    }

    private int contarGrupos(Estudiante e1, Estudiante e2){
        ConjuntoHash<String> set=new ConjuntoHash<>();
        for(GrupoEstudio g:e1.getGruposEstudio()) set.agregar(g.getTema());
        int c=0;
        for(GrupoEstudio g:e2.getGruposEstudio())
            if(set.contiene(g.getTema())) c++;
        return c;
    }
}

