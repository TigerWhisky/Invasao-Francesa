# Invasão!

Bem-vindo ao **Invasão!**, um jogo de estratégia desenvolvido em **Java (Swing)** onde a missão é comandar as forças de Portugal para capturar o exército francês numa grelha de **30 × 30**. 

##  Como Funciona o Jogo

As regras são simples, mas exigem estratégia:
* **Posições Iniciais:** Portugal posiciona-se na linha inferior da grelha, enquanto a França começa na linha superior.
* **A Tua Jogada:** Em cada turno, escolhes o **número de quadrados a mover** e a **direção** (Norte, Sul, Este ou Oeste), sempre respeitando os limites do mapa.
* **O Inimigo:** A cada 3 jogadas, o exército francês mexe-se sozinho de forma aleatória (entre 1 a 3 quadrados na horizontal).
* **Fim de Jogo:** Vences se alcançares o exército francês. Perdes se passarem **25 jogadas** sem conseguir a vitória (a França consegue escapar!). Podes iniciar uma nova partida logo de seguida.

---

## O que há de novo nesta Versão 1.1?

A lógica clássica foi preservada, mas o projeto passou por uma grande evolução a nível de código:
* **Arquitetura Limpa:** Separação clara entre o modelo de dados, as regras de negócio, a interface gráfica e os testes.
* **Interface Fluida:** Utilização de `javax.swing.Timer` para os movimentos automáticos, evitando bloqueios na interface (EDT).
* **Qualidade e Testes:** Inclusão de testes unitários, gestão de dependências com **Maven** e automação com **GitHub Actions**.
* **Código Mais Robusto:** Uso de `enum` para direções, validações de movimentos mais rigorosas e gestão explícita do estado da partida.

##  Estrutura

Invasao/
├── .github/workflows/    # Integração contínua (CI)
├── docs/                 # Documentação detalhada (Regras, Arquitetura, Contribuição)
├── src/
│   ├── main/java/        # Código-fonte principal (Modelo, UI, Regras)
│   └── test/java/        # Testes unitários
├── CHANGELOG.md
├── LICENSE
└── pom.xml               # Configuração Maven

## ⚙️ Requisitos e Como Executar

Tem de ter o **Java 17+** instalado no teu computador.

### Opção 1: Com Maven
mvn clean test
mvn package
java -jar target/invasao-1.1.0.jar


### Opção 2: Sem Maven (Apenas com o compilador Java)
javac -d out src/main/java/pt/github/invasao/*.java
java -cp out pt.github.invasao.Invasao


## Objetivos Pedagógicos
Este projeto serve para demonstrar competências em:
* Programação Orientada a Objetos (POO) e Encapsulamento.
* Desenvolvimento de interfaces gráficas com **Java Swing** e gestão de eventos.
* Boas práticas (separação de responsabilidades, validação de dados).
* Ferramentas de desenvolvimento moderno: **Maven**, **Git/GitHub** e **CI/CD**.

## Próximos Passos (Versões Futuras)

Existem várias ideias planeadas para tornar o jogo ainda mais desafiante:
* Adicionar níveis de dificuldade e obstáculos no mapa.
* Sistema de pontuação, histórico e ranking de partidas.
* Efeitos sonoros e menus interativos.
* Versão web mantendo a mesma lógica de jogo.
