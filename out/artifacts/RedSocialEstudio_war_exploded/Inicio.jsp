<%@ page contentType="text/html;charset=UTF-8" language="java" session="true" %>
<%@ page import="co.edu.uniquindio.redsocial.models.Estudiante" %>
<%
    String nombreUsuario = "Invitado";
    Estudiante estudiante = (Estudiante) session.getAttribute("usuarioActual");
    if (estudiante != null) {
        nombreUsuario = estudiante.getNombre();
    }
%>

<%
    // Parámetro para determinar qué sección cargar
    String seccion = request.getParameter("seccion");
    if (seccion == null || seccion.isEmpty()) {
        seccion = "perfil"; // valor por defecto
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Red Social</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
    <style>
        body {
            background-color: #f4f6fa;
        }
        .sidebar {
            position: fixed;
            top: 0;
            left: 0;
            width: 250px;
            min-width: 250px;
            max-width: 250px;
            height: 100vh;
            background-color: #ffffff;
            box-shadow: 2px 0 5px rgba(0, 0, 0, 0.1);
            display: flex;
            flex-direction: column;
            overflow-y: auto;  /* permite scroll interno si hay muchos ítems */
        }
        .sidebar .profile {
            padding: 20px;
            text-align: center;
            border-bottom: 1px solid #ddd;
        }
        .sidebar .profile h6 {
            margin-top: 10px;
            font-weight: bold;
        }
        .sidebar .nav-link {
            padding: 15px 20px;
            color: #333;
            font-weight: 500;
            transition: background-color 0.3s;
        }
        .sidebar .nav-link:hover {
            background-color: #f0f0f0;
            color: #007bff;
        }
        .main-content {
            margin-left: 250px;
            padding: 30px;
        }
        .header {
            background: linear-gradient(to right, #6a11cb, #2575fc);
            color: white;
            border-radius: 10px;
            padding: 20px;
            margin-bottom: 30px;
        }
        .logout {
            position: absolute;
            bottom: 20px;
            width: 100%;
        }
        .logout .nav-link {
            color: #dc3545;
        }
    </style>
</head>
<body>

<div class="sidebar d-flex flex-column">
    <div class="profile">
        <div class="rounded-circle bg-secondary" style="width: 80px; height: 80px; margin: auto;"></div>
        <h6><%= nombreUsuario %></h6>
        <small>Página principal</small>
    </div>

    <nav class="nav flex-column mt-3">
        <a class="nav-link" href="inicio.jsp?seccion=home"><i class="bi bi-house-door"></i> Home</a>
        <a class="nav-link" href="inicio.jsp?seccion=perfil"><i class="bi bi-person"></i> Perfil</a>
        <a class="nav-link" href="inicio.jsp?seccion=grupos"><i class="bi bi-people"></i> Grupos</a>
        <a class="nav-link" href="inicio.jsp?seccion=chats"><i class="bi bi-chat-dots"></i> Chats</a>
        <a class="nav-link" href="inicio.jsp?seccion=sugerencias"><i class="bi bi-lightbulb"></i> Grupos sugeridos</a>
        <a class="nav-link" href="inicio.jsp?seccion=publicar"><i class="bi bi-pencil-square"></i> Crear publicación</a>
    </nav>

    <div class="logout mt-auto">
        <a class="nav-link" href="CerrarSesionServlet"><i class="bi bi-box-arrow-left"></i> Cerrar sesión</a>
    </div>
</div>

<div class="main-content">
    <div class="header">
        <h4>Hola, <%= nombreUsuario %></h4>
        <p>Bienvenido a tu espacio personal en la red social universitaria</p>
    </div>

    <div id="contenido">
        <%
            switch (seccion) {
                case "home":
        %>
        <jsp:include page="home.jsp" />
        <%
                break;
            case "perfil":
        %>
        <jsp:include page="perfil.jsp" />
        <%
                break;
            case "grupos":
                request.getRequestDispatcher("/grupos").include(request, response);
        %>
        <jsp:include page="grupos/lista.jsp" />
        <%
                break;
            case "sugerencias":
                request.getRequestDispatcher("/grupos/sugeridos").include(request, response);
        %>
        <jsp:include page="grupos/sugeridos.jsp" />
        <%
                break;
            case "gruposDetalle":
                request.getRequestDispatcher("/grupos/detalle").include(request, response);
        %>
        <jsp:include page="grupos/detalle.jsp" />
        <%
                break;
            case "chats":
        %>
        <jsp:include page="chats.jsp" />
        <%
                break;
            case "publicar":
        %>
        <jsp:include page="publicar.jsp" />
        <%
                break;
            default:
        %>
        <p>Bienvenido a tu espacio personal en la red social universitaria.</p>
        <%
                    break;
            }
        %>
    </div>
</div>

</body>
</html>