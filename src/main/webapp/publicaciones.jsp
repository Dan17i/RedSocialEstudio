<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="co.edu.uniquindio.redsocial.models.Contenido" %>
<%@ page import="co.edu.uniquindio.redsocial.models.Valoracion" %>
<%@ page import="co.edu.uniquindio.redsocial.models.Estudiante" %>
<%@ page import="co.edu.uniquindio.redsocial.models.structures.ListaEnlazada" %>
<%@ page import="co.edu.uniquindio.redsocial.models.structures.NodoLista" %>
<%@ page import="co.edu.uniquindio.redsocial.ArchivoMultimedia" %>

<%
    @SuppressWarnings("unchecked")
    ListaEnlazada posts =
            (ListaEnlazada) request.getAttribute("publicacionesFiltradas");
    if (posts == null) {
        posts = (ListaEnlazada) application.getAttribute("publicaciones");
    }
    Estudiante user = (Estudiante) session.getAttribute("usuarioActual");
    String ctx = request.getContextPath();
%>
<!-- Formulario de filtrado -->
<form class="row g-2 mb-4" action="FiltrarPublicaciones" method="get">
    <div class="col-md-3">
        <input type="text" name="tema" class="form-control" placeholder="Filtrar por tema"
               value="<%= co.edu.uniquindio.redsocial.security.Html.esc(String.valueOf(request.getParameter("tema") != null ? request.getParameter("tema") : "")) %>">
    </div>
    <div class="col-md-3">
        <input type="text" name="autor" class="form-control" placeholder="Filtrar por autor"
               value="<%= co.edu.uniquindio.redsocial.security.Html.esc(String.valueOf(request.getParameter("autor") != null ? request.getParameter("autor") : "")) %>">
    </div>
    <div class="col-md-3">
        <select name="tipo" class="form-select">
            <option value="">Todos los tipos</option>
            <option value="IMAGEN"    <%= co.edu.uniquindio.redsocial.security.Html.esc(String.valueOf("IMAGEN".equals(request.getParameter("tipo")) ? "selected" : "")) %>>Imagen</option>
            <option value="VIDEO"     <%= co.edu.uniquindio.redsocial.security.Html.esc(String.valueOf("VIDEO".equals(request.getParameter("tipo")) ? "selected" : "")) %>>Video</option>
            <option value="AUDIO"     <%= co.edu.uniquindio.redsocial.security.Html.esc(String.valueOf("AUDIO".equals(request.getParameter("tipo")) ? "selected" : "")) %>>Audio</option>
            <option value="DOCUMENTO" <%= co.edu.uniquindio.redsocial.security.Html.esc(String.valueOf("DOCUMENTO".equals(request.getParameter("tipo")) ? "selected" : "")) %>>Documento</option>
        </select>
    </div>
    <div class="col-md-3">
        <button type="submit" class="btn btn-primary w-100">Filtrar</button>
    </div>
</form>

