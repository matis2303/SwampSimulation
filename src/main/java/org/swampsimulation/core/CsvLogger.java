package org.swampsimulation.core;

import org.swampsimulation.entities.animal.Animal;

import java.io.File;
import java.io.FileWriter;
import java.util.List;
/**
 * CsvLogger is used to log events that happen on the board
 * at predefined Paths
 */

public class CsvLogger {
    private String filePath;
    private String logFilePath;

    /**
     * Constructs CsvLogger
     * Ensures that files are freshly made and previous files get overwritten
     */

    public CsvLogger() {
        this.filePath = "simulation_out.csv";
        this.logFilePath = "simulation_events.csv";

        try {
            File file = new File(this.filePath);
            file.delete();
            file.createNewFile();

            File logFile = new File(this.logFilePath);
            logFile.delete();
            logFile.createNewFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Logs animals count and current simulation tick
     * @param animals - Animals on board list, used to get number of animals
     * @param currentTick - current tick to mark the time of the event
     */

    public void logState(List<Animal> animals, int currentTick) {
        try {
            FileWriter file = new FileWriter(filePath, true);
            String write = "Number of animals: " + animals.size() + " | Simulation time: " + currentTick;
            file.write(write);
            file.write("\n");
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Logs animals events
     * @param eventDescription - Event description
     * @param currentTick - current tick to mark the time of the event
     */

    public void logEvent(String eventDescription, int currentTick) {
        try {
            FileWriter file = new FileWriter(logFilePath, true);
            String fullEvent = eventDescription + " | " + currentTick;
            file.write(fullEvent);
            file.write("\n");
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}