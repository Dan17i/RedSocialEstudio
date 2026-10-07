<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="co.edu.uniquindio.redsocial.models.Estudiante" %>
<%@ page import="co.edu.uniquindio.redsocial.models.Contenido" %>
<%@ page import="co.edu.uniquindio.redsocial.ArchivoMultimedia" %>
<%@ page import="co.edu.uniquindio.redsocial.models.structures.ListaEnlazada" %>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">

<%
    Estudiante estudiante = (Estudiante) session.getAttribute("usuarioActual");
    if (estudiante != null) {
%>

<div class="container">
    <div class="row">
        <!-- Información personal -->
        <div class="col-md-6">
            <div class="card mb-4 shadow-sm">
                <div class="card-header bg-primary text-white">Información personal</div>
                <div class="card-body">
                    <p><strong>Nombre:</strong> <%= estudiante.getNombre() %></p>
                    <p><strong>ID:</strong> <%= estudiante.getId() %></p>
                    <p><strong>Correo:</strong> <%= estudiante.getEmail() %></p>
                </div>
            </div>
        </div>

        <!-- Intereses -->
        <div class="col-md-6">
            <div class="card mb-4 shadow-sm">
                <div class="card-header bg-secondary text-white">Mis intereses</div>
                <div class="card-body">
                    <%
                        ListaEnlazada<String> intereses = estudiante.getIntereses();
                        if (intereses != null && intereses.getTamanio() > 0) {
                    %>
                    <ul class="list-group">
                        <%
                            for (int i = 0; i < intereses.getTamanio(); i++) {
                        %>
                        <li class="list-group-item d-flex justify-content-between align-items-center">
                            <%= intereses.obtener(i) %>
                            <form action="EliminarInteresServlet" method="post" style="margin: 0;">
                                <input type="hidden" name="interes" value="<%= intereses.obtener(i) %>">
                                <button type="submit" class="btn btn-sm btn-danger">Eliminar</button>
                            </form>
                        </li>
                        <%
                            }
                        %>
                    </ul>
                    <%
                    } else {
                    %>
                    <p>No has seleccionado intereses aún.</p>
                    <%
                        }
                    %>
                </div>
                <!-- Botón para abrir el modal -->
                <button class="btn btn-primary mt-3" data-bs-toggle="modal" data-bs-target="#modalIntereses">
                    Añadir intereses
                </button>
                <!-- Modal -->
                <div class="modal fade" id="modalIntereses" tabindex="-1" aria-labelledby="modalInteresesLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <form action="AgregarInteresServlet" method="post">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="modalInteresesLabel">Selecciona un interés</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                                </div>
                                <div class="modal-body">
                                    <select name="interes" class="form-select" required>
                                        <option value="">-- Selecciona un interés --</option>
                                        <option value="Tecnologia">Tecnología</option>
                                        <option value="Deportes">Deportes</option>
                                        <option value="Ciencia">Ciencia</option>
                                        <option value="Arte">Arte</option>
                                        <option value="Musica">Música</option>
                                    </select>
                                </div>
                                <div class="modal-footer">
                                    <button type="submit" class="btn btn-success">Agregar</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Historial de publicaciones -->
    <div class="row">
        <div class="col-12">
            <div class="card shadow-sm">
                <div class="card-header bg-info text-white">Mis publicaciones</div>
                <div class="card-body">
                    <%
                        ListaEnlazada<Contenido> historial = estudiante.getHistorialContenidos();
                        String ctx = request.getContextPath();
                        if (historial != null && historial.getTamanio() > 0) {
                            // Mostrar de más reciente a más antiguo
                            for (int i = historial.getTamanio() - 1; i >= 0; i--) {
                                Contenido c = historial.obtener(i);
                                ArchivoMultimedia a = c.getArchivoMultimedia();
                                String storedName = null;
                                if (a != null) {
                                    String raw = a.getRutaRelativa();
                                    storedName = raw.startsWith("/archivos/")
                                            ? raw.substring("/archivos/".length())
                                            : raw;
                                }
                    %>
                    <div class="card mb-4">
                        <div class="card-header bg-light">
                            <strong><%= c.getTema() %></strong>
                            <small class="text-muted float-end"><%= c.getFechaCreacion().toLocalDate() %></small>
                        </div>
                        <div class="card-body">
                            <p><%= c.getDescripcion() %></p>
                            <%
                                if (storedName != null) {
                                    String mime = a.getTipoMime();
                                    String url  = ctx + "/archivo/" + storedName;
                                    if (mime.startsWith("image")) {
                            %>
                            <img src="<%= url %>" class="img-fluid rounded mt-2" alt="imagen publicación"/>
                            <%
                            } else if ("application/pdf".equals(mime)) {
                            %>
                            <embed src="<%= url %>" type="application/pdf" width="100%" height="300px" class="mt-2"/>
                            <%
                            } else if (mime.startsWith("video")) {
                            %>
                            <video controls class="w-100 mt-2">
                                <source src="<%= url %>" type="<%= mime %>"/>
                            </video>
                            <%
                            } else if (mime.startsWith("audio")) {
                            %>
                            <audio controls class="mt-2">
                                <source src="<%= url %>" type="<%= mime %>"/>
                            </audio>
                            <%
                            } else {
                            %>
                            <a href="<%= url %>" target="_blank" class="btn btn-outline-secondary mt-2">
                                Descargar archivo
                            </a>
                            <%
                                    }
                                }
                            %>
                        </div>
                    </div>
                    <%
                        } // for
                    } else {
                    %>
                    <p>No has publicado contenidos aún.</p>
                    <%
                        }
                    %>
                </div>
            </div>
        </div>
    </div>
</div>

<%
} else {
%>
<div class="alert alert-danger mt-4">
    No has iniciado sesión. <a href="inicioSesion.jsp">Iniciar sesión</a>
</div>
<%
    }
%>
