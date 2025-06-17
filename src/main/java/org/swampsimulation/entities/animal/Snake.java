package org.swampsimulation.entities.animal;

import org.swampsimulation.entities.Point;
import org.swampsimulation.entities.animal.frog.Frog;
import org.swampsimulation.core.Board;
import org.swampsimulation.core.CsvLogger;

import java.awt.image.BufferedImage;
import java.util.List;

public class Snake extends Animal{
    private Point target;

    public Snake(Point location,Point target, CsvLogger logger) {
        super(location, AnimalSpecies.SNAKE,logger);
        setImages("/animals/snake/snake1.png");
        setImages("/animals/snake/snake2.png");
        setSpriteSize(75, 500);
        this.target = target;
        setMovementSpeed(40);

    }
    public void eat(Animal target, Board board) {
        target.setHealthPoints(0);
        target.setAlive(false);
        logger.logEvent(this.getSpecies()+" ate "+target.getSpecies(),board.getSimulation().getCurrentTick());
        System.out.println(getSpecies() + " eats " +  target.getSpecies());
    }

    public Animal hunt(Board board) {
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
        return null;
    }


    @Override
    public BufferedImage getSprite() {
        if (getImages().isEmpty()) {
            return null;
        }
        return getImages().get(getSpriteInt());
    }
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
