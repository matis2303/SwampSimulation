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

public class Board {
    private ArrayList<Animal> animalsOnBoard;
    private ArrayList<Plants> plantsOnBoard;
    private ArrayList<Entity> entitiesOnBoard;

    private int width;
    private int height;
    private Simulation simulation;


    public Board(int width, int height) {
        this.animalsOnBoard = new ArrayList<>();
        this.width = width;
        this.height = height;
        this.plantsOnBoard = new ArrayList<>();
        this.entitiesOnBoard = new ArrayList<>();

    }

    public Animal getAnimalAt(Point pos) {
        for (Animal animal : animalsOnBoard) {
            if (animal.getPosition().equals(pos)) {
                return animal;
            }
        }
        return null;
    }

    public List<Animal> getAllAnimals() {
        return new ArrayList<>(animalsOnBoard);
    }
    public List<Plants> getAllPlants() {
        return new ArrayList<>(plantsOnBoard);
    }
    public List<Entity> getAllEntities() {
        return new ArrayList<>(entitiesOnBoard);
    }

    public void addAnimal(Animal animal) {
        if (animal != null) {
            this.animalsOnBoard.add(animal);
            this.entitiesOnBoard.add(animal);
        }
    }
    public void addPlant(Plants plant) {
        if (plant != null) {
            this.plantsOnBoard.add(plant);
            this.entitiesOnBoard.add(plant);
        }
    }


    public void removeAnimal(Animal animal) {
        this.animalsOnBoard.remove(animal);
        this.entitiesOnBoard.remove(animal);
    }

    public List<Animal> getNearbyAnimals(Animal attacker, int range) {
        ArrayList<Animal> nearby = new ArrayList<>();
        for (Animal animal : animalsOnBoard) {
            double distance = animal.getPosition().getDistance(attacker.getPosition());
            if (distance <= range) {
                if(animal.isAlive()) {
                    nearby.add(animal);
                }
            }
        }
        return nearby;
    }

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

    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }
    
    public Simulation getSimulation() {
        return simulation;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}