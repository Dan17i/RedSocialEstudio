<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="co.edu.uniquindio.redsocial.models.GrupoEstudio"%>
<%@ page import="co.edu.uniquindio.redsocial.models.structures.ListaEnlazada"%>
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
    <div class="card mb-3">
        <div class="card-body d-flex justify-content-between align-items-center">
            <div>
                <h5 class="card-title"><%= g.getTema() %></h5>
                <p class="card-text">
                    Miembros: <%= g.getMiembros().getTamanio() %>
                </p>
            </div>
            <form action="<%= request.getContextPath() %>/grupos/unirse"
                  method="post">
                <input type="hidden" name="grupoId" value="<%= g.getId() %>" />
                <button class="btn btn-success">Unirse</button>
            </form>
        </div>
    </div>
    <%  }
    } else { %>
    <p>No hay grupos sugeridos.</p>
    <% } %>

    <a href="../inicio.jsp?seccion=grupos" class="btn btn-link">
        Volver a mis grupos
    </a>
</div>
