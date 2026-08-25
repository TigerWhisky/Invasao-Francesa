package pt.github.invasao;

import java.awt.Point;

/**
 * Representa um exército e a sua posição na grelha.
 */
public final class Army {

    private final String name;
    private final Point position;

    public Army(String name, int x, int y) {
        this.name = name;
        this.position = new Point(x, y);
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return position.x;
    }

    public int getY() {
        return position.y;
    }

    public Point getPosition() {
        return new Point(position);
    }

    public void setPosition(int x, int y) {
        position.setLocation(x, y);
    }

    public boolean occupies(int x, int y) {
        return position.x == x && position.y == y;
    }
}
