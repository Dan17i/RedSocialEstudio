
<%@ page import="co.edu.uniquindio.redsocial.models.Estudiante" %>
<%@ page import="co.edu.uniquindio.redsocial.models.structures.ListaEnlazada" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    ListaEnlazada<ListaEnlazada<Estudiante>> comunidades = (ListaEnlazada<ListaEnlazada<Estudiante>>) request.getAttribute("comunidades");
%>
<c:forEach var="comunidad" items="${comunidades}" varStatus="loop">
    <h3>Comunidad ${loop.count}</h3>
    <ul>
        <c:forEach var="estudiante" items="${comunidad}">
            <li>${estudiante.nombre}</li>
        </c:forEach>
    </ul>
</c:forEach>
