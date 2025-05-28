package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.models.Conversacion;
import co.edu.uniquindio.redsocial.models.Mensaje;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
/**
 * Servlet que proporciona los mensajes de una conversación en formato JSON.
 * Este servlet se accede mediante una solicitud GET a la ruta "/ChatMessages"
 * con un parámetro de consulta "id" que identifica la conversación.
 * Devuelve un arreglo JSON con los mensajes de la conversación, incluyendo
 * el remitente, texto y fecha.
 */
@WebServlet("/ChatMessages")
public class ChatMessagesServlet extends HttpServlet {
    /**
     * Maneja solicitudes GET para obtener los mensajes de una conversación específica.
     *
     * @param req  La solicitud HTTP que contiene el parámetro "id" de la conversación.
     * @param resp La respuesta HTTP que contendrá un JSON con los mensajes.
     * @throws IOException Si ocurre un error de entrada/salida.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        @SuppressWarnings("unchecked")
        ListaEnlazada<Conversacion> convs =
                (ListaEnlazada<Conversacion>) getServletContext().getAttribute("conversaciones");

        Conversacion target = null;
        for (int i = 0; i < convs.getTamanio(); i++) {
            if (convs.obtener(i).getId().equals(id)) {
                target = convs.obtener(i);
                break;
            }
        }

        if (target == null) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.print("[");
        DateTimeFormatter fmt = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        ListaEnlazada<Mensaje> msgs = target.getMensajes();
        for (int j = 0; j < msgs.getTamanio(); j++) {
            Mensaje m = msgs.obtener(j);
            out.printf(
                    "{\"remitenteId\":\"%s\",\"texto\":\"%s\",\"fecha\":\"%s\"}%s",
                    m.getRemitente().getId(),
                    m.getTexto().replace("\"", "\\\""),
                    m.getFecha().format(fmt),
                    (j < msgs.getTamanio() - 1) ? "," : ""
            );
        }
        out.print("]");
        out.flush();
    }
}
