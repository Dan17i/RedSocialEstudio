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
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        body {
            background-color: #f4f6fa;
            padding: 30px;
        }
        .header {
            background: linear-gradient(to right, #6a11cb, #2575fc);
            color: white;
            border-radius: 10px;
            padding: 20px;
            margin-bottom: 30px;
        }
        .card {
            border: 1px solid #dee2e6;
            box-shadow: 0 2px 4px rgba(0,0,0,0.05);
            margin-bottom: 20px;
        }
        .card-header {
            background-color: #0dcaf0;
            color: white;
            font-weight: bold;
        }
    </style>
</head>
<body>

<div class="container">

    <!-- Encabezado -->
    <div class="header text-center">
        <h3 class="mb-1">Comunidades</h3>
        <p>Explora los grupos formados por estudiantes según afinidad académica</p>
    </div>

    <!-- Lista de comunidades -->
    <c:forEach var="comunidad" items="${comunidades}" varStatus="loop">
        <div class="card">
            <div class="card-header">
                Comunidad ${loop.count}
            </div>
            <div class="card-body p-0">
                <ul class="list-group list-group-flush">
                    <c:forEach var="estudiante" items="${comunidad}">
                        <li class="list-group-item">
                            <i class="bi bi-person-circle text-primary me-2"></i> ${estudiante.nombre}
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </c:forEach>

    <!-- Botón para regresar -->
    <div class="text-center mt-4">
        <a href="moderador.jsp" class="btn btn-outline-primary">
            <i class="bi bi-arrow-left-circle me-1"></i> Volver
        </a>
    </div>

</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
