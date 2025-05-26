<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="co.edu.uniquindio.redsocial.models.*" %>
<%@ page import="co.edu.uniquindio.redsocial.ArchivoMultimedia" %>
<%@ page import="co.edu.uniquindio.redsocial.models.structures.ListaEnlazada" %>

<%
    Contenido contenido = (Contenido) request.getAttribute("contenido");
    if (contenido == null) {
%>
<div class="alert alert-danger m-4">
    Publicación no encontrada.
    <a href="inicio.jsp?seccion=home" class="btn btn-sm btn-primary ms-2">Volver</a>
</div>
<%
        return;
    }
    Estudiante user = (Estudiante) session.getAttribute("usuarioActual");
    String ctx = request.getContextPath();
    ArchivoMultimedia a = contenido.getArchivoMultimedia();
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title><%= contenido.getTema() %></title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container py-5">
    <div class="card shadow-sm">
        <div class="card-header bg-primary text-white">
            <h4><%= contenido.getTema() %></h4>
            <small><%= contenido.getAutor().getNombre() %> | <%= contenido.getFechaCreacion().toLocalDate() %></small>
        </div>
        <div class="card-body">
            <p><%= contenido.getDescripcion() %></p>

            <% if (a != null) {
                String raw    = a.getRutaRelativa();
                String nombre = raw.startsWith("/archivos/")
                        ? raw.substring("/archivos/".length())
                        : raw;
                String ruta   = ctx + "/archivo/" + nombre;
                String mime   = a.getTipoMime();

                if (mime.startsWith("image")) { %>
            <img src="<%= ruta %>" class="img-fluid mt-3" alt="imagen"/>
            <% } else if ("application/pdf".equals(mime)) { %>
            <embed src="<%= ruta %>" type="application/pdf" width="100%" height="400px"/>
            <% } else if (mime.startsWith("video")) { %>
            <video controls class="w-100 mt-3">
                <source src="<%= ruta %>" type="<%= mime %>"/>
            </video>
            <% } else if (mime.startsWith("audio")) { %>
            <audio controls class="mt-3">
                <source src="<%= ruta %>" type="<%= mime %>"/>
            </audio>
            <% } else { %>
            <a href="<%= ruta %>" class="btn btn-outline-secondary">Descargar archivo</a>
            <% } } %>

            <div class="mt-4">
                <strong>Valoración promedio:</strong> <%= contenido.promedioValoraciones() %> / 5
            </div>

            <% // comentarios
                ListaEnlazada<Valoracion> vs = contenido.getValoraciones();
                if (vs != null && !vs.isEmpty()) { %>
            <ul class="list-group mb-3">
                <% for (int j = 0; j < vs.getTamanio(); j++) {
                    Valoracion v = vs.obtener(j); %>
                <li class="list-group-item">
                    <strong><%= v.getEstudiante().getNombre() %>:</strong>
                    <%= v.getComentario() %> (<%= v.getPuntuacion() %>/5)
                </li>
                <% } %>
            </ul>
            <% } %>

            <% if (user != null) { %>
            <form action="Valorar" method="post">
                <input type="hidden" name="idContenido" value="<%= contenido.getId() %>"/>
                <div class="mb-2">
                    <select name="puntuacion" class="form-select" required>
                        <option value="">Puntaje</option>
                        <option>1</option><option>2</option><option>3</option>
                        <option>4</option><option>5</option>
                    </select>
                </div>
                <div class="mb-3">
          <textarea name="comentario" class="form-control" rows="3"
                    placeholder="Comentario..." required></textarea>
                </div>
                <button class="btn btn-primary">Enviar</button>
            </form>
            <% } else { %>
            <p class="text-muted mt-3">Inicia sesión para comentar.</p>
            <% } %>
        </div>
    </div>
</div>
</body>
</html>
