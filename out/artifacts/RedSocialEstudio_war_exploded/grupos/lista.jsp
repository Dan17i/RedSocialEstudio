<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="co.edu.uniquindio.redsocial.models.GrupoEstudio"%>
<%@ page import="co.edu.uniquindio.redsocial.models.structures.ListaEnlazada"%>
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
    <div class="card mb-3">
        <div class="card-body">
            <h5 class="card-title"><%= g.getTema() %></h5>
            <p class="card-text">
                Miembros: <%= g.getMiembros().getTamanio() %>
            </p>
        </div>
    </div>
    <%  }
    } else { %>
    <p>No perteneces a ningún grupo.</p>
    <% } %>

    <form action="<%= request.getContextPath() %>/grupos/formar"
          method="post" class="d-inline">
        <button class="btn btn-primary">Formar grupos automáticos</button>
    </form>
    <a href="../inicio.jsp?seccion=sugerencias"
       class="btn btn-secondary">Ver grupos sugeridos</a>
</div>
