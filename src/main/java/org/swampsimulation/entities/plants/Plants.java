package org.swampsimulation.entities.plants;

import org.swampsimulation.entities.Entity;
import org.swampsimulation.entities.Point;

/**
 * The abstract Plants class is the base for all plant entities
 * extends the {@link Entity} class
 * contains common properties like position and sprite management.
 */

public abstract class Plants extends Entity {

    /**
     * Makes a new Plants object at the specified position.
     * @param p The initial {@link Point} position of the plant on the board.
     */

    public Plants(Point p) {
        super(p);
    }
}
