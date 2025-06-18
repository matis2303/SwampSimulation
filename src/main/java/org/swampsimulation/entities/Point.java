package org.swampsimulation.entities;

/**
 * Point class used to represent a (X,Y) point, used for entities positions and also sprite sizes
 * Has methods to access current point and calculate distance to another point
 */
public class Point {
    private int x;
    private int y;

    /**
     * Constructs Point
     *
     * @param x - x coordinate
     * @param y - y coordinate
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns current X coordinate
     * @return x - the current X coordinate
     */

    //gettery
    public int getX() {
        return x;
    }
    /**
     * Returns current Y coordinate
     * @return Y - the current X coordinate
     */
    public int getY() {
        return y;
    }

    //settery
    /**
     * Sets current X coordinate
     * @param x - the new current X coordinate
     */
    public void setX(int x) {
        this.x = x;
    }
    /**
     * Sets current Y coordinate
     * @param y - the new current Y coordinate
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Sets current X and Y coordinate
     * @param x - the new current X coordinate
     * @param y - the new current Y coordinate
     */
    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Calculates the Euclidean distance to the other specified point
     *
     * @param p - point to calculate the distance to
     *
     * @return Distance between the two points
     */

    public double getDistance(Point p) {
        int dx = p.getX()- x;
        int dy = p.getY() - y;
        return Math.sqrt(dx * dx + dy * dy);

    }
}
