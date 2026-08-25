# Changelog

## [1.1.0] - 2026-08-25

### Mantido

- Mecânica original do jogo.
- Grelha 30 × 30.
- Portugal na linha inferior.
- França na linha superior.
- Escolha do número de quadrados.
- Escolha de direção.
- Movimento francês a cada três jogadas.
- Máximo de 25 jogadas.

### Melhorado

- Separação entre interface e lógica.
- Classes `Army`, `Direction` e `GameState`.
- Validação de movimentos.
- Gestão correta do ciclo de vida Swing.
- Remoção de `System.exit()` da lógica.
- Utilização de `javax.swing.Timer`.
- Testes unitários.
- Maven.
- GitHub Actions.
- Documentação.
