<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="co.edu.uniquindio.redsocial.models.Contenido" %>
<%@ page import="co.edu.uniquindio.redsocial.models.Estudiante" %>
<%@ page import="co.edu.uniquindio.redsocial.models.structures.ListaEnlazada" %>
<%@ page import="co.edu.uniquindio.redsocial.security.Html" %>
<%
    @SuppressWarnings("unchecked")
    ListaEnlazada<Estudiante> misConexiones = (ListaEnlazada<Estudiante>) request.getAttribute("misConexiones");
    @SuppressWarnings("unchecked")
    ListaEnlazada<Estudiante> amigosDeAmigos = (ListaEnlazada<Estudiante>) request.getAttribute("amigosDeAmigos");
    @SuppressWarnings("unchecked")
    ListaEnlazada<Estudiante> afines = (ListaEnlazada<Estudiante>) request.getAttribute("afines");
    @SuppressWarnings("unchecked")
    ListaEnlazada<Contenido> recomendados = (ListaEnlazada<Contenido>) request.getAttribute("recomendados");
    @SuppressWarnings("unchecked")
    ListaEnlazada<Estudiante> otros = (ListaEnlazada<Estudiante>) request.getAttribute("otrosEstudiantes");
    @SuppressWarnings("unchecked")
    ListaEnlazada<Estudiante> ruta = (ListaEnlazada<Estudiante>) request.getAttribute("ruta");
    Estudiante destino = (Estudiante) request.getAttribute("destinoSeleccionado");
    String ctx = request.getContextPath();
