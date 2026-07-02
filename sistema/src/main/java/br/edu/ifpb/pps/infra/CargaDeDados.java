package br.edu.ifpb.pps.infra;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifpb.pps.domain.enums.CategoriaSubmissao;
import br.edu.ifpb.pps.domain.model.AreaTematica;
import br.edu.ifpb.pps.domain.model.ConhecimentoAreaRevisor;
import br.edu.ifpb.pps.domain.model.Evento;
import br.edu.ifpb.pps.domain.model.Usuario;
import br.edu.ifpb.pps.service.ComiteService;
import br.edu.ifpb.pps.service.EventoAtual;
import br.edu.ifpb.pps.service.EventoService;
import br.edu.ifpb.pps.service.SubmissaoService;
import br.edu.ifpb.pps.service.UsuarioService;

public class CargaDeDados {
    private static final DateTimeFormatter DATA_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final UsuarioService usuarioService;
    private final EventoService eventoService;
    private final EventoAtual eventoAtual;
    private final ComiteService comiteService;
    private final SubmissaoService submissaoService;

    public CargaDeDados(
            UsuarioService usuarioService,
            EventoService eventoService,
            EventoAtual eventoAtual,
            ComiteService comiteService,
            SubmissaoService submissaoService
    ) {
        this.usuarioService = usuarioService;
        this.eventoService = eventoService;
        this.eventoAtual = eventoAtual;
        this.comiteService = comiteService;
        this.submissaoService = submissaoService;
    }

    public void carregar() {
        carregarUsuarios();
        carregarEvento();
        carregarAreas();
        carregarMembrosComite();
        carregarArtigos();
    }

    private void carregarUsuarios() {
        try (BufferedReader reader = abrirCsv("csv/usuarios.csv")) {
            pularCabecalho(reader);

            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.trim().isEmpty()) {
                    continue;
                }

                String[] campos = linha.split(";");

                String nome = campos[0];
                String email = campos[1];
                String senha = campos[2];
                String instituicao = campos[3];
                boolean coordenador = Boolean.parseBoolean(campos[4]);

                usuarioService.cadastrarUsuario(
                        nome,
                        email,
                        senha,
                        instituicao,
                        coordenador
                );
            }
        } catch (Exception e) {
            throw new IllegalStateException("Erro ao carregar usuarios.csv: " + e.getMessage(), e);
        }
    }

    private void carregarEvento() {
        try (BufferedReader reader = abrirCsv("csv/evento.csv")) {
            pularCabecalho(reader);

            String linha = reader.readLine();

            if (linha == null || linha.trim().isEmpty()) {
                return;
            }

            String[] campos = linha.split(";");

            String nome = campos[0];
            String cidade = campos[1];
            LocalDate dataInicio = LocalDate.parse(campos[2], DATA_FORMATTER);
            LocalDate dataFim = LocalDate.parse(campos[3], DATA_FORMATTER);
            CategoriaSubmissao categoria = CategoriaSubmissao.valueOf(campos[4]);

            eventoService.iniciarNovoEvento(
                    nome,
                    cidade,
                    dataInicio,
                    dataFim,
                    categoria
            );
        } catch (Exception e) {
            throw new IllegalStateException("Erro ao carregar evento.csv: " + e.getMessage(), e);
        }
    }

    private void carregarAreas() {
        try (BufferedReader reader = abrirCsv("csv/areas.csv")) {
            pularCabecalho(reader);

            Evento evento = eventoAtual.getEventoAtual();

            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.trim().isEmpty()) {
                    continue;
                }

                eventoService.cadastrarAreaTematica(evento, linha.trim());
            }
        } catch (Exception e) {
            throw new IllegalStateException("Erro ao carregar areas.csv: " + e.getMessage(), e);
        }
    }

    private void carregarMembrosComite() {
        try (BufferedReader reader = abrirCsv("csv/membros_comite.csv")) {
            String cabecalho = reader.readLine();

            if (cabecalho == null || cabecalho.trim().isEmpty()) {
                return;
            }

            String[] nomesAreas = cabecalho.split(";");
            Evento evento = eventoAtual.getEventoAtual();

            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.trim().isEmpty()) {
                    continue;
                }

                String[] campos = linha.split(";");

                String email = campos[0];
                Usuario usuario = usuarioService.buscarPorEmail(email);

                if (usuario == null) {
                    throw new IllegalStateException("Usuario nao encontrado para membro do comite: " + email);
                }

                List<ConhecimentoAreaRevisor> conhecimentos = new ArrayList<>();

                for (int i = 1; i < campos.length; i++) {
                    AreaTematica area = buscarAreaPorNome(evento, nomesAreas[i]);
                    int nivel = Integer.parseInt(campos[i]);

                    conhecimentos.add(new ConhecimentoAreaRevisor(area, nivel));
                }

                comiteService.registrarMembroComConhecimentos(
                        usuario,
                        evento,
                        conhecimentos
                );
            }
        } catch (Exception e) {
            throw new IllegalStateException("Erro ao carregar membros_comite.csv: " + e.getMessage(), e);
        }
    }

    private void carregarArtigos() {
        try (BufferedReader reader = abrirCsv("csv/artigos.csv")) {
            pularCabecalho(reader);

            Evento evento = eventoAtual.getEventoAtual();

            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.trim().isEmpty()) {
                    continue;
                }

                String[] campos = linha.split(";");

                String titulo = campos[0];
                String resumo = campos[1];
                String autorEmail = campos[2];
                String[] nomesAreas = campos[3].split("\\|");

                Usuario autor = usuarioService.buscarPorEmail(autorEmail);

                if (autor == null) {
                    throw new IllegalStateException("Autor nao encontrado para artigo: " + autorEmail);
                }

                List<AreaTematica> areas = new ArrayList<>();

                for (String nomeArea : nomesAreas) {
                    areas.add(buscarAreaPorNome(evento, nomeArea));
                }

                submissaoService.submeterArtigo(
                        autor,
                        evento,
                        titulo,
                        resumo,
                        areas
                );
            }
        } catch (Exception e) {
            throw new IllegalStateException("Erro ao carregar artigos.csv: " + e.getMessage(), e);
        }
    }

    private AreaTematica buscarAreaPorNome(Evento evento, String nome) {
        for (AreaTematica area : evento.getAreasTematicas()) {
            if (area.getNome().equalsIgnoreCase(nome.trim())) {
                return area;
            }
        }

        throw new IllegalStateException("Area tematica nao encontrada: " + nome);
    }

    private BufferedReader abrirCsv(String caminho) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(caminho);

        if (inputStream == null) {
            throw new IllegalStateException("Arquivo nao encontrado: " + caminho);
        }

        return new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    }

    private void pularCabecalho(BufferedReader reader) throws Exception {
        reader.readLine();
    }
}