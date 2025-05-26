<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="co.edu.uniquindio.redsocial.models.Enums.TipoContenido" %>

<div class="container">
    <h4 class="mb-4">Crear nueva publicación</h4>

    <form action="CrearPublicacion" method="post" enctype="multipart/form-data">
        <div class="mb-3">
            <label for="tema" class="form-label">Título / Tema</label>
            <input type="text" class="form-control" id="tema" name="tema" required>
        </div>

        <div class="mb-3">
            <label for="descripcion" class="form-label">Descripción</label>
            <textarea class="form-control" id="descripcion" name="descripcion" rows="4" required></textarea>
        </div>

        <div class="mb-3">
            <label for="tipo" class="form-label">Tipo de contenido</label>
            <select class="form-select" id="tipo" name="tipo" required>
                <%
                    for (TipoContenido tipo : TipoContenido.values()) {
                %>
                <option value="<%= tipo.name() %>"><%= tipo.name() %></option>
                <%
                    }
                %>
            </select>
        </div>

        <div class="mb-3">
            <label for="archivo" class="form-label">Archivo adjunto (opcional)</label>
            <input type="file" class="form-control" id="archivo" name="archivo">
        </div>

        <button type="submit" class="btn btn-primary">Publicar</button>
    </form>
</div>
