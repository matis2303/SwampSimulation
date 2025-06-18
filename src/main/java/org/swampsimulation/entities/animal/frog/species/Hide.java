package org.swampsimulation.entities.animal.frog.species;

/**
 * The Hide interface defines a hiding for animals that have the ability to hide
 * It currently provides a default, empty implementation of a hide method.
 */

public interface Hide {
    /**
     * default method for hiding
     */
    public default void hide() {
        // Default empty implementation, specific hiding logic will be in implementing classes.
    }
}
