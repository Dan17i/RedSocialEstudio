<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="co.edu.uniquindio.redsocial.models.Contenido" %>
<%@ page import="co.edu.uniquindio.redsocial.models.structures.ListaEnlazada" %>
<%@ page import="co.edu.uniquindio.redsocial.security.Html" %>


<%
    ListaEnlazada<Contenido> contenidos = (ListaEnlazada<Contenido>) request.getAttribute("contenidos");
    request.setAttribute("contenidos", contenidos);
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Gestión de Contenidos</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        body {
            background-color: #f4f6fa;
            padding: 40px;
        }
        .header {
            background: linear-gradient(to right,#6a11cb,#2575fc);
            color: #fff;
            padding: 20px;
            border-radius: 10px;
            margin-bottom: 30px;
        }
        .table th {
            background-color: #e9ecef;
        }
    </style>
</head>
<body>

<div class="container">
    <div class="header text-center">
        <h2>Gestión de Contenidos</h2>
        <p class="mb-0">Visualiza y administra los contenidos compartidos por los estudiantes</p>
    </div>

    <% if ("contenido_eliminado".equals(request.getParameter("success"))) { %>
        <div class="alert alert-success alert-dismissible fade show" role="alert">
            <i class="bi bi-check-circle-fill"></i> Contenido eliminado correctamente.
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Cerrar"></button>
        </div>
    <% } %>

    <% if (request.getParameter("error") != null) { %>
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
            <i class="bi bi-exclamation-triangle-fill"></i> Error: <%= Html.esc(request.getParameter("error")) %>
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Cerrar"></button>
        </div>
    <% } %>

    <div class="table-responsive mb-4">
        <table class="table table-bordered table-hover align-middle text-center">
            <thead class="table-light">
            <tr>
                <th>ID</th>
                <th>Tema</th>
                <th>Autor</th>
                <th>Acciones</th>
            </tr>
            </thead>
            <tbody>
            <% if (contenidos == null || contenidos.isEmpty()) { %>
                <tr><td colspan="4" class="text-muted">No hay contenidos publicados.</td></tr>
            <% } else { for (Contenido contenido : contenidos) { %>
                <tr>
                    <td><%= Html.esc(contenido.getId()) %></td>
                    <td><%= Html.esc(contenido.getTema()) %></td>
                    <td><%= Html.esc(contenido.getAutor().getNombre()) %></td>
                    <td>
                        <form method="post" action="GestionContenidosServlet" onsubmit="return confirm('¿Estás seguro de eliminar este contenido?');">
                            <input type="hidden" name="accion" value="eliminar" />
                            <input type="hidden" name="contenidoId" value="<%= Html.esc(contenido.getId()) %>" />
                            <button type="submit" class="btn btn-sm btn-outline-danger">
                                <i class="bi bi-trash"></i> Eliminar
                            </button>
                        </form>
                    </td>
                </tr>
            <% } } %>
            </tbody>
        </table>
    </div>

    <!-- Botón de regreso -->
    <div class="text-center">
        <a href="moderador.jsp" class="btn btn-primary">
            <i class="bi bi-arrow-left-circle"></i> Volver a Moderador
        </a>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
