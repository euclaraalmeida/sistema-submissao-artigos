package br.edu.ifpb.pps.service;

import br.edu.ifpb.pps.domain.model.Usuario;
import br.edu.ifpb.pps.repository.UsuarioRepository;

public class AutenticacaoService {
    private final UsuarioRepository usuarioRepository;
    private final ValidacaoGenericaService validacaoService;

    public AutenticacaoService(UsuarioRepository usuarioRepository, ValidacaoGenericaService validacaoService) {
        this.usuarioRepository = usuarioRepository;
        this.validacaoService = validacaoService;
    }

    public Usuario login(String email, String senha) {
        validacaoService.textoObrigatorio(email, "Email");
        validacaoService.textoObrigatorio(senha, "Senha");

        Usuario usuario = usuarioRepository.buscarPorEmail(email);

        if (usuario == null) {
            throw new IllegalArgumentException("Usuario nao encontrado.");
        }

        if (!usuario.getSenha().equals(senha)) {
            throw new IllegalArgumentException("Senha invalida.");
        }

        return usuario;
    }
}
