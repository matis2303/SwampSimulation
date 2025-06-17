package org.swampsimulation.core;

import java.util.Random;

public interface Randomizer {
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

