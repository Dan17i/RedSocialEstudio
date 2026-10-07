<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="
    co.edu.uniquindio.redsocial.models.Estudiante,
    co.edu.uniquindio.redsocial.models.services.implement.SistemaAutenticacion,
    co.edu.uniquindio.redsocial.models.services.implement.RedAfinidad,
    co.edu.uniquindio.redsocial.models.structures.ListaEnlazada,
    co.edu.uniquindio.redsocial.models.structures.NodoGrafo,
    co.edu.uniquindio.redsocial.models.structures.NodoLista
" %>
<%
    // Cargar todos los estudiantes y la instancia de RedAfinidad
    SistemaAutenticacion auth = (SistemaAutenticacion)
            application.getAttribute("sistemaAutenticacion");
    ListaEnlazada<Estudiante> todos = auth.getEstudiantesRegistrados();
    RedAfinidad red = RedAfinidad.getInstancia();

    int n = todos.getTamanio();
    int centerX = 400, centerY = 300, radius = 250;
    co.edu.uniquindio.redsocial.models.structures.TablaHash<String,int[]> pos = new co.edu.uniquindio.redsocial.models.structures.TablaHash<>();
    for (int i = 0; i < n; i++) {
        Estudiante e = todos.obtener(i);
        double angle = 2 * Math.PI * i / n;
        pos.poner(e.getId(), new int[]{
                (int)(centerX + radius * Math.cos(angle)),
                (int)(centerY + radius * Math.sin(angle))
        });
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Grafo de Afinidad</title>
    <style>
        body { margin:0; font-family:Arial,sans-serif; }
        svg { background:#f9faff; display:block; }
        .link { stroke:#aaa; stroke-width:2; }
        .node circle { fill:#6c8eff; stroke:#fff; stroke-width:2; cursor:pointer; }
        .node text { fill:#fff; font-size:10px; pointer-events:none; }

        #infoPanel {
            position:absolute; top:0; right:0;
            width:300px; height:100%; padding:15px;
            background:#fff; border-left:1px solid #ddd;
            transform:translateX(100%); transition:transform .3s;
            overflow:auto;
        }
        #infoPanel.active { transform:translateX(0); }
        #infoPanel .close-btn {
            position:absolute; top:10px; right:10px;
            background:none; border:none; font-size:20px; cursor:pointer;
        }
        #suggestionsList { list-style:none; padding:0; margin-top:1em; }
        #suggestionsList li {
            margin-bottom:1em; padding-bottom:0.5em;
            border-bottom:1px solid #eee;
        }
    </style>
</head>
<body>
<div style="position:relative;">
    <svg width="800" height="600">
        <g class="links">
            <% for (NodoGrafo<Estudiante> nodoA : red.getGrafo().getNodos()) {
                Estudiante e1 = nodoA.getDato();
                int[] p1 = pos.obtener(e1.getId());
                if (p1 == null) continue;
                for (NodoGrafo<Estudiante> nodoB : nodoA.getAdyacentes().claves()) {
                    Estudiante e2 = nodoB.getDato();
                    int[] p2 = pos.obtener(e2.getId());
                    // cada arista se dibuja una sola vez
                    if (p2 == null || e1.getId().compareTo(e2.getId()) >= 0) continue;
            %>
            <line class="link"
                  x1="<%= co.edu.uniquindio.redsocial.security.Html.esc(String.valueOf(p1[0])) %>" y1="<%= co.edu.uniquindio.redsocial.security.Html.esc(String.valueOf(p1[1])) %>"
                  x2="<%= co.edu.uniquindio.redsocial.security.Html.esc(String.valueOf(p2[0])) %>" y2="<%= co.edu.uniquindio.redsocial.security.Html.esc(String.valueOf(p2[1])) %>" />
            <%   }
            } %>
        </g>

        <g class="nodes">
            <% for (int i = 0; i < n; i++) {
                Estudiante e = todos.obtener(i);
                int[] p = pos.obtener(e.getId());
            %>
            <g class="node"
               data-id="<%= co.edu.uniquindio.redsocial.security.Html.esc(String.valueOf(e.getId())) %>"
               data-name="<%= co.edu.uniquindio.redsocial.security.Html.esc(String.valueOf(e.getNombre())) %>"
               transform="translate(<%= co.edu.uniquindio.redsocial.security.Html.esc(String.valueOf(p[0])) %>,<%= co.edu.uniquindio.redsocial.security.Html.esc(String.valueOf(p[1])) %>)">
                <circle r="20"></circle>
                <text><%= co.edu.uniquindio.redsocial.security.Html.esc(String.valueOf(e.getNombre().substring(0,2).toUpperCase())) %></text>
            </g>
            <% } %>
        </g>
    </svg>

    <div id="infoPanel">
        <button class="close-btn">&times;</button>
        <h3 id="infoName"></h3>
        <ul id="suggestionsList"></ul>
    </div>
</div>

<script>
    const panel = document.getElementById('infoPanel'),
        infoName = document.getElementById('infoName'),
        sugList = document.getElementById('suggestionsList');

    panel.querySelector('.close-btn').onclick = () => panel.classList.remove('active');

    document.querySelectorAll('g.node').forEach(node => {
        node.addEventListener('click', () => {
            const id   = node.getAttribute('data-id'),
                name = node.getAttribute('data-name');
            infoName.textContent = name;
            sugList.innerHTML = '<li>Cargando...</li>';
            panel.classList.add('active');

            fetch('SugerenciasServlet?id=' + encodeURIComponent(id))
                .then(res => res.ok ? res.json() : Promise.reject(res.status))
                .then(arr => {
                    sugList.innerHTML = '';
                    if (!arr.length) {
                        sugList.innerHTML = '<li>No hay sugerencias</li>';
                    } else {
                        arr.forEach(o => {
                            const li = document.createElement('li');
                            li.innerHTML =
                                '<strong>Conectar con:</strong> ' + o.nombre + '<br>' +
                                '<em>Intereses compartidos:</em> ' + o.intereses + '<br>' +
                                '<em>Valoraciones en común:</em> ' + o.valoraciones + '<br>' +
                                '<em>Grupos compartidos:</em> ' + o.grupos;
                            sugList.appendChild(li);
                        });
                    }
                })
                .catch(() => {
                    sugList.innerHTML = '<li>Error cargando sugerencias</li>';
                });
        });
    });
</script>
</body>
</html>
