<h1 align="center">Sistema de Submissao e Avaliacao de Artigos Cientificos</h1>

Projeto desenvolvido para a disciplina **Padroes de Projeto de Software**, do curso de **Sistemas para Internet**.

## Descricao

Este projeto implementa um sistema de submissao e avaliacao de artigos cientificos para eventos academicos. O sistema permite cadastrar usuarios, iniciar eventos, registrar areas tematicas, formar comite tecnico, submeter artigos, distribuir artigos para revisores, registrar pareceres e notificar autores ao final do ciclo de revisoes.

A aplicacao foi construida em Java, com execucao via terminal, armazenamento em memoria e organizacao em camadas de dominio, repositorio, servico, interface de console e padroes de projeto.

O objetivo principal do projeto e demonstrar a aplicacao pratica de padroes de projeto em um dominio realista, mantendo as regras de negocio organizadas e extensiveis.

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

## Funcionalidades e Requisitos Atendidos

- __Cadastro e autenticacao de usuarios:__ o sistema permite cadastrar pesquisadores/autores, revisores e coordenadores, utilizando e-mail como identificador principal.
- __Criacao de evento academico:__ o coordenador inicia um novo evento informando nome, cidade, periodo e categoria de submissao (`FULL_PAPER`, `SHORT_PAPER` ou `DEMO`).
- __Cadastro de areas tematicas:__ o coordenador registra as areas usadas para classificar artigos e definir a expertise dos revisores.
- __Formacao do comite tecnico:__ usuarios cadastrados podem ser registrados como membros do comite, com niveis de conhecimento por area tematica.
- __Submissao de artigos:__ autores submetem artigos informando titulo, resumo e areas tematicas.
- __Consulta de artigos submetidos:__ autores podem listar seus artigos e acompanhar estado e resultado.
- __Distribuicao automatica de artigos:__ a regra de distribuicao seleciona revisores considerando carga de trabalho e restricoes de conflito de autoria.
- __Blind review:__ no menu de revisao, o revisor visualiza titulo e areas do artigo, mas nao recebe informacoes sobre autores ou coautores.
- __Registro de pareceres:__ revisores registram pontos positivos, pontos negativos e veredito (`REJEITADO`, `FRACAMENTE_REJEITADO`, `FRACAMENTE_ACEITO` ou `ACEITO`).
- __Calculo de resultado:__ ao concluir as revisoes de um artigo, o sistema calcula se ele foi aceito ou rejeitado.
- __Notificacao aos autores:__ quando todo o ciclo de revisoes termina, o sistema dispara a notificacao aos autores com o resultado e os pareceres.
- __Envio real ou simulado de e-mail:__ se SMTP estiver configurado, o e-mail e enviado de verdade; caso contrario, a mensagem e exibida no terminal para facilitar a demonstracao.
- __Reinicio do evento:__ ao iniciar um novo evento, os dados do evento anterior sao descartados.

## Estrutura de Arquivos

```text
sistema-submissao-artigos/
|-- DiagramaClasses/
|   |-- diagrama-artigos-atualizado.puml
|   `-- CodigoPlantUML
`-- sistema/
    |-- pom.xml
    `-- src/
        |-- main/java/br/edu/ifpb/pps/
        |   |-- App.java
        |   |-- domain/
        |   |-- pattern/
        |   |-- repository/
        |   |-- service/
        |   `-- view/
        `-- test/java/
