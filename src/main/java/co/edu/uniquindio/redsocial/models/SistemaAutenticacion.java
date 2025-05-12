package co.edu.uniquindio.redsocial.models;

// CLASE SISTEMA AUTENTICACIÓN ACTUALIZADA
class SistemaAutenticacion {
    private ListaEnlazada<Usuario> usuariosRegistrados = new ListaEnlazada<>();

    // Registra estudiante con email y contraseña
    public Estudiante registrarEstudiante(String nombre, String email, String contraseña) {
        validarEmailUnico(email);

        Estudiante nuevo = new Estudiante(
                generarId(),
                nombre,
                email,
                contraseña,
                new ListaEnlazada<>(),  // intereses
                new ListaEnlazada<>(),    // historial
                new ListaEnlazada<>(),    // valoraciones
                new ColaPrioridad<>(new ListaEnlazada<>()),  // solicitudes
                new ListaEnlazada<>()     // grupos
        );

        usuariosRegistrados.agregar(nuevo);
        return nuevo;
    }

    // Inicia sesión con credenciales
    public Usuario iniciarSesion(String email, String contraseña) {
        NodoLista<Usuario> actual = usuariosRegistrados.getCabeza();
        while (actual != null) {
            Usuario usuario = actual.getDato();
            if (usuario.getEmail().equals(email) && usuario.getContraseña().equals(contraseña)) {
                return usuario;
            }
            actual = actual.getSiguiente();
        }
        throw new SecurityException("Credenciales inválidas");
    }

    // Genera ID único (ej: UUID simplificado)
    private String generarId() {
        return "USR-" + System.currentTimeMillis();
    }

    private void validarEmailUnico(String email) {
        NodoLista<Usuario> actual = usuariosRegistrados.getCabeza();
        while (actual != null) {
            if (actual.getDato().getEmail().equalsIgnoreCase(email)) {
                throw new IllegalArgumentException("Email ya registrado");
            }
            actual = actual.getSiguiente();
        }
    }
}