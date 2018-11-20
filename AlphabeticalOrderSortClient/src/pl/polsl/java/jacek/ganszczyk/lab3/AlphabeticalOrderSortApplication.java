package pl.polsl.java.jacek.ganszczyk.lab3;

import pl.polsl.java.jacek.ganszczyk.lab3.networkController.Controller;

import java.io.IOException;

/**
 * Entry Point of application.
 *
 * @author Jacek Ganszczyk
 * @version 1.0
 */
public class AlphabeticalOrderSortApplication {
    /*
      Main method, read file path from program parameters

     */

    /**
     * Main method, read file path from program parameters
     *
     * @param args the command line arguments
     */

    public static void main(String[] args) throws IOException {
        Props.setProperties();
        Props.loadProperties();

        Controller controller = new Controller();
        controller.mainControllerMethod(args);
    }


}

