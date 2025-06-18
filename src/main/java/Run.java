import org.swampsimulation.UI.MenuFrame;

/**
 * Main class to run the Swamp Simulation .
 * This class initializes and displays the main menu frame.
 */

public class Run {
    /**
     * Constructs Run
     */

    public Run() {
    }
    /**
     * The main method to start the simulation.
     * @param args Command line arguments (not used ).
     */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(MenuFrame::new);
    }
}