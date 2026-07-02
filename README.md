<h1 align="center">Sistema de Submissao e Avaliacao de Artigos Cientificos</h1>

Projeto desenvolvido para a disciplina **Padroes de Projeto de Software**, do curso de **Sistemas para Internet**.

## Descricao

Este projeto implementa um sistema de submissao e avaliacao de artigos cientificos para eventos academicos. O sistema permite cadastrar usuarios, iniciar eventos, registrar areas tematicas, formar comite tecnico, submeter artigos, distribuir artigos para revisores, registrar pareceres, acompanhar indicadores da coordenacao, consultar artigos com filtros combinaveis e notificar autores ao final do ciclo de revisoes.

A aplicacao foi construida em Java, com execucao via terminal, armazenamento em memoria e organizacao em camadas de dominio, repositorio, servico, infraestrutura, interface de console e padroes de projeto.

O objetivo principal do projeto e demonstrar a aplicacao pratica de padroes de projeto em um dominio realista, mantendo regras de negocio organizadas, extensveis e com baixo acoplamento.

## Informacoes Academicas

| Item | Informacao |
| --- | --- |
| Curso | Sistemas para Internet |
| Disciplina | Padroes de Projeto de Software |
| Periodo | 5o periodo |
| Professor | Alex Sandro da Cunha Rego |
| Equipe | Maria Clara e Mariana |
| Repositorio | `euclaraalmeida/sistema-submissao-artigos` |

## Tecnologias

- Java 8+
- Maven Wrapper / Maven
- Jakarta Mail 2.0.1
- JUnit 4
- PlantUML para diagrama de classes
- CSV para carga inicial de dados

## Funcionalidades e Requisitos Atendidos

- __Cadastro e autenticacao de usuarios:__ o sistema permite cadastrar pesquisadores/autores, revisores e coordenadores, utilizando e-mail como identificador principal.
- __Criacao de evento academico:__ o coordenador inicia um novo evento informando nome, cidade, periodo e categoria de submissao (`FULL_PAPER`, `SHORT_PAPER` ou `DEMO`).
- __Reinicio do evento:__ ao iniciar um novo evento, os dados do evento anterior sao descartados.
- __Cadastro de areas tematicas:__ o coordenador registra as areas usadas para classificar artigos e definir a expertise dos revisores.
- __Formacao do comite tecnico:__ usuarios cadastrados podem ser registrados como membros do comite, com niveis de conhecimento por area tematica.
- __Carga inicial por CSV:__ o sistema povoa usuarios, evento, areas tematicas, membros do comite e artigos a partir de arquivos CSV.
- __Submissao de artigos:__ autores submetem artigos informando titulo, resumo e areas tematicas.
- __Consulta de artigos submetidos:__ autores podem listar seus artigos e acompanhar estado e resultado.
- __Distribuicao automatica de artigos:__ a regra de distribuicao seleciona revisores considerando carga de trabalho e restricoes de conflito de autoria.
- __Blind review:__ no menu de revisao, o revisor visualiza titulo e areas do artigo, mas nao recebe informacoes sobre autores ou coautores.
- __Registro de pareceres:__ revisores registram pontos positivos, pontos negativos e veredito (`REJEITADO`, `FRACAMENTE_REJEITADO`, `FRACAMENTE_ACEITO` ou `ACEITO`).
- __Calculo de resultado:__ ao concluir as revisoes de um artigo, o sistema calcula se ele foi aceito ou rejeitado.
- __Dashboard da coordenacao:__ o coordenador visualiza total de artigos, revisores, artigos avaliados, artigos pendentes e relacao de pendencias por avaliador.
- __Consulta avancada de artigos:__ o coordenador consulta artigos do evento usando filtros combinaveis por area, estado, resultado e avaliador.
- __Notificacao aos autores:__ quando todo o ciclo de revisoes termina, o sistema dispara a notificacao aos autores com o resultado e os pareceres.
- __Envio real ou simulado de e-mail:__ se SMTP estiver configurado, o e-mail e enviado de verdade; caso contrario, a mensagem e exibida no terminal para facilitar a demonstracao.

## Estrutura de Arquivos

```text
sistema-submissao-artigos/
|-- README.md
|-- DiagramaClasses/
|   |-- diagrama-artigos-atualizado.puml
|   `-- CodigoPlantUML
`-- sistema/
    |-- pom.xml
    |-- mvnw
    |-- mvnw.cmd
    `-- src/
        |-- main/java/br/edu/ifpb/pps/
        |   |-- App.java
        |   |-- domain/
        |   |-- infra/
        |   |-- pattern/
        |   |-- repository/
        |   |-- service/
        |   `-- view/
        |-- main/resources/csv/
        |   |-- usuarios.csv
        |   |-- evento.csv
        |   |-- areas.csv
        |   |-- membros_comite.csv
        |   `-- artigos.csv
        `-- test/java/
```

