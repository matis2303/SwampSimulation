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

public class SimulationBoardPanel extends JPanel {
    private Board board;
    private BufferedImage backgroundImage;

    public SimulationBoardPanel(Board board) {
        this.board = board;
        try {
            backgroundImage = ImageIO.read(getClass().getResource("/background/swamp_bg.png")); //
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
        } else {
            g.setColor(new Color(61, 70, 40));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
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