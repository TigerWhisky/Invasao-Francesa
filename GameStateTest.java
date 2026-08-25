package pt.github.invasao;

import org.junit.jupiter.api.Test;

import java.awt.Point;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class GameStateTest {

    @Test
    void deveComecarComPortugalNaLinhaInferior() {
        GameState game = new GameState(new Random(1));

        assertEquals(GameState.GRID_SIZE - 1,
                game.getPortuguesePosition().y);
        assertEquals(0, game.getJogadas());
        assertEquals(GameState.Status.A_DECORRER,
                game.getStatus());
    }

    @Test
    void deveComecarComFrancaNaLinhaSuperior() {
        GameState game = new GameState(new Random(1));

        assertEquals(0, game.getFrenchPosition().y);
    }

    @Test
    void movimentoValidoDeveIncrementarJogadas() {
        GameState game = new GameState(new Random(1));
        Point initial = game.getPortuguesePosition();

        assertTrue(game.movePortuguese(2, Direction.NORTE));

        assertEquals(initial.x, game.getPortuguesePosition().x);
        assertEquals(initial.y - 2,
                game.getPortuguesePosition().y);
        assertEquals(1, game.getJogadas());
    }

    @Test
    void movimentoQueSaiDaGrelhaDeveSerRecusado() {
        GameState game = new GameState(new Random(1));

        boolean moved = game.movePortuguese(
                GameState.GRID_SIZE,
                Direction.NORTE
        );

        assertFalse(moved);
        assertEquals(0, game.getJogadas());
    }

    @Test
    void movimentoZeroDeveSerRecusado() {
        GameState game = new GameState(new Random(1));

        assertFalse(game.movePortuguese(0, Direction.NORTE));
        assertEquals(0, game.getJogadas());
    }

    @Test
    void movimentoNegativoDeveSerRecusado() {
        GameState game = new GameState(new Random(1));

        assertFalse(game.movePortuguese(-2, Direction.NORTE));
        assertEquals(0, game.getJogadas());
    }

    @Test
    void deveMoverFrancesApenasACadaTresJogadas() {
        GameState game = new GameState(new Random(1));

        assertFalse(game.shouldMoveFrench());

        game.movePortuguese(1, Direction.NORTE);
        assertFalse(game.shouldMoveFrench());

        game.movePortuguese(1, Direction.NORTE);
        assertFalse(game.shouldMoveFrench());

        game.movePortuguese(1, Direction.NORTE);
        assertTrue(game.shouldMoveFrench());
    }

    @Test
    void resetDeveReporOJogo() {
        GameState game = new GameState(new Random(1));

        game.movePortuguese(1, Direction.NORTE);
        game.reset();

        assertEquals(0, game.getJogadas());
        assertEquals(GameState.Status.A_DECORRER,
                game.getStatus());
        assertEquals(GameState.GRID_SIZE - 1,
                game.getPortuguesePosition().y);
        assertEquals(0, game.getFrenchPosition().y);
    }

    @Test
    void deveTerminarAosVinteECincoMovimentos() {
        GameState game = new GameState(new Random(1));

        for (int i = 0; i < GameState.MAX_MOVES; i++) {
            boolean moved = game.movePortuguese(
                    1,
                    i % 2 == 0
                            ? Direction.NORTE
                            : Direction.SUL
            );

            if (!moved) {
                // Reinicia para garantir movimentos válidos no teste.
                game.reset();
                i = -1;
            }
        }

        game.finishIfMaximumMovesReached();

        assertTrue(
                game.getStatus() == GameState.Status.FRANCA_ESCAPOU
                        || game.getStatus() == GameState.Status.VITORIA
        );
    }
}
