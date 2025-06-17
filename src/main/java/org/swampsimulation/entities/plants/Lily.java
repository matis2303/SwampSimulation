package org.swampsimulation.entities.plants;

import org.swampsimulation.entities.Point;

public class Lily extends Plants {


    public Lily(Point position) {
        super(position);
        String path = Random("lily1","lily2","lily3");
        path = "/plants/lily/"+path+".png";
        setImages(path);
        setSpriteSize(56,56);
    }

}
