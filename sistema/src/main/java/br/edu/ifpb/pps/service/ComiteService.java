package br.edu.ifpb.pps.service;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifpb.pps.domain.model.AreaTematica;
import br.edu.ifpb.pps.domain.model.Evento;
import br.edu.ifpb.pps.domain.model.MembroComite;
import br.edu.ifpb.pps.domain.model.Usuario;

public class ComiteService {
    private final ValidacaoGenericaService validacaoService;

    public ComiteService(ValidacaoGenericaService validacaoService) {
        this.validacaoService = validacaoService;
    }

    public MembroComite registrarMembro(
            Usuario usuario,
            Evento evento,
            List<AreaTematica> especialidades
    ) {
        validacaoService.objetoObrigatorio(usuario, "Usuario");
        validacaoService.objetoObrigatorio(evento, "Evento");
        validacaoService.listaObrigatoria(especialidades, "Especialidades");

        // um if para conferir se o membro ja é do comite

        MembroComite membro = new MembroComite();
        membro.setUsuario(usuario);

        for (AreaTematica area : especialidades) {
            membro.adicionarEspecialidade(area);
        }

        evento.adicionarMembroComite(membro);

        return membro;
    }

    //public boolean ehMembro(Usuario usuario, Evento evento) {}

    //public MembroComite buscarMembro(Usuario usuario, Evento evento) {}
    
    

    public List<MembroComite> listarMembros(Evento evento) {
        validacaoService.objetoObrigatorio(evento, "Evento");
        return new ArrayList<>(evento.getMembrosComite());
    }

   
}