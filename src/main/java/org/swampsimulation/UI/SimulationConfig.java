package org.swampsimulation.UI;

/**
 * A record of the configuration parameters for simulation.
 * @param width The width of the simulation board.
 * @param height The height of the simulation board.
 * @param simulationEndTime The total number of ticks the simulation should run.
 * @param frogsCount The initial number of frogs to place on the board.
 */

public record SimulationConfig(int width, int height, int simulationEndTime, int frogsCount) {
}

