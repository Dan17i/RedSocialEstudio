<%@ page import="co.edu.uniquindio.redsocial.models.Moderador" %>
<%@ page import="co.edu.uniquindio.redsocial.models.services.implement.GestorContenidos" %>
<%@ page import="co.edu.uniquindio.redsocial.models.services.implement.RedAfinidad" %>
<%
    Moderador moderador = (Moderador) session.getAttribute("usuario");
    if (moderador == null) {
        response.sendRedirect(request.getContextPath() + "/loginModerador.jsp");
        return;
    }
    GestorContenidos gestorContenidos = GestorContenidos.getInstancia();
    RedAfinidad redAfinidad = RedAfinidad.getInstancia();
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Panel de Moderador</title>
    <style>
        /* Tus estilos CSS aquí */
    </style>
</head>
<body>
<div class="container">
    <div class="header">
        <h1>Panel de Moderador</h1>
        <form method="post" action="<%=request.getContextPath()%>/GenerarDatosServlet">
            <button type="submit">Generar Datos de Prueba</button>
        </form>
    </div>

    <% if ("true".equals(request.getParameter("datosGenerados"))) { %>
    <div class="alert">¡Datos de prueba generados correctamente!</div>
    <% } %>

    <div class="actions">
        <a href="<%=request.getContextPath()%>/GestionUsuariosServlet" target="contentFrame" class="action-btn">Gestionar Usuarios</a>
        <a href="<%=request.getContextPath()%>/GestionContenidosServlet" target="contentFrame" class="action-btn">Gestionar Contenidos</a>
        <a href="<%=request.getContextPath()%>/ComunidadesServlet" target="contentFrame" class="action-btn">Comunidades</a>
        <a href="<%=request.getContextPath()%>/ContenidosValoradosServlet" target="contentFrame" class="action-btn">Contenidos Más Valorados</a>
        <a href="<%=request.getContextPath()%>/EstudiantesConectadosServlet" target="contentFrame" class="action-btn">Estudiantes Más Conectados</a>
        <a href="<%=request.getContextPath()%>/ParticipacionServlet" target="contentFrame" class="action-btn">Participación</a>
        <a href="<%=request.getContextPath()%>/GrafoAfinidadServlet" target="contentFrame" class="action-btn">Grafo de Afinidad</a>
    </div>

    <div class="graph-card">
        <header>Vista</header>
        <iframe id="contentFrame" name="contentFrame" src="<%=request.getContextPath()%>/GrafoAfinidadServlet"></iframe>
    </div>
</div>
</body>
</html>