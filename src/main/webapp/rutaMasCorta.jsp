<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%@ page import="co.edu.uniquindio.redsocial.models.Estudiante" %>
<%@ page import="co.edu.uniquindio.redsocial.models.structures.ListaEnlazada" %>
<%@ page import="co.edu.uniquindio.redsocial.security.Html" %>
<%
    @SuppressWarnings("unchecked")
    ListaEnlazada<Estudiante> estudiantes = (ListaEnlazada<Estudiante>) request.getAttribute("estudiantes");
    @SuppressWarnings("unchecked")
    ListaEnlazada<String> ruta = (ListaEnlazada<String>) request.getAttribute("ruta");
    String origenSel = (String) request.getAttribute("origenSel");
    String destinoSel = (String) request.getAttribute("destinoSel");
    boolean consultado = Boolean.TRUE.equals(request.getAttribute("consultado"));
    String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Ruta más corta</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        body { background-color: #f4f6fa; }
        .card-header { background: linear-gradient(to right, #6a11cb, #2575fc); color: white; }
        .card { border-radius: 10px; box-shadow: 0 4px 10px rgba(0, 0, 0, 0.05); }
    </style>
</head>
<body>
<div class="container mt-5" style="max-width: 800px;">
    <div class="card">
        <div class="card-header">
            <h4 class="mb-0"><i class="bi bi-signpost-split"></i> Camino más corto entre dos estudiantes</h4>
        </div>
        <div class="card-body">
            <form method="get" action="<%= Html.esc(ctx) %>/RutaMasCortaServlet" class="row g-2 align-items-end">
                <div class="col-md-5">
                    <label class="form-label" for="origen">Origen</label>
                    <select class="form-select" id="origen" name="origen" required>
                        <option value="">Selecciona...</option>
                        <% if (estudiantes != null) { for (Estudiante e : estudiantes) { %>
                        <option value="<%= Html.esc(e.getId()) %>" <%= e.getId().equals(origenSel) ? "selected" : "" %>>
                            <%= Html.esc(e.getNombre()) %>
                        </option>
                        <% } } %>
                    </select>
                </div>
                <div class="col-md-5">
                    <label class="form-label" for="destino">Destino</label>
                    <select class="form-select" id="destino" name="destino" required>
                        <option value="">Selecciona...</option>
                        <% if (estudiantes != null) { for (Estudiante e : estudiantes) { %>
                        <option value="<%= Html.esc(e.getId()) %>" <%= e.getId().equals(destinoSel) ? "selected" : "" %>>
                            <%= Html.esc(e.getNombre()) %>
                        </option>
                        <% } } %>
                    </select>
                </div>
                <div class="col-md-2">
                    <button class="btn btn-primary w-100" type="submit">Buscar</button>
                </div>
            </form>

            <% if (consultado) { %>
            <hr/>
            <% if (ruta == null || ruta.isEmpty()) { %>
            <p class="text-muted mb-0">No existe un camino entre los dos estudiantes en la red de afinidad.</p>
            <% } else { %>
            <p>Camino de <strong><%= ruta.getTamanio() - 1 %></strong> conexión(es):</p>
            <div class="d-flex flex-wrap align-items-center gap-2">
                <% int i = 0; for (String nombre : ruta) { %>
                <% if (i++ > 0) { %><i class="bi bi-arrow-right"></i><% } %>
                <span class="badge bg-primary fs-6"><%= Html.esc(nombre) %></span>
                <% } %>
            </div>
            <% } } %>

            <div class="text-center mt-4">
                <a href="<%= Html.esc(ctx) %>/moderador.jsp" class="btn btn-outline-primary">
                    <i class="bi bi-arrow-left-circle me-1"></i> Volver al panel
                </a>
            </div>
        </div>
    </div>
</div>
</body>
</html>
