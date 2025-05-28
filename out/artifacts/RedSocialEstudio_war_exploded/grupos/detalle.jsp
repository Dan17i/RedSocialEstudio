<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="co.edu.uniquindio.redsocial.models.*" %>
<%@ page import="co.edu.uniquindio.redsocial.models.structures.*" %>

<%
    GrupoEstudio grupo = (GrupoEstudio) request.getAttribute("grupo");
%>

<% if (grupo == null) { %>
<div class="container mt-4">
    <div class="alert alert-danger">
        No se encontró el grupo especificado.
    </div>
    <a href="<%= request.getContextPath() %>/inicio.jsp?seccion=grupos" class="btn btn-link">
        &larr; Volver a Mis Grupos
    </a>
</div>
<% } else { %>

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
    <!-- Cabecera -->
    <div class="card mb-4 shadow-sm">
        <div class="card-header bg-primary text-white">
            <h4 class="mb-0">Grupo: <%= grupo.getTema() %></h4>
            <small>ID: <%= grupo.getId() %></small>
        </div>
    </div>

    <div class="row g-4">
        <!-- Miembros -->
        <div class="col-md-4">
            <div class="card shadow-sm">
                <div class="card-header bg-secondary text-white">Miembros</div>
                <ul class="list-group list-group-flush">
                    <% for (int i = 0; i < grupo.getMiembros().getTamanio(); i++) {
                        Estudiante m = grupo.getMiembros().obtener(i); %>
                    <li class="list-group-item">
                        <i class="bi bi-person-fill me-2"></i><%= m.getNombre() %>
                    </li>
                    <% } %>
                </ul>
            </div>
        </div>

        <!-- Sección principal: publicaciones, chat y ayuda -->
        <div class="col-md-8">
            <!-- Publicar contenido -->
            <div class="card mb-4 shadow-sm">
                <div class="card-header">Publicar Contenido</div>
                <div class="card-body">
                    <form action="<%= request.getContextPath() %>/grupos/detalle/publicar"
                          method="post" enctype="multipart/form-data">

                        <input type="hidden" name="grupoId" value="<%= grupo.getId() %>" />


                        <div class="mb-3">
                            <input name="tema" class="form-control" placeholder="Título" required />
                        </div>
                        <div class="mb-3">
                            <textarea name="descripcion" class="form-control"
                                      rows="2" placeholder="Descripción" required></textarea>
                        </div>
                        <div class="mb-3">
                            <input type="file" name="archivo" class="form-control" />
                        </div>
                        <button class="btn btn-primary">Publicar</button>
                    </form>
                </div>
            </div>

            <!-- Listado de publicaciones -->
            <div class="card mb-4 shadow-sm">
                <div class="card-header bg-info text-white">Publicaciones</div>
                <div class="card-body">
                    <% if (grupo.getPublicaciones().getTamanio() == 0) { %>
                    <p class="text-muted">Sin publicaciones aún.</p>
                    <% } %>
                    <% for (int i = 0; i < grupo.getPublicaciones().getTamanio(); i++) {
                        Contenido c = grupo.getPublicaciones().obtener(i); %>
                    <div class="card mb-2">
                        <div class="card-body">
                            <h6 class="card-title"><%= c.getTema() %></h6>
                            <p class="card-text"><%= c.getDescripcion() %></p>
                        </div>
                    </div>
                    <% } %>
                </div>
            </div>

            <!-- Chat -->
            <div class="card mb-4 shadow-sm">
                <div class="card-header bg-light">Chat del Grupo</div>
                <div class="card-body">
                    <form action="<%= request.getContextPath() %>/grupos/detalle/mensaje"
                          method="post" class="mb-3">
                        <input type="hidden" name="grupoId" value="${grupo.id}" />
                        <div class="input-group">
                            <input name="texto" type="text" class="form-control"
                                   placeholder="Mensaje..." required />
                            <button class="btn btn-secondary" type="submit">
                                <i class="bi bi-send-fill"></i>
                            </button>
                        </div>
                    </form>
                    <% if (grupo.getMensajesGrupo().getTamanio() == 0) { %>
                    <p class="text-muted">Sin mensajes aún.</p>
                    <% } %>
                    <% for (int i = 0; i < grupo.getMensajesGrupo().getTamanio(); i++) {
                        Mensaje m = grupo.getMensajesGrupo().obtener(i); %>
                    <div class="mb-2">
                        <small class="text-muted">
                            <%= m.getFecha().toLocalTime() %> • <strong><%= m.getRemitente().getNombre() %>:</strong>
                        </small>
                        <div><%= m.getTexto() %></div>
                    </div>
                    <% } %>
                </div>
            </div>

            <!-- Solicitar ayuda -->
            <div class="card mb-4 shadow-sm">
                <div class="card-header bg-warning">Solicitar Ayuda</div>
                <div class="card-body">
                    <form action="<%= request.getContextPath() %>/grupos/detalle/ayuda"
                          method="post" class="mb-3">
                        <input type="hidden" name="grupoId" value="${grupo.id}" />
                        <div class="row g-2">
                            <div class="col-md-5">
                                <input name="temaAyuda" type="text" class="form-control"
                                       placeholder="Tema" required />
                            </div>
                            <div class="col-md-3">
                                <input name="urgencia" type="number" class="form-control"
                                       min="1" max="10" placeholder="Urgencia (1 alta-10 baja)" required />
                            </div>
                            <div class="col-md-4">
                                <button class="btn btn-warning w-100">Solicitar</button>
                            </div>
                        </div>
                        <div class="mt-3">
                            <textarea name="descripcionAyuda" class="form-control" rows="2"
                                      placeholder="Describe el problema" required></textarea>
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
                    <div class="card mb-2">
                        <div class="card-body">
                            <h6>[Urgencia <%= s.getUrgencia() %>] <%= s.getTema() %></h6>
                            <small class="text-muted">por <%= s.getEstudiante().getNombre() %></small>
                            <p><%= s.getDescripcion() %></p>
                        </div>
                    </div>
                    <%   nodo = nodo.getSiguiente();
                    } %>
                </div>
            </div>
        </div>
    </div>

    <div class="mt-3">
        <a href="<%= request.getContextPath() %>/inicio.jsp?seccion=grupos" class="btn btn-link">
            <i class="bi bi-arrow-left"></i> Volver a Mis Grupos
        </a>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

<% } /* fin del else */ %>
