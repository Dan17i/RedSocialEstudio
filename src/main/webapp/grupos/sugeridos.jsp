<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="co.edu.uniquindio.redsocial.models.GrupoEstudio" %>
<%@ page import="co.edu.uniquindio.redsocial.models.structures.ListaEnlazada" %>

<div class="container mt-4">
    <h3 class="mb-4">Grupos Sugeridos</h3>
    <div class="row">
        <%
            @SuppressWarnings("unchecked")
            ListaEnlazada<GrupoEstudio> sugeridos =
                    (ListaEnlazada<GrupoEstudio>) request.getAttribute("sugeridos");

            if (sugeridos != null && sugeridos.getTamanio() > 0) {
                for (int i = 0; i < sugeridos.getTamanio(); i++) {
                    GrupoEstudio g = sugeridos.obtener(i);
        %>
        <div class="col-md-4 mb-3">
            <div class="card h-100 shadow-sm">
                <div class="card-body d-flex flex-column">
                    <h5 class="card-title"><%= g.getTema() %></h5>
                    <p class="card-text mb-4">
                        Miembros: <%= g.getMiembros().getTamanio() %>
                    </p>
                    <form action="<%= request.getContextPath() %>/grupos/unirse"
                          method="post" class="mt-auto">
                        <input type="hidden" name="grupoId" value="<%= g.getId() %>"/>
                        <button type="submit" class="btn btn-success w-100">
                            Unirse
                        </button>
                    </form>
                </div>
            </div>
        </div>
        <%    }
        } else { %>
        <div class="col-12">
            <p class="text-muted">
                No hay grupos sugeridos para tus intereses.
            </p>
        </div>
        <% } %>
    </div>
</div>
