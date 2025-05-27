<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="co.edu.uniquindio.redsocial.models.GrupoEstudio" %>
<%@ page import="co.edu.uniquindio.redsocial.models.structures.ListaEnlazada" %>

<div class="container mt-4">
    <h3>Mis Grupos</h3>

    <% String msg = (String) request.getAttribute("mensaje");
        if (msg != null) { %>
    <div class="alert alert-success"><%= msg %></div>
    <% } %>

    <%
        @SuppressWarnings("unchecked")
        ListaEnlazada<GrupoEstudio> misGrupos =
                (ListaEnlazada<GrupoEstudio>) request.getAttribute("misGrupos");

        if (misGrupos != null && misGrupos.getTamanio() > 0) {
            for (int i = 0; i < misGrupos.getTamanio(); i++) {
                GrupoEstudio g = misGrupos.obtener(i);
    %>
    <div class="card mb-3 shadow-sm">
        <div class="card-body d-flex justify-content-between align-items-center">
            <div>
                <h5 class="card-title mb-1"><%= g.getTema() %></h5>
                <small class="text-muted">Miembros: <%= g.getMiembros().getTamanio() %></small>
            </div>
            <a href="<%= request.getContextPath() %>/inicio.jsp?seccion=gruposDetalle&id=<%= g.getId() %>"
               class="btn btn-outline-primary btn-sm">
                Ver detalle
            </a>
        </div>
    </div>
    <% }
    } else { %>
    <p class="text-muted">No perteneces a ningún grupo.</p>
    <% } %>

    <div class="mt-4">
        <form action="<%= request.getContextPath() %>/grupos/formar" method="post" class="d-inline">
            <button class="btn btn-primary">Formar grupos automáticos</button>
        </form>
        <a href="inicio.jsp?seccion=sugerencias" class="btn btn-secondary ms-2">
            Ver grupos sugeridos
        </a>
    </div>
</div>
