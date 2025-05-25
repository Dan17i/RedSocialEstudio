package co.edu.uniquindio.redsocial.models.services.implement;

import co.edu.uniquindio.redsocial.models.Usuario;
import co.edu.uniquindio.redsocial.models.services.interf.IGestorUsuarios;
import co.edu.uniquindio.redsocial.models.structures.ListaEnlazada;

public class GestorUsuarios implements IGestorUsuarios {
    /**
     * @param usuario Usuario a registrar
     */
    @Override
    public void registrarUsuario(Usuario usuario) {

    }

    /**
     * @param id ID del usuario a eliminar
     */
    @Override
    public void eliminarUsuario(String id) {

    }

    /**
     * @param usuario Usuario actualizado
     */
    @Override
    public void actualizarUsuario(Usuario usuario) {

    }

    /**
     * @return
     */
    @Override
    public ListaEnlazada<Usuario> listarUsuarios() {
        return null;
    }
}
