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

@WebServlet("/EnviarMensaje")
public class EnviarMensajeServlet extends HttpServlet {
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
