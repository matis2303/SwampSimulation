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
import java.util.Random;

/**
 * The PacmanFrog class represents a specific frog species in the simulation.
 * These frogs hunt flies and other frogs like {@link TomatoFrog}, {@link PacmanFrog} and {@link TreeFrog}.
 * They hunt the same species only if there is no other food in range and the target {@link PacmanFrog} are a same or small {@link FrogSize}
 */

public class PacmanFrog extends Frog {
    private FrogSize size;

    /**
     * Constructs a new PacmanFrog object.
     * @param position The initial {@link Point} position of the PacmanFrog.
     * @param logger The {@link CsvLogger} instance for recording events.
     */

    public PacmanFrog(Point position,CsvLogger logger) {
        super(position,AnimalSpecies.PACMAN, logger,1);

        String Size = Random("SMALL", "MEDIUM", "LARGE");
        String path = Random("G", "O", "R");
        if(Size.equals("SMALL")){
            this.size = FrogSize.SMALL;
            setSpriteSize(72,72);
            path = "/animals/miniPacman/" + path + "miniPacman/";

            setRestImage(2);

            setMovementSpeed(30);

        }else if(Size.equals("MEDIUM"))
        {
            this.size = FrogSize.MEDIUM;
            setSpriteSize(96,96);
            path = "/animals/pacman/" + path + "Pacman/";

            setMovementSpeed(36);

        }
        else if(Size.equals("LARGE")){
            this.size = FrogSize.LARGE;
            setSpriteSize(115,115);
            path = "/animals/pacman/" + path + "Pacman/";

            setMovementSpeed(40);
        }
        setImages(path + "Pacman1.png");
        setImages(path + "Pacman2.png");
        setImages(path + "Pacman3.png");
    }

    /**
     * Sets the {@link FrogSize}
     * @param size - the new FrogSize
     */


    public void setSize(FrogSize size) {
        this.size = size;
    }


    /**
     * Returns the {@link FrogSize}
     * @return Returns the FrogSize
     */

    public FrogSize getSize() {
        return size;
    }

    /**
     * Implements the hunting behavior specific to the PacmanFrog.
     * It searches for nearby {@link Fly} instances within its view distance
     * and eats the closest one if it's within striking range and not on mud.
     * If none were found does the same for other types of frogs and the frog of its own species
     * @param board The current state of the simulation board.
     */

    @Override
    public void hunt(Board board) {
        List<Animal> nearbyAnimals = board.getNearbyAnimals(this, 180);
        Animal target = null;
        double minDistance = Double.MAX_VALUE;
        for (Animal animal : nearbyAnimals) {
            if (animal instanceof Fly && animal.isAlive() && !SwampArea.isOnMud(animal.getPosition())) {
                double distance = getPosition().getDistance(animal.getPosition());
                if (distance < minDistance) {
                    minDistance = distance;
                    target = animal;
                }
            }
        }

        if (target == null) {
            for (Animal animal : nearbyAnimals) {
                if (animal instanceof PacmanFrog && animal.isAlive() && animal != this) {
                    PacmanFrog otherFrog = (PacmanFrog) animal;

                    if (this.getSize().ordinal() > otherFrog.getSize().ordinal()) {
                        double distance = getPosition().getDistance(animal.getPosition());
                        if (distance < minDistance) {
                            minDistance = distance;
                            target = animal;
                        }
                    } else if (this.getSize().ordinal() == otherFrog.getSize().ordinal()) {
                        Random rand = new Random();
                        if (rand.nextBoolean()) {
                            double distance = getPosition().getDistance(animal.getPosition());
                            if (distance < minDistance) {
                                minDistance = distance;
                                target = animal;
                            }
                        }
                    }
                }
            }
        }
        if (target == null && this.getSize() == FrogSize.LARGE) {
            for (Animal animal : nearbyAnimals) {
                if ((animal instanceof TreeFrog && ((TreeFrog) animal).isHiding()) || (animal instanceof TomatoFrog && ((TomatoFrog) animal).isHiding())) {
                    continue;
                }
                if ((animal instanceof TreeFrog || animal instanceof TomatoFrog) && animal.isAlive()) {
                    double distance = getPosition().getDistance(animal.getPosition());
                    if (distance < minDistance) {
                        minDistance = distance;
                        target = animal;
                    }
                }
            }
        }

        if (target != null && minDistance <= 20 && target.isAlive() && this.isAlive()) {
            eat(target,board);
            if(!(this.getSize() == FrogSize.SMALL)){
                setSpriteInt(2);
            }
        }
        if (target != null) {
            move(board, target.getPosition());
        } else {
            move(board, null);
        }
    }

    /**
     * Updates the PacmanFrog's state for the current tick.
     * checks for nearby predators
     * ({@link BufoBufoFrog} and initiates fleeing if detected.
     * If no immediate predator threat, it proceeds with hunting.
     * @param board The current state of the simulation board.
     */
    @Override
    public void update(Board board) {
        super.update(board);

        if(!isMoving()){
            return;
        }

        List<Animal> nearbyAnimals = board.getNearbyAnimals(this, 70);
        Animal closestPredator = null;
        double min_distance_predator = Double.MAX_VALUE;

        for (Animal animal : nearbyAnimals) {
            if ((animal instanceof BufoBufoFrog ) && animal.isAlive())
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



