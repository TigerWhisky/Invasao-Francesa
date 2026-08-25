package pt.github.invasao;

import java.awt.Point;
import java.util.Objects;
import java.util.Random;

/**
 * Modelo e regras do jogo.
 *
 * Esta classe não depende de Swing, permitindo testar a lógica
 * independentemente da interface gráfica.
 */
public final class GameState {

    public static final int GRID_SIZE = 30;
    public static final int MAX_MOVES = 25;
    public static final int FRENCH_MOVE_INTERVAL = 3;
    public static final int MIN_FRENCH_MOVE = 1;
    public static final int MAX_FRENCH_MOVE = 3;

    public enum Status {
        A_DECORRER,
        VITORIA,
        FRANCA_ESCAPOU
    }

    private final Random random;
    private final Army portugueseArmy;
    private final Army frenchArmy;

    private int jogadas;
    private Status status;

    public GameState() {
        this(new Random());
    }

    public GameState(Random random) {
        this.random = Objects.requireNonNull(random);
        this.portugueseArmy = new Army("Exército Português", 0, GRID_SIZE - 1);
        this.frenchArmy = new Army("Exército Francês", 0, 0);
        reset();
    }

    public void reset() {
        portugueseArmy.setPosition(random.nextInt(GRID_SIZE), GRID_SIZE - 1);
        frenchArmy.setPosition(random.nextInt(GRID_SIZE), 0);
        jogadas = 0;
        status = Status.A_DECORRER;
    }

    /**
     * Move o exército português.
     *
     * @return true se o movimento foi aceite.
     */
    public boolean movePortuguese(int squares, Direction direction) {
        if (status != Status.A_DECORRER || squares <= 0 || direction == null) {
            return false;
        }

        int newX = portugueseArmy.getX() + direction.getDeltaX() * squares;
        int newY = portugueseArmy.getY() + direction.getDeltaY() * squares;

        if (!isInside(newX, newY)) {
            return false;
        }

        portugueseArmy.setPosition(newX, newY);
        jogadas++;

        if (hasEncounter()) {
            status = Status.VITORIA;
        }

        return true;
    }

    /**
     * Executa o movimento francês quando a regra das três jogadas é atingida.
     */
    public void moveFrench() {
        if (status != Status.A_DECORRER) {
            return;
        }

        int direction = random.nextInt(2);
        int squares = random.nextInt(MAX_FRENCH_MOVE) + MIN_FRENCH_MOVE;

        int newX;

        if (direction == 0) {
            newX = Math.max(0, frenchArmy.getX() - squares);
        } else {
            newX = Math.min(GRID_SIZE - 1, frenchArmy.getX() + squares);
        }

        frenchArmy.setPosition(newX, frenchArmy.getY());

        if (hasEncounter()) {
            status = Status.VITORIA;
        }
    }

    /**
     * Determina se a França deve mover-se após a jogada atual.
     */
    public boolean shouldMoveFrench() {
        return jogadas > 0 && jogadas % FRENCH_MOVE_INTERVAL == 0;
    }

    /**
     * Se o limite de jogadas foi atingido sem vitória, a França escapa.
     */
    public void finishIfMaximumMovesReached() {
        if (status == Status.A_DECORRER && jogadas >= MAX_MOVES) {
            status = Status.FRANCA_ESCAPOU;
        }
    }

    private boolean hasEncounter() {
        return portugueseArmy.occupies(
                frenchArmy.getX(),
                frenchArmy.getY()
        );
    }

    private boolean isInside(int x, int y) {
        return x >= 0 && x < GRID_SIZE
                && y >= 0 && y < GRID_SIZE;
    }

    public Army getPortugueseArmy() {
        return portugueseArmy;
    }

    public Army getFrenchArmy() {
        return frenchArmy;
    }

    public Point getPortuguesePosition() {
        return portugueseArmy.getPosition();
    }

    public Point getFrenchPosition() {
        return frenchArmy.getPosition();
    }

    public int getJogadas() {
        return jogadas;
    }

    public Status getStatus() {
        return status;
    }
}
