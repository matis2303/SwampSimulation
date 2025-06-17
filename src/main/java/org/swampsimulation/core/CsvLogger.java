package org.swampsimulation.core;

import org.swampsimulation.entities.animal.Animal;

import java.io.File;
import java.io.FileWriter;
import java.util.List;


public class CsvLogger {
    private String filePath;
    private String logFilePath;

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