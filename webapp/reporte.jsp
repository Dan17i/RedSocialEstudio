<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%@ page import="co.edu.uniquindio.redsocial.models.structures.ListaEnlazada" %>
<%
    ListaEnlazada<?> datos = (ListaEnlazada<?>) request.getAttribute("datos");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Reporte de Participación</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
          rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css"
          rel="stylesheet">
    <style>
        body {
            background-color: #f4f6fa;
        }

        .container {
            margin-top: 50px;
        }

        .card-header {
            background: linear-gradient(to right, #6a11cb, #2575fc);
            color: white;
        }

        .card {
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.05);
        }

        .empty-message {
            padding: 20px;
            text-align: center;
            color: #888;
            font-style: italic;
        }
    </style>
</head>
<body>

<div class="container">
    <div class="card">
        <div class="card-header">
            <h4 class="mb-0"><i class="bi bi-bar-chart-line"></i> Reporte de Participación</h4>
        </div>
        <div class="card-body">
            <% if (datos == null || datos.isEmpty()) { %>
            <div class="empty-message">
                No hay datos disponibles para mostrar.
            </div>
            <% } else { %>
            <ul class="list-group">
                <% for (Object dato : datos) { %>
                <li class="list-group-item">
                    <i class="bi bi-chevron-right text-primary"></i>
                    <%= dato.toString() %>
                </li>
                <% } %>
            </ul>
            <% } %>
        </div>
    </div>
</div>

<!-- Bootstrap JS Bundle -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js">
</script>
</body>
</html>
