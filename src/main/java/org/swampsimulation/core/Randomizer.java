package org.swampsimulation.core;

import java.util.Random;

/**
 * Randomizer interface used to randomly selecting Frog color.
 */

public interface Randomizer {
    /**
     * Randomizer interface used to randomly selecting Frog color.
     * @param a - first possible path line
     * @param b - second possible path line
     * @param c - third possible path line
     *
     * @return a, b or c - strings get randomly selected or null if something unexpected happens
     */
    default String Random(String a, String b, String c) {
        Random r = new Random();
        switch (r.nextInt(3)) {
            case 0:
                return a;
            case 1:
                return b;
            case 2:
                return c;
            default:
                return null;
        }
    }
}

