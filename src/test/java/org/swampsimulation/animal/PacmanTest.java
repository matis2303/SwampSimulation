package org.swampsimulation.animal;

import org.junit.jupiter.api.Test;
import org.swampsimulation.entities.Point;
import org.swampsimulation.entities.animal.frog.species.PacmanFrog;
import org.swampsimulation.core.CsvLogger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PacmanTest {

    @Test
    void testPacmanFrogInitialPosition() {
        Point expectedPosition = new Point(75, 125);
        PacmanFrog pacmanFrog = new PacmanFrog(expectedPosition, new CsvLogger());

        assertEquals(expectedPosition, pacmanFrog.getPosition(), "Początkowa pozycja PacmanFroga powinna być zgodna z ustawioną.");
    }

    @Test
    void testPacmanFrogIsAliveInitially() {
        Point frogPos = new Point(10, 10);
        PacmanFrog pacmanFrog = new PacmanFrog(frogPos, new CsvLogger());

        assertTrue(pacmanFrog.isAlive(), "PacmanFrog powinien być żywy po utworzeniu.");
    }
}