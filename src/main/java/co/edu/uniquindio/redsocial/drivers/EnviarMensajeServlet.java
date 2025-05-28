package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.models.Conversacion;
import co.edu.uniquindio.redsocial.models.Mensaje;
import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
/**
 * Servlet encargado de procesar el envío de mensajes dentro de una conversación existente
 * entre estudiantes en la red social educativa.
 * Ruta: /EnviarMensaje
 * Funcionamiento:
 * - Obtiene el ID de la conversación y el texto del mensaje desde la solicitud.
 * - Verifica la sesión del usuario actual (emisor del mensaje).
 * - Busca la conversación correspondiente por ID.
 * - Crea un nuevo objeto Mensaje y lo agrega a la conversación.
 * - Redirige al usuario nuevamente a la vista del chat.
 * Parámetros esperados (POST):
 * - idConversacion: ID único de la conversación donde se enviará el mensaje.
 * - texto: contenido textual del mensaje.
 * Redirección:
 * - Chat?id={idConversacion}
 *
 * @author Daniel Jurado, Sebastian Torres y Juan Soto
 * @since 2025-05-25
 * @version 1.0
 */
@WebServlet("/EnviarMensaje")
public class EnviarMensajeServlet extends HttpServlet {
    /**
     * Maneja las solicitudes POST para enviar un nuevo mensaje en una conversación.
     * Recupera la conversación usando su ID, crea un nuevo mensaje con el emisor actual
     * y lo agrega a la conversación antes de redirigir al chat.
     *
     * @param req  solicitud HTTP con los parámetros "idConversacion" y "texto"
     * @param resp respuesta HTTP que redirige al usuario a la vista del chat
     * @throws ServletException si ocurre un error en el procesamiento del servlet
     * @throws IOException      si ocurre un error de entrada/salida
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String idConv = req.getParameter("idConversacion");
        String texto  = req.getParameter("texto");
        Estudiante emisor = (Estudiante) req.getSession().getAttribute("usuarioActual");

        @SuppressWarnings("unchecked")
        ListaEnlazada<Conversacion> convs =
                (ListaEnlazada<Conversacion>) getServletContext().getAttribute("conversaciones");

        for (int i = 0; i < convs.getTamanio(); i++) {
            Conversacion c = convs.obtener(i);
            if (c.getId().equals(idConv)) {
                Mensaje m = new Mensaje(emisor, emisor, texto, LocalDateTime.now());
                c.agregarMensaje(m);
                break;
            }
        }

        resp.sendRedirect("Chat?id=" + idConv);
    }
}
