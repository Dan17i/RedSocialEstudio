<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="co.edu.uniquindio.redsocial.models.GrupoEstudio" %>
<%@ page import="co.edu.uniquindio.redsocial.models.structures.ListaEnlazada" %>

<div class="container mt-4">
    <h3>Mis Grupos</h3>
    <% @SuppressWarnings("unchecked")
    ListaEnlazada<GrupoEstudio> misGrupos =
            (ListaEnlazada<GrupoEstudio>) request.getAttribute("misGrupos");
        if (misGrupos != null && misGrupos.getTamanio() > 0) {
            for (int i = 0; i < misGrupos.getTamanio(); i++) {
                GrupoEstudio g = misGrupos.obtener(i);
    %>
    <div class="card mb-3 shadow-sm">
        <div class="card-body d-flex justify-content-between align-items-center">
            <div>
                <h5 class="card-title"><%= g.getTema() %></h5>
                <p class="card-text">Miembros: <%= g.getMiembros().getTamanio() %></p>
            </div>
            <a href="<%= request.getContextPath() %>/inicio.jsp?seccion=gruposDetalle&grupoId=<%= g.getId() %>"
               class="btn btn-outline-primary btn-sm">
                Ver detalle
            </a>


        </div>
    </div>
    <%   }
    } else { %>
    <p class="text-muted">No perteneces a ningún grupo.</p>
    <% } %>
    <form action="<%= request.getContextPath() %>/grupos/formar" method="post">
        <button class="btn btn-primary">Formar grupos automáticos</button>
    </form>
</div>
