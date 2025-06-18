package org.swampsimulation.entities;

import org.swampsimulation.core.Randomizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The entity class is the base class used for all the objects that can be placed on board
 * it contains basic attributes, such as points, rotation and sprite sizes and image cache
 */

public class Entity implements Randomizer {

    private Point point;
    private double Rotation;
    private Point SpriteSize;
    private int spriteInt;
    private ArrayList<BufferedImage> Images;
    private static final Map<String, BufferedImage> imageCache = new HashMap<>();
    //Cache for loaded images to save memory


    /**
     * Constructs entity
     * initializes default rotation of 0 and SpriteSize
     *
     * @param position - Entity position {@link Point}
     */
    public Entity(Point position) {
        this.point = position;
        this.Rotation=0;
        this.Images = new ArrayList<>();
        this.SpriteSize = new Point(38, 38);
    }

    /**
     * Sets current rotation
     * @param rotation - the new rotation
     */
    public void setRotation(double rotation) {
        Rotation = rotation;

    }
    /**
     * Returns current rotation
     * @return Current rotation angle in degrees
     */
    public double getRotation() {
        return Rotation;
    }

    /**
     * Returns current point
     * @return Current positon {@link Point}
     */

    public Point getPosition() {
        return point;
    }


    /**
     * Sets a new current point
     * @param position - new entity Position {@link Point}
     */

    public void setPosition(Point position) {
        this.point = position;
    }

    /**
     * Loads an image from the image path and adds it to the entity BufferedImages ArrayList
     * Images are cached on HashMap to avoid loading the image more than once
     *
     * @param imagePath - path to the image
     */
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

    /**
     * Returns image of Entity
     * @return BufferedImage - returns bufferedimage of entity
     */
    public BufferedImage getSprite() {
        if (getImages().isEmpty()) {
            return null;
        }
        return getImages().get(spriteInt);
    }

    /**
     * Returns the list of all images
     * @return list of all images
     */
    public ArrayList<BufferedImage> getImages() {
        return Images;
    }
    /**
     * Sets sprite integer for image indexing
     * @param spriteInt - New spriteint
     */
    public void setSpriteInt(int spriteInt) {
        this.spriteInt = spriteInt;
    }
    /**
     * Returns spriteInt for image indexing
     * @return Returns current spriteInt
     */
    public int getSpriteInt() {
        return spriteInt;
    }

    /**
     * Sets sprite size for rendering and point calculations
     * @param width - sprite x
     * @param height - sprite y
     */

    public void setSpriteSize(int width, int height) {
        SpriteSize = new Point(width, height);
    }
    /**
     * Returns sprite size as a {@link Point}
     * @return SpriteSize {@link Point}
     */

    public Point getSpriteSize() {
        return SpriteSize;
    }
}
