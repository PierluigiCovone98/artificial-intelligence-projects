package it.uniroma1.ai.problems.proteinfolding;

/**
 * Set of possible directions for placing the next amino-acid on the 2D grid.
 * Each action contains the offset regardless of the position from which the action can be taken.
 */
public enum Action {
    UP(0,1), DOWN(0,-1), LEFT(-1,0), RIGHT(1,0);

    private final int dx;
    private final int dy;


    /**
     * Private constructor.
     */
    Action(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * Get the offset for x.
     */
    public int getDx() {
        return dx;
    }
    /**
     * Get the offset for y.
     */
    public int getDy() {
        return dy;
    }

}
