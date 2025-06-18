package org.swampsimulation.core;

import org.swampsimulation.UI.SimulationConfig;
import org.swampsimulation.entities.animal.Fly;
import org.swampsimulation.entities.Point;
import org.swampsimulation.entities.animal.frog.species.BufoBufoFrog;
import org.swampsimulation.entities.animal.frog.species.PacmanFrog;
import org.swampsimulation.entities.animal.frog.species.TomatoFrog;
import org.swampsimulation.entities.animal.frog.species.TreeFrog;
import org.swampsimulation.entities.plants.Bushes;
import org.swampsimulation.entities.plants.Lily;
import org.swampsimulation.map.SwampArea;

import java.util.ArrayList;
import java.util.Random;

/**
 *The BoardGenerator is responsible for adding animals and plants onto the board
 * and simulation area.
 */

public class BoardGenerator {
    private SimulationConfig simConfig;
    private CsvLogger logger;

    Random rand = new Random();

    /**
     *Constructs BoardGenerator
     * @param simConfig - simulation config containing, like board width, Height and max frog count
     * @param logger - CsvLogger used when creating animals
     */
    public BoardGenerator(SimulationConfig simConfig, CsvLogger logger) {
        this.simConfig = simConfig;
        this.logger = logger;
    }
    /**
     *Places plants onto the board, with the max count calculated based on board dimensions
     * Ensures that Bushes and Lilies are placed at correct positions, Lilies on SwampArea and Bushes not.
     * @param board - board for the plants to be placed at
     */


    public void placePlants(Board board) {
        double Lilies = 0.5;
        double Bushes = 0.5;

        int maxNumber = (int) (board.getHeight() * board.getWidth() / 20000);

        int LiliesCount = (int) (Lilies * maxNumber);
        int BushesCount = (int) (Bushes * maxNumber);


        int sum = LiliesCount + BushesCount;
        sum = maxNumber - sum;
        if (sum > 0) {
            LiliesCount += sum;
        }
        ArrayList<Integer> plantsType = new ArrayList<>();
        for (int i = 0; i < BushesCount; i++) plantsType.add(0);
        for (int i = 0; i < LiliesCount; i++) plantsType.add(1);

        for (Integer frogType : plantsType) {
            int X, Y;
            Point p;

            int angle = rand.nextInt(360);
            switch (frogType) {
                case 0:
                    do {
                        X = rand.nextInt(board.getWidth());
                        Y = rand.nextInt(board.getHeight());
                        X = Math.max(0, Math.min(X, board.getWidth() - 80));
                        Y = Math.max(0, Math.min(Y, board.getHeight() - 80));
                        p = new Point(X, Y);
                    } while (SwampArea.isOnMud(p)); //bushes shouldn't be on mud
                    Bushes bushes = new Bushes(p);
                    bushes.setRotation(angle);
                    board.addPlant(bushes);
                    break;
                case 1:
                    do {
                        X = rand.nextInt(board.getWidth());
                        Y = rand.nextInt(board.getHeight());
                        X = Math.max(0, Math.min(X, board.getWidth() - 80));
                        Y = Math.max(0, Math.min(Y, board.getHeight() - 80));
                        p = new Point(X, Y);
                    } while (!SwampArea.isOnMud(p)); //lilies should be on mud
                    Lily lily = new Lily(p);
                    lily.setRotation(angle);
                    board.addPlant(lily);
                    break;
            }
        }

    }
    /**
     *Places animals onto the board, with the max count taken from config
     * Ensures that no frogs are placed on SwampArea.
     * Frogs are ratioed based on type.
     * @param board - board for the animals to be placed at
     */


    public void placeInitialAnimals(Board board) {
        double bufoRatio = 0.12;
        double pacmanRatio = 0.44;
        double tomatoRatio = 0.18;
        double treeRatio = 0.26;

        int totalFrogs = simConfig.frogsCount(); // do the ratio

        int numBufos = (int) (bufoRatio * totalFrogs);
        int numPacmans = (int) (pacmanRatio * totalFrogs);
        int numTomatos = (int) (tomatoRatio * totalFrogs);
        int numTrees = (int) (treeRatio * totalFrogs);

        int currentSum = numBufos + numPacmans + numTomatos + numTrees;
        int difference = totalFrogs - currentSum;

        if (difference > 0) {
            numPacmans += difference;   // if there are missing animals add Pacmans
        }

        ArrayList<Integer> frogTypesToPlace = new ArrayList<>();
        for (int i = 0; i < numBufos; i++) frogTypesToPlace.add(0);
        for (int i = 0; i < numPacmans; i++) frogTypesToPlace.add(1);
        for (int i = 0; i < numTomatos; i++) frogTypesToPlace.add(2);
        for (int i = 0; i < numTrees; i++) frogTypesToPlace.add(3);

        for (Integer frogType : frogTypesToPlace) {
            int X, Y;
            Point p;
            do {
                X = rand.nextInt(board.getWidth() - 40);
                Y = rand.nextInt(board.getHeight() - 40 - 100) + 100;
                p = new Point(X, Y);
            } while (SwampArea.isOnMud(p)); //frogs cannot spawn on mud

            int angle = rand.nextInt(360);

            switch (frogType) {
                case 0: //BufoBufo frog
                    BufoBufoFrog bufo = new BufoBufoFrog(p, this.logger);
                    bufo.setRotation(angle);
                    board.addAnimal(bufo);
                    break;
                case 1: // PacmanFrog
                    PacmanFrog pac = new PacmanFrog(p, this.logger);
                    pac.setRotation(angle);
                    board.addAnimal(pac);
                    break;
                case 2:// TomatoFrog
                    TomatoFrog tom = new TomatoFrog(p, this.logger);
                    tom.setRotation(angle);
                    board.addAnimal(tom);
                    break;
                case 3:// TreeFrog
                    TreeFrog tree = new TreeFrog(p, this.logger);
                    tree.setRotation(angle);
                    board.addAnimal(tree);
                    break;
            }
        }
        System.out.print(board.getAllAnimals().size()); //log total number of created animals
    }
    /**
     * Places a predefined initial number of Flies at the board
     *
     * @param board - board for the animals to be placed at
     */

    public void placeInitialFlies(Board board){
        for(int x = 0; x < 22; x++) {
            int X = rand.nextInt(board.getWidth() - 40);
            int Y = rand.nextInt(board.getHeight() - 40-100)+100; //added buffer to not spawn under UI panel
            Point p = new Point(X, Y);
            int angle = rand.nextInt(360);
            Fly fly = new Fly(p,logger);
            fly.setRotation(angle);
            board.addAnimal(fly);
        }
    }
}