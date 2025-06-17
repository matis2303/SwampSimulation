package org.swampsimulation.entities.animal.frog;

import org.swampsimulation.entities.animal.Animal;
import org.swampsimulation.entities.animal.AnimalSpecies;
import org.swampsimulation.entities.Point;
import org.swampsimulation.entities.animal.Snake;
import org.swampsimulation.core.Board;
import org.swampsimulation.core.CsvLogger;

import java.util.List;


public abstract class Frog extends Animal {
    private boolean isFleeding;
    private boolean isHiding;
    private int restImage;

    public Frog(Point position, AnimalSpecies species, CsvLogger logger,int restImage) {
        super(position,species,logger);
        this.isFleeding = false;
        this.isHiding = false;
        setMovementSpeed(0);
        this.restImage = restImage;
    }

    public boolean isFleeding() {
        return isFleeding;
    }

    public int getRestImage() {
        return restImage;
    }

    public void setRestImage(int restImage) {
        this.restImage=restImage;
    }

    public void setFleeding(boolean fleeding) {
        isFleeding = fleeding;
    }

    public boolean isHiding() {
        return isHiding;
    }

    public void setHiding(boolean hiding) {
        isHiding = hiding;
    }

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

    public abstract Animal hunt(Board board);

    public void eat(Animal target, Board board) {
        target.setHealthPoints(0);
        target.setAlive(false);
        logger.logEvent(this.getSpecies()+" ate "+target.getSpecies(),board.getSimulation().getCurrentTick());
        System.out.println(getSpecies() + " eats " +  target.getSpecies());
    }

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