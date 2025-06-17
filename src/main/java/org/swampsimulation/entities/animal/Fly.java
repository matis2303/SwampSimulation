package org.swampsimulation.entities.animal;

import org.swampsimulation.core.Board;
import org.swampsimulation.core.CsvLogger;
import org.swampsimulation.entities.Point;


public class Fly extends Animal {

    public Fly(Point position, CsvLogger logger) {
        super(position,AnimalSpecies.FLY,logger);
        setSpriteSize(22,22);
        String path = Random("fly1","fly2","fly3");
        path = "/animals/fly/"+path+".png";

        setMovementSpeed(30);
        setMoving(true);
        setImages(path);

    }
    @Override
    public void update(Board board) {
        if(getHealthPoints()<=0){
            setAlive(false);
            board.removeAnimal(this);
        }
        move(board, null);
    }
    @Override
    public void flee(Animal danger, Board board){
    }
}