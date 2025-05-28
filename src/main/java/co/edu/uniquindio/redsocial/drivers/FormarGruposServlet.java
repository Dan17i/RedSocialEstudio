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
import java.util.List;

/**
 * Servlet que se encarga de formar grupos de estudio automáticamente basados en
 * la afinidad de intereses entre estudiantes. Para ello:
 * <ul>
 *   <li>Obtiene la lista de todos los usuarios registrados.</li>
 *   <li>Construye un grafo no dirigido con nodos representando estudiantes.</li>
 *   <li>Conecta nodos cuyos estudiantes comparten intereses en común.</li>
 *   <li>Utiliza un gestor de grupos para crear grupos de estudio basados en la
 *       conectividad del grafo.</li>
 *   <li>Almacena los grupos formados en el contexto de la aplicación para uso global.</li>
 *   <li>Redirige al usuario a la página de sugerencias con un mensaje de éxito.</li>
 * </ul>
 *
 * Ruta: /grupos/formar
 *
 * Este servlet procesa peticiones POST para formar los grupos.
 *
 * @author Daniel Jurado, Sebastian Torres y Juan Soto
 * @since 2025-05-25
 * @version 1.0
 */
@WebServlet("/grupos/formar")
public class FormarGruposServlet extends HttpServlet {
    /**
     * Verifica si dos listas de intereses tienen al menos un elemento en común.
     *
     * @param i1 Lista de intereses del primer estudiante.
     * @param i2 Lista de intereses del segundo estudiante.
     * @return true si hay al menos un interés en común; false en caso contrario.
     */
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
    /**
     * Procesa la petición POST para formar grupos de estudio. Construye un grafo
     * con estudiantes conectados si comparten intereses, y utiliza un gestor de grupos
     * para crear automáticamente los grupos basados en esa afinidad.
     * Finalmente, almacena los grupos en el contexto y redirige a la página de sugerencias.
     *
     * @param req  solicitud HTTP POST
     * @param resp respuesta HTTP con redirección
     * @throws ServletException si ocurre un error en el servlet
     * @throws IOException      si ocurre un error de entrada/salida
     */
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

        // 5) Convierto a ListaEnlazada y guardo en contexto
        ListaEnlazada<GrupoEstudio> todosGrupos = new ListaEnlazada<>();
        for (GrupoEstudio g : creados) {
            todosGrupos.agregar(g);
        }
        getServletContext().setAttribute("todosGrupos", todosGrupos);

        // 6) Redirijo a "Grupos sugeridos" para que se vean inmediatamente
        String ctx = req.getContextPath();
        resp.sendRedirect(ctx + "/inicio.jsp?seccion=sugerencias"
                + "&message=Grupos formados automáticamente");
    }
}
