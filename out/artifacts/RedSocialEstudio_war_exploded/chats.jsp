<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="co.edu.uniquindio.redsocial.models.Estudiante" %>
<%@ page import="co.edu.uniquindio.redsocial.models.Conversacion" %>
<%@ page import="co.edu.uniquindio.redsocial.models.Mensaje" %>
<%@ page import="co.edu.uniquindio.redsocial.models.structures.ListaEnlazada" %>
<%
    Estudiante usuario = (Estudiante) session.getAttribute("usuarioActual");
    @SuppressWarnings("unchecked")
    ListaEnlazada<Conversacion> conversaciones =
            (ListaEnlazada<Conversacion>) application.getAttribute("conversaciones");
    @SuppressWarnings("unchecked")
    ListaEnlazada<Estudiante> resultados =
            (ListaEnlazada<Estudiante>) request.getAttribute("searchResults");
    Conversacion actual = (Conversacion) request.getAttribute("conversacionActual");
    String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>Chat</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="<%= ctx %>/css/chat.css" rel="stylesheet"/>
</head>
<body>
<div class="row g-0" style="height:100vh;">
    <!-- Sidebar: Buscar y Conversaciones -->
    <div class="col-4 border-end d-flex flex-column">
        <form action="Chat" method="get" class="p-3">
            <div class="input-group">
                <input type="text" name="buscar" class="form-control" placeholder="Buscar usuarios..."
                       value="<%= request.getParameter("buscar")!=null?request.getParameter("buscar"):"" %>"/>
                <button class="btn btn-primary" type="submit">Buscar</button>
            </div>
        </form>
        <div class="flex-grow-0">
            <% if (resultados != null && !resultados.isEmpty()) { %>
            <div class="list-group list-group-flush">
                <% for (int i=0; i<resultados.getTamanio(); i++) {
                    Estudiante e = resultados.obtener(i); %>
                <a href="Chat?startId=<%= e.getId() %>&buscar=<%= request.getParameter("buscar") %>"
                   class="list-group-item list-group-item-action">
                    <%= e.getNombre() %> &lt;<%= e.getEmail() %>&gt;
                </a>
                <% } %>
            </div>
            <% } %>
        </div>
        <div class="flex-grow-1 overflow-auto">
            <div class="list-group list-group-flush">
                <% if (conversaciones != null) {
                    for (int i=0; i<conversaciones.getTamanio(); i++) {
                        Conversacion c = conversaciones.obtener(i);
                        boolean activa = actual!=null && actual.getId().equals(c.getId()); %>
                <a href="Chat?id=<%= c.getId() %>"
                   class="list-group-item list-group-item-action <%= activa?"active":"" %>">
                    <div class="d-flex w-100 justify-content-between">
                        <h6 class="mb-1"><%= c.getNombre() %></h6>
                        <% Mensaje ultimo = c.getUltimoMensaje(); %>
                        <small><%= ultimo!=null?ultimo.getFecha().toLocalTime().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm")):"" %></small>
                    </div>
                    <p class="mb-1 text-truncate"><%= ultimo!=null?ultimo.getTexto():"Sin mensajes" %></p>
                </a>
                <% } } %>
            </div>
        </div>
    </div>

    <!-- Chat Area -->
    <div class="col-8 d-flex flex-column">
        <% if (actual != null) { %>
        <div class="border-bottom p-3">
            <h5 class="mb-0"><%= actual.getNombre() %></h5>
        </div>
        <div id="chat-messages" class="flex-grow-1 overflow-auto p-3">
            <% String ultimoDia="";
                ListaEnlazada<Mensaje> msgs = actual.getMensajes();
                for (int j=0; j<msgs.getTamanio(); j++){
                    Mensaje m = msgs.obtener(j);
                    String dia = m.getFecha().toLocalDate().format(java.time.format.DateTimeFormatter.ofPattern("dd 'de' MMM yyyy"));
                    if (!dia.equals(ultimoDia)) { ultimoDia=dia; %>
            <div class="text-center text-muted my-2"><small><%= dia %></small></div>
            <% } boolean enviado = m.getRemitente().getId().equals(usuario.getId()); %>
            <div class="d-flex mb-2 <%= enviado?"justify-content-end":"justify-content-start" %>">
                <div class="p-2 rounded <%= enviado?"bg-primary text-white":"bg-light" %>">
                    <%= m.getTexto() %>
                </div>
            </div>
            <% } %>
        </div>
        <form action="EnviarMensaje" method="post" class="border-top p-3">
            <input type="hidden" name="idConversacion" value="<%= actual.getId() %>"/>
            <div class="input-group">
                <input type="text" name="texto" class="form-control" placeholder="Escribe un mensaje..." required/>
                <button class="btn btn-primary" type="submit">Enviar</button>
            </div>
        </form>
        <% } else { %>
        <div class="flex-grow-1 d-flex justify-content-center align-items-center">
            <p class="text-muted">Selecciona o inicia una conversación.</p>
        </div>
        <% } %>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    const msgs = document.getElementById('chat-messages');
    if (msgs) msgs.scrollTop = msgs.scrollHeight;
</script>
</body>
</html>
