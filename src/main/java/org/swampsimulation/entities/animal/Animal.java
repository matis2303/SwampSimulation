package org.swampsimulation.entities.animal;

import org.swampsimulation.entities.Entity;
import org.swampsimulation.entities.Point;
import org.swampsimulation.entities.animal.frog.species.BufoBufoFrog;
import org.swampsimulation.core.Board;
import org.swampsimulation.core.CsvLogger;
import org.swampsimulation.map.SwampArea;

import java.util.Random;

public abstract class Animal extends Entity {
    private int healthPoints;
    private int movementSpeed;
    private boolean isAlive;
    private boolean isMoving;
    private int movementCoolDown;
    private AnimalSpecies species;
    protected CsvLogger logger;



    public Animal(Point position, AnimalSpecies species, CsvLogger logger) {
        super(position);
        this.healthPoints = 1;
        this.movementSpeed = 0;
        this.isAlive = true;
        this.isMoving = true;
        this.species = species;
        this.logger = logger;
        Random rand = new Random();
        int nr = rand.nextInt(8);
        this.movementCoolDown = nr;
    }


    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    public int getMovementSpeed() {
        return movementSpeed;
    }

    public void setMovementSpeed(int movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public void setMovementCoolDown(int movementCoolDown) {
        this.movementCoolDown = movementCoolDown;
    }
    public int getmovementCoolDown() {
        return movementCoolDown;
    }

    public void update(Board board) {
        if(isMoving){
            animate();
        }
        if(getHealthPoints()<=0){
            isAlive=false;
            board.removeAnimal(this);
        }
    }

    public void move(Board board, Point target) {
        int currentX = getPosition().getX();
        int currentY = getPosition().getY();
        int newX = currentX;
        int newY = currentY;
        int speed = getMovementSpeed();
        Point potentialNewPosition = new Point(newX, newY);
        double currentMovementAngleRadians = Math.toRadians(getRotation() - 90);
        if(target!=null) {
            int targetX = target.getX();
            int targetY = target.getY();

            double targetAngleRadians =  Math.atan2(targetY-currentY,targetX-currentX);
            newX = (int) (currentX+Math.cos(targetAngleRadians)*speed);
            newY = (int) (currentY+Math.sin(targetAngleRadians)*speed);
            setRotation(Math.toDegrees(targetAngleRadians) + 90);
            newX = Math.max(0, Math.min(newX, board.getWidth() - getSpriteSize().getX()));
            newY = Math.max(0, Math.min(newY, board.getHeight() - getSpriteSize().getY()));

            potentialNewPosition.setLocation(newX, newY);
        }
        else{
            int attempt=0;
            do {
                Random rand = new Random();

                double randomDeviationRadians = Math.toRadians((rand.nextDouble() - 0.5) * 90);
                currentMovementAngleRadians = currentMovementAngleRadians + randomDeviationRadians;

                int dx = (int) (Math.cos(currentMovementAngleRadians) * speed);
                int dy = (int) (Math.sin(currentMovementAngleRadians) * speed);

                newX = currentX + dx;
                newY = currentY + dy;

                setRotation(Math.toDegrees(currentMovementAngleRadians) + 90);
                if(this instanceof Fly){
                    newX = Math.max(0, Math.min(newX, board.getWidth() - 80));
                    newY = Math.max(0, Math.min(newY, board.getHeight() - 80));
                }
                newX = Math.max(0, Math.min(newX, board.getWidth() - getSpriteSize().getX()));
                newY = Math.max(0, Math.min(newY, board.getHeight() - getSpriteSize().getY()));


                potentialNewPosition.setLocation(newX, newY);
                attempt++;



            }while(!((this instanceof BufoBufoFrog) || (this instanceof Fly)) && SwampArea.isOnMud(potentialNewPosition) && attempt<10);

        }
        if (!((this instanceof BufoBufoFrog)|| (this instanceof Fly) )&& SwampArea.isOnMud(potentialNewPosition)) {

        } else {

            setPosition(potentialNewPosition);
        }
    }

    public void animate() {

        if (getImages() != null && !getImages().isEmpty()) {
            setSpriteInt((getSpriteInt() + 1) % 2);
        }

    }


    public void flee(Animal danger, Board board) {
        int currentX = getPosition().getX();
        int currentY = getPosition().getY();
        int dangerX = danger.getPosition().getX();
        int dangerY = danger.getPosition().getY();

        double vecX = currentX - dangerX;
        double vecY = currentY - dangerY;

        double fleeAngleRadians = Math.atan2(vecY, vecX);

        Random rand = new Random();


        double randomDeviationRadians = Math.toRadians((rand.nextDouble() - 0.5) * 60);
        fleeAngleRadians += randomDeviationRadians;

        int newX = (int) (currentX + Math.cos(fleeAngleRadians) * getMovementSpeed());
        int newY = (int) (currentY + Math.sin(fleeAngleRadians) * getMovementSpeed());

        Point fleeTarget = new Point(newX, newY);
        move(board, fleeTarget);
    }
    public AnimalSpecies getSpecies() {
        return species;
    }
    public void setSpecies(AnimalSpecies species) {
        this.species = species;
    }


}