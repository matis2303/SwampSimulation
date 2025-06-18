package org.swampsimulation.entities.animal;

import org.swampsimulation.core.Board;
import org.swampsimulation.core.CsvLogger;
import org.swampsimulation.entities.Point;

/**
 * The Fly class represents a fly
 * Flies are food for frogs and move randomly across the board.
 * They are automatically removed from the board if their health drops to zero.
 */

public class Fly extends Animal {

    /**
     * Constructs a new Fly object at the specific position.
     * Initializes the fly's sprite size, movement speed, sets it as moving,
     * takes random sprite image.
     * @param position The initial {@link Point} position of the fly on the board.
     * @param logger The {@link CsvLogger}  for logging events.
     */

    public Fly(Point position, CsvLogger logger) {
        super(position,AnimalSpecies.FLY,logger);
        setSpriteSize(22,22);
        String path = Random("fly1","fly2","fly3");
        path = "/animals/fly/"+path+".png";

        setMovementSpeed(30);
        setMoving(true);
        setImages(path);
    }

    /**
     * Updates the fly's state for the current tick.
     * Checks if the fly's health is zero or less, if so sets it as dead
     * removes dead fly from the board.
     * @param board The current state of the simulation board.
     */

    @Override
    public void update(Board board) {
        if(getHealthPoints()<=0){
            setAlive(false);
            board.removeAnimal(this);
        }
        move(board, null);
    }

    /**
     * Overrides the flee method for Fly, flies do not flee from danger.
     * @param danger The animal representing the threat (not used for flies).
     * @param board The current state of the simulation board (not used for flies).
     */

    @Override
    public void flee(Animal danger, Board board){
        // Flies do not flee. This method is intentionally empty.
    }
}