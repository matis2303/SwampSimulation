package org.swampsimulation.entities.animal.frog.species;

import org.swampsimulation.entities.animal.Animal;
import org.swampsimulation.entities.animal.Fly;
import org.swampsimulation.entities.Point;
import org.swampsimulation.entities.animal.frog.Frog;
import org.swampsimulation.entities.animal.AnimalSpecies;
import org.swampsimulation.core.Board;
import org.swampsimulation.core.CsvLogger;
import org.swampsimulation.map.SwampArea;

import java.util.List;

/**
 * The TomatoFrog class represents a specific frog species in the simulation.
 * These frogs hunt flies and also flee from larger predators
 * like {@link BufoBufoFrog} and {@link PacmanFrog}.
 */

public class TomatoFrog extends Frog {

    /**
     * Constructs a new TomatoFrog object.
     * @param position The initial {@link Point} position of the TomatoFrog.
     * @param logger The {@link CsvLogger} instance for recording events.
     */

    public TomatoFrog(Point position,CsvLogger logger) {
        super(position, AnimalSpecies.TOMATO_FROG, logger,2);

        setImages("/animals/tomato/tomato1.png");
        setImages("/animals/tomato/tomato2.png");
        setImages("/animals/tomato/tomato2.png");
        setSpriteSize(78,78);
        setMovementSpeed(37);
    }

    /**
     * Implements the hunting behavior specific to the TomatoFrog.
     * It searches for nearby {@link Fly} instances within its view distance
     * and eats the closest one if it's within striking range and not on mud.
     * @param board The current state of the simulation board.
     */

    @Override
    public void hunt(Board board) {
        List<Animal> getNearby;
        double min_distance=Double.MAX_VALUE;
        Animal closest = null;
        getNearby=board.getNearbyAnimals(this,150);             //get animals in view distance
        for(Animal animal : getNearby) {
            if(animal instanceof Fly  && !SwampArea.isOnMud(animal.getPosition())) {
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
    }

    /**
     * Updates the TomatoFrog's state for the current tick.
     * checks for nearby predators
     * ({@link BufoBufoFrog} or {@link PacmanFrog}) and initiates fleeing if detected.
     * If no immediate predator threat, it proceeds with hunting.
     * @param board The current state of the simulation board.
     */

    @Override
    public void update(Board board) {
        super.update(board);
        if(!isMoving()){
            return;
        }

        List<Animal> nearbyAnimals = board.getNearbyAnimals(this, 80);
        Animal closestPredator = null;
        double min_distance_predator = Double.MAX_VALUE;

        for (Animal animal : nearbyAnimals) {
            if ((animal instanceof BufoBufoFrog || animal instanceof PacmanFrog ) && animal.isAlive())
            {
                double distance = getPosition().getDistance(animal.getPosition());
                if (distance < min_distance_predator) {
                    min_distance_predator = distance;
                    closestPredator = animal;
                }
            }
        }
        if (closestPredator != null) {
            setFleeding(true);
            flee(closestPredator, board);
            logger.logEvent(this.getSpecies()+" is fleeding from "+closestPredator.getSpecies(),board.getSimulation().getCurrentTick());
        } else {
            setFleeding(false);
            hunt(board);
        }
    }
}