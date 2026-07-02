package br.edu.ifpb.pps.service;

import java.util.List;

import br.edu.ifpb.pps.domain.model.Usuario;
import br.edu.ifpb.pps.repository.UsuarioRepository;

public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final ValidacaoGenericaService validacaoService;

    public UsuarioService(UsuarioRepository usuarioRepository, ValidacaoGenericaService validacaoService) {
        this.usuarioRepository = usuarioRepository;
        this.validacaoService = validacaoService;
    }

    public Usuario cadastrarUsuario(
            String nome,
            String email,
            String senha,
            String instituicao,
            boolean coordenador
    ) {
        validacaoService.textoObrigatorio(nome, "Nome");
        validacaoService.emailValido(email, "Email");
        validacaoService.textoObrigatorio(senha, "Senha");
        validacaoService.textoObrigatorio(instituicao, "Instituicao");

        if (usuarioRepository.buscarPorEmail(email) != null) {
            throw new IllegalArgumentException("Ja existe um usuario cadastrado com este email.");
        }

        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email.trim());
        usuario.setSenha(senha);
        usuario.setInstituicao(instituicao);
        usuario.setCoordenador(coordenador);

        usuarioRepository.salvar(usuario);

        return usuario;
    }

    public Usuario buscarPorEmail(String email) {
        validacaoService.emailValido(email, "Email");
        return usuarioRepository.buscarPorEmail(email.trim());
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.listarTodos();
    }

}
