package br.edu.ifpb.pps.service;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifpb.pps.domain.model.AreaTematica;
import br.edu.ifpb.pps.domain.model.ConhecimentoAreaRevisor;
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
            Evento evento
            // aqui vai ter as especialidades em niveias 
    ) {
        validacaoService.objetoObrigatorio(usuario, "Usuario");
        validacaoService.objetoObrigatorio(evento, "Evento");
        validacaoService.listaObrigatoria(especialidades, "Especialidades");

        MembroComite membro = new MembroComite();
        membro.setUsuario(usuario);

        for (AreaTematica area : especialidades) {
            membro.adicionarConhecimentoArea(area, 1);
        }

        evento.adicionarMembroComite(membro);

        return membro;
    }

    public MembroComite registrarMembroComConhecimentos(
            Usuario usuario,
            Evento evento,
            List<ConhecimentoAreaRevisor> conhecimentosPorArea
    ) {
        validacaoService.objetoObrigatorio(usuario, "Usuario");
        validacaoService.objetoObrigatorio(evento, "Evento");
        validacaoService.listaObrigatoria(conhecimentosPorArea, "Conhecimentos por area");

        if (ehMembro(usuario, evento)) {
            throw new IllegalArgumentException("Usuario ja e membro do comite deste evento.");
        }
        MembroComite membro = new MembroComite();
        membro.setUsuario(usuario);

        for (ConhecimentoAreaRevisor conhecimento : conhecimentosPorArea) {
            membro.adicionarConhecimentoArea(
                    conhecimento.getArea(),
                    conhecimento.getNivelConhecimento()
            );
        }

        evento.adicionarMembroComite(membro);

        return membro;
    }

    public boolean ehMembro(Usuario usuario, Evento evento) {
        return buscarMembro(usuario, evento) != null;
    }

    public MembroComite buscarMembro(Usuario usuario, Evento evento) {
        if (usuario == null || evento == null) {
            return null;
        }

        for (MembroComite membro : evento.getMembrosComite()) {
            if (membro.getUsuario() == usuario) {
                return membro;
            }
        }

        return null;
    }
    
    

    public List<MembroComite> listarMembros(Evento evento) {
        validacaoService.objetoObrigatorio(evento, "Evento");
        return new ArrayList<>(evento.getMembrosComite());
    }

   
}
