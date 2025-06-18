package org.swampsimulation.entities.animal;

import org.swampsimulation.entities.Point;
import org.swampsimulation.entities.animal.frog.Frog;
import org.swampsimulation.core.Board;
import org.swampsimulation.core.CsvLogger;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * The Snake class represents a predator animal in the swamp simulation.
 * Snakes move across the board towards a target point and hunting frogs.
 * snake is automatically removed from the board once it reach  their target.
 */

public class Snake extends Animal{
    private Point target;

    /**
     * Constructs a new Snake object.
     * @param location The initial {@link Point} position of the snake.
     * @param target The target {@link Point} the snake will move to.
     * @param logger The {@link CsvLogger} instance for logging events.
     */

    public Snake(Point location,Point target, CsvLogger logger) {
        super(location, AnimalSpecies.SNAKE,logger);
        setImages("/animals/snake/snake1.png");
        setImages("/animals/snake/snake2.png");
        setSpriteSize(75, 500);
        this.target = target;
        setMovementSpeed(40);

    }

    /**
     * Simulates the snake eating a target animal.
     * Sets the target's health to zero, marks it as not alive, logs the event.
     * @param target The {@link Animal} that the snake eats.
     * @param board The current state of the simulation board.
     */

    public void eat(Animal target, Board board) {
        target.setHealthPoints(0); // Reduce target's health to zero.
        target.setAlive(false); // Mark target as dead.
        logger.logEvent(this.getSpecies()+" ate "+target.getSpecies(),board.getSimulation().getCurrentTick());
        System.out.println(getSpecies() + " eats " +  target.getSpecies());
    }

    /**
     * Implements the hunting for the snake.
     * The snake searches for nearby frogs
     * eats the closest one if it's within striking range
     * @param board The current state of the simulation board.
     */

    public void hunt(Board board) {
        List<Animal> getNearby;
        double min_distance=Double.MAX_VALUE;
        Animal closest = null;
        getNearby=board.getNearbyAnimals(this,110);             //get animals in view distance
        for(Animal animal : getNearby) {
            if(animal instanceof Frog) {
                if(animal.isAlive()) {
                    double distance = getPosition().getDistance(animal.getPosition());
                    if (distance < min_distance) {
                        min_distance = distance;
                        closest = animal;
                    }
                    if (min_distance <= 50) {
                        eat(closest,board);
                    }
                }
            }
        }
    }

    /**
     * Returns the current sprite image for the snake, cycling between two for animation.
     * @return The {@link BufferedImage} representing the current snake sprite.
     */

    @Override
    public BufferedImage getSprite() {
        if (getImages().isEmpty()) {
            return null;
        }
        return getImages().get(getSpriteInt());
    }

    /**
     * Updates the snake's state for the current tick.
     * Handles sprite animation, movement to its target, and removal
     * from the board if it has reached the target.
     * @param board The current state of the simulation board.
     */

    @Override
    public void update(Board board){
        if (!getImages().isEmpty()) {
            setSpriteInt((getSpriteInt() + 1) % 2);
        }
        if(getPosition().getDistance(target) > getMovementSpeed()) {
            move(board, target);
            hunt(board);
        }
        else if(getPosition().getDistance(target) < 5*getMovementSpeed()) {
            board.removeAnimal(this);
            setAlive(false);
        }

    }
    /**
     * Moves the snake towards a specific target point.
     * Calculates the angle to the target and updates the snake's position and rotation.
     * @param board The current state of the simulation board
     * @param target The {@link Point} the snake should move to.
     */

    @Override
    public void move(Board board, Point target) {
        int currentX = getPosition().getX();
        int currentY = getPosition().getY();
        int newX = currentX;
        int newY = currentY;
        int speed = getMovementSpeed();
        if(target!=null) {
            int targetX = target.getX();
            int targetY = target.getY();

            double targetAngleRadians =  Math.atan2(targetY-currentY,targetX-currentX);
            newX = (int) (currentX+Math.cos(targetAngleRadians)*speed);
            newY = (int) (currentY+Math.sin(targetAngleRadians)*speed);
            setRotation(Math.toDegrees(targetAngleRadians) + 90);
        }

        Point newpos = new Point(newX, newY);
        setPosition(newpos);
    }
}
