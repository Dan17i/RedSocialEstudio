<%@ page import="co.edu.uniquindio.redsocial.models.*" %>
<%@ page import="co.edu.uniquindio.redsocial.models.structures.ListaEnlazada" %>
<%
    ListaEnlazada<Usuario> usuarios = (ListaEnlazada<Usuario>) request.getAttribute("usuarios");
%>
<ul>
    <% for(Usuario u : usuarios){ %>
    <li><%=u.getNombre()%> - <%=u.getEmail()%></li>
    <% } %>
</ul>
