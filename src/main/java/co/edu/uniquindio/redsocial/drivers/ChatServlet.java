package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.models.*;
import co.edu.uniquindio.redsocial.models.services.implement.SistemaAutenticacion;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
/**
 * Servlet encargado de manejar las interacciones relacionadas con el chat entre estudiantes.
 * Este servlet permite:
 * <ul>
 *     <li>Buscar estudiantes por nombre o email para iniciar nuevas conversaciones.</li>
 *     <li>Iniciar una nueva conversación entre dos estudiantes.</li>
 *     <li>Seleccionar y visualizar una conversación existente.</li>
 * </ul>
 *
 * La lógica utiliza atributos compartidos en el ServletContext para manejar la lista global
 * de conversaciones y el sistema de autenticación de usuarios.
 * URL de acceso: <code>/Chat</code>
 *
 * @author Daniel Jurado, Sebastian Torres y Juan Soto
 * @since 2025-05-24
 */
@WebServlet("/Chat")
public class ChatServlet extends HttpServlet {
    /**
     * Maneja solicitudes GET para mostrar la interfaz de chat.
     * Realiza las siguientes acciones:
     * <ol>
     *     <li>Obtiene el sistema de autenticación y la lista de conversaciones del contexto.</li>
     *     <li>Filtra usuarios según el parámetro "buscar" para iniciar un nuevo chat.</li>
     *     <li>Inicia una conversación si se proporcionan los parámetros "startId" y "usuarioActual".</li>
     *     <li>Selecciona una conversación existente según su "id".</li>
     *     <li>Redirige a la vista JSP <code>chats.jsp</code> con los datos necesarios.</li>
     * </ol>
     *
     * @param req  la solicitud HTTP con los parámetros para búsqueda, inicio o selección de conversaciones.
     * @param resp la respuesta HTTP que será redirigida a la vista correspondiente.
     * @throws ServletException si ocurre un error de lógica del servlet.
     * @throws IOException si ocurre un error de entrada/salida.
     */
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
