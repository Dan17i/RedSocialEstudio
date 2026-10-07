<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="co.edu.uniquindio.redsocial.models.Enums.EstadoSolicitud" %>
<%@ page import="co.edu.uniquindio.redsocial.models.Estudiante" %>
<%@ page import="co.edu.uniquindio.redsocial.models.SolicitudAyuda" %>
<%@ page import="co.edu.uniquindio.redsocial.models.structures.ListaEnlazada" %>
<%@ page import="co.edu.uniquindio.redsocial.security.Html" %>
<%
    @SuppressWarnings("unchecked")
    ListaEnlazada<SolicitudAyuda> mis = (ListaEnlazada<SolicitudAyuda>) request.getAttribute("misSolicitudes");
    @SuppressWarnings("unchecked")
    ListaEnlazada<SolicitudAyuda> pendientes = (ListaEnlazada<SolicitudAyuda>) request.getAttribute("solicitudesPendientes");
    Estudiante yo = (Estudiante) session.getAttribute("usuarioActual");
    String ctx = request.getContextPath();
    String msg = request.getParameter("msg");
    boolean ok = "true".equals(request.getParameter("ok"));
%>
<div class="container-fluid mt-2">
    <h3 class="mb-1"><i class="bi bi-life-preserver"></i> Ayuda académica</h3>
    <p class="text-muted">Pide ayuda en un tema y ayuda a otros. Las solicitudes se atienden según su urgencia:
        <strong>1 es la más urgente</strong> y 10 la menos urgente.</p>

    <% if (msg != null && !msg.isBlank()) { %>
    <div class="alert <%= ok ? "alert-success" : "alert-danger" %>"><%= Html.esc(msg) %></div>
    <% } %>

    <div class="row g-3">
        <!-- Nueva solicitud -->
        <div class="col-lg-4">
            <div class="card shadow-sm h-100">
                <div class="card-header bg-primary text-white"><i class="bi bi-plus-circle"></i> Nueva solicitud</div>
                <div class="card-body">
                    <form method="post" action="<%= Html.esc(ctx) %>/ayuda">
                        <input type="hidden" name="accion" value="crear"/>
                        <div class="mb-2">
                            <label class="form-label" for="tema">Tema</label>
                            <input class="form-control" id="tema" name="tema" maxlength="80" required
                                   placeholder="Ej: grafos, derivadas..."/>
                        </div>
                        <div class="mb-2">
                            <label class="form-label" for="urgencia">Urgencia (1 alta - 10 baja)</label>
                            <input class="form-control" type="number" id="urgencia" name="urgencia"
                                   min="1" max="10" value="5" required/>
                        </div>
                        <div class="mb-3">
                            <label class="form-label" for="descripcion">¿Qué necesitas?</label>
                            <textarea class="form-control" id="descripcion" name="descripcion" rows="3"
                                      maxlength="500"></textarea>
                        </div>
                        <button class="btn btn-primary w-100" type="submit">Pedir ayuda</button>
                    </form>
                </div>
            </div>
        </div>

        <!-- Mis solicitudes -->
        <div class="col-lg-8">
            <div class="card shadow-sm mb-3">
                <div class="card-header bg-secondary text-white"><i class="bi bi-person-raised-hand"></i> Mis solicitudes</div>
                <ul class="list-group list-group-flush">
                    <% if (mis == null || mis.isEmpty()) { %>
                    <li class="list-group-item text-muted">No has pedido ayuda todavía.</li>
                    <% } else { for (SolicitudAyuda s : mis) { %>
                    <li class="list-group-item d-flex justify-content-between align-items-start">
                        <div>
                            <span class="badge bg-danger me-1">Urgencia <%= s.getUrgencia() %></span>
                            <strong><%= Html.esc(s.getTema()) %></strong>
                            <span class="badge <%= s.getEstado() == EstadoSolicitud.RESUELTA ? "bg-success"
                                    : s.getEstado() == EstadoSolicitud.EN_PROGRESO ? "bg-info" : "bg-warning text-dark" %> ms-1">
                                <%= Html.esc(s.getEstado().name().replace('_', ' ')) %>
                            </span>
                            <div class="small text-muted"><%= Html.esc(s.getDescripcion()) %></div>
                        </div>
                        <% if (s.getEstado() != EstadoSolicitud.RESUELTA) { %>
                        <form method="post" action="<%= Html.esc(ctx) %>/ayuda">
                            <input type="hidden" name="accion" value="resolver"/>
                            <input type="hidden" name="id" value="<%= Html.esc(s.getId()) %>"/>
                            <button class="btn btn-outline-success btn-sm" type="submit">Marcar resuelta</button>
                        </form>
                        <% } %>
                    </li>
                    <% } } %>
                </ul>
            </div>

            <!-- Cola global -->
            <div class="card shadow-sm">
                <div class="card-header bg-dark text-white">
                    <i class="bi bi-list-ol"></i> Solicitudes pendientes de la comunidad (la más urgente primero)
                </div>
                <ul class="list-group list-group-flush">
                    <% if (pendientes == null || pendientes.isEmpty()) { %>
                    <li class="list-group-item text-muted">No hay solicitudes pendientes.</li>
                    <% } else { for (SolicitudAyuda s : pendientes) {
                        boolean mia = yo != null && s.getEstudiante().equals(yo); %>
                    <li class="list-group-item d-flex justify-content-between align-items-start">
                        <div>
                            <span class="badge bg-danger me-1">Urgencia <%= s.getUrgencia() %></span>
                            <strong><%= Html.esc(s.getTema()) %></strong>
                            <span class="text-muted">· <%= Html.esc(s.getEstudiante().getNombre()) %><%= mia ? " (tú)" : "" %></span>
                            <div class="small text-muted"><%= Html.esc(s.getDescripcion()) %></div>
                        </div>
                        <% if (!mia) { %>
                        <form method="post" action="<%= Html.esc(ctx) %>/ayuda">
                            <input type="hidden" name="accion" value="atender"/>
                            <input type="hidden" name="id" value="<%= Html.esc(s.getId()) %>"/>
                            <button class="btn btn-primary btn-sm" type="submit">Ayudar</button>
                        </form>
                        <% } %>
                    </li>
                    <% } } %>
                </ul>
            </div>
        </div>
    </div>
</div>
