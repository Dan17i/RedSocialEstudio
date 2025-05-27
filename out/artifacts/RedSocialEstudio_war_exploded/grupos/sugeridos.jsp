<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="co.edu.uniquindio.redsocial.models.GrupoEstudio" %>
<%@ page import="co.edu.uniquindio.redsocial.models.structures.ListaEnlazada" %>

<div class="container mt-4">
    <h3>Grupos Sugeridos</h3>

    <%
        @SuppressWarnings("unchecked")
        ListaEnlazada<GrupoEstudio> sugeridos =
                (ListaEnlazada<GrupoEstudio>) request.getAttribute("sugeridos");

        if (sugeridos != null && sugeridos.getTamanio() > 0) {
            for (int i = 0; i < sugeridos.getTamanio(); i++) {
                GrupoEstudio g = sugeridos.obtener(i);
    %>
    <div class="card mb-3 shadow-sm">
        <div class="card-body d-flex justify-content-between align-items-center">
            <div>
                <h5 class="card-title mb-1"><%= g.getTema() %></h5>
                <small class="text-muted">Miembros: <%= g.getMiembros().getTamanio() %></small>
            </div>
            <div>
                <form action="<%= request.getContextPath() %>/grupos/unirse" method="post" class="d-inline">
                    <input type="hidden" name="grupoId" value="<%= g.getId() %>" />
                    <button class="btn btn-success btn-sm">Unirse</button>
                </form>
                <a href="<%= request.getContextPath() %>/inicio.jsp?seccion=gruposDetalle&id=<%= g.getId() %>"
                   class="btn btn-outline-primary btn-sm ms-2">
                    Ver detalle
                </a>
            </div>
        </div>
    </div>
    <% }
    } else { %>
    <p class="text-muted">No hay grupos sugeridos.</p>
    <% } %>

    <div class="mt-4">
        <a href="inicio.jsp?seccion=grupos" class="btn btn-secondary">
            Volver a mis grupos
        </a>
    </div>
</div>
