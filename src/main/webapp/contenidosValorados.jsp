<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="co.edu.uniquindio.redsocial.models.Contenido" %>
<%@ page import="co.edu.uniquindio.redsocial.models.structures.ListaEnlazada" %>
<%@ page import="co.edu.uniquindio.redsocial.security.Html" %>
<%
    String resumen = (String) request.getAttribute("resumen");
    @SuppressWarnings("unchecked")
    ListaEnlazada<Contenido> contenidos = (ListaEnlazada<Contenido>) request.getAttribute("contenidos");
%>


<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Contenidos Más Valorados</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">

    <style>
        body {
            background-color: #f4f6fa;
        }

        .card-header {
            background: linear-gradient(to right, #6a11cb, #2575fc);
        }

        .card-header h4 {
            color: white;
            margin: 0;
        }

        .btn-volver {
            background-color: #6a11cb;
            border: none;
        }

        .btn-volver:hover {
            background-color: #2575fc;
        }
    </style>
</head>
<body>

<div class="container mt-5">
    <div class="card shadow rounded-3">
        <div class="card-header">
            <h4><i class="bi bi-star-fill"></i> Contenidos Más Valorados</h4>
        </div>
        <div class="card-body">
            <% if (resumen != null) { %>
                <h5 class="text-muted mb-4"><%= Html.esc(resumen) %></h5>
            <% } %>

            <% if (contenidos != null && !contenidos.isEmpty()) { %>
                    <ul class="list-group">
                        <% for (Contenido contenido : contenidos) { %>
                            <li class="list-group-item d-flex justify-content-between align-items-center">
                                <span>
                                    <i class="bi bi-file-earmark-text"></i>
                                    <%= Html.esc(contenido.getTema()) %>
                                    <small class="text-muted">· <%= Html.esc(contenido.getAutor().getNombre()) %></small>
                                </span>
                                <span class="badge bg-primary rounded-pill">
                                    Promedio: <%= String.format("%.1f", contenido.promedioValoraciones()) %> / 5
                                </span>
                            </li>
                        <% } %>
                    </ul>
            <% } else { %>
                    <p class="text-danger">No hay contenidos disponibles.</p>
            <% } %>
        </div>
    </div>

    <div class="text-center mt-4">
        <a href="moderador.jsp" class="btn btn-volver text-white">
            <i class="bi bi-arrow-left-circle"></i> Volver
        </a>
    </div>
</div>

<!-- Bootstrap Bundle JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
