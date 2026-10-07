
<%@ page import="co.edu.uniquindio.redsocial.models.Estudiante" %>
<%@ page import="co.edu.uniquindio.redsocial.models.Reporte" %>
<%@ page import="co.edu.uniquindio.redsocial.models.structures.ListaEnlazada" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    Reporte<Estudiante> reporte = (Reporte<Estudiante>) request.getAttribute("reporte");
%>
<h3>${reporte.resumen}</h3>
<ul>
    <c:forEach var="estudiante" items="${reporte.datos}">
        <li>${estudiante.nombre}</li>
    </c:forEach>
</ul>
