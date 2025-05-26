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

        // 1) Obtengo el sistema y las colecciones globales
        SistemaAutenticacion sistema =
                (SistemaAutenticacion) getServletContext().getAttribute("sistemaAutenticacion");
        @SuppressWarnings("unchecked")
        ListaEnlazada<Conversacion> convs =
                (ListaEnlazada<Conversacion>) getServletContext().getAttribute("conversaciones");

        // 2) Búsqueda de usuarios
        String buscar = req.getParameter("buscar");
        if (buscar != null && !buscar.isBlank()) {
            ListaEnlazada<Estudiante> resultados = new ListaEnlazada<>();
            for (Usuario u : sistema.getUsuariosRegistrados()) {
                if (u instanceof Estudiante e
                        && !e.getId().equals(
                        ((Estudiante)req.getSession()
                                .getAttribute("usuarioActual"))
                                .getId()
                )
                        && (e.getNombre().toLowerCase().contains(buscar.toLowerCase())
                        || e.getEmail().toLowerCase().contains(buscar.toLowerCase()))
                ) {
                    resultados.agregar(e);
                }
            }
            req.setAttribute("searchResults", resultados);
        }

        // 3) Inicio de nueva conversación
        String startId = req.getParameter("startId");
        if (startId != null && !startId.isBlank()) {
            // busco el estudiante
            Estudiante destino = null;
            for (Usuario u : sistema.getUsuariosRegistrados()) {
                if (u instanceof Estudiante e && e.getId().equals(startId)) {
                    destino = e;
                    break;
                }
            }
            if (destino != null) {
                // evitar duplicados: podrías buscar si ya existe conv con ese nombre
                Conversacion nueva = new Conversacion(destino.getNombre());
                convs.agregar(nueva);
                req.setAttribute("conversacionActual", nueva);
            }
        }

        // 4) Conversación seleccionada
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

        req.setAttribute("conversaciones", convs);
        req.getRequestDispatcher("chats.jsp").forward(req, resp);
    }
}
