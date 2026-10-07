<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="co.edu.uniquindio.redsocial.models.Contenido" %>
<%@ page import="co.edu.uniquindio.redsocial.models.Valoracion" %>
<%@ page import="co.edu.uniquindio.redsocial.models.Estudiante" %>
<%@ page import="co.edu.uniquindio.redsocial.ArchivoMultimedia" %>
<%@ page import="co.edu.uniquindio.redsocial.models.structures.ListaEnlazada" %>
<%@ page import="co.edu.uniquindio.redsocial.models.structures.NodoLista" %>

<%
        // 1) Obtener todas las publicaciones
        ListaEnlazada pubs =
                (ListaEnlazada) application.getAttribute("publicaciones");
        if (pubs == null) pubs = new ListaEnlazada<>();
        // 2) Parámetros de filtro
        String temaParam  = request.getParameter("tema")  != null ? request.getParameter("tema")  : "";
        String autorParam = request.getParameter("autor") != null ? request.getParameter("autor") : "";
        String tipoParam  = request.getParameter("tipo")  != null ? request.getParameter("tipo")  : "";

// 3) Filtrado básico por tema, autor y tipo
        ListaEnlazada<Contenido> filtradas = new ListaEnlazada<>();
        for (NodoLista<Contenido> n = pubs.getCabeza(); n != null; n = n.getSiguiente()) {
            Contenido p = n.getDato();
            boolean ok = true;
            if (!temaParam.isBlank()  && !p.getTema().equalsIgnoreCase(temaParam))           ok = false;
            if (!autorParam.isBlank() && !p.getAutor().getNombre().equalsIgnoreCase(autorParam)) ok = false;
            if (!tipoParam.isBlank()  && !p.getTipo().name().equalsIgnoreCase(tipoParam))     ok = false;
            if (ok) filtradas.agregar(p);
        }
        if (temaParam.isBlank() && autorParam.isBlank() && tipoParam.isBlank()) {
            filtradas = pubs;
        }

// 4) Separar según intereses del usuario
        Estudiante user = (Estudiante) session.getAttribute("usuarioActual");
        ListaEnlazada<Contenido> matched = new ListaEnlazada<>();
        ListaEnlazada<Contenido> others  = new ListaEnlazada<>();
        if (user != null) {
            ListaEnlazada<String> intereses = user.getIntereses();
            for (NodoLista<Contenido> n = filtradas.getCabeza(); n != null; n = n.getSiguiente()) {
                Contenido p = n.getDato();
                boolean isMatch = false;
                for (NodoLista<String> i = intereses.getCabeza(); i != null; i = i.getSiguiente()) {
                    if (p.getTema().equalsIgnoreCase(i.getDato())) { isMatch = true; break; }
                }
                if (isMatch) matched.agregar(p);
                else            others.agregar(p);
            }
        } else {
            matched = filtradas;
        }

        String ctx = request.getContextPath();%>
<h4 class="mb-3">Publicaciones recientes</h4>

<!-- Formulario de filtrado: mantiene la barra lateral -->
<form class="row g-2 mb-4" method="get" action="inicio.jsp">
    <input type="hidden" name="seccion" value="home"/>
    <div class="col-md-3">
        <input type="text" name="tema" class="form-control"
               placeholder="Filtrar por tema" value="<%= temaParam %>"/>
    </div>
    <div class="col-md-3">
        <input type="text" name="autor" class="form-control"
               placeholder="Filtrar por autor" value="<%= autorParam %>"/>
    </div>
    <div class="col-md-3">
        <select name="tipo" class="form-select">
            <option value="">Todos los tipos</option>
            <option value="IMAGEN"    <%= "IMAGEN".equalsIgnoreCase(tipoParam)    ? "selected" : "" %>>Imagen</option>
            <option value="VIDEO"     <%= "VIDEO".equalsIgnoreCase(tipoParam)     ? "selected" : "" %>>Video</option>
            <option value="AUDIO"     <%= "AUDIO".equalsIgnoreCase(tipoParam)     ? "selected" : "" %>>Audio</option>
            <option value="DOCUMENTO" <%= "DOCUMENTO".equalsIgnoreCase(tipoParam) ? "selected" : "" %>>Documento</option>
        </select>
    </div>
    <div class="col-md-3">
        <button type="submit" class="btn btn-primary w-100">Filtrar</button>
    </div>
</form>

