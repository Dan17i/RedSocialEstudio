package co.edu.uniquindio.redsocial.drivers;

import co.edu.uniquindio.redsocial.models.services.implement.SistemaAutenticacion;
import co.edu.uniquindio.redsocial.models.services.interf.IGestorUsuarios;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/GestionUsuariosServlet")
public class GestionUsuariosServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        IGestorUsuarios gestorUsuarios = (IGestorUsuarios)getServletContext().getAttribute("gestorUsuarios");
        req.setAttribute("usuarios", gestorUsuarios.listarUsuarios());
        req.getRequestDispatcher("gestionUsuarios.jsp").forward(req, resp);
    }
}

