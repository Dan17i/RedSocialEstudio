<%@ page import="co.edu.uniquindio.redsocial.models.Usuario" %>
<%@ page import="co.edu.uniquindio.redsocial.models.structures.ListaEnlazada" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    ListaEnlazada<Usuario> usuarios = (ListaEnlazada<Usuario>) request.getAttribute("usuarios");
%>
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Nombre</th>
        <th>Email</th>
        <th>Acciones</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="usuario" items="${usuarios}">
        <tr>
            <td>${usuario.id}</td>
            <td>${usuario.nombre}</td>
            <td>${usuario.email}</td>
            <td>
                <form method="post" action="GestionUsuariosServlet">
                    <input type="hidden" name="accion" value="eliminar">
                    <input type="hidden" name="usuarioId" value="${usuario.id}">
                    <button type="submit">Eliminar</button>
                </form>
                <form method="post" action="GestionUsuariosServlet">
                    <input type="hidden" name="accion" value="modificar">
                    <input type="hidden" name="usuarioId" value="${usuario.id}">
                    <button type="submit">Modificar</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<c:if test="${param.success == 'usuario_eliminado'}">
    <div>Usuario eliminado correctamente.</div>
</c:if>
<c:if test="${param.error != null}">
    <div>Error: ${param.error}</div>
</c:if>