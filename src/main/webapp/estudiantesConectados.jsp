<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="co.edu.uniquindio.redsocial.models.Estudiante" %>
<%@ page import="co.edu.uniquindio.redsocial.models.Reporte" %>
<%@ page import="co.edu.uniquindio.redsocial.security.Html" %>
<%
    @SuppressWarnings("unchecked")
    Reporte<Estudiante> reporte = (Reporte<Estudiante>) request.getAttribute("reporte");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Estudiantes más conectados</title>
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
            <h4 class="mb-0"><i class="bi bi-diagram-3"></i> Estudiantes más conectados</h4>
        </div>
        <div class="card-body">
            <% if (reporte != null) { %>
            <h6 class="text-muted mb-3"><%= Html.esc(reporte.getResumen()) %></h6>
            <% if (reporte.getDatos().isEmpty()) { %>
            <p class="text-muted">Aún no hay conexiones en la red de afinidad.</p>
            <% } else { %>
            <ul class="list-group">
                <% for (Estudiante e : reporte.getDatos()) { %>
                <li class="list-group-item">
                    <i class="bi bi-person-circle text-primary me-2"></i><%= Html.esc(e.getNombre()) %>
                    <small class="text-muted">&lt;<%= Html.esc(e.getEmail()) %>&gt;</small>
                </li>
                <% } %>
            </ul>
            <% } } %>
            <div class="text-center mt-4">
                <a href="moderador.jsp" class="btn btn-outline-primary">
                    <i class="bi bi-arrow-left-circle me-1"></i> Volver
                </a>
            </div>
        </div>
    </div>
</div>
</body>
</html>
