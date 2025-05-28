<%@ page import="co.edu.uniquindio.redsocial.models.Estudiante" %>
<%@ page import="co.edu.uniquindio.redsocial.models.structures.ListaEnlazada" %>
<%
    @SuppressWarnings("unchecked")
    ListaEnlazada<Estudiante> usuarios =
            (ListaEnlazada<Estudiante>) request.getAttribute("usuarios");
%>

<table class="table">
    <thead>
    <tr>
        <th>Nombre</th>
        <th>Email</th>
        <th>Acciones</th>
    </tr>
    </thead>
    <tbody>
    <% for (int i = 0; i < usuarios.getTamanio(); i++) {
        Estudiante u = usuarios.obtener(i);
    %>
    <tr>
        <td><%= u.getNombre() %></td>
        <td><%= u.getEmail()  %></td>
        <td>
            <form action="GestionUsuariosServlet" method="post" style="display:inline">
                <input type="hidden" name="accion" value="eliminar">
                <input type="hidden" name="email"  value="<%= u.getEmail() %>">
                <button class="btn btn-sm btn-danger">Eliminar</button>
            </form>
        </td>
    </tr>
    <% } %>
    </tbody>
</table>
