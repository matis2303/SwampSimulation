package org.swampsimulation.UI;

import javax.swing.*;

public class ControlPanel extends JPanel {
    private JLabel tickLabel;
    private JLabel tpsLabel;
    private JLabel maxTicksLabel;
    private SimulationEngine engine;

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

    public void updateTickDisplay(int currentTicks) {
        tickLabel.setText("Tiks: " + currentTicks);
    }
}