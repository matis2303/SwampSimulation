package org.swampsimulation.entities.plants;

import org.swampsimulation.entities.Point;


public class Bushes extends Plants {



    public Bushes(Point position) {
        super(position);
        String path = Random("bush1","bush2","bush3");
        path = "/plants/bush/"+path+".png";
        setImages(path);
        setSpriteSize(84,84);
    }
}
