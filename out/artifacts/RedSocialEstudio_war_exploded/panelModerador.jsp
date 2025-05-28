<%@ page import="co.edu.uniquindio.redsocial.models.Moderador" %>
<%@ page import="co.edu.uniquindio.redsocial.models.services.implement.GestorContenidos" %>
<%@ page import="co.edu.uniquindio.redsocial.models.services.implement.RedAfinidad" %>
<%
    Moderador moderador = (Moderador) session.getAttribute("usuario");
    GestorContenidos gestorContenidos = GestorContenidos.getInstancia();
    RedAfinidad    redAfinidad        = RedAfinidad.getInstancia();
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Panel de Moderador</title>
    <style>
        :root {
            --primary-color: #4a6fff;
            --secondary-color: #f5f7ff;
            --text-color: #333;
            --accent-color: #ff7043;
            --border-color: #e1e4e8;
        }
        body {
            margin: 0;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: var(--secondary-color);
            color: var(--text-color);
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            background: var(--primary-color);
            padding: 15px 20px;
            border-radius: 8px;
            color: white;
        }
        .header h1 { margin: 0; font-size: 1.5rem; }
        .header form button {
            background: var(--accent-color);
            border: none; padding: 8px 12px;
            border-radius: 6px; color: white;
            font-weight: 500; cursor: pointer;
            transition: background .2s;
        }
        .header form button:hover { background: #e65c2c; }
        .actions {
            display: flex; flex-wrap: wrap; gap: 10px;
            margin: 20px 0;
        }
        .action-btn {
            background: var(--primary-color);
            color: white; text-decoration: none;
            padding: 10px 16px; border-radius: 6px;
            font-weight: 500; cursor: pointer;
            transition: background .2s;
        }
        .action-btn:hover { background: var(--accent-color); }
        .alert {
            margin-top: 10px; padding: 12px;
            background: #d4edda; border:1px solid #c3e6cb;
            border-radius:6px; color:#155724;
        }
        .graph-card {
            background: white; border:1px solid var(--border-color);
            border-radius:8px; overflow:hidden;
            box-shadow:0 2px 5px rgba(0,0,0,0.1);
            margin-top:20px;
        }
        .graph-card header {
            background: var(--primary-color); color: white;
            padding:12px 16px; font-size:1.1rem;
        }
        .graph-card iframe {
            width:100%; height:600px; border:none;
        }
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
        <a href="<%=request.getContextPath()%>/GestionUsuariosServlet"
           target="contentFrame" class="action-btn">Gestionar Usuarios</a>
        <a href="<%=request.getContextPath()%>/GestionContenidosServlet"
           target="contentFrame" class="action-btn">Gestionar Contenidos</a>
        <a href="<%=request.getContextPath()%>/ReporteServlet?tipo=comunidades"
           target="contentFrame" class="action-btn">Comunidades</a>
        <a href="<%=request.getContextPath()%>/ReporteServlet?tipo=contenidosMasValorados"
           target="contentFrame" class="action-btn">Contenidos Más Valorados</a>
        <a href="<%=request.getContextPath()%>/ReporteServlet?tipo=estudiantesMasConectados"
           target="contentFrame" class="action-btn">Estudiantes Más Conectados</a>
        <a href="<%=request.getContextPath()%>/ReporteServlet?tipo=nivelesParticipacion"
           target="contentFrame" class="action-btn">Participación</a>
        <a href="<%=request.getContextPath()%>/GrafoAfinidadServlet"
           target="contentFrame" class="action-btn">Grafo de Afinidad</a>
    </div>

    <div class="graph-card">
        <header>Vista</header>
        <iframe id="contentFrame" name="contentFrame"
                src="<%=request.getContextPath()%>/GrafoAfinidadServlet"></iframe>
    </div>
</div>
</body>
</html>
