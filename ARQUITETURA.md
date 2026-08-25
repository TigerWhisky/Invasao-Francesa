# Arquitetura

A versão 1.1 separa o projeto em três responsabilidades principais.

```text
                    ┌──────────────────┐
                    │    Invasao.java  │
                    │   ponto entrada   │
                    └────────┬─────────┘
                             │
                             ▼
                    ┌──────────────────┐
                    │   GamePanel.java │
                    │  interface Swing │
                    └────────┬─────────┘
                             │
                             ▼
                    ┌──────────────────┐
                    │  GameState.java  │
                    │ regras do jogo   │
                    └───────┬──────────┘
                            │
                 ┌──────────┴──────────┐
                 ▼                     ▼
          ┌─────────────┐       ┌─────────────┐
          │   Army.java │       │Direction.java│
          │   posição   │       │  direções    │
          └─────────────┘       └─────────────┘
```

## `Invasao`

Responsável apenas por iniciar a aplicação Swing e criar a janela.

## `GamePanel`

Responsável por:

- apresentar a grelha;
- apresentar os controlos;
- receber escolhas do utilizador;
- atualizar a interface;
- apresentar mensagens de fim.

A interface não contém as regras fundamentais do jogo.

## `GameState`

Responsável por:

- posições dos exércitos;
- contagem das jogadas;
- validação dos movimentos;
- movimento francês;
- deteção da vitória;
- limite de 25 jogadas;
- estado da partida.

Não depende de Swing.

## `Army`

Representa um exército e encapsula a sua posição.

## `Direction`

Representa as quatro direções possíveis através de um `enum`.

## Swing EDT

A versão original executava o ciclo de jogo diretamente no construtor, bloqueando a Event Dispatch Thread.

Nesta versão:

- a interface é criada na EDT;
- as ações são iniciadas por eventos;
- o atraso do movimento francês utiliza `javax.swing.Timer`;
- não existe um `while` bloqueante para controlar a partida.

Isto torna o comportamento da interface mais adequado ao modelo de programação Swing.

## Testes

`GameStateTest` testa a lógica sem criar uma janela gráfica.

São testados:

- posições iniciais;
- movimentos válidos;
- movimentos inválidos;
- valores negativos;
- movimento a cada três jogadas;
- reinício;
- limite de jogadas.