| Pacote | Descricao |
| --- | --- |
| `domain.model` | Entidades principais do dominio, como usuario, evento, artigo, parecer e membro do comite. |
| `domain.enums` | Enumeracoes de categoria, resultado, status, autoria e veredito. |
| `infra` | Classes de infraestrutura, como a carga inicial de dados por CSV. |
| `pattern.State` | Implementacao do ciclo de vida dos artigos. |
| `pattern.chain` | Validadores da distribuicao automatica. |
| `pattern.Decorator` | Filtros combinaveis da consulta avancada de artigos. |
| `pattern.observer` | Eventos e observadores usados na notificacao aos autores. |
| `pattern.template` | Templates dos e-mails de aceite e rejeicao. |
| `repository` | Armazenamento em memoria e consultas sobre usuarios, eventos, artigos e revisoes. |
| `service` | Regras de negocio e casos de uso da aplicacao. |
| `service.dto` | Objetos de transferencia usados em dashboard e consulta avancada. |
| `view` | Interface de usuario baseada em console. |

## Principais Classes

### Dominio

- `Usuario`: representa pessoas cadastradas no sistema. Um usuario pode atuar como autor, revisor ou coordenador conforme suas relacoes e permissoes.
- `Evento`: representa o evento academico ativo, suas datas, categoria, areas tematicas e membros do comite.
- `Artigo`: representa um artigo submetido e seu estado atual.
- `Autoria`: vincula usuarios a artigos como autor principal ou coautor.
- `MembroComite`: representa um usuario inscrito no comite tecnico.
- `ConhecimentoAreaRevisor`: registra o nivel de conhecimento de um membro do comite em uma area tematica.
- `AtribuicaoRevisao`: liga um artigo a um revisor.
- `Parecer`: armazena pontos positivos, pontos negativos e veredito.
- `AreaTematica`: representa os temas usados na submissao, no comite e nas consultas.

### Servicos

- `UsuarioService`: cadastro e consulta de usuarios.
- `AutenticacaoService`: login por e-mail e senha.
- `EventoService`: criacao de evento e cadastro de areas tematicas.
- `EventoAtual`: guarda o evento ativo da execucao.
- `ComiteService`: registro de membros do comite e seus niveis de conhecimento.
- `SubmissaoService`: submissao e listagem de artigos.
- `DistribuicaoArtigosService`: distribuicao automatica dos artigos aos revisores.
- `RevisaoService`: listagem de revisoes pendentes e registro de pareceres.
- `ResultadoArtigoService`: calculo do resultado final do artigo.
- `CicloRevisoesService`: verifica se o ciclo de revisoes do evento terminou.
- `DashboardService`: consolida indicadores da coordenacao, atuando como Facade.
- `ConsultaArtigoService`: monta dinamicamente os filtros Decorator para consulta avancada.
- `EmailService`: envio real via SMTP ou simulacao no terminal quando SMTP nao estiver configurado.
- `EmailResultadoService`: gera o texto do e-mail de aceite ou rejeicao.
- `ValidacaoGenericaService`: validacoes simples e reutilizaveis.

### Interface de Console

- `MenuInicial`: login e cadastro de usuarios.
- `MenuCriacaoEvento`: criacao inicial de evento por coordenador quando nao existe evento ativo.
- `MenuPrincipal`: direciona o usuario para area de autor, revisor ou coordenacao conforme seus papeis.
- `MenuAutor`: submissao e consulta de artigos do usuario.
- `MenuRevisor`: listagem de revisoes pendentes e registro de parecer.
- `MenuCoordenacao`: operacoes administrativas do evento, como areas, comite, distribuicao e dashboard.
- `MenuConsultaArtigo`: consulta avancada de artigos com filtros combinaveis.
- `ConsoleUI`: centraliza leitura e escrita no terminal.

## Padroes de Projeto Utilizados

A arquitetura foi organizada para demonstrar padroes em pontos reais do dominio, evitando acoplamento desnecessario e regras espalhadas pela interface.

### State

Usado para controlar o ciclo de vida de um artigo.

Classes:

- `EstadoArtigo`
- `ArtigoSubmetido`
- `ArtigoEmRevisao`
- `ArtigoFinalizado`
- `Artigo`

O artigo delega suas transicoes de estado para objetos especificos. Assim, regras como "artigo finalizado nao pode voltar para revisao" ficam encapsuladas no estado correspondente.

### Chain of Responsibility

Usado na validacao da distribuicao de artigos para revisores.

Classes:

- `ValidadorDistribuicao`
- `ValidadorNaoAutor`
- `ValidadorRevisorNaoRepetido`
- `DistribuicaoArtigosService`

Cada validador verifica uma regra independente. A distribuicao so acontece quando todos os validadores aprovam o revisor candidato.