%>
<div class="container-fluid mt-2">
    <h3 class="mb-1"><i class="bi bi-compass"></i> Descubrir</h3>
    <p class="text-muted">Tu red de afinidad: compañeros con los que ya compartes contenidos o grupos,
        y personas que podrían interesarte.</p>

    <div class="row g-3">
        <!-- Conexiones actuales -->
        <div class="col-md-6">
            <div class="card h-100 shadow-sm">
                <div class="card-header bg-primary text-white">
                    <i class="bi bi-people-fill"></i> Tus conexiones
                </div>
                <ul class="list-group list-group-flush">
                    <% if (misConexiones == null || misConexiones.isEmpty()) { %>
                    <li class="list-group-item text-muted">
                        Aún no tienes conexiones. Valora contenidos o únete a grupos para conectar con otros estudiantes.
                    </li>
                    <% } else { for (Estudiante e : misConexiones) { %>
                    <li class="list-group-item">
                        <i class="bi bi-person-circle text-primary me-2"></i><%= Html.esc(e.getNombre()) %>
                        <small class="text-muted">&lt;<%= Html.esc(e.getEmail()) %>&gt;</small>
                    </li>
                    <% } } %>
                </ul>
            </div>
        </div>

        <!-- Amigos de amigos -->
        <div class="col-md-6">
            <div class="card h-100 shadow-sm">
                <div class="card-header bg-success text-white">
                    <i class="bi bi-diagram-2"></i> Amigos de tus amigos
                </div>
                <ul class="list-group list-group-flush">
                    <% if (amigosDeAmigos == null || amigosDeAmigos.isEmpty()) { %>
                    <li class="list-group-item text-muted">
                        Sin sugerencias por ahora (se necesitan conexiones con intereses en común).
                    </li>
                    <% } else { for (Estudiante e : amigosDeAmigos) { %>
                    <li class="list-group-item d-flex justify-content-between align-items-center">
                        <span><i class="bi bi-person-plus text-success me-2"></i><%= Html.esc(e.getNombre()) %></span>
                        <a class="btn btn-outline-success btn-sm"
                           href="<%= Html.esc(ctx) %>/Chat?startId=<%= Html.esc(e.getId()) %>">Escribirle</a>
                    </li>
                    <% } } %>
                </ul>
            </div>
        </div>

        <!-- Afines por intereses -->
        <div class="col-md-6">
            <div class="card h-100 shadow-sm">
                <div class="card-header bg-info text-white">
                    <i class="bi bi-stars"></i> Intereses afines
                </div>
                <ul class="list-group list-group-flush">
                    <% if (afines == null || afines.isEmpty()) { %>
                    <li class="list-group-item text-muted">
                        Nadie comparte aún al menos dos intereses contigo. Agrega intereses en tu perfil.
                    </li>
                    <% } else { for (Estudiante e : afines) { %>
                    <li class="list-group-item d-flex justify-content-between align-items-center">
                        <span><i class="bi bi-person-heart text-info me-2"></i><%= Html.esc(e.getNombre()) %></span>
                        <a class="btn btn-outline-info btn-sm"
                           href="<%= Html.esc(ctx) %>/Chat?startId=<%= Html.esc(e.getId()) %>">Escribirle</a>
                    </li>
                    <% } } %>
                </ul>
            </div>
        </div>

        <!-- Contenidos recomendados -->
        <div class="col-md-6">
            <div class="card h-100 shadow-sm">
                <div class="card-header bg-warning">
                    <i class="bi bi-lightbulb"></i> Contenidos recomendados para ti
                </div>
                <ul class="list-group list-group-flush">
                    <% if (recomendados == null || recomendados.isEmpty()) { %>
                    <li class="list-group-item text-muted">
                        No hay contenidos nuevos que coincidan con tus intereses.
                    </li>
                    <% } else { for (Contenido c : recomendados) { %>
                    <li class="list-group-item">
                        <strong><%= Html.esc(c.getTema()) %></strong>
                        <small class="text-muted d-block">
                            por <%= Html.esc(c.getAutor().getNombre()) %> · <%= Html.esc(c.getTipo().name()) %>
                        </small>
                        <%= Html.esc(c.getDescripcion()) %>
                    </li>
                    <% } } %>
                </ul>
                <div class="card-footer">
                    <a href="<%= Html.esc(ctx) %>/inicio.jsp?seccion=home" class="btn btn-sm btn-outline-secondary">
                        Ir al Home para valorarlos
                    </a>
                </div>
            </div>
        </div>

        <!-- Ruta más corta -->
        <div class="col-12">
            <div class="card shadow-sm">
                <div class="card-header bg-dark text-white">
                    <i class="bi bi-signpost-split"></i> ¿Cómo llego a otro estudiante?
                </div>
                <div class="card-body">
                    <form method="get" action="<%= Html.esc(ctx) %>/inicio.jsp" class="row g-2 align-items-end">
                        <input type="hidden" name="seccion" value="companeros"/>
                        <div class="col-md-8">
                            <label class="form-label" for="destino">Estudiante destino</label>
                            <select class="form-select" id="destino" name="destino" required>
                                <option value="">Selecciona...</option>
                                <% if (otros != null) { for (Estudiante e : otros) { %>
                                <option value="<%= Html.esc(e.getId()) %>"
                                        <%= (destino != null && destino.getId().equals(e.getId())) ? "selected" : "" %>>
                                    <%= Html.esc(e.getNombre()) %>
                                </option>
                                <% } } %>
                            </select>
                        </div>
                        <div class="col-md-4">
                            <button class="btn btn-dark w-100" type="submit">Buscar ruta más corta</button>
                        </div>
                    </form>

                    <% if (destino != null) { %>
                    <hr/>
                    <% if (ruta == null || ruta.isEmpty()) { %>
                    <p class="text-muted mb-0">
                        No hay un camino entre tú y <strong><%= Html.esc(destino.getNombre()) %></strong>
                        en la red de afinidad.
                    </p>
                    <% } else { %>
                    <p class="mb-2">Camino más corto (<%= ruta.getTamanio() - 1 %> conexión(es)):</p>
                    <div class="d-flex flex-wrap align-items-center gap-2">
                        <% int i = 0; for (Estudiante e : ruta) { %>
                        <% if (i++ > 0) { %><i class="bi bi-arrow-right"></i><% } %>
                        <span class="badge bg-primary fs-6"><%= Html.esc(e.getNombre()) %></span>
                        <% } %>
                    </div>
                    <% } } %>
                </div>
            </div>
        </div>
    </div>
</div>
