package org.swampsimulation.entities.animal.frog;

import org.swampsimulation.entities.animal.Animal;
import org.swampsimulation.entities.animal.AnimalSpecies;
import org.swampsimulation.entities.Point;
import org.swampsimulation.entities.animal.Snake;
import org.swampsimulation.core.Board;
import org.swampsimulation.core.CsvLogger;

import java.util.List;

/**
 * Abstract class for frog that extends Animal
 * Frogs are a type of animal that have different species, all having different behaviours, like
 * hunting and fleeing.
 */


public abstract class Frog extends Animal {
    private boolean isFleeding;
    private boolean isHiding;
    private int restImage;

    /**
     * Constructs Frog
     * @param position - new Frog position
     * @param species - given frog species (for example PACMAN, TREE_FROG)
     * @param logger - CsvLogger to record events
     * @param restImage - integer of image index when the frog isn't moving
     */

    public Frog(Point position, AnimalSpecies species, CsvLogger logger,int restImage) {
        super(position,species,logger);
        this.isFleeding = false;
        this.isHiding = false;
        setMovementSpeed(0);
        this.restImage = restImage;
    }
    /**
     * Checks if the frog is currently fleeing from a predator
     *
     * @return true if the frog is fleeing, false if not
     */
    public boolean isFleeding() {
        return isFleeding;
    }
    /**
     * Sets the fleeing status of the frog.
     *
     * @param fleeding - true if the frog is fleeding, false if not
     */
    public void setFleeding(boolean fleeding) {
        isFleeding = fleeding;
    }
    /**
     * Sets the restImage index
     *
     * @return Returns the rest Image
     */
    public int getRestImage() {
        return restImage;
    }

    /**
     * Sets the restImage index
     *
     * @param restImage - the new restImage index
     */

    public void setRestImage(int restImage) {
        this.restImage=restImage;
    }


    /**
     * Returns the frog hiding status
     *
     * @return Returns true if frog is hiding, false if not
     */

    public boolean isHiding() {
        return isHiding;
    }

    /**
     * Sets the frog hiding status
     *
     * @param hiding - Sets the new frog hiding status
     */

    public void setHiding(boolean hiding) {
        isHiding = hiding;
    }

    /**
     * Method for determining whether the Frog can move or not
     * Frogs will alternate between moving and sitting depending on their cooldown
     * When resting, their image index changes to restImage index
     */

    public void SittingLogic() {
        int tick = getmovementCoolDown();
        if(tick<2){
            setMoving(true);

        }
        else if(tick>=2){
            setMoving(false);
            setSpriteInt(getRestImage());
        }
        if(tick==8){
            setMovementCoolDown(0);
            return;
        }
        tick++;
        setMovementCoolDown(tick);
    }


    /**
     * Abstract method used for hunting logic
     * Each frog implements its own hunting logic
     *
     * @param board - current board the animal is at
     * @return the {@link Animal} that the frog hunts
     */
    public abstract Animal hunt(Board board);

    /**
     * Method for animal eating other animal
     * Sets the target animal's health to 0 and Alive status to false
     *
     * @param target - {@link Animal} that the frog eats.
     * @param board - current board the animal is at
     */

    public void eat(Animal target, Board board) {
        target.setHealthPoints(0);
        target.setAlive(false);
        logger.logEvent(this.getSpecies()+" ate "+target.getSpecies(),board.getSimulation().getCurrentTick());
        System.out.println(getSpecies() + " eats " +  target.getSpecies());
    }

    /**
     * Updates frog status for each tick
     * This includes checking if the frog can move, has to flee from predator or hunts
     *
     * @param board - current board the animal is at
     */

    @Override
    public void update(Board board) {

            SittingLogic();
            super.update(board);

            if(!isMoving()){
                return;
            }

            List<Animal> nearbyAnimals = board.getNearbyAnimals(this, 270);
            Animal closestSnake = null;
            double min_distance_snake = Double.MAX_VALUE;

            for (Animal animal : nearbyAnimals) {
                if ((animal instanceof Snake) && animal.isAlive()) {
                    double distance = getPosition().getDistance(animal.getPosition());
                    if (distance < min_distance_snake) {
                        min_distance_snake = distance;
                        closestSnake = animal;
                    }
                }
            }
            if (closestSnake != null) {
                setFleeding(true);
                flee(closestSnake, board);
                logger.logEvent(this.getSpecies()+" is fleeding from "+closestSnake.getSpecies(),board.getSimulation().getCurrentTick());
            } else {
                setFleeding(false);
                if (!isHiding()) {
                    hunt(board);
                }
            }
        }
    }