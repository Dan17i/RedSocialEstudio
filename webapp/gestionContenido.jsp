<%@ page import="co.edu.uniquindio.redsocial.models.*" %>
<%@ page import="co.edu.uniquindio.redsocial.models.structures.ListaEnlazada" %>
<%
    ListaEnlazada<Contenido> contenidos = (ListaEnlazada<Contenido>) request.getAttribute("contenidos");
%>
<ul>
    <% for(Contenido c : contenidos){ %>
    <li><%=c.getTema()%> - Autor: <%=c.getAutor().getNombre()%></li>
    <% } %>
</ul>

