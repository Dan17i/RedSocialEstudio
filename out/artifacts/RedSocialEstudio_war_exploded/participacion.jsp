<%@ page import="co.edu.uniquindio.redsocial.models.Reporte" %>
<%@ page import="co.edu.uniquindio.redsocial.models.structures.ListaEnlazada" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    Reporte<String> reporte = (Reporte<String>) request.getAttribute("reporte");
%>
<h3>${reporte.resumen}</h3>
<ul>
    <c:forEach var="nivel" items="${reporte.datos}">
        <li>${nivel}</li>
    </c:forEach>
</ul>
