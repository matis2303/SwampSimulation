package org.swampsimulation.UI;

import org.swampsimulation.entities.Entity;
import org.swampsimulation.entities.animal.Animal;
import org.swampsimulation.entities.animal.Snake;
import org.swampsimulation.core.Board;
import org.swampsimulation.entities.plants.Bushes;
import org.swampsimulation.entities.plants.Lily;
import org.swampsimulation.entities.plants.Plants;
import org.swampsimulation.map.SwampArea;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * The SimulationBoardPanel is a JPanel responsible for rendering the simulation board,
 * include the background, mud area, all entities.
 */

public class SimulationBoardPanel extends JPanel {
    private Board board;
    private BufferedImage backgroundImage;

    /**
     * Makes a new SimulationBoardPanel.
     * @param board The Board containing all entities to be rendered.
     */

    public SimulationBoardPanel(Board board) {
        this.board = board;
        try {
            backgroundImage = ImageIO.read(getClass().getResource("/background/swamp_bg.png")); //
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Overrides the paintComponent method to custom draw the simulation board.
     * Draws the background, the mud area, and all entities on the board.
     * @param g The Graphics object.
     */

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw the background a solid color if the image is not available.
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        } else {
            g.setColor(new Color(61, 70, 40));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
        // Draw the swamp mud area.
        SwampArea.createMud(g);

        for (Entity entity : board.getAllEntities()) {
            int X = entity.getPosition().getX();
            int Y = entity.getPosition().getY();

            AffineTransform oldTransform = g2d.getTransform();
            double rotationAngleRadians = Math.toRadians(entity.getRotation());
            int centerX = 0;
            int centerY = 0;

            if(!(entity instanceof Snake)){
                centerX = X + entity.getSpriteSize().getX() / 2;
                centerY = Y + entity.getSpriteSize().getY() / 2;
            }else{
                centerX = X + entity.getSpriteSize().getX() / 2;
                centerY=Y;
            }

            g2d.rotate(rotationAngleRadians, centerX, centerY);
            g2d.drawImage(entity.getSprite(), X, Y, entity.getSpriteSize().getX(), entity.getSpriteSize().getY(), null);
            g2d.setTransform(oldTransform);
        }


    }
}