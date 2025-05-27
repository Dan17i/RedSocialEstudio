<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="co.edu.uniquindio.redsocial.models.*" %>
<%@ page import="co.edu.uniquindio.redsocial.models.structures.*" %>

<%
    // El servlet debe haber puesto aquí el GrupoEstudio
    GrupoEstudio grupo = (GrupoEstudio) request.getAttribute("grupo");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Detalle Grupo: <%= grupo.getTema() %></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
          rel="stylesheet">
</head>
<body class="bg-light">
<div class="container py-4">
    <h2 class="mb-3">Grupo: <%= grupo.getTema() %></h2>
    <!-- Nav tabs -->
    <ul class="nav nav-tabs mb-4" id="tabMenu" role="tablist">
        <li class="nav-item" role="presentation">
            <button class="nav-link active" id="miembros-tab" data-bs-toggle="tab"
                    data-bs-target="#miembros" type="button" role="tab">
                Miembros (<%= grupo.getMiembros().getTamanio() %>)
            </button>
        </li>
        <li class="nav-item" role="presentation">
            <button class="nav-link" id="publicaciones-tab" data-bs-toggle="tab"
                    data-bs-target="#publicaciones" type="button" role="tab">
                Publicaciones
            </button>
        </li>
        <li class="nav-item" role="presentation">
            <button class="nav-link" id="chat-tab" data-bs-toggle="tab"
                    data-bs-target="#chat" type="button" role="tab">
                Chat
            </button>
        </li>
        <li class="nav-item" role="presentation">
            <button class="nav-link" id="ayuda-tab" data-bs-toggle="tab"
                    data-bs-target="#ayuda" type="button" role="tab">
                Solicitar Ayuda
            </button>
        </li>
        <li class="nav-item ms-auto" role="presentation">
            <a href="<%= request.getContextPath() %>/inicio.jsp?seccion=grupos" class="nav-link text-danger">
                <i class="bi bi-arrow-left-circle"></i> Volver
            </a>
        </li>
    </ul>

    <!-- Tab panes -->
    <div class="tab-content">
        <!-- Miembros -->
        <div class="tab-pane fade show active" id="miembros" role="tabpanel">
            <ul class="list-group">
                <% for (int i = 0; i < grupo.getMiembros().getTamanio(); i++) {
                    Estudiante m = grupo.getMiembros().obtener(i); %>
                <li class="list-group-item">
                    <i class="bi bi-person-fill me-2"></i><%= m.getNombre() %>
                </li>
                <% } %>
            </ul>
        </div>

        <!-- Publicaciones -->
        <div class="tab-pane fade" id="publicaciones" role="tabpanel">
            <form action="<%= request.getContextPath() %>/grupos/detalle/publicar"
                  method="post" enctype="multipart/form-data" class="mb-3">
                <input type="hidden" name="grupoId" value="<%= grupo.getId() %>" />
                <div class="mb-2">
                    <input name="tema" class="form-control" placeholder="Título" required>
                </div>
                <div class="mb-2">
            <textarea name="descripcion" class="form-control"
                      rows="2" placeholder="Descripción" required></textarea>
                </div>
                <div class="mb-2">
                    <input type="file" name="archivo" class="form-control">
                </div>
                <button class="btn btn-primary">Publicar</button>
            </form>

            <% if (grupo.getPublicaciones().getTamanio() == 0) { %>
            <p class="text-muted">Sin publicaciones aún.</p>
            <% } %>
            <% for (int i = 0; i < grupo.getPublicaciones().getTamanio(); i++) {
                Contenido c = grupo.getPublicaciones().obtener(i); %>
            <div class="card mb-2 shadow-sm">
                <div class="card-body">
                    <h6 class="card-title"><%= c.getTema() %></h6>
                    <p class="card-text"><%= c.getDescripcion() %></p>
                </div>
            </div>
            <% } %>
        </div>

        <!-- Chat -->
        <div class="tab-pane fade" id="chat" role="tabpanel">
            <form action="<%= request.getContextPath() %>/grupos/detalle/mensaje"
                  method="post" class="input-group mb-3">
                <input type="hidden" name="grupoId" value="<%= grupo.getId() %>" />
                <input name="texto" type="text" class="form-control"
                       placeholder="Escribe un mensaje..." required>
                <button class="btn btn-secondary" type="submit">
                    <i class="bi bi-send-fill"></i>
                </button>
            </form>
            <% if (grupo.getMensajesGrupo().getTamanio() == 0) { %>
            <p class="text-muted">Sin mensajes aún.</p>
            <% } %>
            <% for (int i = 0; i < grupo.getMensajesGrupo().getTamanio(); i++) {
                Mensaje m = grupo.getMensajesGrupo().obtener(i); %>
            <div class="mb-2">
                <small class="text-muted">
                    <%= m.getFecha().toLocalTime() %> – <strong><%= m.getRemitente().getNombre() %>:</strong>
                </small>
                <div><%= m.getTexto() %></div>
            </div>
            <% } %>
        </div>

        <!-- Solicitar Ayuda -->
        <div class="tab-pane fade" id="ayuda" role="tabpanel">
            <form action="<%= request.getContextPath() %>/grupos/detalle/ayuda"
                  method="post" class="mb-3">
                <input type="hidden" name="grupoId" value="<%= grupo.getId() %>" />
                <div class="row g-2 mb-2">
                    <div class="col">
                        <input name="temaAyuda" type="text" class="form-control"
                               placeholder="Tema" required>
                    </div>
                    <div class="col-3">
                        <input name="urgencia" type="number" class="form-control"
                               min="1" max="10" placeholder="Urgencia (Donde 1 es mayor prioridad y 10 la menor prioridad)" required>
                    </div>
                    <div class="col-auto">
                        <button class="btn btn-warning">Solicitar</button>
                    </div>
                </div>
                <div class="mb-2">
            <textarea name="descripcionAyuda" class="form-control"
                      rows="2" placeholder="Describe el problema" required></textarea>
                </div>
            </form>
            <%
                ListaEnlazada<NodoPrioridad<SolicitudAyuda>> cola =
                        grupo.getSolicitudesAyudaGrupo().getElementos();
                NodoLista<NodoPrioridad<SolicitudAyuda>> nodo = cola.getCabeza();
                if (nodo == null) { %>
            <p class="text-muted">No hay solicitudes de ayuda.</p>
            <% }
                while (nodo != null) {
                    NodoPrioridad<SolicitudAyuda> np = nodo.getDato();
                    SolicitudAyuda s = np.getDato();
            %>
            <div class="card mb-2 shadow-sm">
                <div class="card-body">
                    <h6>[Urgencia <%= s.getUrgencia() %>] <%= s.getTema() %></h6>
                    <small class="text-muted">por <%= s.getEstudiante().getNombre() %></small>
                    <p><%= s.getDescripcion() %></p>
                </div>
            </div>
            <% nodo = nodo.getSiguiente(); } %>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
