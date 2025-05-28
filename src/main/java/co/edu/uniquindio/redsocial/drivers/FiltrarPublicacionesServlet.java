package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.models.Contenido;
import co.edu.uniquindio.redsocial.models.structures.ArbolBinarioBusqueda;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import co.edu.uniquindio.redsocial.models.structures.NodoLista;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
/**
 * Servlet encargado de filtrar publicaciones de contenido educativo según criterios
 * como tema, autor y tipo. Utiliza un árbol binario de búsqueda para acceder a los
 * contenidos disponibles y retornar aquellos que cumplan con los filtros solicitados.
 * Ruta: /FiltrarPublicaciones
 * Funcionamiento:
 * - Recupera los parámetros de búsqueda: tema, autor y tipo.
 * - Aplica filtros combinados o individuales según disponibilidad de parámetros.
 * - Consulta el árbol binario de contenidos y construye una lista con los resultados.
 * - Envía la lista filtrada a la vista publicaciones.jsp.
 * Parámetros esperados (GET):
 * - tema: tema específico del contenido.
 * - autor: nombre del autor del contenido.
 * - tipo: tipo del contenido (video, documento, etc.).
 * Atributos enviados a la vista:
 * - publicacionesFiltradas: ListaEnlazada<Contenido> con los resultados del filtro.
 * Vista de destino:
 * - publicaciones.jsp
 *
 * @author Daniel Jurado, Sebastian Torres y Juan Soto
 * @version 1.0
 */
@WebServlet("/FiltrarPublicaciones")
public class FiltrarPublicacionesServlet extends HttpServlet {
    /**
     * Maneja las solicitudes GET para filtrar publicaciones educativas según los
     * parámetros especificados (tema, autor y/o tipo). Retorna los resultados a la vista.
     *
     * @param request  solicitud HTTP que contiene los parámetros de filtrado
     * @param response respuesta HTTP que reenvía a publicaciones.jsp con los resultados
     * @throws ServletException si ocurre un error en el procesamiento del servlet
     * @throws IOException      si ocurre un error de entrada/salida
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String tema  = request.getParameter("tema");
        String autor = request.getParameter("autor");
        String tipo  = request.getParameter("tipo");

        @SuppressWarnings("unchecked")
        ArbolBinarioBusqueda<Contenido> arbol =
                (ArbolBinarioBusqueda<Contenido>) getServletContext().getAttribute("arbolContenidos");

        ListaEnlazada<Contenido> resultados = new ListaEnlazada<>();
        boolean hasTema  = tema  != null && !tema.isBlank();
        boolean hasAutor = autor != null && !autor.isBlank();
        boolean hasTipo  = tipo  != null && !tipo.isBlank();

        if (hasTema && hasAutor && hasTipo) {
            ListaEnlazada<Contenido> todos = arbol.listarTodos();
            for (NodoLista<Contenido> nodo = todos.getCabeza(); nodo != null; nodo = nodo.getSiguiente()) {
                Contenido c = nodo.getDato();
                if (c.getTema().equalsIgnoreCase(tema)
                        && c.getAutor().getNombre().equalsIgnoreCase(autor)
                        && c.getTipo().name().equalsIgnoreCase(tipo)) {
                    resultados.agregar(c);
                }
            }
        } else if (hasTema) {
            resultados = arbol.listarContenidosPorTema(tema);
        } else if (hasAutor) {
            for (NodoLista<Contenido> nodo = arbol.listarTodos().getCabeza(); nodo != null; nodo = nodo.getSiguiente()) {
                Contenido c = nodo.getDato();
                if (c.getAutor().getNombre().equalsIgnoreCase(autor)) {
                    resultados.agregar(c);
                }
            }
        } else if (hasTipo) {
            for (NodoLista<Contenido> nodo = arbol.listarTodos().getCabeza(); nodo != null; nodo = nodo.getSiguiente()) {
                Contenido c = nodo.getDato();
                if (c.getTipo().name().equalsIgnoreCase(tipo)) {
                    resultados.agregar(c);
                }
            }
        } else {
            resultados = arbol.listarTodos();
        }

        request.setAttribute("publicacionesFiltradas", resultados);
        request.getRequestDispatcher("publicaciones.jsp").forward(request, response);
    }
}