package org.swampsimulation.entities.animal.frog.species;

import org.swampsimulation.entities.animal.Animal;
import org.swampsimulation.entities.animal.Fly;
import org.swampsimulation.entities.Point;
import org.swampsimulation.entities.animal.frog.Frog;
import org.swampsimulation.entities.animal.AnimalSpecies;
import org.swampsimulation.core.Board;
import org.swampsimulation.core.CsvLogger;

import java.util.List;

/**
 * The BufoBufoFrog class represents a specific frog species in the simulation.
 * These frogs hunt flies
 * BufoBufoFrogs are able to move onto mud areas.
 */

public class BufoBufoFrog extends Frog {

    /**
     * Constructs a new BufoBufoFrog object.
     * @param position The initial {@link Point} position of the BufoBufoFrog.
     * @param logger The {@link CsvLogger} instance for recording events.
     */

    public BufoBufoFrog(Point position,CsvLogger logger) {
        super(position,AnimalSpecies.BUFO_BUFO, logger,0);

        setImages("/animals/bufo/bufo1.png");
        setImages("/animals/bufo/bufo2.png");
        setImages("/animals/bufo/bufo3.png");
        setSpriteSize(120,120);

        setMovementSpeed(47);
    }

    /**
     * Implements the hunting behavior specific to the BufoBufoFrog.
     * searches for nearby {@link Fly} instances within its view distance
     * and eats the closest one if it's within striking range
     * BufoBufoFrogs can hunt flies even if they are on mud.
     * @param board The current state of the simulation board.
     * @return Always returns null, as the direct action is eating, not returning the hunted animal.
     */

    @Override
    public Animal hunt(Board board) {
        List<Animal> getNearby;
        double min_distance=Double.MAX_VALUE;
        Animal closest = null;
        getNearby=board.getNearbyAnimals(this,170);             //get animals in view distance
        for(Animal animal : getNearby) {
            if(animal instanceof Fly) {
                if(animal.isAlive()) {
                    double distance = getPosition().getDistance(animal.getPosition());
                    if (distance < min_distance) {
                        min_distance = distance;
                        closest = animal;
                    }
                    if (min_distance <= 28) {
                        eat(closest,board);
                    }
                }

            }
        }
        if(closest!=null) {
            move(board, closest.getPosition());
        }
        else{
            move(board, null);
        }
        return null;
    }
}