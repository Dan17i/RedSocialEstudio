<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Registro</title>
  <link rel="stylesheet" href="styles.css">
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light" style="background-color: #f8f6fa;">

<div class="container mt-5">
  <div class="row">
    <!-- Columna del formulario -->
    <% if (request.getAttribute("mensaje") != null) { %>
    <div class="alert alert-success" role="alert">
      <%= request.getAttribute("mensaje") %>
    </div>
    <% } %>

    <% if (request.getAttribute("error") != null) { %>
    <div class="alert alert-danger" role="alert">
      <%= request.getAttribute("error") %>
    </div>
    <% } %>

    <div class="col-md-6">
      <div class="card shadow-sm">
        <div class="card-header text-center bg-primary text-white">
          <h4>Registro de Usuario</h4>
        </div>
        <div class="card-body">
          <form action="Registro" method="post">
            <div class="mb-3">
              <label for="nombre" class="form-label">Nombre completo</label>
              <input type="text" class="form-control" id="nombre" name="nombre" required>
            </div>

            <div class="mb-3">
              <label for="id" class="form-label">ID</label>
              <input type="text" class="form-control" id="id" name="id" required>
            </div>

            <div class="mb-3">
              <label for="correo" class="form-label">Correo electrónico</label>
              <input type="email" class="form-control" id="correo" name="correo" required>
            </div>

            <div class="mb-3">
              <label for="contrasena" class="form-label">Contraseña</label>
              <input type="password" class="form-control" id="contrasena" name="contrasena" required>
            </div>

            <!-- Contenedor para los intereses -->
            <div id="intereses-container">
              <!-- intereses.html se puede insertar aquí con JavaScript si es necesario -->
            </div>

            <div class="d-grid mb-2">
              <button type="submit" class="btn btn-primary">Registrarse</button>
            </div>

            <div class="text-center">
              <a href="inicioSesion.jsp" class="btn btn-link">¿Ya tienes cuenta? Iniciar sesión</a>
            </div>
          </form>
        </div>
      </div>
    </div>

    <!-- Columna de la imagen -->
    <div class="col-md-6 d-flex align-items-center justify-content-center">
      <img src="images/logo-img.png" alt="Bienvenido a la red" class="img-fluid" style="max-height: 450px;">
    </div>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
