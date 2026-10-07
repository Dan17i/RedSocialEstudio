package security;

import co.edu.uniquindio.redsocial.models.Moderador;
import co.edu.uniquindio.redsocial.models.Usuario;
import co.edu.uniquindio.redsocial.models.services.implement.SistemaAutenticacion;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;
import co.edu.uniquindio.redsocial.security.FiltroAcceso;
import co.edu.uniquindio.redsocial.security.FiltroAcceso.Decision;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FiltroAccesoTest {

    private Usuario estudiante() {
        return new SistemaAutenticacion().registrarEstudiante("Ana", "ana@correo.com", "clave123");
    }

    private Usuario moderador() {
        return new Moderador("MOD-1", "Mod", "mod@correo.com", null,
                new ListaEnlazada<>(), new ListaEnlazada<>(), new ListaEnlazada<>(),
                true, new ListaEnlazada<>(), null, null, null);
    }

    @Test
    void rutas_publicas_no_requieren_sesion() {
        assertEquals(Decision.PERMITIR, FiltroAcceso.evaluar("/inicioSesion.jsp", null));
        assertEquals(Decision.PERMITIR, FiltroAcceso.evaluar("/login", null));
        assertEquals(Decision.PERMITIR, FiltroAcceso.evaluar("/Registro", null));
        assertEquals(Decision.PERMITIR, FiltroAcceso.evaluar("/css/chat.css", null));
        assertEquals(Decision.PERMITIR, FiltroAcceso.evaluar("", null));
    }

    @Test
    void sin_sesion_se_pide_login_en_rutas_privadas() {
        assertEquals(Decision.REQUIERE_LOGIN, FiltroAcceso.evaluar("/inicio.jsp", null));
        assertEquals(Decision.REQUIERE_LOGIN, FiltroAcceso.evaluar("/rutaMasCorta.jsp", null));
        assertEquals(Decision.REQUIERE_LOGIN, FiltroAcceso.evaluar("/GenerarDatosServlet", null));
    }

    @Test
    void estudiante_accede_a_sus_paginas_pero_no_a_las_de_moderador() {
        Usuario e = estudiante();
        assertEquals(Decision.PERMITIR, FiltroAcceso.evaluar("/inicio.jsp", e));
        assertEquals(Decision.PERMITIR, FiltroAcceso.evaluar("/grupos", e));
        assertEquals(Decision.PROHIBIDO, FiltroAcceso.evaluar("/moderador.jsp", e));
        assertEquals(Decision.PROHIBIDO, FiltroAcceso.evaluar("/GestionUsuariosServlet", e));
        assertEquals(Decision.PROHIBIDO, FiltroAcceso.evaluar("/GenerarDatosServlet", e));
    }

    @Test
    void moderador_accede_a_las_paginas_de_moderador() {
        assertEquals(Decision.PERMITIR, FiltroAcceso.evaluar("/moderador.jsp", moderador()));
        assertEquals(Decision.PERMITIR, FiltroAcceso.evaluar("/RutaMasCortaServlet", moderador()));
    }

    @Test
    void post_del_mismo_origen_se_acepta() {
        assertEquals(true, FiltroAcceso.origenValido("POST", "http://localhost:8080", null, "localhost:8080"));
        assertEquals(true, FiltroAcceso.origenValido("POST", null, "http://localhost:8080/app/perfil.jsp", "localhost:8080"));
    }

    @Test
    void post_de_otro_origen_se_rechaza() {
        assertEquals(false, FiltroAcceso.origenValido("POST", "http://sitio-malo.com", null, "localhost:8080"));
        assertEquals(false, FiltroAcceso.origenValido("POST", "null", "http://sitio-malo.com/x", "localhost:8080"));
        assertEquals(false, FiltroAcceso.origenValido("POST", "no es una url ::", null, "localhost:8080"));
    }

    @Test
    void get_y_post_sin_cabeceras_no_se_bloquean() {
        assertEquals(true, FiltroAcceso.origenValido("GET", "http://sitio-malo.com", null, "localhost:8080"));
        assertEquals(true, FiltroAcceso.origenValido("POST", null, null, "localhost:8080"));
    }
}
