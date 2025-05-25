<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="co.edu.uniquindio.redsocial.models.Estudiante" %>
<%@ page import="co.edu.uniquindio.redsocial.models.Contenido" %>
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
                                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
                                </div>
                                <div class="modal-body">
                                    <select name="interes" class="form-select" required>
                                        <option value="">-- Selecciona un interés --</option>
                                        <option value="Tecnologia">Tecnología</option>
                                        <option value="Deportes">Deportes</option>
                                        <option value="Ciencia">Ciencia</option>
                                        <option value="Arte">Arte</option>
                                        <option value="Musica">Música</option>
                                        <!-- Puedes agregar más -->
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
                        if (historial != null && historial.getTamanio() > 0) {
                    %>
                    <ul class="list-group">
                        <%
                            for (int i = 0; i < historial.getTamanio(); i++) {
                                Contenido c = historial.obtener(i);
                        %>
                        <li class="list-group-item">
                            <strong><%= c.getTema() %></strong> - <%= c.getTipo() %>
                        </li>
                        <%
                            }
                        %>
                    </ul>
                    <%
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
<div class="alert alert-danger mt-4">No has iniciado sesión. <a href="inicioSesion.jsp">Iniciar sesión</a></div>
<%
    }
%>

