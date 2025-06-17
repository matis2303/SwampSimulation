package org.swampsimulation.entities;

import org.swampsimulation.core.Randomizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Entity implements Randomizer {

    private Point point;
    private double Rotation;
    private Point SpriteSize;
    private int spriteInt;
    private ArrayList<BufferedImage> Images;
    private static final Map<String, BufferedImage> imageCache = new HashMap<>();
    //Cache do wczytywania obrazow, aby oszczedzac pamiec
    public Entity(Point position) {
        this.point = position;
        this.Rotation=0;
        this.Images = new ArrayList<>();
        this.SpriteSize = new Point(38, 38);
    }


    public void setRotation(double rotation) {
        Rotation = rotation;

    }
    public double getRotation() {
        return Rotation;
    }

    public Point getPosition() {
        return point;
    }

    public void setPosition(Point position) {
        this.point = position;
    }
    public void setImages(String imagePath) {
        BufferedImage img = imageCache.get(imagePath);
        if (img != null) {
            Images.add(img);
            return;
        }
        try {
            img = ImageIO.read(getClass().getResource(imagePath));
            if (img != null) {
                Images.add(img);
                imageCache.put(imagePath, img);
            } else {
                System.err.println("Failed to load image: " + imagePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getSprite() {
        if (getImages().isEmpty()) {
            return null;
        }
        return getImages().get(spriteInt);
    }
    public ArrayList<BufferedImage> getImages() {
        return Images;
    }

    public void setSpriteInt(int spriteInt) {
        this.spriteInt = spriteInt;
    }
    public int getSpriteInt() {
        return spriteInt;
    }
    public void setSpriteSize(int width, int height) {
        SpriteSize = new Point(width, height);
    }
    public Point getSpriteSize() {
        return SpriteSize;
    }
}
