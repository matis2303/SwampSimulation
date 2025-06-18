package org.swampsimulation.UI;

import org.swampsimulation.core.CsvLogger;
import org.swampsimulation.core.Simulation;
import org.swampsimulation.core.Board;
import org.swampsimulation.core.BoardGenerator;

import javax.swing.*;
import java.awt.*;

/**
 * The SwampSimFrame is the main JFrame for displaying running simulation.
 * It integrates the simulation board, control panel, and the simulation engine.
 */

public class SwampSimFrame extends JFrame {
    private SimulationEngine engine;
    private ControlPanel controlPanel;
    private SimulationBoardPanel boardPanel;
    private Simulation coreSimulation;
    private int simulationEndTime;

    /**
     * Makes a new SwampSimFrame with the given simulation configuration.
     * Sets up the board, places initial entities, starts the simulation engine.
     * @param config The {@link SimulationConfig} containing parameters.
     */

    public SwampSimFrame(SimulationConfig config) {
        setTitle("Swamp Simulation - Running");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(config.width(), config.height());
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        Board gameBoard = new Board(config.width(), config.height());

        coreSimulation = new Simulation(gameBoard);
        CsvLogger sharedLogger = coreSimulation.getLogger();

        BoardGenerator boardGenerator = new BoardGenerator(config, sharedLogger);
        boardGenerator.placeInitialAnimals(gameBoard);
        boardGenerator.placePlants(gameBoard);
        boardGenerator.placeInitialFlies(gameBoard);

        System.out.println("SwampSimFrame: Board created and animals placed. Animals: " + gameBoard.getAllAnimals().size());

        this.simulationEndTime = config.simulationEndTime();

        coreSimulation.setupSimulation();
        gameBoard.setSimulation(coreSimulation);

        boardPanel = new SimulationBoardPanel(gameBoard);
        add(boardPanel, BorderLayout.CENTER);

        int defaultTps = 6;
        engine = new SimulationEngine(defaultTps, ticks -> {
            coreSimulation.gameLoop(ticks);
            if (this.controlPanel != null) {
                this.controlPanel.updateTickDisplay(ticks);
            }
            boardPanel.repaint();

            if (ticks >= simulationEndTime) {
                engine.stop();
                JOptionPane.showMessageDialog(this, "Simulation has ended at " + simulationEndTime + " ticks.", "The end of simulation", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        controlPanel = new ControlPanel(engine, defaultTps, simulationEndTime);
        add(controlPanel, BorderLayout.SOUTH);
    }
}