<!-- Listado de publicaciones con valoraciones y comentarios -->
<% if (posts.getTamanio() == 0) { %>
<div class="alert alert-info">No se encontraron publicaciones.</div>
<% } else {
    for (NodoLista<Contenido> nodo = posts.getCabeza(); nodo != null; nodo = nodo.getSiguiente()) {
        Contenido c = nodo.getDato();
        ArchivoMultimedia a = c.getArchivoMultimedia(); %>
<div class="card mb-4">
    <div class="card-header bg-light">
        <h5><a href="Publicacion?id=<%= co.edu.uniquindio.redsocial.security.Html.esc(String.valueOf(c.getId())) %>"><%= co.edu.uniquindio.redsocial.security.Html.esc(String.valueOf(c.getTema())) %></a></h5>
        <small>Por <%= co.edu.uniquindio.redsocial.security.Html.esc(String.valueOf(c.getAutor().getNombre())) %> | <%= co.edu.uniquindio.redsocial.security.Html.esc(String.valueOf(c.getTipo().name().toLowerCase())) %> | <%= co.edu.uniquindio.redsocial.security.Html.esc(String.valueOf(c.getFechaCreacion().toLocalDate())) %></small>
    </div>
    <div class="card-body">
        <p><%= co.edu.uniquindio.redsocial.security.Html.esc(String.valueOf(c.getDescripcion())) %></p>

        <% if (a != null) {
            String raw    = a.getRutaRelativa();
            String nombre = raw.startsWith("/archivos/")
                    ? raw.substring("/archivos/".length())
                    : raw;
            String ruta   = ctx + "/archivo/" + nombre;
            String mime   = a.getTipoMime();
            if (mime.startsWith("image")) { %>
        <img src="<%= co.edu.uniquindio.redsocial.security.Html.esc(String.valueOf(ruta)) %>" class="img-fluid mb-3" alt="imagen"/>
        <% } else if ("application/pdf".equals(mime)) { %>
        <embed src="<%= co.edu.uniquindio.redsocial.security.Html.esc(String.valueOf(ruta)) %>" type="application/pdf" width="100%" height="200px"/>
        <% } else if (mime.startsWith("video")) { %>
        <video controls class="w-100 mb-3"><source src="<%= co.edu.uniquindio.redsocial.security.Html.esc(String.valueOf(ruta)) %>" type="<%= co.edu.uniquindio.redsocial.security.Html.esc(String.valueOf(mime)) %>"/></video>
        <% } else if (mime.startsWith("audio")) { %>
        <audio controls class="mb-3"><source src="<%= co.edu.uniquindio.redsocial.security.Html.esc(String.valueOf(ruta)) %>" type="<%= co.edu.uniquindio.redsocial.security.Html.esc(String.valueOf(mime)) %>"/></audio>
        <% } else { %>
        <a href="<%= co.edu.uniquindio.redsocial.security.Html.esc(String.valueOf(ruta)) %>" class="btn btn-outline-secondary mb-3">Descargar archivo</a>
        <% } } %>

        <!-- Valoración promedio -->
        <div class="mb-2">
            <strong>Valoración promedio:</strong> <%= co.edu.uniquindio.redsocial.security.Html.esc(String.valueOf(c.promedioValoraciones())) %> / 5
        </div>

        <!-- Comentarios existentes -->
        <%
            ListaEnlazada<Valoracion> vs = c.getValoraciones();
            if (vs != null && !vs.isEmpty()) {
        %>
        <ul class="list-group mb-3">
            <% for (int i = 0; i < vs.getTamanio(); i++) {
                Valoracion v = vs.obtener(i);
            %>
            <li class="list-group-item">
                <strong><%= co.edu.uniquindio.redsocial.security.Html.esc(String.valueOf(v.getEstudiante().getNombre())) %>:</strong> <%= co.edu.uniquindio.redsocial.security.Html.esc(String.valueOf(v.getComentario())) %>
                (<%= co.edu.uniquindio.redsocial.security.Html.esc(String.valueOf(v.getPuntuacion())) %>/5)
            </li>
            <% } %>
        </ul>
        <% } %>

        <!-- Formulario de nueva valoración -->
        <% if (user != null) { %>
        <form action="Valorar" method="post">
            <input type="hidden" name="idContenido" value="<%= co.edu.uniquindio.redsocial.security.Html.esc(String.valueOf(c.getId())) %>"/>
            <div class="row g-2 mb-2">
                <div class="col-4">
                    <select name="puntuacion" class="form-select" required>
                        <option value="">Puntaje</option>
                        <option>1</option><option>2</option><option>3</option>
                        <option>4</option><option>5</option>
                    </select>
                </div>
                <div class="col-8">
                    <textarea name="comentario" class="form-control" rows="2" placeholder="Comentario..." required></textarea>
                </div>
            </div>
            <button class="btn btn-sm btn-primary">Enviar</button>
        </form>
        <% } else { %>
        <p class="text-muted">Inicia sesión para comentar.</p>
        <% } %>

    </div>
</div>
<%   }
} %>