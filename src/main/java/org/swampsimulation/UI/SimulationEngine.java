package org.swampsimulation.UI;

import javax.swing.Timer;
import java.util.function.Consumer;

/**
 * The SimulationEngine manages the core ticks and progress of simulation.
 * Uses a Swing Timer to control the tps update
 */

public class SimulationEngine {
    private int ticks = 0;
    private int currentTps;
    private Timer timer;
    private Consumer<Integer> onTickCallback;

    /**
     * Makes a new SimulationEngine.
     * @param initialTps The initial ticks per second
     * @param onTickCallback Function that will be invoked with the current tick number on each update.
     * @throws IllegalArgumentException If the initial TPS is not positive.
     */

    public SimulationEngine(int initialTps, Consumer<Integer> onTickCallback) {
        if (initialTps <= 0) {
            throw new IllegalArgumentException("Initial TPS must be positive.");
        }
        this.currentTps = initialTps;
        this.onTickCallback = onTickCallback;
        // Calculate delay in milliseconds for the desired TPS
        this.timer = new Timer(1000 / this.currentTps, e -> {
            ticks++;
            if (this.onTickCallback != null) {
                this.onTickCallback.accept(ticks);
            }
        });
    }

    /**
     * Starts the simulation timer
     */

    public void start() {
        timer.start();
    }

    /**
     * Stops the simulation timer
     */

    public void stop() {
        timer.stop();
    }

    /**
     * Resets the simulation to its initial state, stops the timer and resets the tick count.
     */

    public void reset() {
        timer.stop();
        ticks = 0;
        if (onTickCallback != null) {
            onTickCallback.accept(ticks);
        }
    }

    /**
     * Sets a new TPS for the simulation.
     * If the new TPS is not positive, an error message is printed
     * @param newTps New ticks per second value.
     */

    public void setTps(int newTps) {
        if (newTps <= 0) {
            System.err.println("Attempted to set non-positive TPS: " + newTps);
            return;
        }
        this.currentTps = newTps;
        timer.setDelay(1000 / this.currentTps);
    }

    /**
     * Returns the current tps setting of the simulation.
     * @return The current tps.
     */

    public int getCurrentTps() {
        return currentTps;
    }

    /**
     * Checks if the simulation timer is running.
     * @return true if the timer is running, false if not.
     */

    public boolean isRunning() {
        return timer.isRunning();
    }
}