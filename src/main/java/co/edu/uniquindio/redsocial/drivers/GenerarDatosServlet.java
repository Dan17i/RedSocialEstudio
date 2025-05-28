package co.edu.uniquindio.redsocial.drivers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import co.edu.uniquindio.redsocial.models.Estudiante;
import co.edu.uniquindio.redsocial.models.Contenido;
import co.edu.uniquindio.redsocial.models.GrupoEstudio;
import co.edu.uniquindio.redsocial.models.Enums.TipoContenido;
import co.edu.uniquindio.redsocial.models.services.implement.GestorContenidos;
import co.edu.uniquindio.redsocial.models.services.implement.RedAfinidad;
import co.edu.uniquindio.redsocial.models.services.implement.SistemaAutenticacion;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

@WebServlet("/GenerarDatosServlet")
public class GenerarDatosServlet extends HttpServlet {

    // Pool de posibles intereses
    private static final String[] TEMAS = {
            "tecnologia", "deportes", "ciencia", "arte", "musica"
    };

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        SistemaAutenticacion sistemaAuth = (SistemaAutenticacion)
                getServletContext().getAttribute("sistemaAutenticacion");
        GestorContenidos gestorContenidos = GestorContenidos.getInstancia();
        RedAfinidad redAfinidad         = RedAfinidad.getInstancia();

        // Lista enlazada de todos los estudiantes generados
        ListaEnlazada<Estudiante> listaEstudiantes = new ListaEnlazada<>();

        // 1) Crear 10 estudiantes con 3 intereses aleatorios cada uno
        for (int i = 1; i <= 10; i++) {
            String nombre = "Estudiante " + i;
            String email  = "est" + i + "@correo.com";
            String pass   = "pass123";

            // registrarEstudiante crea al Estudiante con intereses vacíos
            Estudiante est = sistemaAuth.registrarEstudiante(nombre, email, pass);

            // ahora le agregamos 3 intereses aleatorios
            for (int k = 0; k < 3; k++) {
                String tema = TEMAS[(int)(Math.random()*TEMAS.length)];
                // asumiendo que Usuario tiene getIntereses()
                est.getIntereses().agregar(tema);
            }

            redAfinidad.agregarEstudiante(est);
            listaEstudiantes.agregar(est);
        }

        // 2) Crear 5 contenidos de distintos tipos y valorarlos
        TipoContenido[] tipos = TipoContenido.values();
        for (int i = 0; i < 5; i++) {
            Estudiante autor = listaEstudiantes.obtener(i);
            TipoContenido tipo = tipos[i % tipos.length];

            Contenido contenido = new Contenido(
                    UUID.randomUUID().toString(),
                    "Tema de prueba " + (i+1),
                    "Descripción de ejemplo " + (i+1),
                    autor,
                    tipo,
                    LocalDateTime.now(),
                    new ListaEnlazada<>(), // valoraciones inicial vacía
                    null                   // sin multimedia
            );

            // publica y añade al árbol/lista interna
            autor.publicarContenido(contenido, gestorContenidos);

            // cada contenido recibe 3 valoraciones con comentario
            for (int j = 1; j <= 3; j++) {
                int idx = (i + j) % listaEstudiantes.getTamanio();
                Estudiante eval = listaEstudiantes.obtener(idx);
                eval.valorarContenido(
                        contenido,
                        1 + (j % 5),
                        "¡Comentario de prueba " + j + "!"
                );
            }
        }

        // 3) Crear un grupo por cada tema y añadir a todos los que lo comparten
        for (String tema : TEMAS) {
            GrupoEstudio grupo = new GrupoEstudio("Grupo " + tema, tema);
            // recorro todos y los uno si comparten el tema
            for (int i = 0; i < listaEstudiantes.getTamanio(); i++) {
                Estudiante est = listaEstudiantes.obtener(i);
                if (est.getIntereses().contiene(tema)) {
                    est.unirseAGrupo(grupo);
                }
            }
        }

        // 4) Redirigir al panel del moderador
        resp.sendRedirect("moderador.jsp?datosGenerados=true");
    }
}
