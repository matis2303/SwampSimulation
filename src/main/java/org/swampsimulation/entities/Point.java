package org.swampsimulation.entities;

public class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //gettery
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    //settery
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double getDistance(Point p) {
        int dx = p.getX()- x;
        int dy = p.getY() - y;
        return Math.sqrt(dx * dx + dy * dy);

    }
}
