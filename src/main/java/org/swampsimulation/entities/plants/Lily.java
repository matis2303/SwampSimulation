package org.swampsimulation.entities.plants;

import org.swampsimulation.entities.Point;

/**
 * The Lily class represents one type of plant,
 * only on mud area.
 * extends the abstract {@link Plants} class
 */

public class Lily extends Plants {

    /**
     * Constructs a new Lily object at the specific position.
     * Initializes the lily with a random sprite image and sets its default size.
     * @param position The initial {@link Point} position of the lily on the board.
     */

    public Lily(Point position) {
        super(position);
        // Randomly select one of the "lily" sprite images.
        String path = Random("lily1","lily2","lily3");
        path = "/plants/lily/"+path+".png";
        setImages(path);
        setSpriteSize(56,56);
    }

}
