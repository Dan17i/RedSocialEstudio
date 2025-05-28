<%@ page import="co.edu.uniquindio.redsocial.models.structures.*" %>
<%
    ListaEnlazada<?> datos = (ListaEnlazada<?>) request.getAttribute("datos");
%>
<ul>
    <% for(Object dato : datos){ %>
    <li><%=dato.toString()%></li>
    <% } %>
</ul>
