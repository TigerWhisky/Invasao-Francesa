package pt.github.invasao;

/**
 * Direções possíveis para o exército português.
 */
public enum Direction {
    NORTE(0, -1, "N"),
    SUL(0, 1, "S"),
    ESTE(1, 0, "E"),
    OESTE(-1, 0, "O");

    private final int deltaX;
    private final int deltaY;
    private final String abbreviation;

    Direction(int deltaX, int deltaY, String abbreviation) {
        this.deltaX = deltaX;
        this.deltaY = deltaY;
        this.abbreviation = abbreviation;
    }

    public int getDeltaX() {
        return deltaX;
    }

    public int getDeltaY() {
        return deltaY;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
}
