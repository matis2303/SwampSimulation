package org.swampsimulation.entities.animal.frog.species;

import org.swampsimulation.entities.Point;
import org.swampsimulation.entities.animal.*;
import org.swampsimulation.entities.animal.frog.Frog;
import org.swampsimulation.core.Board;
import org.swampsimulation.core.CsvLogger;
import org.swampsimulation.entities.plants.Plants;
import org.swampsimulation.map.SwampArea;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;

public class TreeFrog extends Frog implements Hide{

    public TreeFrog(Point position, CsvLogger logger) {
        super(position, AnimalSpecies.TREE_FROG,logger,0);
        String path = Random("Tree1","Tree2","Tree3","Tree4");
        setImages("/animals/treeFrog/"+path+"/tree1.png");
        setImages("/animals/treeFrog/"+path+"/tree2.png");
        setImages("/animals/treeFrog/"+path+"/tree3.png");
        setMovementSpeed(35);
        setSpriteSize(70,70);
    }

    @Override
    public Animal hunt(Board board) {
        List<Animal> getNearby;
        double min_distance=Double.MAX_VALUE;
        Animal closest = null;
        getNearby=board.getNearbyAnimals(this,150);             //get animals in view distance
        for(Animal animal : getNearby) {
            if(animal instanceof Fly && !SwampArea.isOnMud(animal.getPosition())) {
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
            Plants plant=board.getNearbyPlants(this,170);
            if(plant!=null){
                move(board,plant.getPosition());
            }else move(board, null);
        }
        return null;
    }

        String Random(String a, String b, String c,String d) {
        Random r = new Random();
        switch (r.nextInt(4)) {
            case 0:
                return a;
            case 1:
                return b;
            case 2:
                return c;
            case 3:
                return d;
            default:
                return null;
        }
    }

    @Override
    public void update(Board board) {
        super.update(board);

        if(!isMoving()){
            return;
        }

        if (!isFleeding()) {
            List<Animal> nearbyAnimals = board.getNearbyAnimals(this, 70);
            Animal closestPredator = null;
            double min_distance_predator = Double.MAX_VALUE;

            for (Animal animal : nearbyAnimals) {
                if ((animal instanceof BufoBufoFrog || animal instanceof PacmanFrog || animal instanceof TomatoFrog) && animal.isAlive())
                {
                    double distance = getPosition().getDistance(animal.getPosition());
                    if (distance < min_distance_predator) {
                        min_distance_predator = distance;
                        closestPredator = animal;
                    }
                }
            }
            if (closestPredator != null && min_distance_predator <= 60) {
                setHiding(true);
                logger.logEvent(this.getSpecies()+" is hiding from "+closestPredator.getSpecies(),board.getSimulation().getCurrentTick());
            } else {
                setHiding(false);
            }
        }
    }

    @Override
    public BufferedImage getSprite() {
        if (getImages().isEmpty()) {
            return null;
        }

        if (isHiding()) {
            if (getImages().size() > 2) {
                return getImages().get(2);
            } else {
                return super.getSprite();
            }
        } else {
            return getImages().get(getSpriteInt());
        }
    }
}