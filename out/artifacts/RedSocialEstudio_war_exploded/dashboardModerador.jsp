<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Panel Moderador</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">
    <div class="card shadow-sm">
        <div class="card-header bg-primary text-white text-center">
            <h3>Panel del Moderador</h3>
        </div>
        <div class="card-body">
            <h5>Bienvenido, Moderador Principal</h5>
            <p>Aquí puedes administrar la plataforma y acceder a funcionalidades especiales.</p>

            <ul>
                <li><a href="#">Gestionar usuarios</a></li>
                <li><a href="#">Revisar reportes</a></li>
                <li><a href="#">Configurar parámetros del sistema</a></li>
            </ul>
            <div class="logout mt-auto">
                <a class="nav-link" href="CerrarSesionServlet"><i class="bi bi-box-arrow-left"></i> Cerrar sesión</a>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
