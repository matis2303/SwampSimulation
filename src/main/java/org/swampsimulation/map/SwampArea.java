package org.swampsimulation.map;
import org.swampsimulation.entities.Point;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

/**
 * The SwampArea defines and manages swamp's mud area.
 * Provides static methods to check if a point is on the mud
 * Draws the mud area.
 */

public class SwampArea {
    /**
     * A buffer size used when checking if an animal is near mud area.
     */
    private static double MUD_SIZE_BUFFER = 20.0;

    private static double mudOvalX = -600.0;
    private static double mudOvalY = 435.0;
    private static double mudOvalWidth = 1400.0;
    private static double mudOvalHeight = 800.0;
    /**
     * The geometric shape representing the mud area, an ellipse.
     */
    private static Shape mudShape = new Ellipse2D.Double(mudOvalX, mudOvalY, mudOvalWidth, mudOvalHeight);

    /**
     * Checks if a given position is located on the mud area.
     * @param position The {@link Point} to check.
     * @return true if the position  is on the mud, false if not.
     */

    public static boolean isOnMud(Point position) {
        Rectangle2D.Double animalBounds = new Rectangle2D.Double(
                position.getX(),
                position.getY(),
                MUD_SIZE_BUFFER,
                MUD_SIZE_BUFFER
        );
        return mudShape.intersects(animalBounds);
    }

    /**
     * Renders the mud area
     * The mud is drawn in a dark green color
     * and filled according to its shape.
     * @param g The {@link Graphics} object used for drawing.
     */

    public static void createMud(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(23, 44, 20, 242));
        g2d.fill(mudShape);
    }
}
