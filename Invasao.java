package pt.github.invasao;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Ponto de entrada da aplicação.
 */
public final class Invasao extends JFrame {

    public Invasao() {
        super("Invasão!");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(new GamePanel());
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Invasao janela = new Invasao();
            janela.setVisible(true);
        });
    }
}
