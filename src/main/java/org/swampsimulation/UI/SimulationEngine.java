package org.swampsimulation.UI;

import javax.swing.Timer;
import java.util.function.Consumer;

public class SimulationEngine {
    private int ticks = 0;
    private int currentTps;
    private Timer timer;
    private Consumer<Integer> onTickCallback;

    public SimulationEngine(int initialTps, Consumer<Integer> onTickCallback) {
        if (initialTps <= 0) {
            throw new IllegalArgumentException("Initial TPS must be positive.");
        }
        this.currentTps = initialTps;
        this.onTickCallback = onTickCallback;

        this.timer = new Timer(1000 / this.currentTps, e -> {
            ticks++;
            if (this.onTickCallback != null) {
                this.onTickCallback.accept(ticks);
            }
        });
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    public void reset() {
        timer.stop();
        ticks = 0;
        if (onTickCallback != null) {
            onTickCallback.accept(ticks);
        }
    }

    public void setTps(int newTps) {
        if (newTps <= 0) {
            System.err.println("Attempted to set non-positive TPS: " + newTps);
            return;
        }
        this.currentTps = newTps;
        timer.setDelay(1000 / this.currentTps);
    }

    public int getCurrentTps() {
        return currentTps;
    }

    public boolean isRunning() {
        return timer.isRunning();
    }
}