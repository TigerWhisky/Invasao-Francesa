package pt.github.invasao;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.Timer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Random;

/**
 * Interface gráfica e controlo do fluxo de interação do jogo.
 */
public final class GamePanel extends JPanel {

    private static final int CELL_SIZE = 20;
    private static final int FRENCH_DELAY_MS = 250;

    private final GameState gameState;
    private final JLabel[][] grid;
    private final JLabel statusLabel;
    private final JLabel movesLabel;
    private final JSpinner movementSpinner;
    private final JButton northButton;
    private final JButton southButton;
    private final JButton eastButton;
    private final JButton westButton;
    private final JButton restartButton;

    public GamePanel() {
        super(new BorderLayout(8, 8));

        gameState = new GameState(new Random());
        grid = new JLabel[GameState.GRID_SIZE][GameState.GRID_SIZE];

        statusLabel = new JLabel();
        movesLabel = new JLabel();

        movementSpinner = new JSpinner(
                new SpinnerNumberModel(1, 1, GameState.GRID_SIZE, 1)
        );

        northButton = createDirectionButton("Norte", Direction.NORTE);
        southButton = createDirectionButton("Sul", Direction.SUL);
        eastButton = createDirectionButton("Este", Direction.ESTE);
        westButton = createDirectionButton("Oeste", Direction.OESTE);
        restartButton = new JButton("Nova partida");

        restartButton.addActionListener(event -> resetGame());

        add(createTopPanel(), BorderLayout.NORTH);
        add(createGridPanel(), BorderLayout.CENTER);
        add(createBottomPanel(), BorderLayout.SOUTH);

        refresh();
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JPanel information = new JPanel();
        information.add(statusLabel);
        information.add(movesLabel);

        panel.add(information, BorderLayout.WEST);
        panel.add(restartButton, BorderLayout.EAST);

        return panel;
    }

    private JPanel createGridPanel() {
        JPanel panel = new JPanel(
                new GridLayout(GameState.GRID_SIZE, GameState.GRID_SIZE)
        );

        for (int y = 0; y < GameState.GRID_SIZE; y++) {
            for (int x = 0; x < GameState.GRID_SIZE; x++) {
                JLabel cell = new JLabel("", JLabel.CENTER);
                cell.setOpaque(true);
                cell.setPreferredSize(new Dimension(CELL_SIZE, CELL_SIZE));
                cell.setBorder(
                        BorderFactory.createLineBorder(Color.GRAY)
                );
                cell.setFont(
                        new Font(Font.SANS_SERIF, Font.PLAIN, 14)
                );

                grid[y][x] = cell;
                panel.add(cell);
            }
        }

        return panel;
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(2, 4, 2, 4);

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(new JLabel("Quadrados:"), constraints);

        constraints.gridx = 1;
        panel.add(movementSpinner, constraints);

        constraints.gridx = 2;
        panel.add(northButton, constraints);

        constraints.gridx = 3;
        panel.add(southButton, constraints);

        constraints.gridx = 4;
        panel.add(eastButton, constraints);

        constraints.gridx = 5;
        panel.add(westButton, constraints);

        return panel;
    }

    private JButton createDirectionButton(
            String text,
            Direction direction
    ) {
        JButton button = new JButton(text);

        button.addActionListener(
                event -> executePortugueseMove(direction)
        );

        return button;
    }

    private void executePortugueseMove(Direction direction) {
        if (gameState.getStatus() != GameState.Status.A_DECORRER) {
            return;
        }

        int squares = (Integer) movementSpinner.getValue();

        boolean moved = gameState.movePortuguese(squares, direction);

        if (!moved) {
            JOptionPane.showMessageDialog(
                    this,
                    "Movimento inválido. O exército não pode sair da grelha.",
                    "Movimento inválido",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        refresh();

        if (gameState.getStatus() == GameState.Status.VITORIA) {
            showGameOver();
            return;
        }

        if (gameState.shouldMoveFrench()) {
            setControlsEnabled(false);

            Timer timer = new Timer(
                    FRENCH_DELAY_MS,
                    event -> {
                        gameState.moveFrench();
                        gameState.finishIfMaximumMovesReached();
                        refresh();
                        ((Timer) event.getSource()).stop();
                        setControlsEnabled(
                                gameState.getStatus()
                                        == GameState.Status.A_DECORRER
                        );

                        if (gameState.getStatus()
                                != GameState.Status.A_DECORRER) {
                            showGameOver();
                        }
                    }
            );

            timer.setRepeats(false);
            timer.start();
            return;
        }

        gameState.finishIfMaximumMovesReached();
        refresh();

        if (gameState.getStatus() != GameState.Status.A_DECORRER) {
            showGameOver();
        }
    }

    private void setControlsEnabled(boolean enabled) {
        movementSpinner.setEnabled(enabled);
        northButton.setEnabled(enabled);
        southButton.setEnabled(enabled);
        eastButton.setEnabled(enabled);
        westButton.setEnabled(enabled);
    }

    private void refresh() {
        for (int y = 0; y < GameState.GRID_SIZE; y++) {
            for (int x = 0; x < GameState.GRID_SIZE; x++) {
                grid[y][x].setText("");
                grid[y][x].setBackground(Color.WHITE);
            }
        }

        int portugueseX = gameState.getPortuguesePosition().x;
        int portugueseY = gameState.getPortuguesePosition().y;

        int frenchX = gameState.getFrenchPosition().x;
        int frenchY = gameState.getFrenchPosition().y;

        grid[portugueseY][portugueseX].setText("🇵🇹");
        grid[portugueseY][portugueseX].setBackground(Color.GREEN);

        grid[frenchY][frenchX].setText("🇫🇷");
        grid[frenchY][frenchX].setBackground(Color.RED);

        movesLabel.setText(
                "   Jogadas: "
                        + gameState.getJogadas()
                        + " / "
                        + GameState.MAX_MOVES
        );

        switch (gameState.getStatus()) {
            case A_DECORRER ->
                    statusLabel.setText("Batalha em curso");
            case VITORIA ->
                    statusLabel.setText("Portugal venceu!");
            case FRANCA_ESCAPOU ->
                    statusLabel.setText("A França conseguiu escapar!");
        }
    }

    private void showGameOver() {
        String message;

        if (gameState.getStatus() == GameState.Status.VITORIA) {
            message =
                    "Parabéns! Derrotámos os Franceses. Portugal está libertado!";
        } else {
            message =
                    "A batalha acabou... o Exército Francês conseguiu fugir.";
        }

        int choice = JOptionPane.showConfirmDialog(
                this,
                message + "\n\nJogar novamente?",
                "Fim da batalha",
                JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            resetGame();
        } else {
            setControlsEnabled(false);
        }
    }

    private void resetGame() {
        gameState.reset();
        movementSpinner.setValue(1);
        setControlsEnabled(true);
        refresh();
    }

    public GameState getGameState() {
        return gameState;
    }
}
