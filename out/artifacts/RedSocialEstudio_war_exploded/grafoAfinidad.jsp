<%@ page import="co.edu.uniquindio.redsocial.models.Moderador" %>
<%@ page import="co.edu.uniquindio.redsocial.models.services.implement.GestorContenidos" %>
<%@ page import="co.edu.uniquindio.redsocial.models.services.implement.RedAfinidad" %>
<%@ page import="co.edu.uniquindio.redsocial.models.structures.ListaEnlazada" %>
<%@ page import="co.edu.uniquindio.redsocial.models.Estudiante" %>
<%
    Moderador moderador = (Moderador) session.getAttribute("usuario");
    // instancias (puedes usarlas desde dentro de los servlets que cargues en el iframe)
    GestorContenidos gestorContenidos = GestorContenidos.getInstancia();
    RedAfinidad redAfinidad        = RedAfinidad.getInstancia();
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
        .header form button {
            background: var(--accent-color);
            border: none;
            padding: 8px 12px;
            border-radius: 6px;
            color: white;
            font-weight: 500;
            cursor: pointer;
            transition: background 0.2s ease;
        }
        .header form button:hover {
            background: #e65c2c;
        }
        .actions {
            display: flex;
            flex-wrap: wrap;
            gap: 10px;
            margin: 20px 0;
        }
        .action-btn {
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
        .action-btn:hover {
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
    <!-- Título y botón "Generar datos" -->
    <div class="header">
        <h1>Panel de Moderador</h1>
        <form method="post" action="GenerarDatosServlet">
            <button type="submit">Generar Datos de Prueba</button>
        </form>
    </div>

    <!-- Alert de éxito -->
    <% if ("true".equals(request.getParameter("datosGenerados"))) { %>
    <div class="alert">¡Datos de prueba generados correctamente!</div>
    <% } %>

    <!-- Botones de acciones -->
    <div class="actions">
        <button class="action-btn" data-src="GestionUsuariosServlet" data-title="Gestionar Usuarios">
            Gestionar Usuarios
        </button>
        <button class="action-btn" data-src="GestionContenidosServlet" data-title="Gestionar Contenidos">
            Gestionar Contenidos
        </button>
        <button class="action-btn" data-src="ReporteServlet?tipo=comunidades" data-title="Comunidades">
            Comunidades
        </button>
        <button class="action-btn" data-src="ReporteServlet?tipo=contenidosMasValorados" data-title="Contenidos Más Valorados">
            Contenidos Más Valorados
        </button>
        <button class="action-btn" data-src="ReporteServlet?tipo=estudiantesMasConectados" data-title="Estudiantes Más Conectados">
            Estudiantes Más Conectados
        </button>
        <button class="action-btn" data-src="ReporteServlet?tipo=nivelesParticipacion" data-title="Participación">
            Participación
        </button>
        <button class="action-btn" data-src="GrafoAfinidadServlet" data-title="Grafo de Afinidad">
            Grafo de Afinidad
        </button>
    </div>

    <!-- Contenedor principal (iframe dinámico) -->
    <div class="graph-card">
        <header id="contentTitle">Grafo de Afinidad</header>
        <iframe id="contentFrame" src="GrafoAfinidadServlet"></iframe>
    </div>
</div>

<script>
    // Cada botón carga su URL en el iframe y actualiza el título
    document.querySelectorAll('.action-btn').forEach(btn => {
        btn.addEventListener('click', () => {
            const src   = btn.getAttribute('data-src');
            const title = btn.getAttribute('data-title');
            document.getElementById('contentFrame').src = src;
            document.getElementById('contentTitle').textContent = title;
        });
    });
</script>
</body>
</html>
