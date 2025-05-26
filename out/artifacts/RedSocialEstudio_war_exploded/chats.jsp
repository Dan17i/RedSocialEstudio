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
    <!-- Sidebar de conversaciones -->
    <div class="col-4 border-end d-flex flex-column">
        <form id="search-form" action="Chat" method="get" class="p-3">
            <div class="input-group">
                <input type="text" name="buscar" class="form-control" placeholder="Buscar usuarios..."
                       value="<%= request.getParameter("buscar") != null ? request.getParameter("buscar") : "" %>"/>
                <button class="btn btn-primary" type="submit">Buscar</button>
            </div>
        </form>
        <div class="flex-grow-0 overflow-auto">
            <% if (resultados != null && !resultados.isEmpty()) { %>
            <div class="list-group list-group-flush">
                <% for (int i = 0; i < resultados.getTamanio(); i++) {
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
                    for (int i = 0; i < conversaciones.getTamanio(); i++) {
                        Conversacion c = conversaciones.obtener(i);
                        boolean activa = actual != null && actual.getId().equals(c.getId());
                        Mensaje ultimo = c.getUltimoMensaje();
                %>
                <a href="Chat?id=<%= c.getId() %>"
                   class="list-group-item list-group-item-action <%= activa ? "active" : "" %>">
                    <div class="d-flex w-100 justify-content-between">
                        <h6 class="mb-1"><%= c.getNombre() %></h6>
                        <small>
                            <%= ultimo != null
                                    ? ultimo.getFecha().toLocalTime()
                                    .format(java.time.format.DateTimeFormatter.ofPattern("HH:mm"))
                                    : "" %>
                        </small>
                    </div>
                    <p class="mb-1 text-truncate">
                        <%= ultimo != null ? ultimo.getTexto() : "Sin mensajes" %>
                    </p>
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
            <!-- Mensajes se cargarán por AJAX -->
        </div>
        <form id="chat-form" class="border-top p-3 d-flex" action="#">
            <input type="hidden" id="convId" value="<%= actual.getId() %>"/>
            <input type="text" id="texto" class="form-control me-2" placeholder="Escribe un mensaje..." required/>
            <button class="btn btn-primary" type="submit">Enviar</button>
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
    const ctx = '<%= ctx %>';
    const convId = document.getElementById('convId') ? document.getElementById('convId').value : '';

    async function loadMessages() {
        if (!convId) return;
        try {
            const res = await fetch(`${ctx}/ChatMessages?id=${convId}`);
            const data = await res.json();
            const container = document.getElementById('chat-messages');
            container.innerHTML = '';
            let lastDate = '';
            data.forEach(m => {
                const datePart = m.fecha.split('T')[0];
                if (datePart !== lastDate) {
                    const sep = document.createElement('div');
                    sep.className = 'text-center text-muted my-2';
                    sep.innerHTML = `<small>${datePart}</small>`;
                    container.appendChild(sep);
                    lastDate = datePart;
                }
                const wrapper = document.createElement('div');
                wrapper.className = 'd-flex mb-2 ' +
                    (m.remitenteId === '<%= usuario.getId() %>' ? 'justify-content-end' : 'justify-content-start');
                const bubble = document.createElement('div');
                bubble.className = 'p-2 rounded ' +
                    (m.remitenteId === '<%= usuario.getId() %>' ? 'bg-primary text-white' : 'bg-light');
                bubble.innerText = m.texto;
                wrapper.appendChild(bubble);
                container.appendChild(wrapper);
            });
            container.scrollTop = container.scrollHeight;
        } catch (e) {
            console.error('Error loading messages', e);
        }
    }

    document.getElementById('chat-form')?.addEventListener('submit', async e => {
        e.preventDefault();
        const textInput = document.getElementById('texto');
        const text = textInput.value.trim();
        if (!text) return;
        try {
            await fetch(`${ctx}/EnviarMensaje`, {
                method: 'POST',
                headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                body: `idConversacion=${encodeURIComponent(convId)}&texto=${encodeURIComponent(text)}`
            });
            textInput.value = '';
            loadMessages();
        } catch (e) {
            console.error('Error sending message', e);
        }
    });

    setInterval(loadMessages, 3000);
    window.onload = loadMessages;
</script>
</body>
</html>