Regras implementadas:

- Um revisor nao pode revisar artigo do qual seja autor ou coautor.
- O mesmo revisor nao pode ser atribuido duas vezes ao mesmo artigo.

### Observer

Usado para notificar autores quando o ciclo de revisoes do evento e concluido.

Classes:

- `EventoSistema`
- `ObservadorEvento`
- `PublicadorEventos`
- `CicloRevisoesConcluidoEvent`
- `EmailAutorObserver`
- `CicloRevisoesService`

Quando todas as revisoes do evento terminam, `CicloRevisoesService` publica um `CicloRevisoesConcluidoEvent`. O `EmailAutorObserver` observa esse evento e envia o resultado para os autores.

### Template Method

Usado para gerar e-mails de resultado com estrutura comum e variacoes conforme aceite ou rejeicao.

Classes:

- `EmailResultadoTemplate`
- `EmailAceiteTemplate`
- `EmailRejeicaoTemplate`
- `EmailResultadoService`

O template define a estrutura geral do e-mail: saudacao, mensagem principal, assinatura e pareceres. As subclasses especializam a mensagem principal para aceite ou rejeicao.

### Facade

Usado no dashboard da coordenacao.

Classe:

- `DashboardService`

O `DashboardService` atua como Facade porque reune dados de artigos, revisoes e membros do comite, entregando ao menu uma visao consolidada sem expor a complexidade dos repositories e services envolvidos.

Informacoes geradas:

- total de artigos submetidos;
- total de revisores;
- total de artigos avaliados;
- total de artigos pendentes;
- relacao de artigos pendentes com o avaliador responsavel.

### Decorator

Usado na consulta avancada de artigos.

Classes:

- `BuscaArtigos`
- `BuscaTodosArtigos`
- `FiltroArtigoDecorator`
- `FiltroPorArea`
- `FiltroPorEstado`
- `FiltroPorResultado`
- `FiltroPorAvaliador`
- `ConsultaArtigoService`

O `ConsultaArtigoService` monta a cadeia de decorators conforme os filtros escolhidos pelo coordenador. Assim, filtros podem ser combinados sem criar metodos especificos para cada combinacao, como `buscarPorAreaEEstado` ou `buscarPorResultadoEAvaliador`.

### Repository

Usado para isolar o acesso aos dados em memoria.

Classes:

- `BancoDeDados`
- `UsuarioRepository`
- `EventoRepository`
- `ArtigoRepository`
- `RevisaoRepository`

Os repositories centralizam as operacoes de consulta e armazenamento, evitando que menus ou services manipulem diretamente as listas do banco em memoria.

## Carga Inicial por CSV

O sistema carrega automaticamente dados iniciais a partir dos arquivos em:

```text
sistema/src/main/resources/csv/
```

Arquivos usados:

| Arquivo | Finalidade |
| --- | --- |
| `usuarios.csv` | Cadastra coordenador, autores e revisores. |
| `evento.csv` | Inicia o evento ativo. |
| `areas.csv` | Cadastra areas tematicas do evento. |
| `membros_comite.csv` | Registra membros do comite e seus niveis de conhecimento por area. |
| `artigos.csv` | Submete artigos iniciais para o evento. |

A carga e executada no inicio da aplicacao pela classe `CargaDeDados`, antes da abertura do `MenuInicial`. Isso evita digitacoes iniciais e fornece um cenario pronto para demonstrar os padroes.

### Usuarios de Teste

| Perfil | E-mail | Senha |
| --- | --- | --- |
| Coordenadora | `clara@email.com` | `123` |
| Autora | `ana@email.com` | `123` |
| Autor | `lucas@email.com` | `123` |
| Revisor | `bruno@email.com` | `123` |
| Revisora | `marianasarinho14@gmail.com` | `123` |
| Revisor | `arthur@email.com` | `123` |

Observacao: os e-mails usados em `membros_comite.csv` precisam existir em `usuarios.csv`, pois a carga busca os usuarios pelo e-mail.

## Fluxo Sugerido de Demonstracao

1. Execute o sistema e confirme a mensagem de carga dos CSVs.
2. Entre como coordenadora: `clara@email.com` / `123`.
3. Acesse a area da coordenacao.
4. Liste os membros do comite para confirmar os revisores carregados.
5. Distribua os artigos para revisao.
6. Abra o Dashboard para ver artigos submetidos, revisores, avaliados, pendentes e pendencias por avaliador.
7. Use a consulta avancada para combinar filtros, por exemplo area + estado ou avaliador + resultado.
8. Faca logout e entre como um revisor, por exemplo `bruno@email.com` / `123`.
9. Liste revisoes pendentes e registre pareceres.
10. Repita o registro com outros revisores ate concluir as revisoes.
11. Ao concluir o ciclo, observe a publicacao do evento e o envio real ou simulado de e-mail aos autores.

