<%@ page import="co.edu.uniquindio.redsocial.models.Moderador" %>
<%@ page import="co.edu.uniquindio.redsocial.models.services.implement.GestorContenidos" %>
<%@ page import="co.edu.uniquindio.redsocial.models.services.implement.RedAfinidad" %>
<%@ page import="co.edu.uniquindio.redsocial.models.structures.ListaEnlazada" %>
<%@ page import="co.edu.uniquindio.redsocial.models.Estudiante" %>
<%
    Moderador moderador = (Moderador) session.getAttribute("usuario");
    GestorContenidos gestorContenidos = GestorContenidos.getInstancia();
    RedAfinidad redAfinidad = RedAfinidad.getInstancia();
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
        .header h1 {
            margin: 0;
            font-size: 1.5rem;
        }
        .actions {
            display: flex;
            flex-wrap: wrap;
            gap: 10px;
            margin: 20px 0;
        }
        .actions a,
        .actions button {
            background: var(--primary-color);
            color: white;
            text-decoration: none;
            padding: 10px 16px;
            border: none;
            border-radius: 6px;
            font-weight: 500;
            cursor: pointer;
            transition: background 0.2s ease;
        }
        .actions a:hover,
        .actions button:hover {
            background: var(--accent-color);
        }
        .actions .generate-btn {
            background: var(--accent-color);
        }
        .alert {
            margin-top: 10px;
            padding: 12px;
            background: #d4edda;
            border: 1px solid #c3e6cb;
            border-radius: 6px;
            color: #155724;
        }
        .graph-card {
            background: white;
            border: 1px solid var(--border-color);
            border-radius: 8px;
            overflow: hidden;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            margin-top: 20px;
        }
        .graph-card header {
            background: var(--primary-color);
            color: white;
            padding: 12px 16px;
            font-size: 1.1rem;
        }
        .graph-card iframe {
            width: 100%;
            height: 600px;
            border: none;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="header">
        <h1>Panel de Moderador</h1>
        <form method="post" action="GenerarDatosServlet">
            <button type="submit" class="generate-btn">Generar Datos de Prueba</button>
        </form>
    </div>

    <div class="actions">
        <a href="GestionUsuariosServlet">Gestionar Usuarios</a>
        <a href="GestionContenidosServlet">Gestionar Contenidos</a>
        <a href="ReporteServlet?tipo=comunidades">Comunidades</a>
        <a href="ReporteServlet?tipo=contenidosMasValorados">Contenidos Más Valorados</a>
        <a href="ReporteServlet?tipo=estudiantesMasConectados">Estudiantes Más Conectados</a>
        <a href="ReporteServlet?tipo=nivelesParticipacion">Participación</a>
    </div>

    <% if ("true".equals(request.getParameter("datosGenerados"))) { %>
    <div class="alert">
        ¡Datos de prueba generados correctamente!
    </div>
    <% } %>

    <div class="graph-card">
        <header>Grafo de Afinidad</header>
        <iframe src="GrafoAfinidadServlet"></iframe>
    </div>
</div>
</body>
</html>
