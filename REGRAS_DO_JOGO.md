# Regras do jogo

## Objetivo

O objetivo é conduzir o exército português até à posição do exército francês antes que a França consiga escapar.

## Grelha

A arena possui:

- 30 colunas;
- 30 linhas;
- 900 células.

## Posição inicial

### Portugal

O exército português começa aleatoriamente numa coluna da última linha.

### França

O exército francês começa aleatoriamente numa coluna da primeira linha.

## Movimento português

Em cada jogada são escolhidos:

1. número de quadrados;
2. direção.

As direções disponíveis são:

- Norte;
- Sul;
- Este;
- Oeste.

Um movimento que ultrapasse os limites da grelha é rejeitado.

## Movimento francês

A cada três jogadas portuguesas, o exército francês move-se:

- aleatoriamente para a esquerda ou direita;
- entre 1 e 3 quadrados;
- sem ultrapassar os limites da grelha.

## Vitória

Portugal vence quando os dois exércitos ocupam a mesma célula.

## Fim por limite de jogadas

Depois de 25 jogadas, se Portugal ainda não tiver encontrado a França, considera-se que o exército francês conseguiu escapar.

## Nova partida

No fim da batalha o jogador pode iniciar uma nova partida.
