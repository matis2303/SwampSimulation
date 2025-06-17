package org.swampsimulation.animal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.swampsimulation.entities.Point;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {

    @Test
    @DisplayName("testGetXandYValues")
    void testGetXandYValues() {
        int expectedX = 10;
        int expectedY = 20;

        Point p = new Point(expectedX, expectedY);

        assertEquals(expectedX, p.getX(), "X must be the same as set value");
        assertEquals(expectedY, p.getY(), "Y must be the same as set value");
    }

    @Test
    @DisplayName("testGetDistance")
    void testGetDistance() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(3, 4);

        double distance = p1.getDistance(p2);
        assertEquals(5.0, distance, 0.001, "Distance between (0,0) and (3,4) must be 5.0");
    }

    @Test
    @DisplayName("testGetDistanceToSamePoint")
    void testGetDistanceToSamePoint() {
        Point p1 = new Point(5, 5);

        assertEquals(0.0, p1.getDistance(p1), 0.001, "Distance between the same point must be 0.0");
    }
}