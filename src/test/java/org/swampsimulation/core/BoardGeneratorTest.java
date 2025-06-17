package org.swampsimulation.core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.swampsimulation.UI.SimulationConfig;
import org.swampsimulation.entities.animal.Animal;
import org.swampsimulation.entities.animal.frog.Frog;
import org.swampsimulation.map.SwampArea;

import static org.junit.jupiter.api.Assertions.*;


class BoardGeneratorTest {

    private Board board;
    private SimulationConfig config;

    @BeforeEach
    void setUp() {
        config = new SimulationConfig(800, 600, 100, 10);
        board = new Board(config.width(), config.height());
    }

    @Test
    void testNoFrogsGeneratedOnMud() {
        BoardGenerator generator = new BoardGenerator(config, null);
        generator.placeInitialAnimals(board);

        for (Animal animal : board.getAllAnimals()) {
            if (animal instanceof Frog) {
                assertFalse(SwampArea.isOnMud(animal.getPosition()), "No frog should be generated on mud.");
            }
        }
    }
    @Test
    void testFrogCountEqualsUserInput() {
        int expectedFrogCount = 15;
        SimulationConfig customConfig = new SimulationConfig(800, 600, 100, expectedFrogCount);
        BoardGenerator generator = new BoardGenerator(customConfig, new CsvLogger());
        generator.placeInitialAnimals(board);

        int actualFrogCount = 0;
        for (Animal animal : board.getAllAnimals()) {
            if (animal instanceof Frog) {
                actualFrogCount++;
            }
        }
        assertEquals(expectedFrogCount, actualFrogCount, "Number of frogs on board should match user input.");
    }
}