<!-- Mostrar matched primero de más reciente a más antiguo -->
<% if (!matched.isEmpty()) {
    for (int i = matched.getTamanio() - 1; i >= 0; i--) {
        Contenido p = matched.obtener(i);
        ArchivoMultimedia a = p.getArchivoMultimedia();
%>
<div class="card mb-4 shadow-sm">
    <div class="card-header bg-light">
        <strong><%= p.getTema() %></strong><br>
        <small class="text-muted"><%= p.getAutor().getNombre() %> | <%= p.getFechaCreacion().toLocalDate() %></small>
    </div>
    <div class="card-body">
        <p><%= p.getDescripcion() %></p>
        <% if (a != null) {
            String raw = a.getRutaRelativa();
            String nombre = raw.startsWith("/archivos/") ? raw.substring("/archivos/".length()) : raw;
            String ruta   = ctx + "/archivo/" + nombre;
            String mime   = a.getTipoMime();
            if (mime.startsWith("image")) { %>
        <img src="<%= ruta %>" class="img-fluid rounded mt-2" alt="imagen"/>
        <% } else if ("application/pdf".equals(mime)) { %>
        <embed src="<%= ruta %>" type="application/pdf" width="100%" height="300px" class="mt-2"/>
        <% } else if (mime.startsWith("video")) { %>
        <video controls class="w-100 mt-2"><source src="<%= ruta %>" type="<%= mime %>"/></video>
        <% } else if (mime.startsWith("audio")) { %>
        <audio controls class="mt-2"><source src="<%= ruta %>" type="<%= mime %>"/></audio>
        <% } else { %>
        <a href="<%= ruta %>" class="btn btn-outline-secondary mt-2" target="_blank">Descargar archivo</a>
        <% } } %>
        <div class="mt-3"><strong>Valoración promedio:</strong> <%= p.promedioValoraciones() %> / 5</div>
        <% ListaEnlazada<Valoracion> vs = p.getValoraciones(); if (vs != null && !vs.isEmpty()) { %>
        <ul class="list-group mb-3">
            <% for (int j = 0; j < vs.getTamanio(); j++) { Valoracion v = vs.obtener(j); %>
            <li class="list-group-item"><strong><%= v.getEstudiante().getNombre() %>:</strong> <%= v.getComentario() %> (<%= v.getPuntuacion() %>/5)</li>
            <% } %>
        </ul>
        <% } %>
        <% if (user != null) { %>
        <form action="Valorar" method="post">
            <input type="hidden" name="idContenido" value="<%= p.getId() %>"/>
            <div class="row g-2 align-items-center">
                <div class="col-2">
                    <select name="puntuacion" class="form-select" required><option value="">Pts</option><option>1</option><option>2</option><option>3</option><option>4</option><option>5</option></select>
                </div>
                <div class="col-7">
                    <textarea name="comentario" class="form-control" rows="2" placeholder="Comentario..." required></textarea>
                </div>
                <div class="col-3">
                    <button class="btn btn-sm btn-primary w-100">Enviar</button>
                </div>
            </div>
        </form>
        <% } %>
    </div>
</div>
<%   } } %>

<!-- Mostrar others después de matched -->
<% if (!others.isEmpty()) {
    for (int i = others.getTamanio() - 1; i >= 0; i--) {
        Contenido p = others.obtener(i);
        ArchivoMultimedia a = p.getArchivoMultimedia();
%>
<!-- Repetir card como arriba -->
<div class="card mb-4 shadow-sm">
    <div class="card-header bg-light">
        <strong><%= p.getTema() %></strong><br>
        <small class="text-muted"><%= p.getAutor().getNombre() %> | <%= p.getFechaCreacion().toLocalDate() %></small>
    </div>
    <div class="card-body">
        <!-- Mismo bloque de contenido, archivos, valoraciones y formulario Valorar -->
        <p><%= p.getDescripcion() %></p>
        <% if (a != null) {
            String raw = a.getRutaRelativa();
            String nombre = raw.startsWith("/archivos/") ? raw.substring("/archivos/".length()) : raw;
            String ruta   = ctx + "/archivo/" + nombre;
            String mime   = a.getTipoMime();
            if (mime.startsWith("image")) { %>
        <img src="<%= ruta %>" class="img-fluid rounded mt-2" alt="imagen"/>
        <% } else if ("application/pdf".equals(mime)) { %>
        <embed src="<%= ruta %>" type="application/pdf" width="100%" height="300px" class="mt-2"/>
        <% } else if (mime.startsWith("video")) { %>
        <video controls class="w-100 mt-2"><source src="<%= ruta %>" type="<%= mime %>"/></video>
        <% } else if (mime.startsWith("audio")) { %>
        <audio controls class="mt-2"><source src="<%= ruta %>" type="<%= mime %>"/></audio>
        <% } else { %>
        <a href="<%= ruta %>" class="btn btn-outline-secondary mt-2" target="_blank">Descargar archivo</a>
        <% } } %>
        <div class="mt-3"><strong>Valoración promedio:</strong> <%= p.promedioValoraciones() %> / 5</div>
        <% ListaEnlazada<Valoracion> vs = p.getValoraciones(); if (vs != null && !vs.isEmpty()) { %>
        <ul class="list-group mb-3">
            <% for (int j = 0; j < vs.getTamanio(); j++) { Valoracion v = vs.obtener(j); %>
            <li class="list-group-item"><strong><%= v.getEstudiante().getNombre() %>:</strong> <%= v.getComentario() %> (<%= v.getPuntuacion() %>/5)</li>
            <% } %>
        </ul>
        <% } %>
        <% if (user != null) { %>
        <form action="Valorar" method="post">
            <input type="hidden" name="idContenido" value="<%= p.getId() %>"/>
            <div class="row g-2 align-items-center">
                <div class="col-2">
                    <select name="puntuacion" class="form-select" required><option value="">Pts</option><option>1</option><option>2</option><option>3</option><option>4</option><option>5</option></select>
                </div>
                <div class="col-7">
                    <textarea name="comentario" class="form-control" rows="2" placeholder="Comentario..." required></textarea>
                </div>
                <div class="col-3">
                    <button class="btn btn-sm btn-primary w-100">Enviar</button>
                </div>
            </div>
        </form>
        <% } %>
    </div>
</div>
<%   } } %>

<% if (matched.isEmpty() && others.isEmpty()) { %>
<p class="text-muted">No hay publicaciones que coincidan con los filtros.</p>
<% } %>