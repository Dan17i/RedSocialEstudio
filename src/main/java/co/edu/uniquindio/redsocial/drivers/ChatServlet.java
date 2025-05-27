package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.models.*;
import co.edu.uniquindio.redsocial.models.services.implement.SistemaAutenticacion;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/Chat")
public class ChatServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // 1) Obtengo el sistema y la lista global de conversaciones
        SistemaAutenticacion sistema =
                (SistemaAutenticacion) getServletContext().getAttribute("sistemaAutenticacion");
        @SuppressWarnings("unchecked")
        ListaEnlazada<Conversacion> convs =
                (ListaEnlazada<Conversacion>) getServletContext().getAttribute("conversaciones");

        // 2) Búsqueda de usuarios para iniciar nuevos chats
        String buscar = req.getParameter("buscar");
        if (buscar != null && !buscar.isBlank()) {
            ListaEnlazada<Estudiante> resultados = new ListaEnlazada<>();
            Estudiante yo = (Estudiante) req.getSession().getAttribute("usuarioActual");
            for (Usuario u : sistema.getUsuariosRegistrados()) {
                if (u instanceof Estudiante e
                        && !e.getId().equals(yo.getId())
                        && (e.getNombre().toLowerCase().contains(buscar.toLowerCase())
                        || e.getEmail().toLowerCase().contains(buscar.toLowerCase()))) {
                    resultados.agregar(e);
                }
            }
            req.setAttribute("searchResults", resultados);
        }

        // 3) Inicio de nueva conversación si vienen origen y destino
        String startId = req.getParameter("startId");
        Estudiante origen = (Estudiante) req.getSession().getAttribute("usuarioActual");
        if (startId != null && !startId.isBlank() && origen != null) {
            Estudiante destino = null;
            for (Usuario u : sistema.getUsuariosRegistrados()) {
                if (u instanceof Estudiante e && e.getId().equals(startId)) {
                    destino = e;
                    break;
                }
            }
            if (destino != null) {
                // Uso el nuevo constructor con ambos participantes
                Conversacion nueva = new Conversacion(origen, destino);
                convs.agregar(nueva);
                req.setAttribute("conversacionActual", nueva);
            }
        }

        // 4) Selección de conversación existente
        String id = req.getParameter("id");
        if (id != null) {
            for (int i = 0; i < convs.getTamanio(); i++) {
                Conversacion c = convs.obtener(i);
                if (c.getId().equals(id)) {
                    req.setAttribute("conversacionActual", c);
                    break;
                }
            }
        }

        // 5) Pasar al JSP
        req.setAttribute("conversaciones", convs);
        req.getRequestDispatcher("chats.jsp").forward(req, resp);
    }
}
