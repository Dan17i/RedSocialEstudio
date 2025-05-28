<%@ page import="co.edu.uniquindio.redsocial.models.Contenido" %>
<%@ page import="co.edu.uniquindio.redsocial.models.structures.ListaEnlazada" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    ListaEnlazada<Contenido> contenidos = (ListaEnlazada<Contenido>) request.getAttribute("contenidos");
%>
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Tema</th>
        <th>Autor</th>
        <th>Acciones</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="contenido" items="${contenidos}">
        <tr>
            <td>${contenido.id}</td>
            <td>${contenido.tema}</td>
            <td>${contenido.autor.nombre}</td>
            <td>
                <form method="post" action="GestionContenidosServlet">
                    <input type="hidden" name="accion" value="eliminar">
                    <input type="hidden" name="contenidoId" value="${contenido.id}">
                    <button type="submit">Eliminar</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<c:if test="${param.success == 'contenido_eliminado'}">
    <div>Contenido eliminado correctamente.</div>
</c:if>
<c:if test="${param.error != null}">
    <div>Error: ${param.error}</div>
</c:if>