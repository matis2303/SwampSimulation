package org.swampsimulation.UI;

import javax.swing.*;

/**
 * The ControlPanel makes user interface panel
 * contains controls for the simulation - start, stop, reset button
 * and a slider to control the simulation speed (TPS).
 */

public class ControlPanel extends JPanel {
    /**
     * Current TPS
     */
    private JLabel tickLabel;
    /**
     * Current TPS Label
     */
    private JLabel tpsLabel;
    /**
     * Max ticks
     */
    private JLabel maxTicksLabel;
    /**
     * SimulationEngine is used for ticks
     */
    private SimulationEngine engine;

    /**
     * Makes a new ControlPanel.
     * @param engine The simulation engine that control panel will interact with.
     * @param initialTps The initial TPS for the simulation.
     * @param simulationEndTime The maximum number of ticks the simulation will run for.
     */

    public ControlPanel(SimulationEngine engine, int initialTps, int simulationEndTime) {
        this.engine = engine;

        JButton startButton = new JButton("Start");
        startButton.setFocusPainted(false);
        startButton.addActionListener(e -> this.engine.start());

        JButton stopButton = new JButton("Stop");
        stopButton.setFocusPainted(false);
        stopButton.addActionListener(e -> this.engine.stop());

        JButton resetButton = new JButton("Reset");
        resetButton.setFocusPainted(false);
        resetButton.addActionListener(e -> this.engine.reset());

        tickLabel = new JLabel("   Tiks: 0");
        tpsLabel = new JLabel("   TPS: " + initialTps);
        maxTicksLabel = new JLabel("   Max Tiks: " + simulationEndTime);


        JSlider tpsSlider = new JSlider(1, 21, initialTps);
        tpsSlider.setMajorTickSpacing(2);
        tpsSlider.setPaintTicks(true);
        tpsSlider.setPaintLabels(true);
        tpsSlider.addChangeListener(e -> {
            int newTps = tpsSlider.getValue();
            this.engine.setTps(newTps);
            this.tpsLabel.setText("   TPS: " + newTps);
        });

        add(startButton);
        add(stopButton);
        add(resetButton);
        add(tickLabel);
        add(tpsLabel);
        add(maxTicksLabel);
        add(new JLabel("   Speed:"));
        add(tpsSlider);
    }

    /**
     * Updates the displayed current tick on the control panel.
     * @param currentTicks The current tick number of the simulation.
     */

    public void updateTickDisplay(int currentTicks) {
        tickLabel.setText("Tiks: " + currentTicks);
    }
}