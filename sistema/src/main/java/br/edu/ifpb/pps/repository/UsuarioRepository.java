package br.edu.ifpb.pps.repository;

import java.util.List;

import br.edu.ifpb.pps.domain.model.Usuario;

public class UsuarioRepository {
    private final BancoDeDados banco;

    public UsuarioRepository(BancoDeDados banco) {
        this.banco = banco;
    }

    public void salvar(Usuario usuario) {
        banco.getUsuarios().add(usuario);
    }

    public List<Usuario> listarTodos() {
        return banco.getUsuarios();
    }

    public Usuario buscarPorEmail(String email) {
        for (Usuario usuario : banco.getUsuarios()) {
            if (usuario.getEmail() != null
                    && usuario.getEmail().equalsIgnoreCase(email)) {
                return usuario;
            }
        }

        return null;
    }
}