<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="co.edu.uniquindio.redsocial.models.Estudiante" %>
<%@ page import="co.edu.uniquindio.redsocial.models.structures.ListaEnlazada" %>

<%
    ListaEnlazada<ListaEnlazada<Estudiante>> comunidades = (ListaEnlazada<ListaEnlazada<Estudiante>>) request.getAttribute("comunidades");
    request.setAttribute("comunidades", comunidades);
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Comunidades</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap Icons -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        body {
            background-color: #f4f6fa;
            padding: 40px;
        }
        .card {
            margin-bottom: 20px;
        }
    </style>
</head>
<body>

<div class="container">
    <h2 class="mb-4 text-primary">Comunidades</h2>

    <c:forEach var="comunidad" items="${comunidades}" varStatus="loop">
        <div class="card shadow-sm">
            <div class="card-header bg-info text-white">
                <h5 class="mb-0">Comunidad ${loop.count}</h5>
            </div>
            <div class="card-body">
                <ul class="list-group list-group-flush">
                    <c:forEach var="estudiante" items="${comunidad}">
                        <li class="list-group-item">
                            <i class="bi bi-person-circle text-primary"></i> ${estudiante.nombre}
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </c:forEach>

    <!-- Botón de regreso -->
    <div class="text-center mt-4">
        <a href="moderador.jsp" class="btn btn-primary">
            <i class="bi bi-arrow-left-circle"></i> Volver a Moderador
        </a>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
