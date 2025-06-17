package org.swampsimulation.map;
import org.swampsimulation.entities.Point;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;


public class SwampArea {
    private static double MUD_SIZE_BUFFER = 20.0;


    private static double mudOvalX = -600.0;
    private static double mudOvalY = 435.0;
    private static double mudOvalWidth = 1400.0;
    private static double mudOvalHeight = 800.0;
    private static Shape mudShape = new Ellipse2D.Double(mudOvalX, mudOvalY, mudOvalWidth, mudOvalHeight);



    public static boolean isOnMud(Point position) {
        Rectangle2D.Double animalBounds = new Rectangle2D.Double(
                position.getX(),
                position.getY(),
                MUD_SIZE_BUFFER,
                MUD_SIZE_BUFFER
        );
        return mudShape.intersects(animalBounds);
    }

    public static void createMud(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(23, 44, 20, 242));
        g2d.fill(mudShape);
    }
}
