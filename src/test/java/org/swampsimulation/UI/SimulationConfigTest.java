package org.swampsimulation.UI;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SimulationConfigTest {

    @Test
    void testWidthConfig() {
        int expectedWidth = 1200;
        SimulationConfig config = new SimulationConfig(expectedWidth, 900, 200, 13);
        assertEquals(expectedWidth, config.width(), "Width should match the configured value");
    }

    @Test
    void testHeightConfig() {
        int expectedHeight = 900;
        SimulationConfig config = new SimulationConfig(1200, expectedHeight, 200, 13);
        assertEquals(expectedHeight, config.height(), "Height should match the configured value");
    }
    @Test
    void testSimulationEndTimeConfig() {
        int expectedSimulationEndTime = 200;
        SimulationConfig config = new SimulationConfig(800, 600, expectedSimulationEndTime, 13);
        assertEquals(expectedSimulationEndTime, config.simulationEndTime(), "EndTime should match the configured value");
    }

    @Test
    void testFrogCountConfig() {
        int expectedFrogCount = 13;
        SimulationConfig config = new SimulationConfig(800, 600, 200, expectedFrogCount);
        assertEquals(expectedFrogCount, config.frogsCount(), "Frog count should match the configured value");
    }
}