```

| Pacote | Descricao |
| --- | --- |
| `domain.model` | Entidades principais do dominio, como usuario, evento, artigo, parecer e membro do comite. |
| `domain.enums` | Enumeracoes de categoria, resultado, status, autoria e veredito. |
| `pattern.State` | Implementacao do ciclo de vida dos artigos. |
| `pattern.chain` | Validadores da distribuicao automatica. |
| `pattern.observer` | Eventos e observadores usados na notificacao aos autores. |
| `pattern.template` | Templates dos e-mails de aceite e rejeicao. |
| `repository` | Armazenamento em memoria e consultas sobre usuarios, eventos, artigos e revisoes. |
| `service` | Regras de negocio e casos de uso da aplicacao. |
| `view` | Interface de usuario baseada em console. |

## Principais Classes

### Dominio

- `Usuario`: representa autores, revisores e coordenadores.
- `Evento`: representa o evento academico ativo.
- `Artigo`: representa um artigo submetido e seu estado atual.
- `Autoria`: vincula usuarios a artigos como autor principal ou coautor.
- `MembroComite`: representa um usuario inscrito no comite tecnico.
- `AtribuicaoRevisao`: liga um artigo a um revisor.
- `Parecer`: armazena pontos positivos, pontos negativos e veredito.
- `AreaTematica`: representa os temas usados na submissao e na distribuicao.

### Servicos

- `UsuarioService`: cadastro e consulta de usuarios.
- `AutenticacaoService`: login por e-mail e senha.
- `EventoService`: criacao de evento e cadastro de areas tematicas.
- `ComiteService`: registro de membros do comite e seus niveis de conhecimento.
- `SubmissaoService`: submissao e listagem de artigos.
- `DistribuicaoArtigosService`: distribuicao automatica dos artigos aos revisores.
- `RevisaoService`: listagem de revisoes pendentes e registro de pareceres.
- `ResultadoArtigoService`: calculo do resultado final do artigo.
- `CicloRevisoesService`: verifica se o ciclo de revisoes do evento terminou.
- `EmailService`: envio real via SMTP ou simulacao no terminal quando SMTP nao estiver configurado.
- `EmailResultadoService`: gera o texto do e-mail de aceite ou rejeicao.

### Interface de Console

- `MenuInicial`: login e cadastro de usuarios.
- `MenuCriacaoEvento`: criacao inicial de evento por coordenador.
- `MenuPrincipal`: direciona o usuario para areas de autor, revisor ou coordenacao.
- `MenuAutor`: submissao e consulta de artigos.
- `MenuRevisor`: listagem de revisoes pendentes e registro de parecer.
- `MenuCoordenacao`: cadastro de evento, areas tematicas, membros do comite e consulta do comite.
- `ConsoleUI`: centraliza leitura e escrita no terminal.

## Padroes de Projeto Utilizados

A arquitetura foi organizada para demonstrar padroes comportamentais em pontos reais do dominio.

### Padroes Comportamentais

#### State

Usado para controlar o ciclo de vida de um artigo.

Classes:

- `EstadoArtigo`
- `ArtigoSubmetido`
- `ArtigoEmRevisao`
- `ArtigoFinalizado`
- `Artigo`

O artigo delega suas transicoes de estado para objetos especificos. Assim, regras como "artigo finalizado nao pode voltar para revisao" ficam encapsuladas no estado correspondente.

#### Chain of Responsibility

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

#### Observer

Usado para notificar autores quando o ciclo de revisoes do evento e concluido.

Classes:

- `EventoSistema`
- `ObservadorEvento`
- `PublicadorEventos`
- `CicloRevisoesConcluidoEvent`
- `EmailAutorObserver`
- `CicloRevisoesService`

Quando todas as revisoes do evento terminam, `CicloRevisoesService` publica um `CicloRevisoesConcluidoEvent`. O `EmailAutorObserver` observa esse evento e envia o resultado para os autores.

#### Template Method

Usado para gerar e-mails de resultado com estrutura comum e variacoes conforme aceite ou rejeicao.

Classes:

- `EmailResultadoTemplate`
- `EmailAceiteTemplate`
- `EmailRejeicaoTemplate`
- `EmailResultadoService`

O template define a estrutura geral do e-mail: saudacao, mensagem principal, assinatura e pareceres. As subclasses especializam a mensagem principal para aceite ou rejeicao.

## Requisitos Nao Funcionais

- Separacao em camadas: dominio, servico, repositorio, padroes e view.
- Validacoes concentradas em `ValidacaoGenericaService` e nos servicos de negocio.
- Uso de excecoes para impedir fluxos invalidos.
- Regras de estado encapsuladas pelo padrao State.
- Regras de distribuicao extensiveis pelo Chain of Responsibility.
- Notificacao desacoplada do fluxo principal pelo Observer.
- Templates de e-mail reutilizaveis pelo Template Method.

## Como Executar

### Pre-requisitos

Instale:

- Java 8 ou superior

O Maven nao precisa estar instalado para a execucao principal, porque o projeto inclui Maven Wrapper (`mvnw` e `mvnw.cmd`). Na primeira execucao, o wrapper baixa automaticamente a versao do Maven configurada no projeto.

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


## Configuracao de E-mail

O sistema possui dois comportamentos:

1. **Envio real**, quando SMTP esta configurado.
2. **Envio simulado no terminal**, quando SMTP nao esta configurado.

O modo simulado facilita a apresentacao em sala, porque nao depende de internet, senha de aplicativo ou conta Gmail. Mesmo assim, para cumprir o envio real, configure SMTP antes de executar.

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
