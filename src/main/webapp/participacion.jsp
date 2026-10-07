<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="co.edu.uniquindio.redsocial.models.Reporte" %>


<%
    Reporte<String> reporte = (Reporte<String>) request.getAttribute("reporte");
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
        .card-header {
            background: linear-gradient(to right,#6a11cb,#2575fc);
            color: white;
        }
    </style>
</head>
<body>

<div class="container mt-5">
    <div class="card shadow rounded-3">
        <div class="card-header">
            <h4 class="mb-0">Reporte de Participación</h4>
        </div>
        <div class="card-body">
            <h5 class="text-muted mb-4">${reporte.resumen}</h5>
            <ul class="list-group">
                <c:forEach var="nivel" items="${reporte.datos}">
                    <li class="list-group-item">
                        <i class="bi bi-graph-up"></i> ${nivel}
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>

    <!-- Botón de regreso -->
    <div class="text-center mt-4">
        <a href="moderador.jsp" class="btn btn-primary">
            <i class="bi bi-arrow-left-circle"></i> Volver
        </a>
    </div>
</div>

<!-- Bootstrap JS Bundle -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js">
</script>
</body>
</html>
