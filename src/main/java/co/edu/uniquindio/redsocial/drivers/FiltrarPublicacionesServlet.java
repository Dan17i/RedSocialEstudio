package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.models.Contenido;
import co.edu.uniquindio.redsocial.models.structures.ArbolBinarioBusqueda;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import co.edu.uniquindio.redsocial.models.structures.NodoLista;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/FiltrarPublicaciones")
public class FiltrarPublicacionesServlet extends HttpServlet {

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