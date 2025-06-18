package org.swampsimulation.entities.animal;

import org.swampsimulation.entities.Entity;
import org.swampsimulation.entities.Point;
import org.swampsimulation.entities.animal.frog.species.BufoBufoFrog;
import org.swampsimulation.core.Board;
import org.swampsimulation.core.CsvLogger;
import org.swampsimulation.map.SwampArea;

import java.util.Random;

/**
 * The animal abstract class is used for all living entities
 * it provides basic attributes, like healthPoints, movementSpeed, species and life status
 */

public abstract class Animal extends Entity {
    private int healthPoints;
    private int movementSpeed;
    private boolean isAlive;
    private boolean isMoving;
    private int movementCoolDown;
    private AnimalSpecies species;
    protected CsvLogger logger;


    /**
     * Constructs Animal
     *
     * @param position - current animal position
     * @param species - species of the animal
     * @param logger - CsvLogger for logging events
     */
    public Animal(Point position, AnimalSpecies species, CsvLogger logger) {
        super(position);
        this.healthPoints = 1; //default
        this.movementSpeed = 0; //default
        this.isAlive = true;
        this.isMoving = true;
        this.species = species;
        this.logger = logger;
        Random rand = new Random();
        int nr = rand.nextInt(8);
        this.movementCoolDown = nr;
    }

    /**
     * Returns the current health points
     * @return Current healthPoints
     */
    public int getHealthPoints() {
        return healthPoints;
    }
    /**
     * Sets the health points of the animal
     * @param healthPoints - New current health points
     */

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    /**
     * Returns the movement speed
     * @return Current movement speed
     */

    public int getMovementSpeed() {
        return movementSpeed;
    }

    /**
     * Sets the movement speed of the animal
     * @param movementSpeed - New movement speed.
     */

    public void setMovementSpeed(int movementSpeed) {
        this.movementSpeed = movementSpeed;
    }

    /**
     * Checks if the animal is alive
     * @return true if the animal is alive, false if not
     */
    public boolean isAlive() {
        return isAlive;
    }

    /**
     * Sets the alive status of the animal
     * If health drops to 0 or below, this is set to false
     * @param alive - New alive status.
     */

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    /**
     * Checks if the animal can move
     * @return true if the animal is moving, false if not
     */
    public boolean isMoving() {
        return isMoving;
    }

    /**
     * Sets the current moving status
     * @param moving - true to enable movement, false to pause it
     */

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    /**
     * Sets the new movement cooldown for the animal
     * @param movementCoolDown - New cooldown value
     */

    public void setMovementCoolDown(int movementCoolDown) {
        this.movementCoolDown = movementCoolDown;
    }
    /**
     * Returns the current movement cooldown of the animal
     * @return current movement cooldown
     */
    public int getmovementCoolDown() {
        return movementCoolDown;
    }

    /**
     * Updates the animal's state for the current tick
     * Checks if the animal is alive and can move
     *
     * @param board - the board the animal is at
     */

    public void update(Board board) {
        if(getHealthPoints()<=0){
            isAlive=false;
            board.removeAnimal(this);
        }
        if(isMoving){
            animate();
        }
    }
    /**
     * Handles moving logic
     * Animal moves to target point, if none exist moves randomly
     * Searches for a new point if given species cannot move to SwampArea
     * Movement is blocked by board boundaries
     * Random movement is calculated based on current angle and its deviation
     *
     * @param board - the board the animal is at
     * @param target - target point the animal is moving to
     */

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

                //Attempt to find a position outside mud for most frogs
            }while(!((this instanceof BufoBufoFrog) || (this instanceof Fly)) && SwampArea.isOnMud(potentialNewPosition) && attempt<10);


        }
        if (!((this instanceof BufoBufoFrog)|| (this instanceof Fly) )&& SwampArea.isOnMud(potentialNewPosition)) {

        } else {

            setPosition(potentialNewPosition);
        }
    }

    /**
     * Updates the animal's animation
     * Cycles through images
     */

    public void animate() {

        if (getImages() != null && !getImages().isEmpty()) {
            setSpriteInt((getSpriteInt() + 1) % 2);
        }

    }

    /**
     * Handles the animal fleeing logic
     * Calculates the escape point and maximum angle so the animal doesn't rotate by 180 degrees
     *
     * @param danger - the threat animal
     * @param board - current board the animal is at
     */
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


    /**
     * Returns animal species
     * @return animal species {@link AnimalSpecies}
     */
    public AnimalSpecies getSpecies() {
        return species;
    }

    /**
     * Sets the species of the animal
     * @param species - New animal species {@link AnimalSpecies} for this animal.
     */
    public void setSpecies(AnimalSpecies species) {
        this.species = species;
    }


}