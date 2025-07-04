package org.swampsimulation.core;

import org.swampsimulation.entities.animal.Animal;
import org.swampsimulation.entities.animal.Fly;
import org.swampsimulation.entities.Point;
import org.swampsimulation.entities.animal.Snake;

import java.util.ArrayList;
import java.util.Random;
/**
 * Simulation class is the main simulation class, it makes a simulation, runs the gameLoop.
 * It's also used for fly generation and snake appearance
 */

public class Simulation {
    private Board board;
    private CsvLogger logger;
    private boolean isSnakeActive;
    private int snakeTimer;
    private Animal snake;
    private int SnakeCooldown;
    private int currentTick;


    /**
     * Constructs Simulation
     * @param board - the board where the simulation takes place
     */

    public Simulation(Board board) {
        this.board = board;
        this.logger = new CsvLogger();
        this.isSnakeActive = false;
        this.snakeTimer = 0;
        this.snake = null;
        Random rand = new Random();
        int r = rand.nextInt(0,50);
        this.SnakeCooldown = r;
        this.currentTick = 0;
    }

    /**
     * Setup ups a Simulation
     * Used to check if animals are on board
     */

    public void setupSimulation() {



        if (this.board != null && this.board.getAllAnimals() != null) {
            System.out.println("Simulation setup. Board received. Initial animals: " + this.board.getAllAnimals().size());
        } else {
            System.out.println("Simulation setup. Board is null or has no animal list.");
        }
    }
    /**
     * Runs current gameLoop, represents one tick of the simulation
     * Updates all the animals
     * Counts number of flies and if some are missing adds required number
     * It's also used to log current game tick number of Animals
     *
     * @param currentTick - current tick of the simulation
     */

    public void gameLoop(int currentTick) {
        int Flies=0;
        setCurrentTick(currentTick);
        if(getSnake()!=null) {      //Check if the snake is active and Alive
            if(getSnake().isAlive()) {
                setSnakeActive(true);
                logger.logEvent("Snake is active",currentTick);
            }
            else if(!getSnake().isAlive()) {
                setSnakeActive(false);
                snakeTimer = 0;
                setSnake(null);
            }
        }
        snakeTimer++;
        if(snakeTimer >= SnakeCooldown && !isSnakeActive) {
            setSnakeActive(true);
            Snake snake = new Snake(setSnakePosition().get(0),setSnakePosition().get(1),logger);
            board.addAnimal(snake);
            setSnake(snake);
        }
        for(Animal animal : board.getAllAnimals()) {
            animal.update(board);
            if(animal instanceof Fly) Flies++;

        }
            //check if correct number of flies are on board
        if(Flies<20) {
            board.placeFlies();
        }



        if (board != null && board.getAllAnimals() != null) {
            logger.logState(board.getAllAnimals(), currentTick);
        }
    }

    /**
     * Determines a random position for snake spawn and target point
     * The snake can spawn in any of the sides of the board
     * Buffer makes sure the snake appears and disappears outside the board visible area
     *
     * @return Returns Points ArrayList containing spawn Point and target Point
     */
    public ArrayList<Point> setSnakePosition() {
        int X = 0;
        int Y = 0;
        int targetX = 0;
        int targetY = 0;
        ArrayList<Point> points = new ArrayList<>();
        Random rand = new Random();
        int buffer = 600;


        int side = rand.nextInt(4);

        if (side == 0) {
            X = rand.nextInt(board.getWidth());
            Y = -buffer;

            targetX = rand.nextInt(board.getWidth());
            targetY = board.getHeight() + buffer;
        } else if (side == 1) {
            X = rand.nextInt(board.getWidth());
            Y = board.getHeight() + buffer;


            targetX = rand.nextInt(board.getWidth());
            targetY = -buffer;
        } else if (side == 2) {
            X = -buffer;
            Y = rand.nextInt(board.getHeight());


            targetX = board.getWidth() + buffer;
            targetY = rand.nextInt(board.getHeight());
        } else {
            X = board.getWidth() + buffer;
            Y = rand.nextInt(board.getHeight());

            targetX = -buffer;
            targetY = rand.nextInt(board.getHeight());
        }
        Point startPoint = new Point(X, Y);
        Point targetPoint = new Point(targetX, targetY);
        points.add(startPoint);
        points.add(targetPoint);
        return points;
    }

    /**
     * Returns current board
     * @return Board object
     */

    public Board getBoard() {
        return board;
    }

    /**
     * Returns current snake status
     * @return true if snake active, false if not
     */
    public boolean isSnakeActive() {
        return isSnakeActive;

    }
    /**
     * Sets the snake status
     * @param snakeActive - new snake status
     */
    public void setSnakeActive(boolean snakeActive) {

        isSnakeActive = snakeActive;
    }
    /**
     * Returns current snake
     * @return returns active snake on board
     */
    public Animal getSnake() {
        return snake;
    }
    /**
     * Sets current snake
     * @param snake - current snake to set
     */
    public void setSnake(Animal snake) {
        this.snake = snake;
    }
    /**
     * Returns simulation logger
     * @return Returns CsvLogger object
     */
    public CsvLogger getLogger() {
        return logger;
    }

    /**
     * Returns current tick
     * @param snakeCooldown - new snakeCooldown int
     */

    public void setSnakeCooldown(int snakeCooldown) {
        SnakeCooldown = snakeCooldown;
    }
    /**
     * Returns snake cooldown
     * @return Current snake cooldown.
     */
    public int getSnakeCooldown() {
        return SnakeCooldown;
    }
    /**
     * Returns current tick
     * @return currentTick integer
     */
    public int getCurrentTick() {
        return currentTick;
    }
    /**
     * Sets current tick
     * @param currentTick - the new value of current tick
     */
    public void setCurrentTick(int currentTick) {
        this.currentTick = currentTick;
    }
}