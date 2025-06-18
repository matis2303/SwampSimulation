package org.swampsimulation.entities.plants;

import org.swampsimulation.entities.Point;

/**
 * Bushes represents a type of ground plant in the swamp simulation.
 * Bushes can be hiding spots for TreeFrogs.
 * extends the abstract {@link Plants} class.
 */

public class Bushes extends Plants {

    /**
     * Makes a new Bushes object at the specific position.
     * Initializes the bush with a random sprite image and sets its default size.
     * @param position The initial {@link Point} position of the bush on the board.
     */

    public Bushes(Point position) {
        super(position);
        String path = Random("bush1","bush2","bush3");
        path = "/plants/bush/"+path+".png";
        setImages(path);
        setSpriteSize(84,84);
    }
}
