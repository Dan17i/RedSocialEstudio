<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="co.edu.uniquindio.redsocial.models.*" %>
<%@ page import="co.edu.uniquindio.redsocial.ArchivoMultimedia" %>
<%@ page import="co.edu.uniquindio.redsocial.models.structures.ListaEnlazada" %>

<%
    ListaEnlazada<Contenido> pubs =
            (ListaEnlazada<Contenido>) application.getAttribute("publicaciones");
    Estudiante user = (Estudiante) session.getAttribute("usuarioActual");
    String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Feed - EduSocial</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container py-4">
    <h4>Publicaciones recientes</h4>
    <%
        if (pubs != null && !pubs.isEmpty()) {
            for (int i = pubs.getTamanio() - 1; i >= 0; i--) {
                Contenido p = pubs.obtener(i);
                ArchivoMultimedia a = p.getArchivoMultimedia();
    %>
    <div class="card mb-4 shadow-sm">
        <div class="card-header bg-light">
            <strong><%= p.getTema() %></strong><br>
            <small class="text-muted">
                <%= p.getAutor().getNombre() %> | <%= p.getFechaCreacion().toLocalDate() %>
            </small>
        </div>
        <div class="card-body">
            <p><%= p.getDescripcion() %></p>

            <% if (a != null) {
                // extraer solo el nombre
                String raw    = a.getRutaRelativa();
                String nombre = raw.startsWith("/archivos/")
                        ? raw.substring("/archivos/".length())
                        : raw;
                String ruta   = ctx + "/archivo/" + nombre;
                String mime   = a.getTipoMime();

                if (mime.startsWith("image")) { %>
            <img src="<%= ruta %>" class="img-fluid rounded mt-2" alt="imagen"/>
            <% } else if ("application/pdf".equals(mime)) { %>
            <embed src="<%= ruta %>"
                   type="application/pdf"
                   width="100%" height="300px" class="mt-2"/>
            <% } else if (mime.startsWith("video")) { %>
            <video controls class="w-100 mt-2">
                <source src="<%= ruta %>" type="<%= mime %>"/>
            </video>
            <% } else if (mime.startsWith("audio")) { %>
            <audio controls class="mt-2">
                <source src="<%= ruta %>" type="<%= mime %>"/>
            </audio>
            <% } else { %>
            <a href="<%= ruta %>" target="_blank" class="btn btn-outline-secondary mt-2">
                Descargar archivo
            </a>
            <% } } %>

            <div class="mt-3">
                <strong>Valoración promedio:</strong> <%= p.promedioValoraciones() %> / 5
            </div>

            <%
                ListaEnlazada<Valoracion> vs = p.getValoraciones();
                if (vs != null && !vs.isEmpty()) { %>
            <ul class="list-group mb-3">
                <% for (int j = 0; j < vs.getTamanio(); j++) {
                    Valoracion v = vs.obtener(j); %>
                <li class="list-group-item">
                    <strong><%= v.getEstudiante().getNombre() %>:</strong>
                    <%= v.getComentario() %>
                    <span class="text-muted">(<%= v.getPuntuacion() %>/5)</span>
                </li>
                <% } %>
            </ul>
            <% } %>

            <% if (user != null) { %>
            <form action="Valorar" method="post">
                <input type="hidden" name="idContenido" value="<%= p.getId() %>"/>
                <div class="row g-2 align-items-center">
                    <div class="col-2">
                        <select name="puntuacion" class="form-select" required>
                            <option value="">Pts</option>
                            <option>1</option><option>2</option>
                            <option>3</option><option>4</option>
                            <option>5</option>
                        </select>
                    </div>
                    <div class="col-7">
                        <input type="text" name="comentario" class="form-control"
                               placeholder="Comentario..." required/>
                    </div>
                    <div class="col-3">
                        <button class="btn btn-sm btn-primary w-100">Enviar</button>
                    </div>
                </div>
            </form>
            <% } else { %>
            <p class="text-muted mt-2">Inicia sesión para comentar.</p>
            <% } %>
        </div>
    </div>
    <% } } else { %>
    <p class="text-muted">Aún no hay publicaciones.</p>
    <% } %>
</div>
</body>
</html>
