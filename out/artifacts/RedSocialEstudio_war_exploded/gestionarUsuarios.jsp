<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%@ page import="co.edu.uniquindio.redsocial.models.Estudiante" %>
<%@ page import="co.edu.uniquindio.redsocial.models.structures.ListaEnlazada" %>

<%
    String mensaje = (String) request.getAttribute("mensaje");
    String error = (String) request.getAttribute("error");
    ListaEnlazada<Estudiante> usuarios = (ListaEnlazada<Estudiante>) request.getAttribute("usuarios");
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Gestión de Usuarios</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body style="background-color: #f4f6fa;">

<div class="container mt-5">
    <div class="card shadow rounded-3">
        <div class="card-header bg-primary text-white">
            <h4 class="mb-0">Gestión de Usuarios</h4>
        </div>
        <div class="card-body">

            <!-- Mensajes -->
            <%
                if (mensaje != null) {
            %>
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <%= mensaje %>
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
            <%
                }
                if (error != null && !error.trim().isEmpty()) {
            %>
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <strong>Error:</strong> <%= error %>
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
            <%
                }
            %>

            <!-- Tabla de usuarios -->
            <div class="table-responsive">
                <table class="table table-striped align-middle text-center">
                    <thead class="table-light">
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Email</th>
                        <th>Acciones</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        if (usuarios != null && !usuarios.isEmpty()) {
                            for (Estudiante u : usuarios) {
                    %>
                    <tr>
                        <td><%= u.getId() %></td>
                        <td><%= u.getNombre() %></td>
                        <td><%= u.getEmail() %></td>
                        <td>
                            <form action="GestionUsuariosServlet" method="post" class="d-inline">
                                <input type="hidden" name="accion" value="eliminar">
                                <input type="hidden" name="codigo" value="<%= u.getId() %>">
                                <button type="submit" class="btn btn-danger btn-sm">
                                    <i class="bi bi-trash"></i> Eliminar
                                </button>
                            </form>
                            <form action="ModificarUsuarioServlet" method="get" class="d-inline">
                                <input type="hidden" name="codigo" value="<%= u.getId() %>">
                                <button type="submit" class="btn btn-warning btn-sm">
                                    <i class="bi bi-pencil"></i> Modificar
                                </button>
                            </form>
                        </td>
                    </tr>
                    <%
                        }
                    } else {
                    %>
                    <tr>
                        <td colspan="4" class="text-muted">No hay usuarios registrados.</td>
                    </tr>
                    <%
                        }
                    %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap JS Bundle -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<!-- Bootstrap Icons -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
</body>
</html>