## Regras Importantes

- Um usuario pode assumir mais de um papel.
- Autor, revisor e coordenador nao sao subclasses de `Usuario`.
- O papel de autor depende de uma `Autoria` ligada ao artigo.
- O papel de revisor depende da participacao como `MembroComite` do evento.
- O papel de coordenador depende da permissao booleana no usuario.
- O sistema trabalha com um evento ativo por vez.
- Ao iniciar novo evento, os dados do evento anterior sao desconsiderados.
- O artigo herda a categoria de submissao do evento.
- A distribuicao inicial seleciona revisores respeitando carga e validadores da cadeia.
- O nivel de conhecimento do revisor por area e usado no calculo do resultado final.
- Os menus apenas coletam dados e exibem resultados; regras de negocio ficam nos services.

## Requisitos Nao Funcionais

- Separacao em camadas: dominio, infraestrutura, servico, repositorio, padroes e view.
- Validacoes concentradas em `ValidacaoGenericaService` e nos servicos de negocio.
- Uso de excecoes para impedir fluxos invalidos.
- Regras de estado encapsuladas pelo padrao State.
- Regras de distribuicao extensveis pelo Chain of Responsibility.
- Notificacao desacoplada do fluxo principal pelo Observer.
- Templates de e-mail reutilizaveis pelo Template Method.
- Dashboard consolidado por Facade.
- Consulta avancada extensivel por Decorator.
- Repositories isolando o acesso ao armazenamento em memoria.
- Saida de console centralizada em `ConsoleUI`, com e-mail simulado isolado no `EmailService` quando SMTP nao esta configurado.

## Como Executar

### Pre-requisitos

Instale:

- Java 8 ou superior

O projeto inclui Maven Wrapper (`mvnw` e `mvnw.cmd`). Na primeira execucao, o wrapper baixa automaticamente a versao do Maven necessaria.

Verifique:

```bash
java -version
```

### Clonar o repositorio

```bash
git clone https://github.com/euclaraalmeida/sistema-submissao-artigos.git
cd sistema-submissao-artigos/sistema
```

### Rodar com Maven Wrapper (recomendado)

No Windows PowerShell:

```powershell
.\mvnw.cmd clean compile exec:java "-Dexec.mainClass=br.edu.ifpb.pps.App"
```

No Linux/Mac/Git Bash:

```bash
./mvnw clean compile exec:java -Dexec.mainClass="br.edu.ifpb.pps.App"
```

No Linux/Mac, se necessario, libere a execucao do arquivo:

```bash
chmod +x mvnw
```

### Rodar com Maven instalado

```bash
mvn clean compile exec:java -Dexec.mainClass="br.edu.ifpb.pps.App"
```

## Configuracao de E-mail

O sistema possui dois comportamentos:

1. **Envio real**, quando SMTP esta configurado.
2. **Envio simulado no terminal**, quando SMTP nao esta configurado.

O modo simulado facilita a apresentacao em sala, porque nao depende de internet, senha de aplicativo ou conta Gmail. Mesmo assim, para testar o envio real, configure SMTP antes de executar.

### Envio real via Gmail no PowerShell

```powershell
$env:SMTP_HOST="smtp.gmail.com"
$env:SMTP_PORT="587"
$env:SMTP_USER="seuemail@gmail.com"
$env:SMTP_PASSWORD="sua_senha_de_app"
$env:SMTP_FROM="seuemail@gmail.com"

.\mvnw.cmd clean compile exec:java "-Dexec.mainClass=br.edu.ifpb.pps.App"
```

### Envio real via Gmail no Linux/Mac/Git Bash

```bash
export SMTP_HOST=smtp.gmail.com
export SMTP_PORT=587
export SMTP_USER=seuemail@gmail.com
export SMTP_PASSWORD=sua_senha_de_app
export SMTP_FROM=seuemail@gmail.com

./mvnw clean compile exec:java -Dexec.mainClass="br.edu.ifpb.pps.App"
```

Observacao: para Gmail, `SMTP_PASSWORD` deve ser uma senha de app, nao a senha normal da conta.

### Modo simulado

Se nenhuma variavel SMTP for configurada, o sistema imprime no terminal:

```text
=== E-MAIL SIMULADO ===
SMTP nao configurado. O e-mail abaixo seria enviado em producao.
Destinatario: autor@email.com
Assunto: Resultado do artigo: Titulo do artigo
Corpo:
...
=======================
```

## Observacoes de Projeto

- A persistencia e em memoria. Os CSVs sao usados apenas para carga inicial de demonstracao.
- A classe `App` atua como ponto de composicao, criando repositories, services, menus e ligando as dependencias.
- Os menus nao acessam repositories diretamente.
- O fluxo de e-mail real depende das variaveis SMTP estarem configuradas no mesmo terminal em que o sistema for executado.
