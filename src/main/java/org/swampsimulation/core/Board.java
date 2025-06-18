package org.swampsimulation.core;

import org.swampsimulation.entities.Entity;
import org.swampsimulation.entities.animal.Animal;
import org.swampsimulation.entities.animal.Fly;
import org.swampsimulation.entities.Point;
import org.swampsimulation.entities.plants.Bushes;
import org.swampsimulation.entities.plants.Plants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Simulation board, stores all the entities
 * Is responsible for adding, deleting
 * and searching for all sorts of entities.
 * It's also responsible for adding Flies during the simulation
 */

public class Board {
    private ArrayList<Animal> animalsOnBoard;
    private ArrayList<Plants> plantsOnBoard;
    private ArrayList<Entity> entitiesOnBoard;

    private int width;
    private int height;
    private Simulation simulation;


    /**
     * Makes a board with given width and height
     * Initializes empty ArrayLists for animals, plants and entities
     * @param width - board x in pixels
     * @param height - board y in pixels
     */

    public Board(int width, int height) {
        this.animalsOnBoard = new ArrayList<>();
        this.width = width;
        this.height = height;
        this.plantsOnBoard = new ArrayList<>();
        this.entitiesOnBoard = new ArrayList<>();
    }

    /**
     * Retrives animal at a Point position
     * @param pos - position
     * @return Animal at a given point or null if none were found
     */


    public Animal getAnimalAt(Point pos) {
        for (Animal animal : animalsOnBoard) { //iterates for animals on board
            if (animal.getPosition().equals(pos)) {
                return animal;
            }
        }
        return null;
    }
    /**
     * Returns a list of all animals currently on the board.
     *
     * @return A new ArrayList containing all animals on the board.
     */

    public List<Animal> getAllAnimals() {
        return new ArrayList<>(animalsOnBoard);
    }
    /**
     * Returns a list of all plants currently on the board.
     *
     * @return A new ArrayList containing all plants on the board.
     */
    public List<Plants> getAllPlants() {
        return new ArrayList<>(plantsOnBoard);
    }
    /**
     * Returns a list of all entities currently on the board.
     *
     * @return A new ArrayList containing all entities on the board.
     */
    public List<Entity> getAllEntities() {
        return new ArrayList<>(entitiesOnBoard);
    }

    /**
     * Adds given animal to the board ArrayLists
     *
     * @param animal - animal to add
     */
    public void addAnimal(Animal animal) {
        if (animal != null) {
            this.animalsOnBoard.add(animal);
            this.entitiesOnBoard.add(animal);
        }
    }
    /**
     * Adds given plant to the board ArrayLists
     *
     * @param plant - Plant to add
     */
    public void addPlant(Plants plant) {
        if (plant != null) {
            this.plantsOnBoard.add(plant);
            this.entitiesOnBoard.add(plant);
        }
    }

    /**
     * Removes given animal from the board ArrayLists
     *
     * @param animal - animal to remove
     */
    public void removeAnimal(Animal animal) {
        this.animalsOnBoard.remove(animal);
        this.entitiesOnBoard.remove(animal);
    }
    /**
     * Finds and returns animals within given attackers range.
     * Only Alive animals are included when searching for targets
     *
     * @param attacker - searching animal
     * @param range - maximum attacker range
     * @return Return ArrayList of nearby animals
     */

    public List<Animal> getNearbyAnimals(Animal attacker, int range) {
        ArrayList<Animal> nearby = new ArrayList<>();
        for (Animal animal : animalsOnBoard) {
            double distance = animal.getPosition().getDistance(attacker.getPosition()); //iterates each animal
            if (distance <= range) { //compares distance to range
                if(animal.isAlive()) {
                    nearby.add(animal);
                }
            }
        }
        return nearby;
    }
    /**
     * Finds and returns plants within given hiders range.
     *
     * @param Hider - searching animal
     * @param range - maximum Hider range
     * @return Return ArrayList of nearby plants
     */
    public Plants getNearbyPlants(Animal Hider, int range) {
        double min_distance=Double.MAX_VALUE;
        Plants closest = null;
        for (Plants plant : plantsOnBoard) {
            double distance = plant.getPosition().getDistance(Hider.getPosition());
            if (distance <= range) {
                if(plant instanceof Bushes) {
                    if (distance < min_distance) {
                        min_distance = distance;
                        closest = plant;
                    }
                }
            }
        }
        return closest;
    }

    /**
     *Places 1 fly at a random location
     */

    public void placeFlies(){
        for(int x = 0; x < 1; x++) {
            Random rand = new Random();
            int X = rand.nextInt(getWidth() - 40);
            int Y = rand.nextInt(getHeight() - 40-100)+100;
            Point p = new Point(X, Y);
            int angle = rand.nextInt(360);
            Fly fly = new Fly(p,getSimulation().getLogger());
            fly.setRotation(angle);
            addAnimal(fly);
        }
    }
    /**
     *Sets the simulation parameter connected to the board
     * @param simulation  - simulation to be connected
     */


    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }
    /**
     *Returns the simulation parameter connected to the board
     * @return Returns the simulation it's connected with
     */
    
    public Simulation getSimulation() {
        return simulation;
    }

    /**
     *Returns the board width
     * @return Returns the board width (x) in pixels
     */

    public int getWidth() {
        return width;
    }
    /**
     *Returns the board Height
     * @return Returns the board Height (y) in pixels
     */
    public int getHeight() {
        return height;
    }
}