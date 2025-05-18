<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>Iniciar Sesión</title>
  <link rel="stylesheet" href="styles.css" />
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet"/>
</head>
<body class="bg-light" style="background-color: #f8f6fa;">

<div class="container mt-5">
  <div class="row">

    <!-- Imagen a la izquierda -->
    <div class="col-md-6 d-flex align-items-center justify-content-center">
      <img src="images/logo-img.png" alt="Inicio de sesión" class="img-fluid" style="max-height: 450px;">
    </div>

    <!-- Formulario de inicio de sesión a la derecha -->
    <div class="col-md-6 row justify-content-center">
      <div class="card shadow-sm">
        <div class="card-header text-center bg-primary text-white row justify-content-center">
          <h4>Iniciar Sesión</h4>
        </div>
        <div class="card-body">
          <form action="login" method="post"> <!-- Debe coincidir con el servlet -->
            <div class="mb-3">
              <label for="correo" class="form-label">Correo electrónico</label>
              <input type="email" class="form-control" id="correo" name="correo" required />
            </div>

            <div class="mb-3">
              <label for="contrasena" class="form-label">Contraseña</label>
              <input type="password" class="form-control" id="contrasena" name="contrasena" required />
            </div>

            <div class="d-grid">
              <button type="submit" class="btn btn-primary row justify-content-center">Ingresar</button>
            </div>
          </form>
        </div>

        <div class="card-footer text-center">
          ¿No tienes cuenta? <a href="Registro.jsp">Regístrate aquí</a>
        </div>
      </div>
    </div>

  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
